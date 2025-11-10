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
    
    private List<Jugador> jugadoresFinales;

    // El Reporte necesita la lista de jugadores para procesar los datos
    public Reporte(List<Jugador> jugadores) {
        // Se recomienda pasar una copia para no modificar accidentalmente la lista del Casino
        this.jugadoresFinales = new ArrayList<>(jugadores); 
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
   
}
