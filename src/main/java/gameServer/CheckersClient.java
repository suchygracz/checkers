package gameServer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class CheckersClient extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        GridPane board = new GridPane();
        board.setPadding(new Insets(0,0,0,0));

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
                } else {
                    rect.setFill(Color.BLACK);
                }

                // Add the rectangle to the board
                board.add(rect, col, row);
            }
        }

        // Add the button container to the top of the board
        root.getChildren().addAll(buttons, board);


        Scene scene = new Scene(root);//root=scene
        primaryStage.setScene(scene);//root=scene
        primaryStage.show();
    }
}


