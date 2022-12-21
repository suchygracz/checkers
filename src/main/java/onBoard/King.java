package onBoard;

import javafx.util.Pair;

public class King extends Piece {
    private final static int jumpLength = 10;
    public King(Piece.color color, Pair<Integer, Integer> pos) {
        super(color, pos);
    }
    public King(Piece pawn)
    {
        super(pawn.getColor(), pawn.getPos());
    }
}
