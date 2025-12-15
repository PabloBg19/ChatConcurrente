package Fase3;

// Importación de clases necesarias para la comunicación por red
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Clase que gestiona un cliente concreto usando un hilo
public class GestorCliente implements Runnable {

    // Método sincronizado para mostrar mensajes por consola
    // Evita que varios hilos escriban a la vez y se mezclen los mensajes
    private static synchronized void log(String mensaje){
        System.out.println(mensaje);
    }

    // Socket asociado a este cliente
    private Socket sc;
    // Identificador del cliente (nombre + separador)
    private String idCliente;

    // Constructor que recibe el socket del cliente
    public GestorCliente(Socket sc){
        this.sc = sc;
    }

    // Método que se ejecuta cuando se inicia el hilo
    @Override
    public void run(){
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            // Flujo de entrada para recibir datos del cliente
            in = new DataInputStream(sc.getInputStream());
            // Flujo de salida para enviar datos al cliente
            out = new DataOutputStream(sc.getOutputStream());

            // 🔹 Se recibe el nombre del cliente nada más conectarse
            String nombreCliente = in.readUTF();
            // Se crea un identificador para mostrarlo en los mensajes
            this.idCliente = nombreCliente + " - ";

            boolean salir = false;

            // Mensaje de conexión del cliente
            log("Cliente " + idCliente + " conectado correctamente");

            // Bucle principal de comunicación con el cliente
            while(!salir){
                // Lee el mensaje enviado por el cliente
                String mensaje = in.readUTF();
                log("- " + idCliente + ": " + mensaje);

                // Si el cliente envía "FIN", se cierra la conexión
                if (mensaje.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    // Devuelve el mensaje al cliente con su identificador
                    out.writeUTF(idCliente + mensaje);
                }
            }

            // Se cierra el socket del cliente
            sc.close();
            log("Socket del cliente " + idCliente + " terminado");

        } catch (IOException e) {
            // Manejo de errores de entrada/salida
            throw new RuntimeException(e);
        }
    }
}
