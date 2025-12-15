package Fase1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        ServerSocket servidor = null; // Declaración del servidor
        Socket sc = null; // Declaración del socket

        DataInputStream in = null;
        DataOutputStream out = null;
        // creacion constante, se le pone en mayusuculas para indicar que es una constante
        final int PUERTO = 5000;

        try{
            servidor = new ServerSocket(PUERTO); // Creación del servidor en el puerto 5000
            System.out.println("servidor a la espera..."); // Mensaje de inicio del servidor

            while(true){
                sc = servidor.accept(); // Acepta conexiones entrantes, en este punto el servidor se queda esperando

                /*espera 15 segundos antes de conectar el cliente
                    esto hace que el cliente2 abierto tenga que esperar*/
                try {
                    Thread.sleep(15000); // Espera de 15 segundos
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Cliente conectado correctamente" ); // Mensaje de conexión exitosa

                in = new DataInputStream(sc.getInputStream()); // Flujo de entrada
                out = new DataOutputStream(sc.getOutputStream()); // Flujo de salida

                //leer mensaje del cliente
                String mensaje = in.readUTF(); // Lee el mensaje enviado por el cliente
                System.out.println("Mensaje recibido del cliente: " + mensaje); // Muestra el mensaje

                //envio un mensaje
                out.writeUTF("Hello World desde el servidor"); // Envía un mensaje al cliente

                //cerrar conexion
                sc.close(); // Cierra el socket
                System.out.println("Socket terminado"); // Mensaje de desconexión
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado inesperadamente");
        }
    }
}
