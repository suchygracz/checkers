package gameServer;

import GUI.CheckerG;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import onBoard.Piece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Objects;
import java.util.Vector;

/**
 * The type Checkers client.
 */
public class CheckersClient extends Application implements Runnable{

    private int player;

    /**
     * The Root.
     */
    VBox root = new VBox();
    /**
     * The Board.
     */
    Pane board = new Pane();
    /**
     * The Buttons.
     */
    HBox buttons = new HBox();
    /**
     * The Stack pane.
     */
    StackPane stackPane = new StackPane();

    /**
     * The Socket.
     */
    Socket socket = null;
    /**
     * The Out.
     */
    PrintWriter out = null; //out.println
    /**
     * The In.
     */
    BufferedReader in = null;

    /**
     * The Russian game button.
     */
    Button russianGameButton = new Button("Russian Game");
    /**
     * The Turkish game button.
     */
    Button turkishGameButton = new Button("Turkish Game");
    /**
     * The English game button.
     */
    Button englishGameButton = new Button("English Game");



    //out.println("")

    /**
     * The White and black checkers.
     */
    Vector<CheckerG> whiteAndBlackCheckers = new Vector<>(24);
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
                        CheckerG blackChecker = new CheckerG((-25+(j)*50),(-25+(i)*50),20,20, Piece.color.black, out, this);
                        blackChecker.setStroke(Color.BEIGE);
                        blackChecker.setStrokeWidth(2);
                        board.getChildren().add(blackChecker);
                        whiteAndBlackCheckers.add(blackChecker);
                    }
                    else if (i > 5) {
                        CheckerG whiteChecker = new CheckerG((-25+(j)*50),(-25+(i)*50),20,20, Piece.color.white, out, this);
                        whiteChecker.setFill(Color.BEIGE);
                        whiteChecker.setStroke(Color.DARKGRAY);
                        whiteChecker.setStrokeWidth(2);
                        whiteAndBlackCheckers.add(whiteChecker);
                        board.getChildren().add(whiteChecker);
                    }
                }
            }
        }

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        stackPane.getChildren().addAll(board);
        listenSocket();
        initializeBoard();
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(10);
        russianGameButton.setPrefWidth(120);
        turkishGameButton.setPrefWidth(120);
        englishGameButton.setPrefWidth(120);

        EventHandler<MouseEvent> eventHandler = event -> {
            russianGameButton.setDisable(true);
            turkishGameButton.setDisable(true);
            englishGameButton.setDisable(true);

            Object source = event.getSource();
            if (source == russianGameButton) {
                out.println("RussianGame");
            } else if (source == turkishGameButton) {
                out.println("TurkishGame");
            } else if (source == englishGameButton) {
                out.println("EnglishGame");
            }

            board.setDisable(false);
            startThread();
        };

        russianGameButton.setOnMouseClicked(eventHandler);
        turkishGameButton.setOnMouseClicked(eventHandler);
        englishGameButton.setOnMouseClicked(eventHandler);

        buttons.getChildren().addAll(russianGameButton, turkishGameButton, englishGameButton);
        root.getChildren().addAll(buttons, stackPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkers");
        primaryStage.setResizable(false);
        primaryStage.show();

        receiveInitFromServer(primaryStage);
        if(player==2) {
            startThread();
        }
    }

    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }
    @Override
    public void run()
    {
        if (player==1) {
            try {
                f1();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                f2();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void f1() throws IOException {
        while(true) {
            synchronized (this) {
                if (board.isDisable()){
                    receive();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                receiveKill();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    board.setDisable(false);
                }
                notifyAll();
            }
        }
    }
    private void f2() throws IOException {
        while(true) {
            synchronized (this) {
                if (board.isDisable()){
                    receive();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                receiveKill();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    board.setDisable(false);
                }
                notifyAll();
            }
        }
    }

    /**
     * Receive kill.
     *
     * @throws IOException the io exception
     */
    public void receiveKill() throws IOException {
        if(Objects.equals(in.readLine(), "kill"))
        {
            int posX = Integer.parseInt(in.readLine());
            int posY = Integer.parseInt(in.readLine());
            board.getChildren().remove(findChecker(new Pair<>(-25 + posX * 50, -25 + posY * 50)));
            whiteAndBlackCheckers.remove(findChecker(new Pair<>(-25 + posX * 50, -25 + posY * 50)));
        }
    }
    private void receive(){
        try {
            int OldX, OldY, NewX, NewY;
            OldX = Integer.parseInt(in.readLine());
            NewX = Integer.parseInt(in.readLine());
            OldY = Integer.parseInt(in.readLine());
            NewY = Integer.parseInt(in.readLine());
            findChecker(new Pair<>(OldX, OldY)).setPos(NewX, NewY);
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

    /**
     * Change state.
     */
    public void changeState(){
        board.setDisable(true);
    }

    /**
     * Find checker checker g.
     *
     * @param pos the pos
     * @return the checker g
     */
    public CheckerG findChecker(Pair<Integer, Integer> pos)
    {
        for(CheckerG check : whiteAndBlackCheckers)
        {
            if(Objects.equals(check.getPos(), pos)) return check;
        }
        return null;
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
    private void receiveInitFromServer(Stage primaryStage) {
        try {
            player = Integer.parseInt(in.readLine());
            if(player != 1)
            {
                root.getChildren().remove(0);
                primaryStage.setHeight(430);
            }
            board.setDisable(true);
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    /**
     * Gets in.
     *
     * @return the in
     */
    public BufferedReader getIn()
    {
        return in;
    }

}




