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

    // CONSTRUCTOR REFRACTORIZADO: Ahora recibe el Casino
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
}
