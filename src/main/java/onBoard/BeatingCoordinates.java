package onBoard;

import javafx.util.Pair;

/**
 * The type Beating coordinates.
 */
public class BeatingCoordinates {
    /**
     * The Next pos.
     */
    public final Pair<Integer, Integer> nextPos;
    /**
     * The Lost piece.
     */
    public final Pair<Integer, Integer> lostPiece;

    /**
     * Instantiates a new Beating coordinates.
     *
     * @param nextPos   the next pos
     * @param lostPiece the lost piece
     */
    public BeatingCoordinates(final Pair<Integer, Integer> nextPos, Pair<Integer, Integer> lostPiece)
    {
        this.nextPos = nextPos;//gdzie sie trzeba ruszyć zeby zbic
        this.lostPiece = lostPiece;//koordynaty zbitego
    }
}
