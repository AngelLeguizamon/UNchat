package UNchat;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cliente extends Ventana {

    private Socket conexion;
    private DataOutputStream flujoSalida;
    private DataInputStream flujoEntrada;

    public Cliente() {
        super("Cliente");
        
        // Intentar conectarse al servidor con reintentos
        boolean conectado = false;
        while (!conectado) {
            try {
                conexion = new Socket("127.0.0.1", 9999);
                flujoSalida = new DataOutputStream(conexion.getOutputStream());
                flujoEntrada = new DataInputStream(conexion.getInputStream());
                conectado = true;  // Conexión exitosa
            } catch (IOException ex) {
                // Esperar 1 segundo antes de reintentar
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Iniciar el hilo para escuchar los mensajes del servidor
        new Thread(new MessageListener()).start();

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

    // Hilo para recibir mensajes del servidor
    private class MessageListener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    String message = flujoEntrada.readUTF();
                    appendMessage("Servidor: " + message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Cliente().setVisible(true);
    }
}
