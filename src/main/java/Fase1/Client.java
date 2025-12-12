package Fase1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //host del servidor
        final String HOST = "127.0.0.1";
        //puerto del servidor
        final int PUERTO = 5000;

        DataInputStream in = null;
        DataOutputStream out = null;

        Socket sc;

        {
            try {
                sc = new Socket(HOST, PUERTO);

                in = new DataInputStream(sc.getInputStream()); // Flujo de entrada
                out = new DataOutputStream(sc.getOutputStream()); // Flujo de salida

                out.writeUTF("Hola desde el cliente"); // Envía un mensaje al servidor

                String mensaje = in.readUTF(); // Lee el mensaje enviado por el servidor
                System.out.println("Mensaje recibido del servidor: " + mensaje); // Muestra el mensaje

                sc.close(); // Cierra el socket
                System.out.println("Socket terminado"); // Mensaje de desconexión
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
