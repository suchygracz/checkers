package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import onBoard.Piece;

public class CheckerG extends Ellipse {
    final Piece.color color;
    public CheckerG(double xCenter, double yCenter, double radiusWOX, double radiusWOY, Piece.color color){
        super(xCenter, yCenter, radiusWOX, radiusWOY);
        setOnMouseReleased(new CheckerGEventHandler());
        this.color = color;
    }
    public boolean isHit(double x, double y){
        return getBoundsInLocal().contains(x, y);
    }

    public void addX(double x){
        int X = (int) (getCenterX() + x);
        setCenterX((X / 50) * 50 + 25);
    }

    public void addY(double y){
        int Y = (int) (getCenterY() + y);
        setCenterY((Y / 50) * 50 + 25)  ;
    }

    class CheckerGEventHandler implements EventHandler<MouseEvent> {
        CheckerG checkerG;
        private double x;
        private double y;

        public void moveChecker(MouseEvent mouseEvent) {
            double dx = mouseEvent.getX() - x;
            double dy = mouseEvent.getY() - y;

            if (checkerG.isHit(x, y)) {
                checkerG.addX(dx);
                checkerG.addY(dy);
            }
            x += dx;
            y += dy;
        }

        public void handle(MouseEvent mouseEvent) {
            checkerG = (CheckerG) mouseEvent.getSource();

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                moveChecker(mouseEvent);
            }

        }
    }
}
