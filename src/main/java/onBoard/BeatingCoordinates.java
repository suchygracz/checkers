package onBoard;

import javafx.util.Pair;

public class BeatingCoordinates {
    public final boolean ifTrue;
    public final Pair<Integer, Integer> nextPos;
    public final Pair<Integer, Integer> lostPiece;
    public BeatingCoordinates(final boolean ifTrue, final Pair<Integer, Integer> nextPos, Pair<Integer, Integer> lostPiece)
    {
        this.ifTrue = ifTrue;
        this.nextPos = nextPos;
        this.lostPiece = lostPiece;
    }
}