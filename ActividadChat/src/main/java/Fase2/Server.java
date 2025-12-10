package Fase2;

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

                System.out.println("Cliente conectado correctamente" ); // Mensaje de conexión exitosa

                in = new DataInputStream(sc.getInputStream()); // Flujo de entrada
                out = new DataOutputStream(sc.getOutputStream()); // Flujo de salida

                boolean salir = false;

                while(!salir){
                    String mensaje = in.readUTF(); // Lee el mensaje enviado por el cliente
                    System.out.println("Mensaje recibido del cliente: " + mensaje); // Muestra el mensaje

                    if (mensaje.equalsIgnoreCase("FIN")) {
                        salir = true;
                    }else{
                        out.writeUTF("Mensaje recibido: " + mensaje); // Envía un mensaje al cliente
                    }
                }

                sc.close();
                System.out.println("Socket terminado"); // Mensaje de desconexión
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
