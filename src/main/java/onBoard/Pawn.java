package onBoard;

import javafx.util.Pair;

/**
 * The type Pawn.
 */
public class Pawn extends Piece{
    /**
     * Instantiates a new Pawn.
     *
     * @param color the color
     * @param pos   the pos
     */
    public Pawn(Piece.color color, Pair<Integer, Integer> pos) {
        super(color, pos);
    }
}
