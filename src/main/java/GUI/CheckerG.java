package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class CheckerG extends Ellipse {
    public CheckerG(double xSrodka, double ySrodka, double promienWOX, double promienWOY){
        super(xSrodka, ySrodka, promienWOX, promienWOY);
        setOnMouseDragged(new CheckerGEventHandler());


    }
    public boolean isHit(double x, double y){
        return getBoundsInLocal().contains(x, y);
    }

    public void addX(double x){
        setCenterX(getCenterX() + x);
    }

    public void addY(double y){
        setCenterY(getCenterY() + y);
    }

    class CheckerGEventHandler implements EventHandler<MouseEvent> {
        CheckerG checkerG;
        private double x;
        private double y;

        public void moveChecker(MouseEvent mouseEvent) {
            double dx = mouseEvent.getX() - x;
            double dy = mouseEvent.getY() - y;

            if (CheckerG.isHit(x, y)) {
                CheckerG.addX(dx);
                CheckerG.addY(dy);
            }
            x += dx;
            y += dy;
        }

        public void handle(MouseEvent mouseEvent) {
            checkerG = (CheckerG) mouseEvent.getSource();

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                moveChecker(mouseEvent);
            }

        }
    }
}
