package onBoard;

import javafx.util.Pair;
import java.util.Vector;

public class Board {
    Vector<Piece> whitePieces = new Vector<>(12);
    Vector<Piece> blackPieces = new Vector<>(12);
    public Board()
    {
        fillTheBoard();
    }
    public boolean movePiece(Piece piece, Pair<Integer, Integer> pos){
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
    public boolean doYouHaveToBeatWhite()
    {
        int posX;
        int posY;
        for(Piece piece : blackPieces)
        {
            posX = piece.getPos().getKey();
            posY = piece.getPos().getValue();
            if(getWhitePiece(new Pair<>(posX + 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY + 2))) return true;
            if(getWhitePiece(new Pair<>(posX - 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY + 2))) return true;
            if(getWhitePiece(new Pair<>(posX + 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY - 2))) return true;
            if(getWhitePiece(new Pair<>(posX - 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY - 2))) return true;
        }

        return false;
    }
    public boolean doYouHaveToBeatBlack()
    {
        int posX;
        int posY;
        for(Piece piece : whitePieces)
        {
            posX = piece.getPos().getKey();
            posY = piece.getPos().getValue();
            if(getBlackPiece(new Pair<>(posX + 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY + 2))) return true;
            if(getBlackPiece(new Pair<>(posX - 1, posY + 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY + 2))) return true;
            if(getBlackPiece(new Pair<>(posX + 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX + 2, posY - 2))) return true;
            if(getBlackPiece(new Pair<>(posX - 1, posY - 1)) != null && isThisSquareFree(new Pair<>(posX - 2, posY - 2))) return true;
        }

        return false;
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
        for(Piece piece : whitePieces)
        {
            if(piece.getPos().equals(pos)) return false;
        }

        for(Piece piece : blackPieces)
        {
            if(piece.getPos().equals(pos)) return false;
        }

        return true;
    };
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
