package checkersRules;

public class RussianGame implements AbstractGame{
    @Override
    public final boolean getDoYouHaveToBeat() {
        return true;
    }

    @Override
    public final boolean getCanYouMoveBackwardsWithNormalPieces() {
        return true;
    }

    @Override
    public final boolean getCanYouBeatBackwards() {
        return true;
    }

    @Override
    public final boolean getCanKingMoveMultipleFields() {
        return true;
    }
}
