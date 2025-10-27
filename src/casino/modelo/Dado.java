/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino.modelo;

/**
 *
 * @author Alumno
 */

import java.util.Random;


public class Dado {

    private Random random;

    public Dado() {
        random = new Random();
    }

    // Método que simula tirar el dado (devuelve un número del 1 al 6)
    public int tirar() {
        return random.nextInt(6) + 1;
    }
}
