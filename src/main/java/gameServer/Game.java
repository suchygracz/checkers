package gameServer;

import checkersRules.AbstractGame;
import factory.RulesFactory;
import javafx.util.Pair;
import onBoard.Board;

import java.io.*;
import java.net.Socket;

/**
 * The type Game.
 */
public class Game implements Runnable{

    private final Socket firstPlayer;
    private final Socket secondPlayer;
    private Board board;
    private int OldX, OldY, NewX, NewY;
    private AbstractGame gameType;

    /**
     * The Input first player.
     */
    InputStream inputFirstPlayer;
    /**
     * The Buffor first player.
     */
    BufferedReader bufforFirstPlayer;
    /**
     * The Input second player.
     */
    InputStream inputSecondPlayer;
    /**
     * The Buffor second player.
     */
    BufferedReader bufforSecondPlayer;
    /**
     * The Output first player.
     */
    OutputStream outputFirstPlayer;
    /**
     * The Printer first player.
     */
    PrintWriter printerFirstPlayer;
    /**
     * The Output second player.
     */
    OutputStream outputSecondPlayer;
    /**
     * The Printer second player.
     */
    PrintWriter printerSecondPlayer;

    /**
     * Instantiates a new Game.
     *
     * @param firstPlayer  the first player
     * @param secondPlayer the second player
     */
    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
    @Override
    public void run() {
        try{
            initializeInputAndOutput();

            printerFirstPlayer.println("1");
            printerSecondPlayer.println("2");

            RulesFactory factory = new RulesFactory();

            gameType = factory.create(bufforFirstPlayer.readLine());
            board = new Board(gameType);

            do {
                whiteSequence();
                blackSequence();
            } while (true);

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }
    private void takeSecondPlayerMove() throws IOException {
        OldX = Integer.parseInt(bufforSecondPlayer.readLine());
        NewX = Integer.parseInt(bufforSecondPlayer.readLine());
        OldY = Integer.parseInt(bufforSecondPlayer.readLine());
        NewY = Integer.parseInt(bufforSecondPlayer.readLine());
    }
    private void takeFirstPlayerMove() throws IOException {
        OldX = Integer.parseInt(bufforFirstPlayer.readLine());
        NewX = Integer.parseInt(bufforFirstPlayer.readLine());
        OldY = Integer.parseInt(bufforFirstPlayer.readLine());
        NewY = Integer.parseInt(bufforFirstPlayer.readLine());
    }
    private void sendMoveToFirstPlayer()
    {
        printerFirstPlayer.println(OldX);
        printerFirstPlayer.println(NewX);
        printerFirstPlayer.println(OldY);
        printerFirstPlayer.println(NewY);
    }
    private void sendMoveToSecondPlayer()
    {
        printerSecondPlayer.println(OldX);
        printerSecondPlayer.println(NewX);
        printerSecondPlayer.println(OldY);
        printerSecondPlayer.println(NewY);
    }
    private Pair<Boolean, Pair<Integer, Integer>> isWhiteMovePossible()
    {
        return board.moveWhitePiece(board.getWhitePiece(new Pair<>((OldX + 25)/50, (OldY + 25)/50)), new Pair<>((NewX + 25)/50, (NewY + 25)/50));
    }
    private Pair<Boolean, Pair<Integer, Integer>> isBlackMovePossible()
    {
        return board.moveBlackPiece(board.getBlackPiece(new Pair<>((OldX + 25)/50, (OldY + 25)/50)), new Pair<>((NewX + 25)/50, (NewY + 25)/50));
    }
    private void whiteSequence() throws IOException {
        takeFirstPlayerMove();
        Pair<Boolean, Pair<Integer, Integer>> move = isWhiteMovePossible();
        while(!move.getKey())
        {
            printerFirstPlayer.println("not possible");
            takeFirstPlayerMove();
            move = isWhiteMovePossible();
        }
        printerFirstPlayer.println("possible");
        System.out.println(OldX + " " + OldY + " ---> " + NewX + " " + NewY);
        sendMoveToSecondPlayer();
        sendKill(move.getValue());
    }
    private void blackSequence() throws IOException {
        takeSecondPlayerMove();
        Pair<Boolean, Pair<Integer, Integer>> move = isBlackMovePossible();
        while(!move.getKey())
        {
            printerSecondPlayer.println("not possible");
            takeSecondPlayerMove();
            move = isBlackMovePossible();
        }
        printerSecondPlayer.println("possible");;
        System.out.println(OldX + " " + OldY + " ---> " + NewX + " " + NewY);
        sendMoveToFirstPlayer();
        sendKill(move.getValue());
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
    private void sendKill(Pair<Integer, Integer> pos)
    {
        if(pos != null)
        {
            sendKillCoordinate(pos);
        }
        else
        {
            printerFirstPlayer.println("no kill");
            printerSecondPlayer.println("no kill");
        }
    }
    private void sendKillCoordinate(Pair<Integer, Integer> pos)
    {
        printerFirstPlayer.println("kill");
        printerFirstPlayer.println(pos.getKey());
        printerFirstPlayer.println(pos.getValue());
        printerSecondPlayer.println("kill");
        printerSecondPlayer.println(pos.getKey());
        printerSecondPlayer.println(pos.getValue());
    }

}