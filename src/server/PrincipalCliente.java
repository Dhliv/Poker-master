package server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PrincipalCliente {

   private static Cliente cliente;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // TODO Auto-generated method stub
        cliente = new Cliente();
        if (args.length > 0) {
            cliente.iniciarConexion(args[0]);
        } else {
            cliente.iniciarConexion("localhost");
        }
        cliente.ejecutarCliente();
    }

}