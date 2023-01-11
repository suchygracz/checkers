package gameServer;
import GUI.CheckerG;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Pair;
import onBoard.Board;
import onBoard.Pawn;
import onBoard.Piece;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

public class CheckersClient extends Application implements ActionListener, Runnable{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Vector<Piece> whitePieces = new Vector<>(12);
        Vector<Piece> blackPieces = new Vector<>(12);
        final int PLAYER1 = 1;
        final int PLAYER2 = 2;
        int player;
        private int actualPlayer = PLAYER1;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if ( (i + j) % 2 != 0 ) {

                    if (i < 4) {
                        blackPieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(j, i)));
                    }



                    else if (i > 5) {
                        whitePieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(j, i)));
                    }

            }
        }
        }

        VBox root = new VBox();
        StackPane stackPane = new StackPane();
        Pane board = new Pane();//grid
        Pane whiteAndBlackCheckers = new Pane();
        stackPane.getChildren().addAll(board,whiteAndBlackCheckers);

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(10, 10, 10, 10)); // top, right, bottom, left
        buttons.setSpacing(10);

        // Create the buttons
        Button russianGameButton = new Button("Russian Game");
        Button turkishGameButton = new Button("Turkish Game");
        Button englishGameButton = new Button("English Game");
        russianGameButton.setPrefWidth(120);
        turkishGameButton.setPrefWidth(120);
        englishGameButton.setPrefWidth(120);


        // Add the buttons to the container
        buttons.getChildren().addAll(russianGameButton, turkishGameButton, englishGameButton);


        // Add the button container to the top of the board

        // Add the tiles to the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a rectangle to represent the tile
                Rectangle rect = new Rectangle(50, 50);

                // Set the color of the tile
                if ((row + col) % 2 == 0) {
                    rect.setFill(Color.WHITE);
                }
                else {
                    rect.setFill(Color.BLACK);
                }
                rect.setX(col*50);
                rect.setY(row*50);

                // Add the rectangle to the board
                board.getChildren().add(rect);// board.add(rect, col, row);
            }
        }

        for (Piece piece : whitePieces) {
            int x = piece.getPositionx();
            int y = piece.getPositiony();
            //Ellipse wchecker = new Ellipse(20,20);
            //wchecker.setCenterX(-25+(x)*50);//-50
            //wchecker.setCenterY(-25+(y)*50);
            CheckerG whiteChecker = new CheckerG((-25+(x)*50),(-25+(y)*50),20,20);
            whiteChecker.setFill(Color.BEIGE);
            whiteChecker.setStroke(Color.DARKGRAY);
            whiteChecker.setStrokeWidth(2);
            board.getChildren().add(whiteChecker);
        }

        for (Piece piece : blackPieces) {
            int x = piece.getPositionx();
            int y = piece.getPositiony();
            //Image blackPawnImage = new Image(new FileInputStream("/Users/w/Downloads/checkers/src/main/java/pngS/blackpawn.png"));
            //ImageView ivb = new ImageView(blackPawnImage); // blackPawnImage is the image of black pawn
            //ivb.setX(x*50);
            //ivb.setY(y*50);
            //ivb.setFitHeight(50);
            //ivb.setFitWidth(50);
            CheckerG blackChecker = new CheckerG((-25+(x)*50),(-25+(y)*50),20,20);
            //Ellipse bchecker = new Ellipse(20,20);
            //bchecker.setCenterX(-25+(x)*50);
            //bchecker.setCenterY(-25+(y)*50);
            blackChecker.setStroke(Color.BEIGE);
            blackChecker.setStrokeWidth(2);
            board.getChildren().add(blackChecker);
        }


        // Add the button container to the top of the board
        root.getChildren().addAll(buttons, stackPane, whiteAndBlackCheckers);


        Scene scene = new Scene(root);//root=scene
        primaryStage.setScene(scene);//root=scene
        primaryStage.show();
    }
    public void actionPerformed(ActionEvent event) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            send();
        }
    }
    private void send(){
        // Wysylanie do serwera
        out.println(input.getText());
        actualPlayer = player;
    }

    public void listenSocket() {
        try {
            Socket socket = new Socket("localhost", 4444);
            // Inicjalizacja wysylania do serwera
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Inicjalizacja odbierania z serwera
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    /*
        Poczatkowe ustawienia klienta. Ustalenie ktory socket jest ktorym kliente
    */
    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            if (player== PLAYER1) {
                msg.setText("My Turn");
            } else {
                msg.setText("Opposite turn");
                send.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }
    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }

    @Override
    public void run() {
        if (player==PLAYER1) {
            f(1);
        }
        else{
            f(2);
        }
        // Mozna zrobic w jednej metodzie. Zostawiam
        // dla potrzeb prezentacji
        // f(player);
    }
    void f(int iPlayer){
        while(true) {
            synchronized (this) {
                if (actualPlayer== iPlayer) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }
}



