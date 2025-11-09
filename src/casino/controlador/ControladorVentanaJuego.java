package casino.controlador;

import casino.modelo.Casino;
import casino.modelo.JuegoDados;
import casino.modelo.Jugador;
//import casino.modelo.Reporte; 
import casino.vista.VentanaJuego;
import casino.vista.VentanaPausa;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import casino.vista.VentanaConfiguracion; 
import javax.swing.SwingUtilities;

public class ControladorVentanaJuego {
    private Casino casino;
    private VentanaJuego vistaJuego;
    private VentanaPausa vistaPausa;
    private JuegoDados juegoDados;
    private VentanaConfiguracion vistaConfig;
    
    private List<String> historialDeJuego;
    // Variables para controlar el estado del juego
    private int partidaActual;
    private int rondaActual;
    private int totalPartidas;
    private int totalRondas;
    private HashMap<Jugador, Integer> rondasGanadasEnPartida;

    
    public ControladorVentanaJuego(Casino casino, VentanaJuego vistaJuego, VentanaConfiguracion vistaConfig) {
        this.casino = casino;
        this.vistaJuego = vistaJuego;
        this.vistaConfig = vistaConfig; 
        // Creamos la ventana de pausa. El 'true' la hace modal.
        this.vistaPausa = new VentanaPausa(vistaJuego, true);
        // Inicializamos el mapa para contar las rondas ganadas
        this.rondasGanadasEnPartida = new HashMap<>();
        configurarEventos();
    }
    
    // Método para iniciar el juego con los parámetros de configuración
    public void iniciarJuego(int totalPartidas, int totalRondas) {
        this.totalPartidas = totalPartidas;
        this.totalRondas = totalRondas;
        this.partidaActual = 1;
        this.rondaActual = 1;
        
        // Reiniciamos las estadísticas del casino al comenzar la primera partida
        casino.reiniciarEstadisticas();
        
        this.juegoDados = new JuegoDados(casino);
        // Preparamos el contador de rondas ganadas para la primera partida
        reiniciarContadorRondasPartida();

        // Actualizamos la UI con la información inicial
        SwingUtilities.invokeLater(() -> {
            vistaJuego.limpiarLog(); 
            vistaJuego.agregarAlLog(">>> ¡Nueva Partida Iniciada! <<<");
            vistaJuego.prepararInterfazJugadores(casino.getJugadores());
            actualizarInfoPartidaUI(0);
        });
        
        // Hacemos visible la ventana del juego
        vistaJuego.setVisible(true);
    }
    
     /* @param totalPartidas La configuración de partidas cargada del archivo.
     * @param totalRondas La configuración de rondas cargada del archivo.
     */
    public void continuarJuegoCargado(int totalPartidas, int totalRondas) {
        this.totalPartidas = totalPartidas;
        this.totalRondas = totalRondas;
        this.partidaActual = 1;
        this.rondaActual = 1;

        casino.reiniciarEstadisticas(); // Las estadísticas de juego (mayor apuesta, etc.) se reinician.

        this.juegoDados = new JuegoDados(casino); 

        reiniciarContadorRondasPartida();
        actualizarInfoPartidaUI(0);
        vistaJuego.setVisible(true);
    }
    
    
    private void configurarEventos() {
        /* ============= BOTON AVANZAR  =============*/
        vistaJuego.getBtnAvanzar().addActionListener(e -> jugarSiguienteRonda());
        
        /* ============= MENU PARTIDA  =============*/
        vistaJuego.getMenuItemPausar().addActionListener(e -> pausarJuego());
        vistaJuego.getMenuItemGuardar().addActionListener(e -> {
            casino.guardarPartida(this.totalPartidas, this.totalRondas);
            JOptionPane.showMessageDialog(vistaJuego, "Partida guardada correctamente.");
        });
        //vistaJuego.getMenuItemSalir().addActionListener(e -> System.exit(0));

        /* ============= MENU PAUSA  =============*/
        vistaPausa.getBtnVolver().addActionListener(e -> vistaPausa.dispose()); // Simplemente cierra el diálogo
        vistaPausa.getBtnGuardarPausa().addActionListener(e -> {
            casino.guardarPartida(this.totalPartidas, this.totalRondas);
            JOptionPane.showMessageDialog(vistaPausa, "Partida guardada.");
        });
        
        vistaPausa.getBtnSalirPausa().addActionListener(e -> {
            vistaPausa.dispose(); // Cierra la ventana de pausa
            vistaJuego.dispose(); // Cierra la ventana de juego
            vistaConfig.setVisible(true); // Muestra de nuevo la ventana de configuración
        });   
        vistaJuego.getMenuItemSalir().addActionListener(e -> {
              int confirmacion = JOptionPane.showConfirmDialog(
                vistaJuego,
                "¿Estás seguro de que quieres salir de la partida actual?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                vistaJuego.dispose();
                vistaConfig.setVisible(true);
            }
        });
    }
    
