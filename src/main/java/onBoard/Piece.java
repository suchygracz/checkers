package onBoard;

import javafx.util.Pair;

/**
 * The type Piece.
 */
public class Piece {


    /**
     * The enum Color.
     */
    public enum color{
        /**
         * White color.
         */
        white,
        /**
         * Black color.
         */
        black}
    private final Piece.color color;
    private final static int jumpLength = 1;
    private Pair<Integer, Integer> pos;

    /**
     * Instantiates a new Piece.
     *
     * @param color the color
     * @param pos   the pos
     */
    public Piece(Piece.color color, Pair<Integer, Integer> pos) {
        this.color = color;
        this.pos = pos;
    }

    /**
     * Gets pos.
     *
     * @return the pos
     */
    public Pair<Integer, Integer> getPos() {return pos;}

    /**
     * Sets pos.
     *
     * @param pos the pos
     */
    public void setPos(Pair<Integer, Integer> pos) {
        this.pos = pos;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Piece.color getColor() {
        return color;
    }

    /**
     * Gets jump length.
     *
     * @return the jump length
     */
    public int getJumpLength()
    {
        return jumpLength;
    }
}
