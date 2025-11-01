package casino.modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JuegoDados {   
    private Dado dado;
    private Casino casino;
    private boolean juegoTerminado; //  bandera para controlar si alguien quedó en 0
    
    // CONSIGNA 4: Se agrega el parametro del objeto casino, para utilizar su metodo
    //actualizarEstgadistica
    public JuegoDados(Casino casino) {
        this.dado = new Dado();
        this.casino = casino;
        this.juegoTerminado = false;
    }

    public List<Jugador> jugarRonda() {
        
        if (juegoTerminado) {
            System.out.println("\n️ El juego ya ha finalizado. No se puede jugar más rondas.");
            return null;
        }        
        
        ArrayList<Jugador> jugadores = casino.getJugadores();
        
        int pozo = 0;
        HashMap<Jugador, Integer> resultados = new HashMap<>();

        // Consigna 3: Habilidad Confusión - elección del jugador confundido
        Jugador jugadorConfundido = null;

        // Obtener la instancia del JugadorCasino (asumiendo que hay uno)
        JugadorCasino jugadorCasino = null;
        for (Jugador j : jugadores) {
            if (j instanceof JugadorCasino) {
                jugadorCasino = (JugadorCasino) j;
                break;
            }
        }

        // Ejecutar la habilidad de confundir del casino si existe
        if (jugadorCasino != null) {
            jugadorConfundido = jugadorCasino.seleccionarJugadorAConfundir(jugadores);            
            if (jugadorConfundido != null) {
                System.out.println("¡El Casino confunde a " + jugadorConfundido.getNombreConTipo() + "!");
                casino.registrarVictima(jugadorConfundido); // CONSIGNA 4: Registrar victima
            }
        }
        // Fin elección

        System.out.println("\nApuestas y lanzamientos:");       
        
        for (Jugador jugador : jugadores) {
            int apuesta = jugador.calcularApuesta();
            if (apuesta > jugador.getDinero()) apuesta = jugador.getDinero();
            jugador.perder(apuesta);
            pozo += apuesta;

            int tiro1, tiro2, suma;

            // Consigna 3: Se incorpora el tiro de dados cargados del jugadorCasino
            if (jugador instanceof JugadorCasino) {
                  // El casino usa su método de dados cargados
                  tiro1 = ((JugadorCasino) jugador).tirarDadoCargado();
                  tiro2 = ((JugadorCasino) jugador).tirarDadoCargado();
                  casino.registrarUsoDadosCargados(); 
            } else {
                  // Jugadores normales tiran dados comunes
                  tiro1 = dado.tirar();
                  tiro2 = dado.tirar();
            }

            // Aplica la penalización si el jugador actual es el confundido
            if (jugador.equals(jugadorConfundido)) {
                  tiro1 = Math.max(1, tiro1 - 1);
                  tiro2 = Math.max(1, tiro2 - 1);
                  System.out.println(" -> ¡El efecto de la confusión reduce el puntaje de " + jugador.getNombreConTipo() + "!");
            }
            suma = tiro1 + tiro2;

            System.out.println(jugador.getNombreConTipo() + " apostó $" + apuesta + " y sacó " + tiro1 + " + " + tiro2 + " = " + suma);

            // Lógica del VIP si corresponde
            if (jugador instanceof JugadorVIP vip) { // Usando pattern matching para más limpieza
                if (vip.puedeRepetir() && suma < 8) {
                    System.out.println("→ " + vip.getNombreConTipo() + " decide usar su re-roll...");
                    tiro1 = dado.tirar();
                    tiro2 = dado.tirar();
                    suma = tiro1 + tiro2; 
                    System.out.println("  Nuevo tiro: " + tiro1 + " + " + tiro2 + " = " + suma);
                    vip.usarRepeticion();
                }
            }

            // CONSIGNA 4: ¡LUGAR CORRECTO PARA ACTUALIZAR ESTADÍSTICAS!
            // Se hace para CADA jugador, dentro del bucle.
            casino.actualizarEstadisticas(apuesta, suma, jugador);

            resultados.put(jugador, suma);
        } // <-- FIN DEL BUCLE for

        // Determinar puntaje más alto
        int maxPuntaje = resultados.values().stream().max(Integer::compare).orElse(0);
        ArrayList<Jugador> ganadores = new ArrayList<>();
        for (Jugador j : resultados.keySet()) {
            if (resultados.get(j) == maxPuntaje) ganadores.add(j);
        }

        // Repartir pozo
        if (ganadores.isEmpty()) {
            System.out.println("\nNo hay ganadores en esta ronda. El pozo se pierde.");
        } else {
            int premioPorJugador = pozo / ganadores.size();
            System.out.println("\nGanador(es) de la ronda:");
            for (Jugador ganador : ganadores) {
                ganador.ganar(premioPorJugador);
                System.out.println("-> " + ganador.getNombreConTipo() + " gana $" + premioPorJugador);
            }
        }

        // Mostrar estado de dinero de cada jugador al final de la ronda
        System.out.println("\nEstado de dinero de los jugadores tras la ronda:");
        for (Jugador j : jugadores) {
            System.out.println(j.getNombreConTipo() + ": $" + j.getDinero());
            
            if (j.getDinero() == 0) {
                juegoTerminado = true;
                System.out.println("\n️ El jugador " + j.getNombreConTipo() + " quedó sin dinero. El juego finaliza.");
                break;
            }
        }

        // Resetear re-roll VIP
        for (Jugador j : jugadores) if (j instanceof JugadorVIP) ((JugadorVIP) j).resetearRepeticion();
        
        if (juegoTerminado) {
            return null; //  el juego ya no sigue
        }



        
        return ganadores; // Devuelve todos los ganadores de la ronda
    }
    
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }


    
}
