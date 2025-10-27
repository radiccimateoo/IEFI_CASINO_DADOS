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
public class JugadorNovato extends Jugador {
    // Constructor: llama al constructor de la clase padre (Jugador)
    public JugadorNovato(String nombre, String apodo, int dineroInicial) {
        super(nombre, apodo, dineroInicial); // super() es el constructor de Jugador
    }

    // Implementa el método abstracto: siempre apuesta $50
    @Override
    public int calcularApuesta() {
        return 50;
    }

    // Implementa el método abstracto: devuelve tipo
    @Override
    public String obtenerTipoJugador() {
        return "Novato";
    }
}
