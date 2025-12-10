package Fase3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GestorCliente implements Runnable{
    private static int contadorClientes = 0;



    private static synchronized void log(String mensaje){ // Método sincronizado para evitar mezclas en la salida por consola
        System.out.println(mensaje);
    }

    private Socket sc;
    private String idCliente;
    Scanner teclado = new Scanner(System.in);

    public GestorCliente(Socket sc){
        this.sc = sc;
        System.out.println("Di su Nombre:");
        String nombreCliente = teclado.nextLine();
        this.idCliente = nombreCliente + " - ";
    }

    @Override
    public void run(){
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            in = new DataInputStream(sc.getInputStream()); // Flujo de entrada
            out = new DataOutputStream(sc.getOutputStream());// Flujo de salida

            boolean salir = false;

            log("Cliente " + idCliente + " conectado correctamente");

            while(!salir){
                String mensaje = in.readUTF();
                log("- " + idCliente + ": " + mensaje);

                if (mensaje.equalsIgnoreCase("FIN")) {
                    salir = true;
                }else {
                    out.writeUTF(idCliente +  mensaje);
                }
            }

            sc.close();
            log("Socket del cliente " + idCliente + " terminado");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
