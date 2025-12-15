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

    // ✅ Nombre para avisos de entrada/salida
    private String nombreCliente;

    // ✅ salida guardada para poder enviar mensajes desde el Server.broadcast()
    private DataOutputStream out;

    // Constructor que recibe el socket del cliente
    public GestorCliente(Socket sc){
        this.sc = sc;
    }

    // ✅ Método para enviar mensajes a ESTE cliente (lo usa Server.broadcast)
    public void enviar(String msg) {
        try {
            if (out != null) out.writeUTF(msg);
        } catch (IOException e) {
            // Si falla, no hacemos nada aquí; se limpiará en el finally
        }
    }

    // Método que se ejecuta cuando se inicia el hilo
    @Override
    public void run(){
        DataInputStream in = null;

        try {
            // Flujo de entrada para recibir datos del cliente
            in = new DataInputStream(sc.getInputStream());
            // Flujo de salida para enviar datos al cliente (guardado en atributo)
            out = new DataOutputStream(sc.getOutputStream());

            // 🔹 Se recibe el nombre del cliente nada más conectarse
            nombreCliente = in.readUTF();
            // Se crea un identificador para mostrarlo en los mensajes
            this.idCliente = nombreCliente + " - ";

            boolean salir = false;

            // Mensaje de conexión del cliente
            log("Cliente " + idCliente + " conectado correctamente");

            // ✅ Aviso a TODOS los clientes de que este ha ENTRADO
            Server.broadcast("🔔 " + nombreCliente + " ha entrado al chat");

            // Bucle principal de comunicación con el cliente
            while(!salir){
                // Lee el mensaje enviado por el cliente
                String mensaje = in.readUTF();
                log("- " + idCliente + ": " + mensaje);

                // Si el cliente envía "FIN", se cierra la conexión
                if (mensaje.equalsIgnoreCase("FIN")) {
                    salir = true;
                } else {
                    // ✅ (opcional) si quieres que el mensaje lo vean todos, lo mandamos a todos:
                    // Si NO quieres chat general, cambia esto por out.writeUTF(...) como lo tenías
                    Server.broadcast(idCliente + mensaje);
                }
            }

        } catch (IOException e) {
            log("Cliente desconectado inesperadamente: " + (nombreCliente != null ? nombreCliente : "desconocido"));
        } finally {

            // ✅ Aviso a TODOS los clientes de que este ha SALIDO
            if (nombreCliente != null) {
                Server.broadcast("🚪 " + nombreCliente + " ha salido del chat");
            }

            // ✅ Quitamos el cliente de la lista
            Server.clientes.remove(this);

            // Se cierra el socket del cliente
            try { sc.close(); } catch (IOException ignored) {}

            log("Socket del cliente " + (idCliente != null ? idCliente : "") + " terminado");
        }
    }
}
