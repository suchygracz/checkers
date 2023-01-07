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
    private void fillTheBoard()
    {
        for (int i = 1; i < 9; i++)
        {
            for (int j = 1; j < 9; j++)
            {
                if ( (i + j) % 2 == 0 ) {
                    if(i < 4) {
                        whitePieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(i, j)));
                    }
                } else if (i > 5){
                    blackPieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(i, j)));
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
    }
    public boolean isJumpLengthEnough(Piece piece, Pair<Integer, Integer> pos) {
        int posX = piece.getPos().getKey();
        int posY = piece.getPos().getValue();
        int posXNew = pos.getKey();
        int posYNew = pos.getValue();
        int jumpLength = piece.getJumpLength();

        return posXNew - posX <= jumpLength && posYNew - posY <= jumpLength;
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
