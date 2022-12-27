package onBoard;

import javafx.util.Pair;

public class Piece {
    public static enum color{white, black};
    private final Piece.color color;
    private final static int jumpLength = 1;
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
    public int getJumpLength()
    {
        return jumpLength;
    }
}
