package casino.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
