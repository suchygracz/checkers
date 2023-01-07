package checkersRules;

public class TurkishGame implements AbstractGame{
    public boolean doYouHaveToBeat = true;
    public boolean canYouMoveBackwardsWithNormalPieces = false;
    public boolean canYouBeatBackwards = false;
    public boolean canQueenMoveMultipleFields = true;
    @Override
    public boolean getDoYouHaveToBeat() {
        return doYouHaveToBeat;
    }

    @Override
    public boolean getCanYouMoveBackwardsWithNormalPieces() {
        return canYouMoveBackwardsWithNormalPieces;
    }

    @Override
    public boolean getCanYouBeatBackwards() {
        return canYouBeatBackwards;
    }

    @Override
    public boolean getCanQueenMoveMultipleFields() {
        return canQueenMoveMultipleFields;
    }
}
