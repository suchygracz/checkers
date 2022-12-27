package boardTests;

import javafx.util.Pair;
import onBoard.Board;
import onBoard.King;
import onBoard.Pawn;
import onBoard.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest{
    Board board = new Board();
    @Test
    public void simpleTest(){
        assertEquals(board.getClass(), Board.class);
    }
    @Test
    public void pawnCantMoveFurtherThanOne(){
        Piece piece = board.getWhitePiece(new Pair<Integer, Integer>(3, 1));
        assertFalse(board.movePiece(piece, new Pair<Integer, Integer>(5, 3)));
    }
    @Test
    public void pawnCanMoveByOne(){
        Piece piece = board.getWhitePiece(new Pair<Integer, Integer>(3, 1));
        assertTrue(board.isJumpLengthEnough(piece, new Pair<Integer, Integer>(4, 2)));
        assertTrue(board.isThisSquareFree(new Pair<Integer, Integer>(4, 2)));
        assertTrue(board.movePiece(piece, new Pair<Integer, Integer>(4, 2)));
        assertTrue(board.isThisSquareFree(new Pair<Integer, Integer>(3, 1)));
    }
    @Test
    public void exchangeForKingShouldWork(){
        Piece piece = new Pawn(Piece.color.white, new Pair<Integer, Integer>(7, 1));
        assertTrue(board.movePiece(piece, new Pair<Integer, Integer>(8, 2)));
        assertEquals(board.getWhitePiece(new Pair<Integer, Integer>(8, 2)).getClass(), King.class);
    }
}