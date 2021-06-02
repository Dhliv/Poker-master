package poker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiMesaDeJuego.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
public class GuiMesaDeJuego extends JPanel {

        /**
	 * 
	 */
	private static final long serialVersionUID = 6235731;

		JLabel montoTotal;
	
        /** The fondo. JLabel que tiene un icono llamado fondo, con el fin de graficar el fondo de la mesa si se es necesario*/
        private JLabel fondo;
        
        /** The aux T jugador. Representa el tamaño de la Gui del jugador*/
        private final double auxTJugador;
        
        /** Tamaños estaticos del JFrame principal (GuiJuego)*/
        protected final int widthWindow, heightWindow, tJugador;
        
        /**
         * Instantiates a new gui mesa de juego.
         */
        public GuiMesaDeJuego() {
        	
        	fondo = new JLabel();
        	
        	auxTJugador = Math.round(1.2*(new ImageIcon("src/mazo/corazon (5).png").getIconHeight()));
        	widthWindow = 1300;
        	heightWindow = 800;
            tJugador = (int)auxTJugador;
        }
        
        /**
         * Inicializar.
         *Inicializa los componentes graficos de la mesa (centro del juego), en donde se grafican las cartas reveladas por el Crupier
         *
         * @param cartaEnMesa the carta en mesa
         */
        public void inicializar(ArrayList<JLabel> cartaEnMesa)
        {
                this.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                fondo.setPreferredSize(new Dimension((int) Math.round(widthWindow*0.7), heightWindow-2*tJugador));

                GuiMesa(cartaEnMesa);
                this.add(fondo, c);
        }

        /**
         * Reiniciar.
         *Reinicializa la mesa, dandole nuevos valores a las cartas y volviendo a graficar tan solo las primeras 3 cartas
         *
         * @param cartaEnMesa the carta en mesa
         */
        public void reiniciar(ArrayList<JLabel> cartaEnMesa){
                GuiMesa(cartaEnMesa);
        }

        /**
         * Gui mesa.
         *Añade las cartas que estan en la mesa
         *
         * @param cartaEnMesa the carta en mesa. Cartas seleccionadas al azar por el Crupier
         */
        public void GuiMesa(ArrayList<JLabel> cartaEnMesa)
        {
                montoTotal = new JLabel();

                fondo.removeAll();
                fondo.setLayout(new GridBagLayout());
                GridBagConstraints cMesa = new GridBagConstraints();
                cMesa.gridx=0;
                fondo.add(montoTotal,cMesa);
                cMesa.gridy+=10;
                for(int i=0; i<cartaEnMesa.size();i++)
                {
                        fondo.add(cartaEnMesa.get(i), cMesa);
                        cMesa.gridx++;
                }
        }

}
