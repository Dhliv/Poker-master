package poker;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiJugador.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
class GuiJugador extends JPanel {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 65176512;
	
	/** The aux T jugador. tamaño del JPanel en el que se encuentran ubicados los componentes de Jugador */
	private final double auxTJugador = Math.round(1.2*(new ImageIcon("src/mazo/corazon (5).png").getIconHeight()));
    private final int tJugador = (int)auxTJugador;

    /** JPanels
     * guiCartas: Se ubican todos los componentes graficos de las cartas que posee el jugador en su baraja
     * guiMonto: Se ubican todos los componentes graficos del monto del jugador, esto incluye: Botones, monto, txtBox en donde se ingresan los datos.
     *
     * */
    private JPanel guiCartas, guiMonto, guiBtns;
    
    /** The txt monto. Se imprime el monto junto a un pequeñop mensaje*/
    public JLabel txtMonto;
    
    /** The color panel jugadores. Miscelanea*/
    private Color colorPanelJugadores;
    
    /** Botones:
     * btnApostar: el boton con el cual se puede interactuar para apostar un monto en especifico (la interaccion se encuentra en FaseDeJuego)
     * btnPasar: el boton sirve para pasar la ronda sin apostar dinero (la interaccion se encuentra en FaseDeJuego)
     * btnRetirarse: el boton sirve para retirarse de la ronda en cuestion e iniciar con una nueva mano (la interaccion se encuentra en FaseDeJuego)
     * */
    public JButton btnApostar, btnPasar, btnRetirarse;
    
    /** The txt boxapostar N dinero. En esta se puede ingresar la cantidad que el jugador desea apostar*/
    public JTextField txtBoxapostarNDinero;
    
    /** The c cartas. Las restricciones que se usan para graficar las cartas*/
    private GridBagConstraints cCartas = new GridBagConstraints();
    
    /** The c monto. las restricciones que se usan para graficar la guiMonto*/
    private GridBagConstraints cMonto = new GridBagConstraints();

    /** The non margin. Miscelanea*/
    private Insets regularMargin, nonMargin;
    
    /** The font monto. Miscelanea*/
    private Font fontMonto;

        /**
         * Inicializar.
         *
         * Inicializa todos los componentes del objeto GuiJugador y pinta los componentes dependiendo si es el jugador que esta jugando en el pc o no.
         * @param juegaAqui the juega aqui. Representa si el jugador es activo en ese ordenador o no.
         * @param jugador the jugador. se ingresa un objeto Jugador, en donde se encuentran todas las variables perecederas de el mismo.
         * @param voltearCartas the voltear cartas. Representa si las cartas se pueden o no ver.
         */
        void inicializar(boolean juegaAqui, Jugador jugador, boolean voltearCartas)
        {
        	this.setLayout(new BorderLayout(0,0));
            int widthWindow = 1300;
            this.setPreferredSize(new Dimension(widthWindow /2,tJugador));

            this.estilos();
            this.guiCartas(juegaAqui, jugador, voltearCartas);
            this.guiMonto(jugador.fondos);
            this.pintar(juegaAqui);
        }

        /**
         * Dar vuelta.
         *Cambia de Icon al JLabel enviado, con el fin de denegar la visibilidad de las cartas a los oponentes.
         * @param carta the carta. Se le envia un JLabel que representa la carta a la que se le debe dar la vuelta
         * @return the j label
         */
        private JLabel darVuelta(JLabel carta)
        {
            carta.setIcon(new ImageIcon("src/mazo/fichaVuelta2.png"));
            return carta;
        }

        /**
         * Gui cartas.
         * Se inicializan y grafican todos los componentes relacionados a las cartas que le pertenecen al jugador
         * @param juegaAqui the juega aqui.  Representa si el jugador es activo en ese ordenador o no.
         * @param jugador the jugador. Datos del jugador al que se va a graficar
         * @param voltearCartas the voltear cartas. Representa si las cartas se pueden o no ver.
         */
        private void guiCartas(boolean juegaAqui, Jugador jugador, boolean voltearCartas)
        {
        	
            //fondoCartas = new JLabel(new ImageIcon("src/mazo/ponerMazo.png"));
            //fondoCartas.setLayout(new GridBagLayout());

            cCartas.gridx=0;
            cCartas.insets=new Insets(10,0,10,0);

            guiCartas = new JPanel();
            guiCartas.setLayout(new GridBagLayout());
            
            
            for(int i=0; i<2; i++) {
            	
            	JLabel aux = new JLabel();
            	aux.setName(jugador.getBarajaMia().get(i).getName());
            	aux.setIcon(jugador.getBarajaMia().get(i).getIcon());
            	
                if(juegaAqui) {
                    guiCartas.add(jugador.getBarajaMia().get(i), cCartas);
                }
                else {
                	
                	if(voltearCartas) {
                		guiCartas.add(jugador.getBarajaMia().get(i), cCartas);
                	}
                	else {
                		guiCartas.add(darVuelta(aux), cCartas);
                	}
                }
                cCartas.gridx += 1;
            }

            guiCartas.setOpaque(false);
        }

