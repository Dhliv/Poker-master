package poker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class FaseDeJuego.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
class FaseDeJuego extends JPanel {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 12875123;
	
	/** The aux T jugador almacena el tamaño de los paneles de los jugadores en Y. */
	private final double auxTJugador = Math.round(1.2*(new ImageIcon("src/mazo/corazon (5).png").getIconHeight()));
    
    /** The n jugadores el numero de jugadores. */
    final int nJugadores = 2;

    /** tJugador almacena el tamaño de los paneles de los jugadores en Y (Pero en entero).
     *  widthWindow almacena la anchura de la ventana de juego.
     *  heightWindow almacena la altura de la ventana de juego.
     *  */
    private int widthWindow, heightWindow, tJugador;
    
    /** The rondas almacena el numero de veces que se ha apostado. */
    private int rondas;

    /** The mesa de juego es el panel que grafica a guiMesaDeJuego. */
    private JPanel mesaDeJuego;

    /** The gui mesa de juego donde se almacenan los componentes graficos de la mesa. */
    private GuiMesaDeJuego guiMesaDeJuego;

    /** The jugadores almacena varios objetos de jugador. */
    private ArrayList<Jugador> jugadores;
    
    /** The guis jugadores almacena varios objetos de guiJugador. */
    private ArrayList<GuiJugador> guisJugadores;
    
    /** The zona guarda los paneles que grafican a los jugadores con su respectiva ubicacion. */
    private ArrayList<JPanel> zona;

    /** The bid crea un objeto de clase Bid. */
    private Bid bid;
    
    /** The crupier crea un objeto de clase Crupier. */
    private Crupier crupier;

    /** The mouse fase crea un objeto de clase MouseListener. */
    private MouseFase mouseFase;
    
    
    /**
     * Instantiates a new fase de juego.
     */
    public FaseDeJuego() {
    	
    	widthWindow = 1300;
    	heightWindow = 800;
    	tJugador = (int)auxTJugador;
    	rondas = 0;
    	
    	guisJugadores = new ArrayList<>();
        zona = new ArrayList<>();

        bid = new Bid();
        crupier = new Crupier();

        mouseFase = new MouseFase();
    }
    

