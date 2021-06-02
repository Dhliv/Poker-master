package poker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiJuego.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
class GuiJuego extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8763284;

	/** The height window. Tamaños bases del JFrame*/
    private final int widthWindow, heightWindow;

    /** The fondo. Imagen de fondo que se vera en el JFrame*/
    private JLabel fondo;
    
    /** The mouse inicio. Las interacciones que existen desde el MenuInicio con el Mouse*/
    private MouseInicio mouseInicio;
    
    /**
     * Instantiates a new gui juego.
     */
    public GuiJuego()
    {
    	
    	widthWindow = 1300;
    	heightWindow = 800;
    	
        mouseInicio = new MouseInicio();
        this.imprimirMenuInicio();
        setTitle("Poker");
        this.setResizable(false);
        this.setSize(widthWindow, heightWindow);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    
    /**
     * Imprimir menu inicio. Inicializa algunas propiedades del JFrame en la primer fase de juego (MenuInicial) como: tamaño, escuchas, añade todos los componentes graficos del menu, etc
     */
    private void imprimirMenuInicio()
    {
        this.getContentPane().removeAll();

        MenuInicio menuInicio = new MenuInicio();
        menuInicio.inicializar();
        menuInicio.setPreferredSize(new Dimension(widthWindow, heightWindow));
        menuInicio.botonPrueba.addMouseListener(mouseInicio);

        add(menuInicio);

        this.repaint();
        this.revalidate();
    }
    
    /**
     * Imprimir fase. Inicializa los componentes del juego principal como:
     * - Toda la parte grafica (llamando la instancia faseDeJuego)
     * - El fondo cambia
     * - Se inicializa todos los eventos de mouse y se le ponen unos limites en los que se graficará la interfaz.
     */
    private void imprimirFase()
    {
    	this.getContentPane().removeAll();
        fondo = new JLabel(new ImageIcon("src/mazo/try3.png"));
        fondo.setLayout(null);
        fondo.setBounds(0,0,widthWindow,heightWindow);

        FaseDeJuego faseDeJuego = new FaseDeJuego();
        faseDeJuego.imprimir();

        fondo.add(faseDeJuego);
        this.add(fondo);
        this.repaint();
        this.revalidate();
    }

    /**
     * The Class MouseInicio. Hace el salto de la fase MenuInicio a FaseDeJuego.
     */
    public class MouseInicio implements MouseListener
    {

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(MouseEvent e) {
                
        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         *
         */
        @Override
        public void mousePressed(MouseEvent e) {
        	imprimirFase();
        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}