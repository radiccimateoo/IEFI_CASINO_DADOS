package casino.modelo;
import java.util.ArrayList;

public class PartidaGuardadaDTO {        
    private final int totalPartidas;
    private final int totalRondas;
    private final ArrayList<Jugador> jugadores;

    public PartidaGuardadaDTO(int totalPartidas, int totalRondas, ArrayList<Jugador> jugadores) {
        this.totalPartidas = totalPartidas;
        this.totalRondas = totalRondas;
        this.jugadores = jugadores;
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
}
