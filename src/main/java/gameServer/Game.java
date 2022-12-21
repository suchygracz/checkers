package gameServer;

import checkersRules.ValidatorOfRules;
import onBoard.Board;

import java.io.*;
import java.net.Socket;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;
    private Board board = new Board();
    private final ValidatorOfRules validator;

    private final static int FIRST = 1;
    private final static int SECOND = 2;
    private static int turn = FIRST;

    InputStream inputFirstPlayer;
    BufferedReader bufforFirstPlayer;
    InputStream inputSecondPlayer;
    BufferedReader bufforSecondPlayer;
    OutputStream outputFirstPlayer;
    PrintWriter printerFirstPlayer;
    OutputStream outputSecondPlayer;
    PrintWriter printerSecondPlayer;

    public Game(Socket firstPlayer, Socket secondPlayer, ValidatorOfRules validator){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;
        this.validator = validator;
    }
    @Override
    public void run() {
        try{
            initializeInputAndOutput();

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

    private void initializeInputAndOutput() throws IOException {
        inputFirstPlayer = firstPlayer.getInputStream();
        bufforFirstPlayer = new BufferedReader(new InputStreamReader(inputFirstPlayer));

        inputSecondPlayer = secondPlayer.getInputStream();
        bufforSecondPlayer = new BufferedReader(new InputStreamReader(inputSecondPlayer));

        outputFirstPlayer = firstPlayer.getOutputStream();
        printerFirstPlayer = new PrintWriter(outputFirstPlayer, true);

        outputSecondPlayer = secondPlayer.getOutputStream();
        printerSecondPlayer = new PrintWriter(outputSecondPlayer, true);
    }

}