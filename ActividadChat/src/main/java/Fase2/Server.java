package Fase2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        final int PUERTO = 5000;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor a la espera...");

            Socket sc = servidor.accept();
            System.out.println("Cliente conectado correctamente");

            try (
                    DataInputStream in = new DataInputStream(sc.getInputStream());
                    DataOutputStream out = new DataOutputStream(sc.getOutputStream())
            ) {
                boolean salir = false;

                while (!salir) {
                    String mensaje = in.readUTF();
                    System.out.println("Cliente: " + mensaje);

                    if (mensaje.equalsIgnoreCase("FIN")) {
                        salir = true;
                    } else {
                        out.writeUTF("Mensaje recibido: " + mensaje);
                    }
                }
            }

            sc.close();
            System.out.println("Servidor cerrado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
