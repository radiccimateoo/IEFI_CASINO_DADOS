package casino.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

public class Casino {
    
    private ArrayList<Jugador> jugadores;
    // CONSIGNA 4: Se añade atributos globales para el reporte
    private int mayorApuesta = 0;
    private String nombreJugadorMayorApuesta = "Sin registro";
    private int mejorPuntajeDados = 0;
    private String nombreJugadorMejorPuntaje = "Sin registro";
    private int conteoDadosCargados = 0;
    private HashMap<String, Integer> victimasDeTrampas = new HashMap<>();
    public int cantPartidasTotal = 0;
    
    //--------------------------------------------------------
    public Casino() {
        jugadores = new ArrayList<>();
    }
    
    // CONSIGNA 4: Se añade getters para el reporte
    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public int getMayorApuesta() { return mayorApuesta; }
    public String getNombreJugadorMayorApuesta() { return nombreJugadorMayorApuesta; }
    public int getMejorPuntajeDados() { return mejorPuntajeDados; }
    public String getNombreJugadorMejorPuntaje() { return nombreJugadorMejorPuntaje; }
    public HashMap<String, Integer> getVictimasDeTrampas() { return victimasDeTrampas; }

    // -------------------------------------------------------------------------------
    // CONSIGNA 4: Metodo para actualizar estadisticas
    public void actualizarEstadisticas(int apuesta, int puntajeDados, Jugador jugador) {
        if (apuesta > this.mayorApuesta) {
            this.mayorApuesta = apuesta;
            this.nombreJugadorMayorApuesta = jugador.getNombreConTipo();
        }
        if (puntajeDados > this.mejorPuntajeDados) {
            this.mejorPuntajeDados = puntajeDados;
            this.nombreJugadorMejorPuntaje = jugador.getNombreConTipo();
        }
    }   
    // CONSIGNA 4: Metodo para registrar trampeados
    public void registrarVictima(Jugador victima) {
    String nombreVictima = victima.getNombre();
    // getOrDefault busca el contador actual o devuelve 0 si es la primera vez
    int conteoActual = victimasDeTrampas.getOrDefault(nombreVictima, 0);
    victimasDeTrampas.put(nombreVictima, conteoActual + 1);
}
    // -------------------------------------------------------------------------------  
    public Jugador crearJugador(String nombre, String apodo, int tipo) {
        int dineroInicial = 500; // Todos empiezan con $500
        switch (tipo) {
            case 1 -> {
                return new JugadorNovato(nombre, apodo, dineroInicial);
            }
            case 2 -> {
                return new JugadorExperto(nombre, apodo, dineroInicial);
            }
            case 3 -> {
                return new JugadorVIP(nombre, apodo, dineroInicial);
            }
            case 4 -> {
                return new JugadorCasino(nombre, dineroInicial); //Consigna 3
            }
            default -> {
                System.out.println("Tipo inválido, se asignará como Novato.");
                return new JugadorNovato(nombre, apodo, dineroInicial);
            }
        }
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }
    
    public void registrarUsoDadosCargados() {
    conteoDadosCargados++;
    }
    
    public int getConteoDadosCargados() {
    return conteoDadosCargados;
    }
    
    public void eliminarJugador(String apodo, javax.swing.JFrame ventana) {
        Jugador aEliminar = null;
        for (Jugador j : jugadores) {
            if (j.getApodo().equalsIgnoreCase(apodo)) {
                aEliminar = j;
                break;
            }
        }
        if (aEliminar != null) {
            jugadores.remove(aEliminar);
            JOptionPane.showMessageDialog(ventana, 
                "Jugador eliminado: " + aEliminar.getNombreConTipo(),
                "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(ventana, 
                "No se encontró jugador con apodo: " + apodo,
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void guardarHistorial(List<String> historial) {
        // Guardar en archivo, base de datos o imprimir en consola
        historial.forEach(System.out::println);
    }

    
    /* por x cantidad de partidas siempre se juegan 3 rondas
       ahora devuelve una lista tipo string del detalle de cada ganador
       ademas de mostrarlo por consola, para este metodo ser tratado en la clase
       main, para guardar la partida completa
    */
    public List<String> jugar(int cantPartidas) {
        List<String> detalles = new ArrayList<>();

        for (int i = 1; i <= cantPartidas; i++) {
            System.out.println("\n=== Partida " + i + " ===");

            // Inicializamos contador de rondas ganadas por jugador para esta partida
            HashMap<Jugador, Integer> rondasGanadas = new HashMap<>();
            for (Jugador j : jugadores) {
                rondasGanadas.put(j, 0);
            }

            // 3 rondas fijas
            JuegoDados juego = new JuegoDados(jugadores, this); 
            // CONSIGNA 4: a la clase JuegoDados, se le pasa por parametro el metodo del casino
            for (int r = 1; r <= 3; r++) {
                if (!juego.isJuegoTerminado()) {
                    System.out.println("\n---- Ronda " + r);
                    List<Jugador> ganadoresRonda = juego.jugarRonda();
                    
                    if (ganadoresRonda == null) {
                        System.out.println("️ Juego finalizado anticipadamente en la ronda " + r + " de la partida " + i);
                        cantPartidasTotal = i;
                        return detalles; // corta el método y devuelve lo jugado hasta acá
                    }
                    for (Jugador g : ganadoresRonda) {
                        rondasGanadas.put(g, rondasGanadas.get(g) + 1);
                    }
                }
            }

            // Determinar ganador de la partida según rondas ganadas
            Jugador ganadorPartida = jugadores.get(0);
            int maxRondas = rondasGanadas.get(ganadorPartida);
            for (Jugador j : jugadores) {
                if (rondasGanadas.get(j) > maxRondas) {
                    ganadorPartida = j;
                    maxRondas = rondasGanadas.get(j);
                }
            }
            // CONSIGNA 4: Agrega el contador de victorias para el registro
            ganadorPartida.sumarVictoria();
            
            

            // Construir detalle
            StringBuilder detalle = new StringBuilder();
            detalle.append("PARTIDA #").append(i).append(" - Jugadores: ");
            for (int j = 0; j < jugadores.size(); j++) {
                detalle.append(jugadores.get(j).getNombre());
                if (j < jugadores.size() - 1) detalle.append(", ");
            }
            detalle.append(" | Ganador: ").append(ganadorPartida.getNombreConTipo());
            detalle.append(" | Rondas ganadas: ").append(maxRondas).append(" de 3");

            detalles.add(detalle.toString());
            
            cantPartidasTotal++;
        }

        return detalles;
    }
    
    public int getCantPartidas() { return this.cantPartidasTotal; }

    
}
