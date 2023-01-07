package checkersRules;

public class RussianGame implements AbstractGame{
    public boolean doYouHaveToBeat = true;
    public boolean canYouMoveBackwardsWithNormalPieces = true;
    public boolean canYouBeatBackwards = true;
    public boolean canKingMoveMultipleFields = true;
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
    public boolean getCanKingMoveMultipleFields() {
        return canKingMoveMultipleFields;
    }
}
