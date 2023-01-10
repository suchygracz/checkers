package checkersRules;

public class TurkishGame implements AbstractGame{
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
        return true;
    }
}
