/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino.modelo;

import casino.modelo.Jugador;

/**
 *
 * @author Alumno
 */
public class JugadorVIP extends Jugador {

    // Atributo para controlar si ya usó el re-roll (repetir tirada) en la ronda actual
    private boolean puedeRepetirTirada;

    public JugadorVIP(String nombre, String apodo, int dineroInicial) {
        super(nombre, apodo, dineroInicial);
        this.puedeRepetirTirada = true; // Al iniciar cada ronda, puede repetir la tirada
    }

    @Override
    public int calcularApuesta() {
        int apuesta = (int)(getDinero() * 0.3);
        return Math.max(apuesta, 1);
    }

    @Override
    public String obtenerTipoJugador() {
        return "VIP";
    }

    // Método para saber si puede repetir la tirada
    public boolean puedeRepetir() {
        return puedeRepetirTirada;
    }

    // Método para marcar que ya usó el re-roll
    public void usarRepeticion() {
        puedeRepetirTirada = false;
    }

    // Método para resetear el estado de repetición (al comenzar una ronda nueva)
    public void resetearRepeticion() {
        puedeRepetirTirada = true;
    }
}