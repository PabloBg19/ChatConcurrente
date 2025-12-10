package Fase3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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

                Scanner teclado= new Scanner(System.in);

                System.out.println("Conectado al servidor.");
                boolean salir = false;


                while(!salir){
                    System.out.println("Escribe un mensaje para el servidor (escribe 'FIN' para salir): ");
                    String mensajeEnviar = teclado.nextLine();
                    out.writeUTF(mensajeEnviar); // Envía un mensaje al servidor

                    String mensajeRecibido = in.readUTF(); // Lee el mensaje enviado por el servidor
                    System.out.println("Mensaje recibido del servidor: " + mensajeRecibido); // Muestra el mensaje

                    if (mensajeEnviar.equalsIgnoreCase("FIN")) {
                        salir = true;
                    }
                }

                sc.close(); // Cierra el socket
                System.out.println("Socket terminado"); // Mensaje de desconexión
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static String cogerNombre(){
        System.out.println("Escribe tu nombre: ");
        Scanner teclado = new Scanner(System.in);
        String nombre = teclado.nextLine();
        return nombre;
    }
}
