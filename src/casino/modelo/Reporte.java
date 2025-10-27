package casino.modelo;
// CONSIGNA 4: Generar Reporte
// IMPORTACIONES
import java.io.BufferedReader;  //  para leer el archivo txt
import java.io.File;            //  para verificaciones respecto al archivo txt
import java.io.FileReader;      //  para facilitar la lecturar del archivo txt
import java.io.IOException;     //  para capturar errores
import java.util.ArrayList;     //  para definir el comportamiento de la lista
import java.util.Comparator;    //  para comparar elementos y definir el orden
import java.util.List;  
import java.util.HashMap; 

public class Reporte {

    public static void generarReporteFinal(Casino casino, int totalPartidas) {
        // StringBuilder para construir strings largos
        StringBuilder sb = new StringBuilder();

        // Encabezado del reporte
        sb.append("========================================\n");
        sb.append("      REPORTE FINAL DEL CASINO\n");
        sb.append("========================================\n");
        sb.append("Jugadores participantes: ").append(casino.getJugadores().size()).append("\n");
        sb.append("Total de partidas jugadas: ").append(totalPartidas).append("\n\n");

        // se añade las secciones del reporte
        construirRanking(sb, casino.getJugadores());
        construirEstadisticas(sb, casino);
        construirHistorial(sb);

        // Pie de página
        sb.append("========================================\n");

        // Imprimir el reporte completo en la consola
        System.out.println(sb.toString());
    }

    // Seccion Ranking
    private static void construirRanking(StringBuilder sb, ArrayList<Jugador> jugadores) {
        sb.append("--- RANKING FINAL ---\n");

        // Se crea una copia para no modificar la lista original
        ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(jugadores);
        
        // Se ordena la lista de jugadores por dinero en orden descendente
        jugadoresOrdenados.sort(Comparator.comparingInt(Jugador::getDinero).reversed());

        int posicion = 1;
        for (Jugador j : jugadoresOrdenados) {
            sb.append(posicion).append(". ").append(j.getNombreConTipo())
              .append(" - $").append(j.getDinero())
              .append(" - ").append(j.getPartidasGanadas()).append(" partidas ganadas\n");
            posicion++;
        }
        sb.append("\n");
    }

    // Seccion Estadistica
    private static void construirEstadisticas(StringBuilder sb, Casino casino) {
        sb.append("--- ESTADÍSTICAS GENERALES ---\n");
        sb.append("Mayor apuesta realizada: $").append(casino.getMayorApuesta())
          .append(" (").append(casino.getNombreJugadorMayorApuesta()).append(")\n");
        sb.append("Mejor puntaje de dados: ").append(casino.getMejorPuntajeDados())
          .append(" (").append(casino.getNombreJugadorMejorPuntaje()).append(")\n");
        
        // codigo registrar trampa .    
        HashMap<String, Integer> victimas = casino.getVictimasDeTrampas();
        if (victimas.isEmpty()) {
            sb.append("Jugadores afectados por trampas: Ninguno\n");
        } else {
            sb.append("Jugadores afectados por trampas: ");
            List<String> listaVictimas = new ArrayList<>();
            for (String nombre : victimas.keySet()) {
                listaVictimas.add(nombre + "(" + victimas.get(nombre) + ")");
            }
            sb.append(String.join(", ", listaVictimas)); // Une todo con comas
            sb.append("\n");
        }   
        sb.append("La trampa 'Dados Cargados' fue usada ")
        .append(casino.getConteoDadosCargados())
        .append(" veces por el casino.\n");
        sb.append("\n");
    }


    // Seccion Historial
    private static void construirHistorial(StringBuilder sb) {
        sb.append("--- HISTORIAL RECIENTE ---\n");
        
        String nombreArchivo = "historial_partidas.txt";
        File archivo = new File(nombreArchivo);

        if (!archivo.exists()) {
            sb.append("[No se encontró el historial de partidas.]\n");
            return;
        }

        List<String> partidas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                partidas.add(linea);
            }
        } catch (IOException e) {
            sb.append("[Error al leer el historial: ").append(e.getMessage()).append("]\n");
            return;
        }

        if (partidas.isEmpty()) {
            sb.append("[No hay partidas registradas en el historial.]\n");
        } else {
            for (String partida : partidas) {
                sb.append(partida).append("\n");
            }
        }
    }
}
