package gameServer;

import checkersRules.ClassicGame;
import checkersRules.GameType;
import checkersRules.ValidatorOfRules;

import java.io.*;
import java.net.*;


public class WinServerThread {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            ClassicGame type = new ClassicGame();
            ValidatorOfRules validator = new ValidatorOfRules(type);
            while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");

                Game g = new Game(firstClient, secondClient, validator);
                Thread gTh = new Thread(g);
                gTh.start();
                // TO DO: Musi byc dokldnie dwoch klientow
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


