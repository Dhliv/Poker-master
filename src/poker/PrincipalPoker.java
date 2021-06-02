package poker;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * The Class PrincipalPoker.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
public class PrincipalPoker {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        }
        catch (Exception e) {}
        EventQueue.invokeLater(
                new Runnable()
                {
                    public void run()
                    {
                        GuiJuego myWindow = new GuiJuego();
                    }
                });
    }

}