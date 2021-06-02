package server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloCliente extends Thread {
    private Socket socket;
    protected int txtEnviar=-1;
    private ObjectInputStream in;
    protected ObjectOutputStream out;
    protected boolean turno, sigueJugando = true;


    public HiloCliente(Socket clientSocket, boolean turno) throws IOException {
        this.socket = clientSocket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        out.writeObject(turno);
        out.flush();
        this.turno = turno;
    }


    public void isTurno(boolean turno) throws IOException {
        out.writeObject(turno);
        out.flush();
        this.turno = turno;
    }

    public void run() {
        while(sigueJugando) {
            try {
                Thread.sleep(50);
                if(turno) {
                    System.out.println("Es mi turno");
                    txtEnviar = (int) in.readObject();
                    if (txtEnviar == -3) { //CONDICION DE MUERTE
                        sigueJugando = false;
                        txtEnviar = 0;
                    }
                    System.out.println("el usuario escribió " + txtEnviar);
                    turno = false;
                }
            }
            catch (IOException | ClassNotFoundException e) {
                System.err.println("Error");
                e.printStackTrace();
                return;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


}
