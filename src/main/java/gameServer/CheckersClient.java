package gameServer;

import GUI.CheckerG;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import onBoard.Piece;

import java.io.*;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Vector;

public class CheckersClient extends Application implements Runnable{
    Vector<Piece> whitePieces = new Vector<>(12);
    Vector<Piece> blackPieces = new Vector<>(12);

    int player;

    VBox root = new VBox();
    Pane board = new Pane();
    HBox buttons = new HBox();
    Pane whiteAndBlackCheckers = new Pane();
    StackPane stackPane = new StackPane();

    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    Button russianGameButton = new Button("Russian Game");
    Button turkishGameButton = new Button("Turkish Game");
    Button englishGameButton = new Button("English Game");
    private void initializeBoard()
    {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle rect = new Rectangle(50, 50);
                if ((row + col) % 2 == 0) {
                    rect.setFill(Color.WHITE);
                }
                else {
                    rect.setFill(Color.BLACK);
                }
                rect.setX(col*50);
                rect.setY(row*50);
                board.getChildren().add(rect);
            }
        }

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if ( (i + j) % 2 != 0 ){
                    if (i < 4) {
                        CheckerG blackChecker = new CheckerG((-25+(j)*50),(-25+(i)*50),20,20, Piece.color.black);
                        blackChecker.setStroke(Color.BEIGE);
                        blackChecker.setStrokeWidth(2);
                        board.getChildren().add(blackChecker);
                    }
                    else if (i > 5) {
                        CheckerG whiteChecker = new CheckerG((-25+(j)*50),(-25+(i)*50),20,20, Piece.color.white);
                        whiteChecker.setFill(Color.BEIGE);
                        whiteChecker.setStroke(Color.DARKGRAY);
                        whiteChecker.setStrokeWidth(2);
                        board.getChildren().add(whiteChecker);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        stackPane.getChildren().addAll(board,whiteAndBlackCheckers);
        initializeBoard();
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(10);
        russianGameButton.setPrefWidth(120);
        turkishGameButton.setPrefWidth(120);
        englishGameButton.setPrefWidth(120);

        buttons.getChildren().addAll(russianGameButton, turkishGameButton, englishGameButton);
        root.getChildren().addAll(buttons, stackPane, whiteAndBlackCheckers);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        listenSocket();
        receiveInitFromServer();
        startThread();
    }

    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }
    @Override
    public void run()
    {
        while(true)
        {

        }
    }
    private void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            board.setDisable(player != 1);
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }
}