    private void jugarSiguienteRonda() {
        
        if (partidaActual > totalPartidas || (juegoDados != null && juegoDados.isJuegoTerminado())) {
            return;
        }
        
        vistaJuego.agregarAlLog("----------------------------------------");
        vistaJuego.agregarAlLog(String.format("Partida %d, Ronda %d:", partidaActual, rondaActual));
        
        JuegoDados.ResultadoRondaDTO resultadoRonda = juegoDados.jugarRonda();
        
        if (resultadoRonda == null) {
            return;
        }
        
        // --- Bucle para actualizar la UI y construir el log ---
        List<Jugador> jugadores = casino.getJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugadorActual = jugadores.get(i);
            JuegoDados.InfoTiroDTO infoTiro = resultadoRonda.resultadosIndividuales.get(jugadorActual);
            boolean esGanador = resultadoRonda.ganadores.contains(jugadorActual);

            // Actualizar el panel visual del jugador (ya estaba implementado)
            vistaJuego.actualizarPanelJugador(i, jugadorActual, infoTiro, esGanador);

            // Construir y añadir el mensaje al log de texto
            String logMensaje = String.format("- %s apostó $%d y sacó %d + %d = %d.",
                    jugadorActual.getNombreConTipo(),
                    infoTiro.apuesta,
                    infoTiro.tiro1,
                    infoTiro.tiro2,
                    infoTiro.suma);
            if(infoTiro.fueConfundido) {
                logMensaje += " (¡Confundido!)";
            }
            vistaJuego.agregarAlLog(logMensaje);
        }
        
        if(resultadoRonda.ganadores.isEmpty()){
            vistaJuego.agregarAlLog("\nNadie ganó la ronda. El pozo se pierde.");
        } else {
            StringBuilder ganadoresStr = new StringBuilder();
            for(Jugador g : resultadoRonda.ganadores){
                ganadoresStr.append(g.getNombre()).append(", ");
            }
            ganadoresStr.setLength(ganadoresStr.length() - 2);
            vistaJuego.agregarAlLog("\nGanador(es) de la ronda: " + ganadoresStr.toString());
        }
        
        int pozoDeLaRonda = juegoDados.getUltimoPozo();
        SwingUtilities.invokeLater(() -> actualizarInfoPartidaUI(pozoDeLaRonda));

        if (juegoDados.isJuegoTerminado()) {
            vistaJuego.agregarAlLog("\n!!! Un jugador se quedó sin dinero. Fin del juego. !!!");
            finalizarJuego("Un jugador se quedó sin dinero.");
            return;
        }

        rondaActual++;
        if (rondaActual > totalRondas) {
            finalizarPartida();
        }
        
