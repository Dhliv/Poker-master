package server;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window extends JFrame {

    private final int widthWindow, heightWindow;
    protected JLabel prueba, txtTotal;
    protected JTextField txtBox;
    protected JButton btnApostar, btnRetirarse;
    protected int total=0;

    private MouseApostar mouseApostar;
    private MouseRetirarse mouseRetirarse;
    private MouseIgualar mouseIgualar;

    protected boolean diClick, meRetiro;
    protected int valorApostado, elIgualado = 0, apostadoTotal = 0;

    public Window()
    {

        this.iniciar();

        meRetiro = false;

        mouseApostar = new MouseApostar();
        mouseRetirarse = new MouseRetirarse();
        mouseIgualar = new MouseIgualar();

        btnApostar.addMouseListener(mouseApostar);
        btnRetirarse.addMouseListener(mouseRetirarse);
        prueba.addMouseListener(mouseIgualar);

        widthWindow = 500;
        heightWindow = 600;

        setTitle("Poker");
        this.setResizable(false);
        this.setSize(widthWindow, heightWindow);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }

    public void iniciar() {

        prueba = new JLabel("Igualar");
        prueba.setBounds(50, 50, 300, 50);

        txtTotal = new JLabel(Integer.toString(total));
        txtTotal.setBounds(300, 450, 200, 50);

        btnApostar = new JButton();
        btnApostar.setBounds(50, 150, 300, 50);

        btnRetirarse = new JButton();
        btnRetirarse.setText("Retirarse");
        btnRetirarse.setBounds(50,400,200,50);

        txtBox = new JTextField(10);
        txtBox.setBounds(50, 350, 300, 50);



        this.add(prueba);
        this.add(btnApostar);
        this.add(btnRetirarse);
        this.add(txtBox);
        this.add(txtTotal);
    }
    private int actualizarTxt(int valor)
    {
        total+=valor;
        apostadoTotal += valor;
        txtTotal.setText(String.valueOf(total));
        diClick = true;
        return total;
    }

    public void bloquear(boolean block)
    {
            btnApostar.setVisible(block);
    }

    private class MouseApostar implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            valorApostado = Integer.parseInt(txtBox.getText());
            actualizarTxt(valorApostado);
            bloquear(false);
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
    private class MouseRetirarse implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            meRetiro = true;
            diClick = true;
            bloquear(false);
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
    private class MouseIgualar implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            diClick = true;
            System.out.println(elIgualado + "    " + apostadoTotal);
            if(elIgualado > apostadoTotal)
                valorApostado = elIgualado - apostadoTotal;
            else
                valorApostado = 0;

                actualizarTxt(valorApostado);

            bloquear(false);
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
