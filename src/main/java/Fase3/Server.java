package Fase3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    //  Lista de clientes conectados (thread-safe)
    // Guardamos los gestores para poder enviar mensajes a todos
    // usamos un Set para evitar duplicados y ConcurrentHashMap para seguridad en hilos
    public static final Set<GestorCliente> clientes = ConcurrentHashMap.newKeySet();

    //  Envía un mensaje a TODOS los clientes conectados
    public static void broadcast(String msg) {
        for (GestorCliente c : clientes) {
            c.enviar(msg);
        }
    }

    public static void main(String[] args) {

        // creacion constante, se le pone en mayusuculas para indicar que es una constante
        final int PUERTO = 5000;
        ServerSocket server = null;

        try {
            server = new ServerSocket(PUERTO); // Creación del servidor en el puerto 5000
            System.out.println("servidor a la espera..."); // Mensaje de inicio del servidor

            while(true){
                Socket sc= server.accept(); // Acepta conexiones entrantes

                // crear el gestor
                GestorCliente gc = new GestorCliente(sc);

                //  añadimos el cliente a la lista para poder hacer broadcast
                clientes.add(gc);

                // crear el hilo
                Thread t = new Thread(gc);
                t.start();
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado inesperadamente");
        }
    }
}
