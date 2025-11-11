package casino;

import casino.modelo.*;
import casino.vista.*;
import casino.controlador.*;

import java.util.ArrayList;


public class AppCasino {

    public static void main(String[] args) {
        CasinoDAO casinoDAO = new CasinoDAO();
        casinoDAO.crearTablasSiNoExisten();
        Casino casino = new Casino();         VentanaConfiguracion ventanaConfig = new VentanaConfiguracion();
        ControladorJuego controlador = new ControladorJuego(casino, ventanaConfig);
        ventanaConfig.setVisible(true);
    }
}
