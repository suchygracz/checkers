package onBoard;

import onBoard.Piece;

import java.util.Vector;

public class Board {
    Vector<Piece> whitePieces = new Vector<>(12);
    Vector<Piece> blackPieces = new Vector<>(12);
    public Board()
    {
        fillTheBoard();
    }
    public void movePiece(Piece piece, int desiredPosX, int desiredPosY){
        piece.setPosX(desiredPosX);
        piece.setPosY(desiredPosY);
    }
    private void fillTheBoard()
    {
        for (int i = 1; i < 9; i++)
        {
            for (int j = 1; j < 9; j++)
            {
                if ( (i + j) % 2 == 0 ) {
                    whitePieces.add(new Pawn(Piece.color.white, i, j));
                } else {
                    blackPieces.add(new Pawn(Piece.color.white, i, j));
                }
            }
        }
    }
}
