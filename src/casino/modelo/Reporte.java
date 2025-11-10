/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino.modelo;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Ceciia CONSIGNA 4
 */
public class Reporte {
    
    // Nuevo atributo: la referencia al objeto Casino
    private final Casino casino; 
    private List<Jugador> jugadoresFinales;

    // CONSTRUCTOR 
    public Reporte(Casino casino) { 
        this.casino = casino;
        // Obtenemos la lista de jugadores del Casino
        this.jugadoresFinales = new ArrayList<>(casino.getJugadores()); 
    }

    /**
     * Genera la lista de jugadores ordenada por ranking.
     * Criterio: 1. Mayor partidas ganadas (desc), 2. Mayor dinero (desc).
     * @return Lista de Jugadores ordenados por ranking.
     */
    public List<Jugador> getRankingJugadores() {
        
        // El Reporte tiene la responsabilidad de ordenar la lista.
        Collections.sort(this.jugadoresFinales, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador j1, Jugador j2) {
                // 1. Criterio: Partidas Ganadas (Mayor a Menor)
                int comparacionVictorias = Integer.compare(j2.getPartidasGanadas(), j1.getPartidasGanadas());
                if (comparacionVictorias != 0) {
                    return comparacionVictorias;
                }
                // 2. Criterio: Dinero (Mayor a Menor, desempate)
                return Integer.compare(j2.getDinero(), j1.getDinero());
            }
        });
        
        return this.jugadoresFinales;
    }
   
    // === MÉTODOS PARA ESTADÍSTICAS  ===
    
    // lblMayorApuesta: Mayor apuesta realizada (monto + jugador)
    public String getMayorApuestaInfo() {
        int monto = casino.getMayorApuesta();
        String nombre = casino.getNombreJugadorMayorApuesta();
        // El modelo devuelve la información formateada como String
        return String.format("$%d (%s)", monto, nombre);
    }

    // lblMejorPuntaje: Mejor puntaje de dados (valor + jugador)
    public String getMejorPuntajeInfo() {
        int puntaje = casino.getMejorPuntajeDados();
        String nombre = casino.getNombreJugadorMejorPuntaje();
        // El modelo devuelve la información formateada como String
        return String.format("%d (%s)", puntaje, nombre);
    }
    
    // lblJugadoresAfectados: Jugadores afectados por trampas (total)
    public String getJugadoresAfectadosInfo() {
        // Asumimos que Casino tiene el método getCantidadJugadoresAfectados()
        int afectados = casino.getCantidadJugadoresAfectados(); 
        // El modelo devuelve la información formateada como String
        return String.format("%d Jugadores", afectados);
    }
    
    // === NUEVO MÉTODO: HISTORIAL ===
    
    /**
     * Obtiene el historial de las últimas 3 partidas jugadas, leyendo desde el archivo.
     * @return Una cadena de texto con las partidas listadas, separadas por salto de línea.
     */
    public String getHistorialUltimasTresPartidas() {
        // Usa el nuevo método de Casino para leer el archivo.
        List<String> historialCompleto = casino.leerHistorialCompleto(); 
        
        if (historialCompleto.isEmpty()) {
            return "Aún no hay partidas registradas.";
        }
        
        int total = historialCompleto.size();
        // Calcula el índice de inicio: el mayor entre 0 y el total menos 3.
        int startIndex = Math.max(0, total - 3); 
        
        // Obtiene la sublista de las últimas 3 partidas
        List<String> ultimasTres = historialCompleto.subList(startIndex, total);
        
        // Concatena las partidas en un solo String, separadas por salto de línea.
        StringBuilder sb = new StringBuilder();
        for (String partida : ultimasTres) {
            sb.append(partida).append("\n");
        }
        
        return sb.toString().trim(); // Retorna el historial formateado
    }
}
