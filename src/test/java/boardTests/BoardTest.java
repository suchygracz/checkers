package boardTests;

import checkersRules.EnglishGame;
import javafx.util.Pair;
import onBoard.Board;
import onBoard.Piece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTest{
    private checkersRules.AbstractGame AbstractGame;
    private EnglishGame englishGame = new EnglishGame();
    Board board = new Board(englishGame);
    @Test
    public void simpleTest(){
        assertEquals(board.getClass(), Board.class);
    }
    @Test
    public void pawnCantMoveFurtherThanOne(){
        Piece piece = board.getWhitePiece(new Pair<Integer, Integer>(3, 6));
        assertFalse(board.moveWhitePiece(piece, new Pair<Integer, Integer>(5, 3)).getKey());//movePiece --> moveWhitePiece
    }
    @Test
    public void pawnCanMoveByOne(){
        Piece piece = board.getWhitePiece(new Pair<Integer, Integer>(3, 6));
        assertTrue(board.isJumpLengthEnough(piece, new Pair<Integer, Integer>(4, 5)));
        assertTrue(board.isThisSquareFree(new Pair<Integer, Integer>(4, 5)));
        assertTrue(board.moveWhitePiece(piece, new Pair<Integer, Integer>(4, 5)).getKey());//movePiece --> moveWhitePiece
        assertTrue(board.isThisSquareFree(new Pair<Integer, Integer>(3, 6)));
    }




}