    /**
     * Imprimir.
     * 
     * Inicializa todos los objetos para el inicio del juego.
     * Imprime la fase de juego.
     */
    void imprimir()
    {
        final String []ubicacion = {"South", "North", "East", "West"};

        mesaDeJuego = new JPanel();
        jugadores = new ArrayList<>();

        for(int i=0; i<nJugadores; i++) {
            zona.add(new JPanel());
        }

        crupier.iniciarBaraja();
        crupier.abrirMesa();

        for(int i=0; i<nJugadores; i++) {
            Jugador jugador = new Jugador();
            
            jugador.iniciarBaraja(crupier.retirarCarta(),crupier.retirarCarta());
            jugadores.add(jugador);
        }

        
        for(int i=0; i<nJugadores; i++){
            GuiJugador guiJugador = new GuiJugador();
            guisJugadores.add(guiJugador);
        }

        for(int i=0; i<nJugadores; i++) {
            boolean juegaAqui = false;
            if(i == 0)
                juegaAqui = true;
            this.inicializarGuiJugadores(guisJugadores.get(i), zona.get(i), juegaAqui, jugadores.get(i));
        }
        this.inicializarGuiMesaDeJuego();

        this.setLayout(new BorderLayout());
        this.setBounds(0,0,widthWindow,heightWindow);

        //AÑADIR EN PANTALLA
        for(int i=0; i<nJugadores; i++)
            this.add(zona.get(i), ubicacion[i]);

        this.add(mesaDeJuego, BorderLayout.CENTER);
        this.setOpaque(false);
        for(int i=0; i<getComponentCount();i++)
            this.getComponent(i).setBackground(new Color(0,0,0,0));
    }
    
    
    /**
     * Inicializar gui jugadores.
     * 
     * Inicializa los objetos para la gui del jugador.
     *
     * @param guiJugador the gui jugador recibe la gui del jugador que haya que imprimir.
     * @param zona the zona recibe el panel donde se va a ubicar la gui del jugador
     * @param juegaAqui the juega aqui identifica quien juega en este computador.
     * @param jugador the jugador recibe el jugador con sus cartas.
     */
    private void inicializarGuiJugadores(GuiJugador guiJugador, JPanel zona, boolean juegaAqui, Jugador jugador)
    {
        zona.setLayout(new GridBagLayout());
        zona.setPreferredSize(new Dimension(widthWindow, tJugador));
        zona.setOpaque(true);

        GridBagConstraints c = new GridBagConstraints();
        guiJugador.inicializar(juegaAqui, jugador, false);
        
        if(juegaAqui) {
            guiJugador.btnApostar.addMouseListener(mouseFase);
            guiJugador.btnRetirarse.addMouseListener(mouseFase);
            guiJugador.btnPasar.addMouseListener(mouseFase);
        }
        
        for(int i = 0; i < guiJugador.getComponentCount(); i++)
            guiJugador.setBackground(new Color(0,0,0,0));
        
        zona.add(guiJugador, c);
    }
    
    
    /**
     * Inicializar gui mesa de juego.
     * 
     * Inicializa los objetos para la gui de la mesa.
     */
    private void inicializarGuiMesaDeJuego()
    {

        guiMesaDeJuego = new GuiMesaDeJuego();

        mesaDeJuego.setLayout(new GridBagLayout());
        mesaDeJuego.setPreferredSize(new Dimension(widthWindow, heightWindow-2*tJugador));

        GridBagConstraints c = new GridBagConstraints();

        guiMesaDeJuego.inicializar(crupier.cartasMesa);
        
        for(int i = 0; i < guiMesaDeJuego.getComponentCount(); i++)
        	guiMesaDeJuego.setBackground(new Color(0,0,0,0));
        
        mesaDeJuego.add(guiMesaDeJuego, c);
    }

    /**
     * Adds the carta mesa.
     * 
     * Agrega cartas a la gui de la mesa de juego.
     */
    private void addCartaMesa()
    {
        crupier.addToMesa();
        guiMesaDeJuego.GuiMesa(crupier.cartasMesa);
        this.repaint();
        this.revalidate();
    }

    /**
     * Mostrar cartas volteadas.
     * 
     * Gira las cartas que tiene la maquina.
     */
    private void mostrarCartasVolteadas() {
    	
    	guisJugadores.get(1).removeAll();
    	guisJugadores.get(1).reiniciar(false, jugadores.get(1), guisJugadores.get(1), true);
    	
    	this.repaint();
    	this.revalidate();
    }
    
    /**
     * Reiniciar.
     * 
     * Reinicia todos los objetos para iniciar una nueva ronda de juego.
     */
    private void reiniciar(){

    	rondas = 0;
    	if(!bid.puedeApostar(jugadores.get(0).fondos, jugadores.get(0).fondos,rondas)) {
            JOptionPane.showMessageDialog(null, "<html><p>No puedes seguir jugando debido a que no tienes el monto minimo para hacerlo</p><br><p>Sorry Bro :'(</p></html>", "ALERTA", JOptionPane.YES_NO_CANCEL_OPTION);
            System.exit(0);
        }
    	
    	bid.reiniciar();

        crupier.cerrarMesa();
        for(int i = 0; i < nJugadores; i++){
        	
        	guisJugadores.get(i).removeAll();
            jugadores.get(i).borrarCartas();
        }

        crupier.iniciarBaraja();
        crupier.abrirMesa();

        for(int i = 0; i < nJugadores; i++){
            
            jugadores.get(i).iniciarBaraja(crupier.retirarCarta(), crupier.retirarCarta());
        }

        guiMesaDeJuego.reiniciar(crupier.cartasMesa);


        
        for(int i = 0; i < guisJugadores.size(); i++) {
        	boolean juegaAqui = false;
            if(i == 0)
                juegaAqui = true;
        	
        	guisJugadores.get(i).reiniciar(juegaAqui, jugadores.get(i), guisJugadores.get(i), false);
        	if(juegaAqui) {
        		guisJugadores.get(i).btnApostar.addMouseListener(mouseFase);
        		guisJugadores.get(i).btnRetirarse.addMouseListener(mouseFase);
        		guisJugadores.get(i).btnPasar.addMouseListener(mouseFase);
            }
        }
        
        this.setOpaque(false);
        for(int i = 0; i< this.getComponentCount(); i++)
            this.getComponent(i).setBackground(new Color(0,0,0,0));

        this.repaint();
        this.revalidate();
    }

