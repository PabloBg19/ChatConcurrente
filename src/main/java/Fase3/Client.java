package Fase3;

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
        // Puerto de conexión con el servidor
        final int PUERTO = 5000;

        // Flujos de entrada y salida
        DataInputStream in = null;
        DataOutputStream out = null;
        Socket sc;

        try {
            // Se crea el socket y se conecta al servidor
            sc = new Socket(HOST, PUERTO);

            // Flujo para recibir datos del servidor
            in = new DataInputStream(sc.getInputStream());
            // Flujo para enviar datos al servidor
            out = new DataOutputStream(sc.getOutputStream());

            // Scanner para leer datos del teclado
            Scanner teclado = new Scanner(System.in);

            //  Se solicita el nombre del usuario
            String nombre = cogerNombre();
            //  Se envía el nombre al servidor nada más conectarse
            out.writeUTF(nombre);

            // Mensaje de confirmación de conexión
            System.out.println("Conectado al servidor.");

            //  HILO RECEPTOR:
            // Este hilo está SIEMPRE leyendo lo que manda el servidor,
            // así el cliente ve cuando otros entran y salen, aunque él no escriba.
            DataInputStream finalIn = in;
            Thread receptor = new Thread(() -> {
                try {
                    while (true) {
                        String msg = finalIn.readUTF();
                        System.out.println("\n" + msg);
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("\nConexión cerrada.");
                }
            });
            receptor.setDaemon(true); // si el main termina, este hilo no bloquea el cierre
            receptor.start();

            boolean salir = false;

            // Bucle principal de comunicación con el servidor
            while (!salir) {
                System.out.print("> ");
                String mensajeEnviar = teclado.nextLine();

                // Envía el mensaje al servidor
                out.writeUTF(mensajeEnviar);

                // Si el usuario escribe "FIN", se termina la comunicación
                if (mensajeEnviar.equalsIgnoreCase("FIN")) {
                    salir = true;
                }
            }

            // Cierra el socket del cliente
            sc.close();
            System.out.println("Socket terminado");

        } catch (IOException e) {
            // Manejo de errores
            throw new RuntimeException(e);
        }
    }

    // Método que pide el nombre al usuario por teclado
    private static String cogerNombre() {
        System.out.println("Escribe tu nombre: ");
        Scanner teclado = new Scanner(System.in);
        return teclado.nextLine();
    }
}
