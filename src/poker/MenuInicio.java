package poker;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuInicio.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
public class MenuInicio extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9876123;

	/** The boton prueba. Boton que borra toda la Gui de MenuJuego y llama la nueva instancia de FaseDEJuego (esta interaccion se realiza en GuiJuego) */
    protected JButton botonPrueba;
    
    /** The centro. JPanel que contiene todos los componentes graficos del menu inicial */
    private JPanel centro;
    
    /** The fondo. Este JLabel muestra una imagen que se grafica en el fondo del JFrame con el fin de dibujar una imagen como fondo si es necesario*/
    private JLabel fondo;
    
    /** The color centro. Miscelanea*/
    private Color colorCentro;
    
    /** The non margin. Miscelanea*/
    private Insets marginTextCenter, nonMargin;
    
    /** The font other. Miscelanea*/
    private Font fontTitle, fontButton, fontOther;

    /** The t boton. Tamaños preestandarizados del JFrame y del boton*/
    private final int widthWindow, heightWindow, tBoton;

    protected JLabel txtInstrucciones;

    private MouseInstrucciones mouseInstrucciones = new MouseInstrucciones();
    
    /**
     * Instantiates a new menu inicio.
     */
    public MenuInicio() {
    	
    	tBoton = 100;
    	widthWindow = 1300;
    	heightWindow = 800;
    }
    
    
    /**
     * Inicializar.
     * Inicializa todos los componentes graficos del MenuInicio. Para esto llama algunas funciones y se le añade el layout que se usará
     */
    public void inicializar()
    {
        this.setBounds(0,0,widthWindow,heightWindow);

        fondo = new JLabel();

        this.setLayout(new GridBagLayout());

        fondo.setBounds(0,0,widthWindow,heightWindow);
        fondo.setIcon(new ImageIcon("src/mazo/fondo.jpg"));
        fondo.setLayout(null);
        this.GUIzonaCentral();
        this.pintar();
    }

    /**
     * GU izona central.
     * -Ubica todos los componentes graficos de la zona central (botones, labels, etc)
     * -Selecciona las coordenadas en donde estará ubicado el panel centro.
     */
    public void GUIzonaCentral()
    {
        this.estilos();

        centro = new JPanel();
        centro.setLayout(new GridBagLayout());
        centro.setBackground(colorCentro);

        GridBagConstraints cC = new GridBagConstraints();
        cC.weighty = tBoton;
        cC.insets = marginTextCenter;

        //BOTON
        botonPrueba = new JButton("JUGAR");
        botonPrueba.setName("boton");
        botonPrueba.setPreferredSize(new Dimension( 2*tBoton, tBoton));
        botonPrueba.setBackground(colorCentro);
        botonPrueba.setFont(fontButton);

        //TITULO
        JLabel titulo = new JLabel("Poker");
        titulo.setText("POKER");
        titulo.setFont(fontTitle);

        //OTROS TEXTOS
        txtInstrucciones = new JLabel("Instrucciones");
        txtInstrucciones.setFont(fontOther);
        txtInstrucciones.addMouseListener(mouseInstrucciones);
        cC.gridy=0;
        centro.add(titulo, cC);
        cC.gridy++;
        centro.add(txtInstrucciones, cC);
        cC.gridy++;
        cC.insets = nonMargin;
        centro.add(botonPrueba, cC);
        centro.setBounds(920,270,tBoton*2+2,tBoton*3);

    }
    
    /**
     * Estilos. Miscelanea
     */
    public void estilos()
    {
        marginTextCenter = new Insets(10,10,tBoton/2,10);
        colorCentro = new Color(255, 230,230,220);
        nonMargin = new Insets(0,0,0,0);
        fontTitle = new Font ("Comic sans", Font.BOLD | Font.ITALIC, 30);
        fontOther = new Font ("TimesRoman", Font.BOLD, 18);
        fontButton = new Font ("TimesRoman", Font.BOLD, 20);
    }

    /**
     * Pintar. Grafica en pantalla todos los componentes anteriormente inicializados y ubicados.
     */
    public void pintar()
    {
        this.add(fondo);
        fondo.add(centro);

    }
    private void mostrarInstrucciones(){
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://youtu.be/oLSMasFvzxE"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private class MouseInstrucciones implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            mostrarInstrucciones();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}
