package Fase4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class GestorCliente implements Runnable {

    // Método sincronizado para que varios hilos no mezclen mensajes en consola
    private static synchronized void log(String mensaje){
        System.out.println(mensaje);
    }

    // Socket asociado a este cliente
    private Socket sc;

    // Identificador del cliente (por defecto sin nombre hasta recibirlo)
    private String idCliente = "(sin nombre) - ";

    // Constructor: se le pasa el socket que representa a ese cliente
    public GestorCliente(Socket sc){
        this.sc = sc;
    }

    @Override
    public void run(){

        // try-with-resources: cierra automáticamente in y out al terminar
        try (DataInputStream in = new DataInputStream(sc.getInputStream());
             DataOutputStream out = new DataOutputStream(sc.getOutputStream())) {

            // 🔹 1) Recibir el nombre del cliente nada más conectarse
            String nombreCliente = in.readUTF();
            this.idCliente = nombreCliente + " - ";

            // Mostrar por consola que el cliente se conectó correctamente
            log("Cliente " + idCliente + " conectado correctamente");

            boolean salir = false;

            // 🔹 2) Bucle principal: leer mensajes hasta "FIN" o desconexión
            while(!salir){

                // Lee el mensaje del cliente
                // Si el cliente se desconecta de golpe, aquí puede saltar:
                // - EOFException: fin de stream (se cerró la conexión)
                // - SocketException: conexión rota
                String mensaje = in.readUTF();
                log("- " + idCliente + ": " + mensaje);

                // Si el cliente manda FIN, se sale de forma normal
                if (mensaje.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    // Si no, el servidor responde devolviendo el mensaje con el id del cliente
                    out.writeUTF(idCliente + mensaje);
                }
            }

            // Desconexión normal (FIN)
            log("Socket del cliente " + idCliente + " terminado");

        } catch (EOFException | SocketException e) {
            // FASE 4.2: desconexión brusca (cliente cerró ventana, se cayó internet, etc.)
            log("El cliente se ha desconectado inesperadamente (" + idCliente + ")");

        } catch (IOException e) {
            // Otros errores de E/S
            throw new RuntimeException(e);

        } finally {
            // Asegura que el socket se cierra SIEMPRE (aunque haya excepción)
            try { sc.close(); } catch (IOException ignored) {}
        }
    }
}
