package Fase2;

// Importación de clases necesarias para la comunicación por red
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        // Puerto en el que el servidor va a escuchar conexiones
        final int PUERTO = 5000;

        // Se crea el ServerSocket y se deja escuchando en el puerto indicado
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor a la espera...");

            // El servidor queda bloqueado hasta que un cliente se conecte
            Socket sc = servidor.accept();
            System.out.println("Cliente conectado correctamente");

            // Flujos de entrada y salida para comunicarse con el cliente
            try (
                    DataInputStream in = new DataInputStream(sc.getInputStream());
                    DataOutputStream out = new DataOutputStream(sc.getOutputStream())
            ) {
                // Variable para controlar la salida del bucle
                boolean salir = false;

                // Bucle principal del servidor
                while (!salir) {
                    // Lee el mensaje enviado por el cliente
                    String mensaje = in.readUTF();
                    System.out.println("Cliente: " + mensaje);

                    // Si el cliente envía "FIN", se termina la comunicación
                    if (mensaje.equalsIgnoreCase("FIN")) {
                        salir = true;
                    } else {
                        // Envía una respuesta al cliente
                        out.writeUTF("Mensaje recibido: " + mensaje);
                    }
                }
            }

            // Cierra el socket del cliente
            sc.close();
            System.out.println("Servidor cerrado");

        } catch (IOException e) {
            System.out.println("Cliente desconectado inesperadamente");
        }
    }
}
