package casino.modelo;
import java.util.ArrayList;

public class PartidaGuardadaDTO {        
    private final int totalPartidas;
    private final int totalRondas;
    private final ArrayList<Jugador> jugadores;
    private final int partidaActual; 
    private final int rondaActual;

    public PartidaGuardadaDTO(int totalPartidas, int totalRondas, int partidaActual, int rondaActual ,ArrayList<Jugador> jugadores) {
        this.totalPartidas = totalPartidas;
        this.totalRondas = totalRondas;
        this.jugadores = jugadores;
        this.partidaActual = partidaActual; 
        this.rondaActual = rondaActual;
    }
    public int getTotalPartidas() {
        return totalPartidas;
    }
    public int getTotalRondas() {
        return totalRondas;
    }
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    //agregamos nuevos metodos getters para la consigna 5
    public int getPartidaActual() {
        return partidaActual;
    }

    public int getRondaActual() {
        return rondaActual;
    }
}
