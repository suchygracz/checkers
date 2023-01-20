package gameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * The type Win server thread.
 */
public class WinServerThread {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");

                Game g = new Game(firstClient, secondClient);
                Thread gTh = new Thread(g);
                gTh.start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