        // 7. Actualizamos la información de la partida en la UI
        //actualizarInfoPartidaUI();
    }
    
    private void actualizarInfoPartidaUI(int pozoActual) {
        System.out.println(String.format("DEBUG UI: Actualizando a Partida=%d, Ronda=%d, Pozo=%d", partidaActual, rondaActual, pozoActual));
        
        String textoPartida = String.format("Partida: %d / %d", partidaActual, totalPartidas);
        vistaJuego.getLblPartidaActual().setText(textoPartida);
        
        String textoRonda = String.format("Ronda: %d / %d", rondaActual, totalRondas);
        vistaJuego.getLblRondaActual().setText(textoRonda);
        
        String textoPozo = String.format("Pozo: $%d", pozoActual);
        vistaJuego.getLblPozoAcumulado().setText(textoPozo);
        
        vistaJuego.setTitle(String.format("Casino - Partida %d/%d | Ronda %d/%d", 
                                          partidaActual, totalPartidas, rondaActual, totalRondas));
    }
    
     private void finalizarPartida() {
        // Determinamos el ganador de la partida (quien ganó más rondas)
        Jugador ganadorPartida = determinarGanadorPartida();
        ganadorPartida.sumarVictoria();
        
        // Mostramos un mensaje al usuario
        vistaJuego.agregarAlLog(String.format(">>> Fin de la Partida %d. Ganador: %s <<<", partidaActual, ganadorPartida.getNombre()));    
        JOptionPane.showMessageDialog(vistaJuego, "Fin de la Partida " + partidaActual + ". Ganador: " + ganadorPartida.getNombre());
        
        // Avanzamos a la siguiente partida
        partidaActual++;
        rondaActual = 1;
        
        // Verificamos si se terminaron todas las partidas
        if (partidaActual > totalPartidas) {
            finalizarJuego("Se completaron todas las partidas.");
        } else {
            // Si no, preparamos la siguiente partida
            //rondaActual = 1;
            reiniciarContadorRondasPartida();
            SwingUtilities.invokeLater(() -> actualizarInfoPartidaUI(0));
        }
    }
    
    private void finalizarJuego(String motivo) {
        JOptionPane.showMessageDialog(vistaJuego, "¡Juego Terminado! Motivo: " + motivo);
        
        // Deshabilitamos los controles del juego
        vistaJuego.getBtnAvanzar().setEnabled(false);
        vistaJuego.getMenuItemPausar().setEnabled(false);
        vistaJuego.getMenuItemGuardar().setEnabled(false);
        
        // Generamos y guardamos los reportes finales, como se hacía antes
        // El total de partidas jugadas puede ser menor si alguien quebró.
        int partidasJugadas = (partidaActual > totalPartidas) ? totalPartidas : partidaActual -1;
         if (partidasJugadas < 1) partidasJugadas = 1;
        //Reporte.generarReporteFinal(casino, partidasJugadas);
        
        casino.guardarPartida(this.totalPartidas, this.totalRondas);         
        System.out.println("Reporte final generado. Cierra esta ventana para volver a configurar.");
        
        Object[] options = {"Volver al Menú Principal"};
        int result = JOptionPane.showOptionDialog(vistaJuego,
                "¡Juego Terminado! Motivo: " + motivo + "\nEl reporte final ha sido generado en la consola.",
                "Fin del Juego",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (result == 0 || result == JOptionPane.CLOSED_OPTION) {
             vistaJuego.dispose(); // Cierra la ventana de juego
             vistaConfig.setVisible(true); // Muestra de nuevo la ventana de configuración
        }
    }
    
    private void pausarJuego() {
        vistaPausa.setVisible(true); // Esto detiene la ejecución hasta que se cierre el diálogo
    }
    
    // --- MÉTODOS AUXILIARES ---

    private void reiniciarContadorRondasPartida() {
        for (Jugador j : casino.getJugadores()) {
            rondasGanadasEnPartida.put(j, 0);
        }
    }

    private Jugador determinarGanadorPartida() {
        Jugador ganador = null;
        int maxRondas = -1;
        for (Jugador j : rondasGanadasEnPartida.keySet()) {
            if (rondasGanadasEnPartida.get(j) > maxRondas) {
                maxRondas = rondasGanadasEnPartida.get(j);
                ganador = j;
            }
        }
        // En caso de empate, se puede mejorar la lógica, pero por ahora devuelve el primero que encuentre.
        return (ganador != null) ? ganador : casino.getJugadores().get(0);
    }
    
    //private void actualizarInfoPartidaUI(){
        // ESTE MÉTODO SERÁ CLAVE. Llenará los JLabels de la ventana de juego.
        // Por ahora, solo imprime en consola para demostrar el flujo.
    //    String info = String.format("Partida: %d/%d | Ronda: %d/%d", 
    //                                partidaActual, totalPartidas, 
    //                                rondaActual, totalRondas);
        
    //    vistaJuego.setTitle("Casino - " + info); // Actualizamos el título de la ventana
    //    System.out.println("UI actualizada: " + info);
    //}
}
