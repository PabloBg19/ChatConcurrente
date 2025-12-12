package Fase2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        final String HOST = "127.0.0.1";
        final int PUERTO = 5000;

        try (
                Socket sc = new Socket(HOST, PUERTO);
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                Scanner teclado = new Scanner(System.in)
        ) {
            System.out.println("Conectado al servidor.");

            boolean salir = false;

            while (!salir) {
                System.out.print("Escribe un mensaje (FIN para salir): ");
                String mensajeEnviar = teclado.nextLine();
                out.writeUTF(mensajeEnviar);

                if (mensajeEnviar.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    String respuesta = in.readUTF();
                    System.out.println("Servidor: " + respuesta);
                }
            }

            System.out.println("Cliente desconectado.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
