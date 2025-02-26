package UNchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Servidor extends Ventana {

    private static ServerSocket server;
    private static Socket clientSocket;
    private static DataInputStream flujoEntrada;
    private static DataOutputStream flujoSalida;

    public Servidor() {
        super("Servidor");
        startServer();

        // Añadir el listener al botón "Enviar"
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flujoSalida != null) {
                    try {
                        String message = texto.getText();
                        flujoSalida.writeUTF(message);
                        appendMessage("Tú: " + message);
                        texto.setText("");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    // Método para inicializar y manejar la conexión del servidor
    private void startServer() {
        try {
            server = new ServerSocket(9999);
            clientSocket = server.accept();  // Acepta la conexión del cliente
            flujoEntrada = new DataInputStream(clientSocket.getInputStream());
            flujoSalida = new DataOutputStream(clientSocket.getOutputStream());

            // Inicia el hilo para escuchar los mensajes del cliente
            new Thread(new MessageListener()).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Hilo para escuchar los mensajes del cliente
    private class MessageListener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    String message = flujoEntrada.readUTF();
                    appendMessage("Cliente: " + message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Servidor().setVisible(true);
    }
}
