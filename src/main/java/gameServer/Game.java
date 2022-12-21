package gameServer;

import java.io.*;
import java.net.Socket;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;

    private final static int FIRST = 1;
    private final static int SECOND = 2;
    private static int turn = FIRST;
    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;
    }
    @Override
    public void run() {
        try{
            //Inicjalizacja pobieranie od socketa dla player1
            InputStream inputFirstPlayer = firstPlayer.getInputStream();
            BufferedReader bufforFirstPlayer = new BufferedReader(new InputStreamReader(inputFirstPlayer));

            //Inicjalizacja pobieranie od socketa dla player2
            InputStream inputSecondPlayer = secondPlayer.getInputStream();
            BufferedReader bufforSecondPlayer = new BufferedReader(new InputStreamReader(inputSecondPlayer));

            //Inicjalizacja Wysylania do socketa dla player1
            OutputStream outputFirstPlayer = firstPlayer.getOutputStream();
            PrintWriter printerFirstPlayer = new PrintWriter(outputFirstPlayer, true);

            //Inicjalizacja Wysylania do socketa dla player2
            OutputStream outputSecondPlayer = secondPlayer.getOutputStream();
            PrintWriter printerSecondPlayer = new PrintWriter(outputSecondPlayer, true);

            printerFirstPlayer.println("1");
            printerSecondPlayer.println("2");

            String line;
            do {
                if (turn == SECOND) {
                    // Odbieranie od socketa
                    line = bufforSecondPlayer.readLine();
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    printerFirstPlayer.println("-> (" + line + ")");
                    turn = FIRST;
                }

                if (turn == FIRST) {
                    // Odbieranie od socketa
                    line = bufforFirstPlayer.readLine();
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    printerSecondPlayer.println("-> (" + line + ")");
                    turn = SECOND;
                }
            } while (true);

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

    private void sendMove(DataOutputStream out, String text) throws IOException {
        out.writeChars(text);
    }
}