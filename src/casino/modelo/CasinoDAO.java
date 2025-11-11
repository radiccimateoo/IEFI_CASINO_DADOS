/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino.modelo;

//improtaciones necesarias
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class CasinoDAO {

    public void crearTablasSiNoExisten() {
        String sqlJugadores = "CREATE TABLE IF NOT EXISTS jugadores ("
                            + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " nombre TEXT NOT NULL UNIQUE,"
                            + " apodo TEXT,"
                            + " tipo TEXT,"
                            + " dinero INTEGER,"
                            + " victorias INTEGER"
                            + ");";

        String sqlPartidas = "CREATE TABLE IF NOT EXISTS partidas ("
                           + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                           + " fecha TEXT,"
                           + " ganador_id INTEGER,"
                           + " rondas INTEGER,"
                           + " pozo INTEGER,"
                           + " FOREIGN KEY (ganador_id) REFERENCES jugadores(id)"
                           + ");";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sqlJugadores);
            stmt.execute(sqlPartidas);
            
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    public void guardarOActualizarJugador(Jugador jugador) {
        String sql = "INSERT OR REPLACE INTO jugadores (nombre, apodo, tipo, dinero, victorias) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, jugador.getNombre());
            pstmt.setString(2, jugador.getApodo());
            pstmt.setString(3, jugador.obtenerTipoJugador());
            pstmt.setInt(4, jugador.getDinero());
            pstmt.setInt(5, jugador.getPartidasGanadas());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar el jugador " + jugador.getNombre() + ": " + e.getMessage());
        }
    }
    
    // ---- MÉTODO FALTANTE PARA GUARDAR LA PARTIDA (NO EXISTIA ANTES, NO LO PUSE) ----
    public void guardarPartida(Jugador ganador, int rondas, int pozo) {
        String sqlBusqueda = "SELECT id FROM jugadores WHERE nombre = ?";
        String sqlInsert = "INSERT INTO partidas (fecha, ganador_id, rondas, pozo) VALUES (datetime('now', 'localtime'), ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection()) {

            // 1. Necesitamos el ID del ganador (por la FOREIGN KEY)
            int ganadorId = -1;
            try (PreparedStatement pstmtBusqueda = conn.prepareStatement(sqlBusqueda)) {
                pstmtBusqueda.setString(1, ganador.getNombre());
                ResultSet rs = pstmtBusqueda.executeQuery();
                if (rs.next()) {
                    ganadorId = rs.getInt("id");
                }
            }

            // Si no encontramos al ganador, no podemos guardar la partida
            if (ganadorId == -1) {
                System.err.println("Error: No se pudo guardar la partida. ID del ganador no encontrado.");
                return;
            }

            // 2. Ahora sí, insertamos la partida
            try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                pstmtInsert.setInt(1, ganadorId);
                pstmtInsert.setInt(2, rondas);
                pstmtInsert.setInt(3, pozo);
                pstmtInsert.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    public List<String> obtenerRankingJugadores() {
        List<String> ranking = new ArrayList<>();
        String sql = "SELECT nombre, tipo, dinero, victorias FROM jugadores ORDER BY dinero DESC;";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            int pos = 1;
            while (rs.next()) {
                String linea = String.format("%d. %s (%s) - Dinero: $%d - Victorias: %d",
                        pos,
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getInt("dinero"),
                        rs.getInt("victorias"));
                ranking.add(linea);
                pos++;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener el ranking: " + e.getMessage());
        }
        return ranking;
    }
}
