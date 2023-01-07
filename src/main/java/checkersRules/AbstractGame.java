package checkersRules;

public interface AbstractGame {
    boolean getDoYouHaveToBeat();
    boolean getCanYouMoveBackwardsWithNormalPieces();
    boolean getCanYouBeatBackwards();
    boolean getCanQueenMoveMultipleFields();
}
