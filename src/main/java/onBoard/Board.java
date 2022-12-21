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
    public void movePiece(Piece piece, Pair<Integer, Integer> pos){
        if(isThisSquareFree(pos)) {
            piece.setPos(pos);
        }
    }
    private void fillTheBoard()
    {
        for (int i = 1; i < 9; i++)
        {
            for (int j = 1; j < 9; j++)
            {
                if ( (i + j) % 2 == 0 ) {
                    whitePieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(i, j)));
                } else {
                    blackPieces.add(new Pawn(Piece.color.white, new Pair<Integer, Integer>(i, j)));
                }
            }
        }
    }
    private boolean isThisSquareFree(Pair<Integer, Integer> pos)
    {
        for(Piece piece : whitePieces)
        {
            if(piece.getPos() == pos) return false;
        }

        for(Piece piece : blackPieces)
        {
            if(piece.getPos() == pos) return false;
        }

        return true;
    }
}
