package Fase4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String HOST = "127.0.0.1";
        final int PUERTO = 5000;

        DataInputStream in = null;
        DataOutputStream out = null;
        Socket sc;

        try {
            sc = new Socket(HOST, PUERTO);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            Scanner teclado = new Scanner(System.in);

            //  PEDIR NOMBRE EN EL CLIENTE
            String nombre = cogerNombre();
            out.writeUTF(nombre); //  se envía al servidor

            System.out.println("Conectado al servidor.");
            boolean salir = false;

            while (!salir) {
                System.out.println("Escribe un mensaje para el servidor (escribe 'FIN' para salir): ");
                String mensajeEnviar = teclado.nextLine();
                out.writeUTF(mensajeEnviar);

                if (mensajeEnviar.equalsIgnoreCase("FIN")) {
                    salir = true;              // ✅ salir sin esperar respuesta
                } else {
                    String mensajeRecibido = in.readUTF();
                    System.out.println("Mensaje recibido del servidor: " + mensajeRecibido);
                }
            }


            sc.close();
            System.out.println("Socket terminado");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String cogerNombre(){
        System.out.println("Escribe tu nombre: ");
        Scanner teclado = new Scanner(System.in);
        return teclado.nextLine();
    }
}
