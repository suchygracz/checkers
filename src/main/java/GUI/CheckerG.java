package GUI;

import gameServer.CheckersClient;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.util.Pair;
import onBoard.Piece;

import java.io.IOException;
import java.io.PrintWriter;

public class CheckerG extends Ellipse {
    final Piece.color color;
    private final CheckersClient client;
    private final PrintWriter out;
    public CheckerG(double xCenter, double yCenter, double radiusWOX, double radiusWOY, Piece.color color, PrintWriter out, CheckersClient client){
        super(xCenter, yCenter, radiusWOX, radiusWOY);
        setOnMouseReleased(new CheckerGEventHandler());
        this.out = out;
        this.color = color;
        this.client = client;
    }
    public Pair<Integer, Integer> getPos()
    {
        return new Pair<>((int)getCenterX(), (int)getCenterY());
    }
    public void setPos(int PosX, int PosY)
    {
        setCenterX(PosX);
        setCenterY(PosY);
    }
    private boolean isHit(double x, double y){
        return getBoundsInLocal().contains(x, y);
    }

    private int addX(double x){
        out.println((int)getCenterX());
        int X = (int) (getCenterX() + x);
        int result = (X / 50) * 50 + 25;
        out.println(result);
        return result;
    }

    private int addY(double y){
        out.println((int)getCenterY());
        int Y = (int) (getCenterY() + y);
        int result = (Y / 50) * 50 + 25;
        out.println(result);
        return result;
    }

    class CheckerGEventHandler implements EventHandler<MouseEvent> {
        CheckerG checkerG;
        private double x;
        private double y;
        public void moveChecker(MouseEvent mouseEvent) throws IOException {
            double dx = mouseEvent.getX() - x;
            double dy = mouseEvent.getY() - y;
            x += dx;
            y += dy;
            if (checkerG.isHit(x - dx, y - dy)) {
                int CenterX = checkerG.addX(dx);
                int CenterY = checkerG.addY(dy);
                if(client.getIn().readLine().equals("possible")) {
                    setCenterX(CenterX);
                    setCenterY(CenterY);
                    client.receiveKill();
                    client.changeState();
                }
            }
        }

        public void handle(MouseEvent mouseEvent) {
            checkerG = (CheckerG) mouseEvent.getSource();

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                try {
                    moveChecker(mouseEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
