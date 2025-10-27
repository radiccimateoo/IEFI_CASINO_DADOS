package casino.modelo;
import java.util.List;
import java.util.Random; 
import java.util.stream.Collectors;


/**
 *
 * @author Ceciia
 */
    // CONSIGNA 3: JUGADOR CASINO
    public class JugadorCasino extends Jugador{    
        private final Random random;
        private int trampasUsadas;
        
        //Constructor:
    public JugadorCasino(String nombre, int dineroInicial) {
        // Le pasamos "La Casa" como apodo por defecto.
        super("Casino", "La Casa", dineroInicial); 
        this.random = new Random();
    } 
    // Implementación del método abstracto para obtener el tipo de jugador
    @Override
    public String obtenerTipoJugador() {
        return "El Casino";
    }

    // Implementación del método abstracto para calcular la apuesta
        @Override   
        public int calcularApuesta() {
        return 0;
    }
        
    //Habilidad especial: dados cargados (40% probabilidad de sacar 6 en cada dado)
    public int tirarDadoCargado() {
        if (random.nextDouble() < 0.4) {  
            return 6; // 40% de probabilidad
        } else {
            return random.nextInt(6) + 1; // valor normal 1–6
        }
    }

    // Método para lanzar dos dados cargados
    public int lanzarDadosCargados() {
        int dado1 = tirarDadoCargado();
        int dado2 = tirarDadoCargado();
        System.out.println("Casino tiró dados: " + dado1 + " + " + dado2 + " = " + (dado1 + dado2));
        return (dado1 + dado2);        
    }
        
     // Nuevo método para seleccionar un jugador a confundir
    public Jugador seleccionarJugadorAConfundir(List<Jugador> jugadores) {
        if (new Random().nextDouble() < 0.3) { // 30% de probabilidad de activar
            // Filtra a los jugadores, excluyendo al propio Casino
            List<Jugador> jugadoresSinCasino = jugadores.stream()
                .filter(j -> !(j instanceof JugadorCasino))
                .collect(Collectors.toList());

            if (!jugadoresSinCasino.isEmpty()) {
                return jugadoresSinCasino.get(new Random().nextInt(jugadoresSinCasino.size()));
            }
        }
        return null; // Devuelve null si no se activa la habilidad
    }
    
    // getter para obtener la cantidad total de trampas usadas
    public int getTrampasUsadas() {
        return trampasUsadas;
    }
}
        
 