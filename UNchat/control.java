package UNchat;

public class control {
    
    public static void main(String[] args) {
        
        // Se inicia el servidor
        Thread serverThread = new Thread(() -> {
            new Servidor(); // Inicia el servidor
        });
        serverThread.start();

        // Se esperan 2 segundos para dar tiempo al servidor de iniciar (2000 milisegundos)
        try {
            Thread.sleep(2000);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Se inicia el cliente en un hilo aparte para que se ejecuten simultaneamente Servidor y Cliente
        Thread clientThread = new Thread(() -> {
            new Cliente(); 
        });
        clientThread.start();
    }
}
