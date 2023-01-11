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
import javafx.util.Pair;
import onBoard.Pawn;
import onBoard.Piece;

import java.io.FileNotFoundException;
import java.util.Vector;

public class CheckersClient extends Application {

    private void initializeBoard(Vector<Piece> whitePieces, Vector<Piece> blackPieces, Pane board)
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
                if ( (i + j) % 2 != 0 )
                {
                    if (i < 4) {
                        blackPieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(j, i)));
                    }
                    else if (i > 5) {
                        whitePieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(j, i)));
                    }
                }
            }
        }

        for (Piece piece : whitePieces) {
            int x = piece.getPositionx();
            int y = piece.getPositiony();
            CheckerG whiteChecker = new CheckerG((-25+(x)*50),(-25+(y)*50),20,20, Piece.color.white);
            whiteChecker.setFill(Color.BEIGE);
            whiteChecker.setStroke(Color.DARKGRAY);
            whiteChecker.setStrokeWidth(2);
            board.getChildren().add(whiteChecker);
        }

        for (Piece piece : blackPieces) {
            int x = piece.getPositionx();
            int y = piece.getPositiony();
            CheckerG blackChecker = new CheckerG((-25+(x)*50),(-25+(y)*50),20,20, Piece.color.black);
            blackChecker.setStroke(Color.BEIGE);
            blackChecker.setStrokeWidth(2);
            board.getChildren().add(blackChecker);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Vector<Piece> whitePieces = new Vector<>(12);
        Vector<Piece> blackPieces = new Vector<>(12);

        VBox root = new VBox();
        StackPane stackPane = new StackPane();
        Pane board = new Pane();//grid
        Pane whiteAndBlackCheckers = new Pane();
        stackPane.getChildren().addAll(board,whiteAndBlackCheckers);
        initializeBoard(whitePieces, blackPieces, board);
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

        buttons.getChildren().addAll(russianGameButton, turkishGameButton, englishGameButton);
        root.getChildren().addAll(buttons, stackPane, whiteAndBlackCheckers);

        Scene scene = new Scene(root);//root=scene
        primaryStage.setScene(scene);//root=scene
        primaryStage.show();
    }

}


