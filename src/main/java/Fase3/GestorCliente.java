package Fase3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GestorCliente implements Runnable {

    private static synchronized void log(String mensaje){
        System.out.println(mensaje);
    }

    private Socket sc;
    private String idCliente;

    public GestorCliente(Socket sc){
        this.sc = sc;
    }

    @Override
    public void run(){
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            // 🔹 RECIBIR NOMBRE DEL CLIENTE
            String nombreCliente = in.readUTF();
            this.idCliente = nombreCliente + " - ";

            boolean salir = false;

            log("Cliente " + idCliente + " conectado correctamente");

            while(!salir){
                String mensaje = in.readUTF();
                log("- " + idCliente + ": " + mensaje);

                if (mensaje.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    out.writeUTF(idCliente + mensaje);
                }
            }

            sc.close();
            log("Socket del cliente " + idCliente + " terminado");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
