package onBoard;

import checkersRules.AbstractGame;
import javafx.util.Pair;
import java.util.Vector;

/**
 * The type Board.
 */
public class Board {
    /**
     * The White pieces.
     */
    Vector<Piece> whitePieces = new Vector<>(12);
    /**
     * The Black pieces.
     */
    Vector<Piece> blackPieces = new Vector<>(12);
    private final AbstractGame gameType;

    /**
     * Instantiates a new Board.
     *
     * @param gameType the game type
     */
    public Board(AbstractGame gameType)
    {
        fillTheBoard();
        this.gameType = gameType;
        if(!gameType.getCanKingMoveMultipleFields())
        {
            King.setJumpLength(1);
        }
    }

    /**
     * Move white piece pair.
     *
     * @param piece the piece
     * @param pos   the pos
     * @return the pair
     */
    public Pair<Boolean, Pair<Integer, Integer>> moveWhitePiece(Piece piece, Pair<Integer, Integer> pos){
        Pair<Boolean, Vector<BeatingCoordinates>> beat = doYouHaveToBeatBlack();
        if(beat.getKey())
        {
            BeatingCoordinates cords = findPos(pos, beat.getValue());
            if(cords != null)
            {
                piece.setPos(cords.nextPos);
                killBlack(cords.lostPiece);
                return new Pair<>(true, cords.lostPiece);
            }
            return new Pair<>(false, null);
        }
        else
        {
            if(!gameType.getCanYouMoveBackwardsWithNormalPieces())
            {
                if(piece.getPos().getValue() - pos.getValue() < 0)
                {
                    return new Pair<>(false, null);
                }
            }
            return new Pair<>(standardMove(piece, pos), null);
        }
    }

    /**
     * Move black piece pair.
     *
     * @param piece the piece
     * @param pos   the pos
     * @return the pair
     */
    public Pair<Boolean, Pair<Integer, Integer>> moveBlackPiece(Piece piece, Pair<Integer, Integer> pos){
        Pair<Boolean, Vector<BeatingCoordinates>> beat = doYouHaveToBeatWhite();
        if(beat.getKey())
        {
            BeatingCoordinates cords = findPos(pos, beat.getValue());
            if(cords != null)
            {
                piece.setPos(cords.nextPos);
                killWhite(cords.lostPiece);
                return new Pair<>(true, cords.lostPiece);
            }
            return new Pair<>(false, null);
        }
        else
        {
            if(!gameType.getCanYouMoveBackwardsWithNormalPieces())
            {
                if(piece.getPos().getValue() - pos.getValue() > 0)
                {
                    return new Pair<>(false, null);
                }
            }
            return new Pair<>(standardMove(getBlackPiece(piece.getPos()), pos), null);
        }
    }

    /**
     * Gets black piece.
     *
     * @param pos the pos
     * @return the black piece
     */
    public Piece getBlackPiece(Pair<Integer, Integer> pos)
    {
        for(Piece piece : blackPieces)
        {
            if(piece.getPos().equals(pos)) return piece;
        }
        return null;
    }

