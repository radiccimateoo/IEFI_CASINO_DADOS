package casino.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Casino {
    
    private ArrayList<Jugador> jugadores;
    private int mayorApuesta = 0;
    private String nombreJugadorMayorApuesta = "Sin registro";
    private int mejorPuntajeDados = 0;
    private String nombreJugadorMejorPuntaje = "Sin registro";
    private int conteoDadosCargados = 0;
    private HashMap<String, Integer> victimasDeTrampas = new HashMap<>();
    public int cantPartidasTotal = 0;
    private static final String ARCHIVO_GUARDADO = "partida_guardada.txt";

    
    public Casino() {
        jugadores = new ArrayList<>();
    }
    
    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public int getMayorApuesta() { return mayorApuesta; }
    public String getNombreJugadorMayorApuesta() { return nombreJugadorMayorApuesta; }
    public int getMejorPuntajeDados() { return mejorPuntajeDados; }
    public String getNombreJugadorMejorPuntaje() { return nombreJugadorMejorPuntaje; }
    public HashMap<String, Integer> getVictimasDeTrampas() { return victimasDeTrampas; }

     /**
     * Reinicia todas las estadísticas a sus valores iniciales.
     * Es crucial llamar a este método antes de iniciar una nueva serie de partidas
     * para evitar que los datos de la sesión anterior se acumulen.
     */
     public void reiniciarEstadisticas() {
        this.mayorApuesta = 0;
        this.nombreJugadorMayorApuesta = "Sin registro";
        this.mejorPuntajeDados = 0;
        this.nombreJugadorMejorPuntaje = "Sin registro";
        this.conteoDadosCargados = 0;
        this.victimasDeTrampas.clear(); // Limpia el mapa de víctimas
        this.cantPartidasTotal = 0;
        
        // También reiniciamos las victorias de cada jugador
        for (Jugador j : jugadores) {
            j.resetearVictorias(); 
        }
    }
     
    public void guardarPartida() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_GUARDADO))) {
            // Escribimos una cabecera para que el archivo sea más legible
            writer.write("nombre,apodo,tipo,dinero");
            writer.newLine();
            
            for (Jugador j : this.jugadores) {
                String linea = String.join(",",
                        j.getNombre(),
                        j.getApodo(),
                        j.obtenerTipoJugador(),
                        String.valueOf(j.getDinero())
                );
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Partida guardada correctamente en " + ARCHIVO_GUARDADO);
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }   
 
    public String cargarPartida() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GUARDADO))) {
            // Limpiamos la lista de jugadores actual antes de cargar la nueva.
            this.jugadores.clear();
            
            String linea;
            reader.readLine(); // Saltamos la línea de la cabecera (nombre,apodo,tipo,dinero)

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    String nombre = datos[0];
                    String apodo = datos[1];
                    String tipo = datos[2];
                    int dinero = Integer.parseInt(datos[3]);

                    Jugador jugadorCargado = null;
                    // Re-creamos el jugador basado en su tipo
                    switch (tipo) {
                        case "Novato":
                            jugadorCargado = new JugadorNovato(nombre, apodo, 0);
                            break;
                        case "Experto":
                            jugadorCargado = new JugadorExperto(nombre, apodo, 0);
                            break;
                        case "VIP":
                            jugadorCargado = new JugadorVIP(nombre, apodo, 0);
                            break;
                        case "El Casino":
                            jugadorCargado = new JugadorCasino(nombre, 0);
                            break;
                    }
                    
                    if (jugadorCargado != null) {
                        jugadorCargado.setDinero(dinero); // Establecemos el dinero guardado
                        this.jugadores.add(jugadorCargado);
                    }
                }
            }
            return "Partida cargada exitosamente.";

        } catch (FileNotFoundException e) {
            return "Error: No se encontró el archivo de partida guardada.";
        } catch (IOException | NumberFormatException e) {
            return "Error al leer el archivo de partida: " + e.getMessage();
        }
    }    
     
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
    public void registrarVictima(Jugador victima) {
    String nombreVictima = victima.getNombre();
    int conteoActual = victimasDeTrampas.getOrDefault(nombreVictima, 0);
    victimasDeTrampas.put(nombreVictima, conteoActual + 1);
}
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
    
    public boolean eliminarJugador(String apodo) {
        Jugador aEliminar = null;
        for (Jugador j : jugadores) {
            if (j.getApodo().equalsIgnoreCase(apodo)) {
                aEliminar = j;
                break;
            }
        }

        if (aEliminar != null) {
            jugadores.remove(aEliminar);
            return true; // Éxito
        } else {
            return false; // Fracaso (no se encontró)
        }
    }
    
/* Guarda el historial de partidas en un archivo de texto llamado "historial_partidas.txt".
     * Este archivo será leído por la clase Reporte para mostrar el historial.
     * El archivo se sobreescribe en cada nueva ejecución del juego.
     * @param historial La lista de strings que contiene el detalle de cada partida.     
*/  
    public void guardarHistorial(List<String> historial) {
        String nombreArchivo = "historial_partidas.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, false))) { // false para sobreescribir
            for (String linea : historial) {
                writer.write(linea);
                writer.newLine(); 
            }
            System.out.println("Historial guardado correctamente en " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el historial en el archivo: " + e.getMessage());
        }
    }
    
    
    public List<String> jugar(int cantPartidas, int cantRondas) {
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


            for (int r = 1; r <= cantRondas; r++) {
              if (!juego.isJuegoTerminado()) {
                    System.out.println("\n---- Ronda " + r);
                    List<Jugador> ganadoresRonda = juego.jugarRonda();
                    
                    if (ganadoresRonda == null) {
                        System.out.println("️ Juego finalizado anticipadamente en la ronda " + r + " de la partida " + i);
                        cantPartidasTotal = i;
                        return detalles;
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
            detalle.append(" | Rondas ganadas: ").append(maxRondas).append(" de ").append(cantRondas);

            detalles.add(detalle.toString());
            
            cantPartidasTotal++;
        }

        return detalles;
    }
   
    
    public int getCantPartidas() { return this.cantPartidasTotal; }
    
    
}
