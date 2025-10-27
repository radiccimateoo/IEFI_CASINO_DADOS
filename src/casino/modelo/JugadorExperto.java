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
public class JugadorExperto extends Jugador {

    // Constructor: llama al constructor de la clase padre (Jugador)
    public JugadorExperto(String nombre, String apodo, int dineroInicial) {
        super(nombre, apodo, dineroInicial);
    }

    // Apuesta el 20% del dinero actual (mínimo 1)
    @Override
    public int calcularApuesta() {
        int apuesta = (int)(getDinero() * 0.2);
        return Math.max(apuesta, 1); // Asegura que apueste al menos 1
    }

    @Override
    public String obtenerTipoJugador() {
        return "Experto";
    }
}