    /**
     * Gets white piece.
     *
     * @param pos the pos
     * @return the white piece
     */
    public Piece getWhitePiece(Pair<Integer, Integer> pos)
    {
        for(Piece piece : whitePieces)
        {
            if(piece.getPos().equals(pos)) return piece;
        }
        return null;
    }
    private boolean standardMove(Piece piece, Pair<Integer, Integer> pos)
    {
        if(isThisSquareFree(pos) && isJumpLengthEnough(piece, pos)) {
            piece.setPos(pos);
            if(piece.getClass() == Pawn.class && pos.getKey() == 1)
            {
                exchangeForKing(piece);
            }
            return true;
        }
        return false;
    }
    private Pair<Boolean, Vector<BeatingCoordinates>> doYouHaveToBeatWhite()
    {
        int posX;
        int posY;
        Vector<BeatingCoordinates> results = new Vector<>();
        for(Piece piece : blackPieces)
        {
            posX = piece.getPos().getKey();
            posY = piece.getPos().getValue();

            if(getWhitePiece(new Pair<>(posX + 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY + 2))) {
                results.add(new BeatingCoordinates(new Pair<>(posX + 2, posY + 2), new Pair<>(posX + 1, posY + 1)));
                //return new BeatingCoordinates(true, new Pair<>(posX + 2, posY + 2), new Pair<>(posX + 1, posY + 1));
            }
            if(getWhitePiece(new Pair<>(posX - 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY + 2))) {
                results.add(new BeatingCoordinates(new Pair<>(posX - 2, posY + 2), new Pair<>(posX - 1, posY + 1)));
                //return new BeatingCoordinates(true, new Pair<>(posX - 2, posY + 2), new Pair<>(posX - 1, posY + 1));
            }
            if(gameType.getCanYouBeatBackwards()) {
                if (getWhitePiece(new Pair<>(posX + 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY - 2))) {
                    results.add(new BeatingCoordinates(new Pair<>(posX + 2, posY - 2), new Pair<>(posX + 1, posY - 1)));
                    //return new BeatingCoordinates(true, new Pair<>(posX + 2, posY - 2), new Pair<>(posX + 1, posY - 1));
                }
                if (getWhitePiece(new Pair<>(posX - 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY - 2))) {
                    results.add(new BeatingCoordinates(new Pair<>(posX - 2, posY - 2), new Pair<>(posX - 1, posY - 1)));
                    //return new BeatingCoordinates(true, new Pair<>(posX - 2, posY - 2), new Pair<>(posX - 1, posY - 1));
                }
            }
        }

        if(!results.isEmpty()) return new Pair<>(true, results);
        return new Pair<>(false, new Vector<BeatingCoordinates>());
    }
    private Pair<Boolean, Vector<BeatingCoordinates>> doYouHaveToBeatBlack()
    {
        int posX;
        int posY;
        Vector<BeatingCoordinates> results = new Vector<>();
        for(Piece piece : whitePieces)
        {
            posX = piece.getPos().getKey();
            posY = piece.getPos().getValue();
            if(gameType.getCanYouBeatBackwards()) {
                if (getBlackPiece(new Pair<>(posX + 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY + 2)))
                    results.add(new BeatingCoordinates(new Pair<>(posX + 2, posY + 2), new Pair<>(posX + 1, posY + 1)));
                if (getBlackPiece(new Pair<>(posX - 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY + 2)))
                    results.add(new BeatingCoordinates(new Pair<>(posX - 2, posY + 2), new Pair<>(posX - 1, posY + 1)));
            }
            if(getBlackPiece(new Pair<>(posX + 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY - 2)))
                results.add(new BeatingCoordinates(new Pair<>(posX + 2, posY - 2), new Pair<>(posX + 1, posY - 1)));
            if(getBlackPiece(new Pair<>(posX - 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY - 2)))
                results.add(new BeatingCoordinates(new Pair<>(posX - 2, posY - 2), new Pair<>(posX - 1, posY - 1)));
        }

        if(!results.isEmpty()) return new Pair<>(true, results);
        return new Pair<>(false, new Vector<BeatingCoordinates>());
    }
    private void killWhite(Pair<Integer, Integer> pos){
        whitePieces.remove(getWhitePiece(pos));
    }
    private void killBlack(Pair<Integer, Integer> pos){
        blackPieces.remove(getBlackPiece(pos));
    }
    private BeatingCoordinates findPos(Pair<Integer, Integer> pos, Vector<BeatingCoordinates> cords)
    {
        for(BeatingCoordinates cord : cords)
        {
            if(cord.nextPos.equals(pos))
            {
                return cord;
            }
        }

        return null;
    }

    private void fillTheBoard()
    {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if ( (i + j) % 2 != 0 ) {
                    if (i < 4) {
                        blackPieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(j, i)));
                    }
                    else if (i > 5) {
                        whitePieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(j, i)));
                    }

                }
            }
        }
    }

    /**
     * Is this square free boolean.
     *
     * @param pos the pos
     * @return the boolean
     */
    public boolean isThisSquareFree(Pair<Integer, Integer> pos)
    {
        if(pos.getKey() > 0 && pos.getKey() < 9 && pos.getValue() > 0 && pos.getValue() < 9)
        {
            for(Piece piece : whitePieces)
            {
                if(piece.getPos().equals(pos)) return false;
            }

            for(Piece piece : blackPieces)
            {
                if(piece.getPos().equals(pos)) return false;
            }

            return true;
        }

        return false;
    };

    /**
     * Is jump length enough boolean.
     *
     * @param piece the piece
     * @param pos   the pos
     * @return the boolean
     */
    public boolean isJumpLengthEnough(Piece piece, Pair<Integer, Integer> pos) {
        int posXDifference = Math.abs(pos.getKey() - piece.getPos().getKey());
        int posYDifference = Math.abs(pos.getValue() - piece.getPos().getValue());
        int jumpLength = piece.getJumpLength();
        return (posXDifference <= jumpLength && posYDifference <= jumpLength && posYDifference > 0 && posXDifference > 0);
    }
    private void exchangeForKing(Piece piece)
    {
        King king = new King(piece);
        if(piece.getColor() == Piece.color.black)
        {
            blackPieces.remove(piece);
            blackPieces.add(king);
        }
        else
        {
            whitePieces.remove(piece);
            whitePieces.add(king);
        }
    }

}
