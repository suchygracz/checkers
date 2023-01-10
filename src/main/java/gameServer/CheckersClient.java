package gameServer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CheckersClient extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane board = new GridPane();

        // Add the tiles to the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a rectangle to represent the tile
                Rectangle rect = new Rectangle(50, 50);

                // Set the color of the tile
                if ((row + col) % 2 == 0) {
                    rect.setFill(Color.WHITE);
                } else {
                    rect.setFill(Color.BLACK);
                }

                // Add the rectangle to the board
                board.add(rect, col, row);
            }
        }

        Scene scene = new Scene(board);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


