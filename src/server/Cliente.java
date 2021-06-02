package server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {
    private final int PUERTO=5050, ESCRIBIR = 100, RECIBIR = 50;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    protected Window myWindow = new Window();
    protected Jugador jugador;

    private JLabel nuevaCarta;
    private ArrayList<JLabel> cartasMesa, cartasMias;

    protected boolean turno;

    public void ejecutarCliente() throws IOException {
        while(true) {
            procesarDatos();
        }
    }

    public void iniciarConexion(String host) throws IOException, ClassNotFoundException {
        socket = new Socket(InetAddress.getByName(host),PUERTO);

        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();

        nuevaCarta = new JLabel();
        cartasMesa = new ArrayList<>();
        cartasMias = new ArrayList<>();

        turno = isTurn();
        System.out.println("Cliente conectado al servidor");
    }

    private void procesarDatos() {
        try{
        	
            Thread.sleep(RECIBIR);
            if((boolean) input.readObject()){
                recibirDatosInicialesDelServer();
            }
            
            if(turno)
            {
                while (!myWindow.diClick) {
                    Thread.sleep(ESCRIBIR);
                }


                if(!myWindow.meRetiro) {
                    Thread.sleep(ESCRIBIR);
                    output.writeObject(myWindow.valorApostado);
                    output.flush();
                    turno = false;
                    myWindow.diClick = false;
                }
                else
                {
                    this.retirado();
                }



            }else {
                recibirGlobal();
            }

            System.out.println("Esperando booleano de carta");
            recibirCarta((boolean) input.readObject());
            System.out.println("Esperando booleano de reinicio");
            reiniciarCliente((boolean) input.readObject());
            System.out.println("Esperando valor del mayor apostado");
            recibirApostado((int) input.readObject());

        }catch (Exception e){e.printStackTrace();}
    }

    public void recibirApostado(int valor){

        myWindow.elIgualado = valor;
    }

    public void retirado() throws InterruptedException, IOException {

        output.writeObject(-2);//-2 SIGNIFICA RETIRARSE
        output.flush();
        turno = false;
        myWindow.diClick = true;
    }

    public void recibirCarta(boolean recibir) throws IOException, ClassNotFoundException, InterruptedException {
        if(recibir)
        {
            System.out.println("Esperando Carta");
            Thread.sleep(RECIBIR);
            nuevaCarta.setText((String) input.readObject());
            myWindow.btnApostar.setText(nuevaCarta.getText());
            System.out.println("Llego carta");
        }

    }

    public void reiniciarCliente(boolean reset) throws InterruptedException {
        if(reset) {
            myWindow.total = 0;
            myWindow.apostadoTotal = 0;
            myWindow.elIgualado = 0;
            myWindow.txtTotal.setText("0");
            myWindow.meRetiro = false;
            myWindow.diClick = false;
        }
    }

    public void recibirGlobal() throws IOException, ClassNotFoundException {
        System.out.println("Recibiendo...");

        myWindow.total =(int) input.readObject();
        myWindow.txtTotal.setText(String.valueOf(myWindow.total));
        System.out.println("Recibi el total");
        turno = isTurn();
        myWindow.bloquear(turno);
        System.out.println("es mi turno?");
    }

    public boolean isTurn() throws IOException, ClassNotFoundException {
         return (boolean) input.readObject();
    }

    public void recibirDatosInicialesDelServer() throws IOException, ClassNotFoundException, InterruptedException {

        Thread.sleep(RECIBIR);
        cartasMesa = (ArrayList<JLabel>) input.readObject();
        Thread.sleep(RECIBIR);
        cartasMias = (ArrayList<JLabel>) input.readObject();

        for(int i=0; i<2; i++) {
            System.out.println(cartasMesa.get(i).getName());
            System.out.println(cartasMias.get(i).getName());
        }
    }



    private void cerrarConexion() throws IOException {
        output.close();
        input.close();
        socket.close();
        System.out.println("Cliente cerro conexion ...");
    }


}