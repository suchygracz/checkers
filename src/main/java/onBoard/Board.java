package onBoard;

import checkersRules.AbstractGame;
import javafx.util.Pair;
import java.util.Vector;

public class Board {
    Vector<Piece> whitePieces = new Vector<>(12);
    Vector<Piece> blackPieces = new Vector<>(12);
    private final AbstractGame gameType;
    public Board(AbstractGame gameType)
    {
        fillTheBoard();
        this.gameType = gameType;
        if(!gameType.getCanKingMoveMultipleFields())
        {
            King.setJumpLength(1);
        }
    }
    public Pair<Boolean, Pair<Integer, Integer>> moveWhitePiece(Piece piece, Pair<Integer, Integer> pos){
        BeatingCoordinates beat = doYouHaveToBeatBlack();
        if(beat.ifTrue)
        {
            if(pos.equals(beat.nextPos))
            {
                piece.setPos(beat.nextPos);
                killBlack(beat.lostPiece);
                return new Pair<>(true, beat.lostPiece);
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
    public Pair<Boolean, Pair<Integer, Integer>> moveBlackPiece(Piece piece, Pair<Integer, Integer> pos){
        BeatingCoordinates beat = doYouHaveToBeatWhite();
        if(beat.ifTrue)
        {
            if(pos.equals(beat.nextPos))
            {
                piece.setPos(beat.nextPos);
                killWhite(beat.lostPiece);
                return new Pair<>(true, beat.lostPiece);
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
    public Piece getBlackPiece(Pair<Integer, Integer> pos)
    {
        for(Piece piece : blackPieces)
        {
            if(piece.getPos().equals(pos)) return piece;
        }
        return null;
    }
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
            if(piece.getClass() == Pawn.class && pos.getKey() == 8)
            {
                exchangeForKing(piece);
            }
            return true;
        }
        return false;
    }

    private BeatingCoordinates doYouHaveToBeatWhite()
    {
        int posX;
        int posY;
        for(Piece piece : blackPieces)
        {
            posX = piece.getPos().getKey();
            posY = piece.getPos().getValue();
            if(gameType.getCanYouBeatBackwards())
            {
                if(getWhitePiece(new Pair<>(posX + 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY + 2)))
                    return new BeatingCoordinates(true, new Pair<>(posX + 2, posY + 2), new Pair<>(posX + 1, posY + 1));
                if(getWhitePiece(new Pair<>(posX - 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY + 2)))
                    return new BeatingCoordinates(true, new Pair<>(posX - 2, posY + 2), new Pair<>(posX - 1, posY + 1));
            }
            if(getWhitePiece(new Pair<>(posX + 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY - 2)))
                return new BeatingCoordinates(true, new Pair<>(posX + 2, posY - 2), new Pair<>(posX + 1, posY - 1));
            if(getWhitePiece(new Pair<>(posX - 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY - 2)))
                return new BeatingCoordinates(true, new Pair<>(posX - 2, posY - 2), new Pair<>(posX - 1, posY - 1));
        }

        return new BeatingCoordinates(false, null, null);
    }
    private BeatingCoordinates doYouHaveToBeatBlack()
    {
        int posX;
        int posY;
        for(Piece piece : whitePieces)
        {
            posX = piece.getPos().getKey();
            posY = piece.getPos().getValue();
            if(getBlackPiece(new Pair<>(posX + 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY + 2)))
                return new BeatingCoordinates(true, new Pair<>(posX + 2, posY + 2), new Pair<>(posX + 1, posY + 1));
            if(getBlackPiece(new Pair<>(posX - 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY + 2)))
                return new BeatingCoordinates(true, new Pair<>(posX - 2, posY + 2), new Pair<>(posX - 1, posY + 1));
            if(gameType.getCanYouBeatBackwards())
            {
                if(getBlackPiece(new Pair<>(posX + 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY - 2)))
                    return new BeatingCoordinates(true, new Pair<>(posX + 2, posY - 2), new Pair<>(posX + 1, posY - 1));
                if(getBlackPiece(new Pair<>(posX - 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY - 2)))
                    return new BeatingCoordinates(true, new Pair<>(posX - 2, posY - 2), new Pair<>(posX - 1, posY - 1));
            }
        }

        return new BeatingCoordinates(false, null, null);
    }
    private void killWhite(Pair<Integer, Integer> pos){
        whitePieces.remove(getWhitePiece(pos));
    }
    private void killBlack(Pair<Integer, Integer> pos){
        blackPieces.remove(getBlackPiece(pos));
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
    public boolean isJumpLengthEnough(Piece piece, Pair<Integer, Integer> pos) {
        int posXDifference = Math.abs(pos.getKey() - piece.getPos().getKey());
        int posYDifference = Math.abs(pos.getValue() - piece.getPos().getValue());
        int jumpLength = piece.getJumpLength();
        return (posXDifference <= jumpLength && posYDifference <= jumpLength && posYDifference > 0 && posXDifference > 0);
    }
    private King exchangeForKing(Piece piece)
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
        return king;
    }

}
