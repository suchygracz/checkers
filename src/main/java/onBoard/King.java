package onBoard;

import javafx.util.Pair;

/**
 * The type King.
 */
public class King extends Piece {
    private static int jumpLength = 10;

    /**
     * Instantiates a new King.
     *
     * @param color the color
     * @param pos   the pos
     */
    public King(Piece.color color, Pair<Integer, Integer> pos) {
        super(color, pos);
    }

    /**
     * Instantiates a new King.
     *
     * @param pawn the pawn
     */
    public King(Piece pawn)
    {
        super(pawn.getColor(), pawn.getPos());
    }

    /**
     * Sets jump length.
     *
     * @param jumpLength the jump length
     */
    public static void setJumpLength(int jumpLength) {
        King.jumpLength = jumpLength;
    }
}
