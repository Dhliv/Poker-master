package server;

import javax.swing.*;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc

/**
 * The Class Jugador.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
public class Jugador {

    /** The baraja mia almacena las cartas del jugador. */
    private ArrayList<JLabel> barajaMia;
    
    /** The fondos almacena el dinero del jugador. */
    public int fondos;

    /**
     * Instantiates a new jugador.
     */
    Jugador() {

        barajaMia = new ArrayList<>();
        fondos = 50;
    }

    /**
     * Iniciar baraja.
     * 
     * Obtiene dos cartas en su baraja.
     *
     * @param uno the uno es la primera carta.
     * @param dos the dos es la segunda carta.
     */
    void iniciarBaraja(JLabel uno, JLabel dos) {

        barajaMia.add(uno);
        barajaMia.add(dos);
    }

    /**
     * Borrar cartas.
     * 
     * Devuelve las cartas que tenia en su baraja al crupier.
     */
    public void borrarCartas() {
        barajaMia.removeAll(barajaMia);
    }

    /**
     * Gets the baraja mia.
     *
     * @return the baraja mia
     */
    public ArrayList<JLabel> getBarajaMia() {
        return barajaMia;
    }

}
