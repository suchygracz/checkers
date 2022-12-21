package onBoard;

import javafx.util.Pair;

public class Piece {
    private int ID;
    static enum color{white, black};
    private Piece.color color;
    private Pair<Integer, Integer> pos;
    public Piece(Piece.color color, Pair<Integer, Integer> pos) {
        this.color = color;
        this.pos = pos;
    }
    public Pair<Integer, Integer> getPos() {return pos;}
    public void setPos(Pair<Integer, Integer> pos ) {
        this.pos = pos;
    }
    public Piece.color getColor() {
        return color;
    }
    public void setColor(Piece.color color) {
        this.color = color;
    }
}