        /**
         * Sets the gui btns.
         * Inicializar y ubica todos los Btns que posee la guiJugador, como: btnApostar, btnPasar, btnRetirarse
         */
        private void setGuiBtns()
        {

            btnApostar = new JButton("BID");
            btnApostar.setName("1");
            btnPasar = new JButton("SKIP");
            btnPasar.setName("2");
            btnRetirarse = new JButton("Retirarse");
            btnRetirarse.setName("3");

            guiBtns = new JPanel();
            guiBtns.setLayout(new GridBagLayout());
            GridBagConstraints cBtn = new GridBagConstraints();
            cBtn.gridwidth=4;
            guiBtns.add(btnApostar, cBtn);
            cBtn.gridx+=5;
            guiBtns.add(btnPasar, cBtn);
            cBtn.gridy+=2;
            cBtn.gridwidth=2;
            cBtn.gridx=3;
            cBtn.insets=new Insets(10,0,0,0);
            guiBtns.setOpaque(false);
            guiBtns.add(btnRetirarse, cBtn);
        }
        
        /**
         * Reiniciar.
         *Reinicia todos los valores de la instancia de GuiJugador.
         *
         * @param juegaAqui the juega aqui
         * @param jugador the jugador
         * @param guiJugador the gui jugador
         * @param voltearCartas the voltear cartas
         */
        public void reiniciar(boolean juegaAqui, Jugador jugador, GuiJugador guiJugador, boolean voltearCartas) {
        	
        	guiCartas = guiJugador;
        	
        	this.setGuiBtns();
            this.guiCartas(juegaAqui, jugador, voltearCartas);
            this.guiMonto(jugador.fondos);
            this.pintar(juegaAqui);
        }


        /**
         * Gui monto.
         *Grafica todos los componentes del jugador relacionados al Monto, como lo son:
         * txtBox para apostar
         * botones de accion
         * Monto actual
         *
         * @param montoUsuario the monto usuario
         */
        private void guiMonto(int montoUsuario)
        {

            guiMonto = new JPanel();
            guiMonto.setLayout(new GridBagLayout());

            //TEXTOS
            txtMonto = new JLabel("<html><font color=\"white\"> Monto: " + montoUsuario + " </font></html>");
            txtMonto.setFont(fontMonto);

            JLabel txtApostar = new JLabel("<html><font color=\"white\">Cuanto quieres apostar?</font></html>");

            //CAJAS DE INPUTS

            txtBoxapostarNDinero = new JTextField(10);
            txtBoxapostarNDinero.setPreferredSize(new Dimension(guiMonto.getWidth()/2,20));
            //BOTONES

            this.setGuiBtns();

            //PINTAR
            cMonto.gridy=0;
            guiMonto.add(txtMonto, cMonto);
            cMonto.gridy++;
            cMonto.insets = regularMargin;
            guiMonto.add(txtApostar, cMonto);
            cMonto.insets = nonMargin;
            cMonto.gridy++;
            guiMonto.add(txtBoxapostarNDinero, cMonto);
            cMonto.insets = regularMargin;
            cMonto.gridy++;
            guiMonto.add(guiBtns, cMonto);
            guiMonto.setOpaque(true);
        }

        /**
         * Pintar.
         *Grafica toda la Gui de jugador, con lo que permite ver todos los componentes y objetos antes inicializados e introducidos.
         *
         * @param juegaAqui the juega aqui
         */
        private void pintar(boolean juegaAqui)
        {
            if(juegaAqui) {
                this.add(Box.createVerticalStrut(-30), BorderLayout.NORTH);
                this.add(guiMonto, BorderLayout.EAST);

                for(int i=0; i<this.getComponentCount();i++)
                    this.getComponent(i).setBackground(colorPanelJugadores);}

            //fondoCartas.add(guiCartas);
            this.add(guiCartas, BorderLayout.CENTER);
        }

        /**
         * Estilos. Miscelanea y decoracion.
         */
        private void estilos()
        {
            colorPanelJugadores = new Color(0, 0,0, 100);
            fontMonto = new Font ("Comic sans", Font.BOLD | Font.ITALIC, 25);
            regularMargin = new Insets(10,10,10,10);
            nonMargin = new Insets(0,0,0,0);

        }


}