    /**
     * The Class MouseFase.
     * 
     * Implementa los escuchas a cada uno de los botones que son albergados en GuiJugador->JPanel->JPanel
     */
    public class MouseFase implements MouseListener
    {
        /*
        Funcion apostar:
        - Permite al jugador apostar un monto dictado por parametro
         */

        private void apostar(GuiJugador jugador, int dineroAApostar){
            jugador.txtBoxapostarNDinero.setText("");

            if(bid.puedeApostar(jugadores.get(0).fondos, dineroAApostar, rondas)) {

                if(rondas < 2)
                    addCartaMesa();

                jugadores.get(0).fondos -= dineroAApostar;
                jugador.txtMonto.setText("<html><font color=\"white\"> Monto: " + jugadores.get(0).fondos + " </font></html>");
                guiMesaDeJuego.montoTotal.setText("<html><font color=\"red\">" + Integer.toString(bid.pozoDeApuestas) + "</font></html>");
                repaint();
                revalidate();
                rondas++;
            }


            if(crupier.cartasMesa.size() == 5 && rondas == 3)
            {
                int ganador = crupier.decidirGanador(jugadores);

                if(ganador >= -1) {

                    mostrarCartasVolteadas();
                    repaint();
                    revalidate();

                    if(ganador == 1) {
                    	JOptionPane.showMessageDialog(null, "Ganaste", "GANADOR", JOptionPane.INFORMATION_MESSAGE);
                        jugadores.get(0).fondos += bid.pozoDeApuestas;
                    }
                    
                    if(ganador == 2) {
                    	JOptionPane.showMessageDialog(null, "Perdiste", "PERDEDOR", JOptionPane.INFORMATION_MESSAGE);
                    }

                    if(ganador/10 > 0) {
                    	JOptionPane.showMessageDialog(null, "Has empatado con la maquina", "EMPATE", JOptionPane.INFORMATION_MESSAGE);
                        jugadores.get(0).fondos += bid.pozoDeApuestas/2;
                    }
                    jugador.txtMonto.setText("<html><font color=\"white\"> Monto: " + jugadores.get(0).fondos + " </font></html>");


                    reiniciar();
                }
            }
        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {

        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         * 
         * Evalua que clase de boton fue presionado y se ejecuta su accion correspondiente
         */
        @Override
        public void mousePressed(MouseEvent e) {

            String auxTypeBtn = ((JButton) e.getSource()).getName();
            int typeBtn = Integer.parseInt(auxTypeBtn);

        	JPanel guiBtn = (JPanel) ((JButton) e.getSource()).getParent();
        	JPanel panel = (JPanel) guiBtn.getParent();
            GuiJugador jugador = (GuiJugador) panel.getParent();

            JTextField cajaDeTexto = jugador.txtBoxapostarNDinero;
            String sDineroAApostar = cajaDeTexto.getText();

            switch (typeBtn){
                case 1:
                    int dineroAApostar = Integer.parseInt(sDineroAApostar);
                    apostar(jugador, dineroAApostar);
                    break;
                case 2:
                    apostar(jugador, 0);
                    break;
                case 3:
                    if(rondas != 0)
                        reiniciar();
                    else
                        JOptionPane.showMessageDialog(null, "NO PUEDES RETIRARTE SIN APOSTAR EL MONTO BASE", "ACCION NEGADA", JOptionPane.ERROR_MESSAGE);
                    break;
            }


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
