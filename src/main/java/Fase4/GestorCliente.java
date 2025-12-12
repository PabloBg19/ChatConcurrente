package Fase4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class GestorCliente implements Runnable {

    private static synchronized void log(String mensaje){
        System.out.println(mensaje);
    }

    private Socket sc;
    private String idCliente = "(sin nombre) - ";

    public GestorCliente(Socket sc){
        this.sc = sc;
    }

    @Override
    public void run(){
        try (DataInputStream in = new DataInputStream(sc.getInputStream());
             DataOutputStream out = new DataOutputStream(sc.getOutputStream())) {

            // 🔹 Recibir nombre del cliente
            String nombreCliente = in.readUTF();
            this.idCliente = nombreCliente + " - ";

            log("Cliente " + idCliente + " conectado correctamente");

            boolean salir = false;

            while(!salir){
                String mensaje = in.readUTF(); // <-- aquí saltará EOFException/SocketException si se cierra abrupto
                log("- " + idCliente + ": " + mensaje);

                if (mensaje.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    out.writeUTF(idCliente + mensaje);
                }
            }

            log("Socket del cliente " + idCliente + " terminado");

        } catch (EOFException | SocketException e) {
            // ✅ FASE 4.2: desconexión brusca
            log("El cliente se ha desconectado inesperadamente (" + idCliente + ")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try { sc.close(); } catch (IOException ignored) {}
        }
    }
}
