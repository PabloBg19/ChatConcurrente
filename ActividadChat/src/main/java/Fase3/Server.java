package Fase3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        // creacion constante, se le pone en mayusuculas para indicar que es una constante
        final int PUERTO = 5000;
        ServerSocket server = null;
        try {
            server = new ServerSocket(PUERTO); // Creación del servidor en el puerto 5000
            System.out.println("servidor a la espera..."); // Mensaje de inicio del servidor

            while(true){
                Socket sc= server.accept(); // Acepta conexiones entrantes, en este punto el servidor se queda esperando

                //crear el gestor
                GestorCliente gc = new GestorCliente(sc);

                //crear el hilo
                Thread t = new Thread(gc);
                t.start();
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
