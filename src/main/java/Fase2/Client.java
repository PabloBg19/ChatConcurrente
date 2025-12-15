package Fase2;

// Importación de clases necesarias para la comunicación y entrada de datos
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        // Dirección IP del servidor (localhost)
        final String HOST = "127.0.0.1";
        // Puerto por el que se conecta el cliente al servidor
        final int PUERTO = 5000;

        // Bloque try-with-resources: los recursos se cierran automáticamente
        try (
                // Se crea el socket para conectarse al servidor
                Socket sc = new Socket(HOST, PUERTO);
                // Flujo de entrada para recibir datos del servidor
                DataInputStream in = new DataInputStream(sc.getInputStream());
                // Flujo de salida para enviar datos al servidor
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                // Scanner para leer datos introducidos por teclado
                Scanner teclado = new Scanner(System.in)
        ) {
            // Mensaje de confirmación de conexión
            System.out.println("Conectado al servidor.");

            // Variable para controlar la salida del bucle
            boolean salir = false;

            // Bucle principal del cliente
            while (!salir) {
                // Solicita un mensaje al usuario
                System.out.print("Escribe un mensaje (FIN para salir): ");
                String mensajeEnviar = teclado.nextLine();

                // Envía el mensaje al servidor
                out.writeUTF(mensajeEnviar);

                // Si el usuario escribe "FIN", se termina la conexión
                if (mensajeEnviar.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    // Recibe la respuesta del servidor
                    String respuesta = in.readUTF();
                    // Muestra la respuesta recibida
                    System.out.println("Servidor: " + respuesta);
                }
            }

            // Mensaje de cierre del cliente
            System.out.println("Cliente desconectado.");

        } catch (IOException e) {
            // Manejo de errores de entrada/salida
            e.printStackTrace();
        }
    }
}
