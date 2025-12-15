package Fase4;

// Importación de clases necesarias para sockets
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        // Puerto en el que el servidor escucha conexiones
        final int PUERTO = 5000;

        // Se crea el ServerSocket (queda escuchando en el puerto)
        try (ServerSocket server = new ServerSocket(PUERTO)) {
            System.out.println("Servidor a la espera...");

            // 🔹 Bucle infinito para aceptar múltiples clientes
            while (true) {

                // El servidor queda bloqueado hasta que entra un cliente
                Socket sc = server.accept();

                // 🔹 FASE 4.1: identificación del cliente por IP
                System.out.println(
                        "Cliente conectado desde: " +
                                sc.getInetAddress().getHostAddress()
                );

                // Se crea el gestor del cliente (un hilo por cliente)
                GestorCliente gc = new GestorCliente(sc);

                // Se crea y lanza el hilo
                Thread t = new Thread(gc);
                t.start();
            }

        } catch (IOException e) {
            // Manejo de errores
            throw new RuntimeException(e);
        }
    }
}
