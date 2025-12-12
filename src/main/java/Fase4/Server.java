package Fase4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        final int PUERTO = 5000;

        try (ServerSocket server = new ServerSocket(PUERTO)) {
            System.out.println("Servidor a la espera...");

            while (true) {
                Socket sc = server.accept();

                // ✅ FASE 4.1: Identificación por IP
                System.out.println("Cliente conectado desde: " + sc.getInetAddress().getHostAddress());

                GestorCliente gc = new GestorCliente(sc);
                Thread t = new Thread(gc);
                t.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
