package server;

import poker.Jugador;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private final int PUERTO=5050;
    private ServerSocket server;
    private Socket socket;
    private int jugadorActivo, jActual, total, ciclo = 0, ronda = 0, valorAIgualar = 0, NJUGADORES=2;
    private final int NOHAJUGADO=-1, SERETIRO=-2, RONDAFINAL = 3, ESPERARDATO = 100;
    private boolean reiniciar = false, juegonIniciado = false;

    private Crupier crupier;

    private ArrayList<Boolean>  retirado;
    private ArrayList<Integer> apuestaJugador;

    private ArrayList<HiloCliente> jugadores;




    public Servidor() throws IOException {

        jActual=0;
        server = new ServerSocket(PUERTO, NJUGADORES);
        jugadores = new ArrayList<>();
        apuestaJugador = new ArrayList<>();

        retirado = new ArrayList<>();
        crupier = new Crupier();

        for(int i=0; i<NJUGADORES; i++) {
            apuestaJugador.add(0);
            retirado.add(false);
        }


    }

    public void ejecutarServidor() throws IOException, InterruptedException {
        try {
            System.out.println("Inicializando el servidor ...");

            for(int i=0; i<NJUGADORES; i++) {
                escucharCliente();
            }

            execute();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            cerrarConexion();
            e.printStackTrace();
        }

    }

    private void escucharCliente() throws IOException {
        socket = server.accept();
        HiloCliente jugadorEntrante;
        if(jugadores.size() == 0){
            jugadorEntrante = new HiloCliente(socket, true);
        }
        else{
            jugadorEntrante = new HiloCliente(socket, false);}
        jugadorEntrante.start();
        jugadores.add(jugadorEntrante);

    }

    private void gameOver() throws IOException {

        JOptionPane.showMessageDialog(null, "El resto de usuarios se han salido o han quedado sin dinero, eres el único jugadores. " +
                "\n!!ERES LO MAXIMO !! :) Eres el ganador definitivo, tienes un acomulado de: "+total+"\nGracias por jugar.", "Ganador indiscutible", 1);
        for(int i=0; i<NJUGADORES; i++)
        {
            if(jugadores.get(i).isAlive())
                jugadores.get(i).sigueJugando = false;
        }

        cerrarConexion();
    }

    private void execute() throws IOException, InterruptedException {
        while (!server.isClosed()) {
        	
            Thread.sleep(ESPERARDATO);
            jugadores.get(jActual % NJUGADORES).out.writeObject(true);
            inicializarJuego();

            while (!reiniciar) {

                jugadores.get(jActual % NJUGADORES).out.writeObject(false);

                Thread.sleep(ESPERARDATO / 10);
                if (NJUGADORES == 1)
                    gameOver();
                if (jugadores.get(jActual % NJUGADORES).isAlive()) {

                    Thread.sleep(ESPERARDATO);
                    int auxTotal = jugadores.get(jActual % NJUGADORES).txtEnviar;
                    boolean nuevaRonda = false;
                    if (auxTotal != NOHAJUGADO) {
                        if (SERETIRO != auxTotal) {
                            total += auxTotal;
                            retirado.set(jActual % NJUGADORES, true);
                        }

                        ciclo++;
                        apuestaJugador.set(jActual % NJUGADORES, auxTotal);

                        if (ciclo % NJUGADORES == 0) {
                            nuevaRonda = verificarApuestas();
                            if (nuevaRonda)
                                ronda++;
                        }

                        //ACTUALIZA EL NUMERO MAYOR
                        this.updateMayor();

                        //ENVIA EL TOTAL ACTUAL
                        Thread.sleep(ESPERARDATO);
                        for (int j = jActual + 1; j < jActual + NJUGADORES; j++) {
                            jugadores.get(j % NJUGADORES).out.writeObject(total);
                            jugadores.get(j % NJUGADORES).out.flush();

                            if ((jActual + 1) == j) {
                                jugadores.get((jActual + 1) % NJUGADORES).isTurno(true);
                            } else {
                                jugadores.get(j % NJUGADORES).isTurno(false);
                            }
                        }


                        //ENVIA LA CONFIRMACION DE SI EXISTE UNA NUEVA RONDA O DE LO CONTRARIO SIGUEN EN LA MISMA
                        Thread.sleep(ESPERARDATO);
                        for (int j = 0; j < NJUGADORES; j++) {
                            jugadores.get(j % NJUGADORES).out.writeObject(nuevaRonda);
                            jugadores.get(j % NJUGADORES).out.flush();
                        }

                        //REVISA SI ES NECESARIO ENVIAR UNA NUEVA CARTA (PASAN DE RONDA)
                        Thread.sleep(ESPERARDATO);
                        enviarCarta(nuevaRonda);

                        //REVISA SI HAY GANADOR Y NOTIFICA A LOS JUGADORES DE QUIEN HA GANADO
                        Thread.sleep(ESPERARDATO);
                        hayGanador(jugadorActivo);

                        //ENVIA EL VALOR A IGUAL (VALOR DEL QUE APOSTO MÁS)
                        Thread.sleep(ESPERARDATO);
                        for (int i = 0; i < NJUGADORES; i++) {
                            System.out.println("pillelo: " + valorAIgualar);
                            jugadores.get(i % NJUGADORES).out.writeObject(valorAIgualar);
                            jugadores.get(i % NJUGADORES).out.flush();
                        }


                        jugadores.get(jActual % NJUGADORES).txtEnviar = NOHAJUGADO;
                        jActual++;
                    }
                } else {
                    jugadores.remove(jActual % NJUGADORES);
                    jActual++;
                    NJUGADORES--;
                }
            }
        }
    }

    public void enviarCarta(boolean nuevaRonda) throws IOException {
        if(nuevaRonda) {
            for (int j = 0; j < NJUGADORES; j++)
                jugadores.get(j % NJUGADORES).out.writeObject("La ronda es: "+(ronda+1));
        }
    }

    public void updateMayor(){
        valorAIgualar = apuestaJugador.get(0);

        for(int i = 0; i < NJUGADORES; i++){
            if(valorAIgualar < apuestaJugador.get(i))
                valorAIgualar = apuestaJugador.get(i);
        }

    }

    public boolean verificarApuestas() {
        int nRetirados = NJUGADORES;
        boolean pasarRonda=true;
        for(int i=0; i<NJUGADORES; i++)
        {
            if(apuestaJugador.get(i) != valorAIgualar) {
                pasarRonda = false;
            }

            if(retirado.get(i)) {
                nRetirados--;
            }
            else{
                jugadorActivo = i;
            }
        }

        if(nRetirados == 1) {
            ronda = RONDAFINAL;
            return false;
        }

        if(pasarRonda){
            for(int i=0; i<NJUGADORES; i++)
                apuestaJugador.set(i, 0);
        }

        return pasarRonda;
    }

    private void hayGanador(int winner) throws InterruptedException, IOException {
        reiniciar = false;
        if(ronda == RONDAFINAL) {
            JOptionPane.showMessageDialog(null, "El ganador fue: " + winner, "GANADOR", JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < NJUGADORES; i++) {
                apuestaJugador.set(i, 0);
                retirado.set(i, false);
            }
            reiniciar = true;
            ronda = 0;
            ciclo = 0;
            total = 0;

        }
            for (int j = 0; j < NJUGADORES; j++)
            jugadores.get(j % NJUGADORES).out.writeObject(reiniciar);
    }

    private void inicializarJuego() throws IOException, InterruptedException {
        crupier.iniciarBaraja();
        crupier.abrirMesa();

        Thread.sleep(ESPERARDATO);
        for(int i=0; i<NJUGADORES; i++) {
            jugadores.get(i).out.writeObject(crupier.cartasJugador());
            jugadores.get(i).out.flush();
        }
        reiniciar = false;
        juegonIniciado = true;
    }

    private void cerrarConexion() throws IOException{
        System.out.println("Servidor cerró conexion ...");
        server.close();
    }
}