package checkersRules;

import java.util.concurrent.TransferQueue;

public class EnglishGame implements AbstractGame{
    public boolean doYouHaveToBeat = true;
    public boolean canYouMoveBackwardsWithNormalPieces = false;
    public boolean canYouBeatBackwards = false;
    public boolean canKingMoveMultipleFields = false;

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
