package poker;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Bid.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
class Bid {
	
    /** The pozo de apuestas almacena la cantidad de dinero apostada. */
    public int pozoDeApuestas;
    
    /**
     * Instantiates a new bid.
     */
    public Bid() {
    	
    	pozoDeApuestas = 0;
    }

    /**
     * Adds the dinero.
     * 
     * agrega al pozo de apuestas la cantidad que hayan apostado los jugadores.
     *
     * @param DineroApostado the dinero apostado
     */
    private void addDinero(int DineroApostado)
    {
        pozoDeApuestas += 2*DineroApostado;
    }
    
    /**
     * Puede apostar.
     * 
     * Se verifica que el jugador tenga el dinero suficiente para hacer la primera apuesta
     * Se verifica que el jugador tenga que apostar todo si su dinero es menor a la apuesta fija en la primera ronda.
     *
     * @param montoUsuario the monto usuario
     * @param dineroAApostar the dinero A apostar
     * @param fase the fase la ronda del juego en la que estan.
     * @return true, si se puede apostar. False si no es asi.
     */
    public boolean puedeApostar(int montoUsuario, int dineroAApostar, int fase)
    {
        if(fase==0 && dineroAApostar<10) {
        	
        	if(dineroAApostar == montoUsuario && montoUsuario != 0) {
        		this.addDinero(dineroAApostar);
        		return true;
        	}
            JOptionPane.showMessageDialog(null, "Dinero para la primera ronda es insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(montoUsuario>=dineroAApostar && dineroAApostar>=0) {
            this.addDinero(dineroAApostar);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "No puede aportar esa cantidad", "Alerta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Reiniciar.
     * 
     * Se iguala al cero el pozo de apuestas para comenzar una nueva ronda.
     */
    public void reiniciar() {
    	pozoDeApuestas = 0;
    }
}
