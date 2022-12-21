package onBoard;

import onBoard.Piece;

import java.util.Vector;

public class Board {
    Vector<Piece> whitePieces = new Vector<>(12);
    Vector<Piece> blackPieces = new Vector<>(12);
    public Board()
    {
        for(int i = 0; i < 12; i++)
        {
            whitePieces.add(new Pawn(Piece.color.white, 1, 1));
        }
    }
}
