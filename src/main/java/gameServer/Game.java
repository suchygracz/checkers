package gameServer;

import onBoard.Board;

import java.io.*;
import java.net.Socket;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;
    private Board board = new Board();

    InputStream inputFirstPlayer;
    BufferedReader bufforFirstPlayer;
    InputStream inputSecondPlayer;
    BufferedReader bufforSecondPlayer;
    OutputStream outputFirstPlayer;
    PrintWriter printerFirstPlayer;
    OutputStream outputSecondPlayer;
    PrintWriter printerSecondPlayer;

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

            int OldX, OldY, NewX, NewY;
            do {
                OldX = Integer.parseInt(bufforFirstPlayer.readLine());
                NewX = Integer.parseInt(bufforFirstPlayer.readLine());
                OldY = Integer.parseInt(bufforFirstPlayer.readLine());
                NewY = Integer.parseInt(bufforFirstPlayer.readLine());

                //board.movePiece(board.getWhitePiece(new Pair<>((OldX - 25)/50, (OldY-25)/50)), new Pair<>((NewX-25)/50, (NewY-25)/50));
                System.out.println(OldX + " " + OldY + " ---> " + NewX + " " + NewY);

                printerSecondPlayer.println(OldX);
                printerSecondPlayer.println(NewX);
                printerSecondPlayer.println(OldY);
                printerSecondPlayer.println(NewY);

                OldX = Integer.parseInt(bufforSecondPlayer.readLine());
                NewX = Integer.parseInt(bufforSecondPlayer.readLine());
                OldY = Integer.parseInt(bufforSecondPlayer.readLine());
                NewY = Integer.parseInt(bufforSecondPlayer.readLine());

                //board.movePiece(board.getBlackPiece(new Pair<>((OldX - 25)/50, (OldY-25)/50)), new Pair<>((NewX-25)/50, (NewY-25)/50));
                System.out.println(OldX + " " + OldY + " ---> " + NewX + " " + NewY);

                printerFirstPlayer.println(OldX);
                printerFirstPlayer.println(NewX);
                printerFirstPlayer.println(OldY);
                printerFirstPlayer.println(NewY);

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