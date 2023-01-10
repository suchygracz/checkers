package checkersRules;

public class EnglishGame implements AbstractGame{
    @Override
    public final boolean getDoYouHaveToBeat() {
        return true;
    }

    @Override
    public final boolean getCanYouMoveBackwardsWithNormalPieces() {
        return false;
    }

    @Override
    public final boolean getCanYouBeatBackwards() {
        return false;
    }

    @Override
    public final boolean getCanKingMoveMultipleFields() {
        return false;
    }
}
