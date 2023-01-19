package boardTests;

import checkersRules.AbstractGame;
import checkersRules.EnglishGame;
import checkersRules.RussianGame;
import checkersRules.TurkishGame;
import factory.RulesFactory;
import gameServer.Game;
import javafx.util.Pair;
import onBoard.Board;
import onBoard.King;
import onBoard.Pawn;
import onBoard.Piece;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.*;
import java.net.Socket;

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
        Piece piece = board.getWhitePiece(new Pair<Integer, Integer>(3, 1));
        assertFalse(board.moveWhitePiece(piece, new Pair<Integer, Integer>(5, 3)).getKey());//movePiece --> moveWhitePiece
    }
    @Test
    public void pawnCanMoveByOne(){
        Piece piece = board.getWhitePiece(new Pair<Integer, Integer>(3, 1));
        assertTrue(board.isJumpLengthEnough(piece, new Pair<Integer, Integer>(4, 2)));
        assertTrue(board.isThisSquareFree(new Pair<Integer, Integer>(4, 2)));
        assertTrue(board.moveWhitePiece(piece, new Pair<Integer, Integer>(4, 2)).getKey());//movePiece --> moveWhitePiece
        assertTrue(board.isThisSquareFree(new Pair<Integer, Integer>(3, 1)));
    }
    @Test
    public void exchangeForKingShouldWork(){
        Piece piece = new Pawn(Piece.color.white, new Pair<Integer, Integer>(7, 1));
        assertTrue(board.moveWhitePiece(piece, new Pair<Integer, Integer>(8, 2)).getKey());//movePiece --> moveWhitePiece
        assertEquals(board.getWhitePiece(new Pair<Integer, Integer>(8, 2)).getClass(), King.class);
    }

    @Test
    public void rulesFactoryTest(){
        RulesFactory factory = new RulesFactory();

        AbstractGame englishGame = factory.create("EnglishGame");
        assertEquals(englishGame.getClass(), EnglishGame.class);

        AbstractGame russianGame = factory.create("RussianGame");
        assertEquals(russianGame.getClass(), RussianGame.class);

        AbstractGame turkishGame = factory.create("TurkishGame");
        assertEquals(turkishGame.getClass(), TurkishGame.class);
    }

    @Test
    public void testRun() throws IOException {
        //mock the socket
        Socket firstPlayer = mock(Socket.class);
        Socket secondPlayer = mock(Socket.class);

        InputStream inputFirstPlayer = new ByteArrayInputStream("EnglishGame".getBytes());
        BufferedReader bufforFirstPlayer = new BufferedReader(new InputStreamReader(inputFirstPlayer));
        InputStream inputSecondPlayer = new ByteArrayInputStream("1\n2\n3\n4\n5\n6\n7\n8\n".getBytes());
        BufferedReader bufforSecondPlayer = new BufferedReader(new InputStreamReader(inputSecondPlayer));
        OutputStream outputFirstPlayer = new ByteArrayOutputStream();
        PrintWriter printerFirstPlayer = new PrintWriter(outputFirstPlayer);
        OutputStream outputSecondPlayer = new ByteArrayOutputStream();
        PrintWriter printerSecondPlayer = new PrintWriter(outputSecondPlayer);

        when(firstPlayer.getInputStream()).thenReturn(inputFirstPlayer);
        when(firstPlayer.getOutputStream()).thenReturn(outputFirstPlayer);
        when(secondPlayer.getInputStream()).thenReturn(inputSecondPlayer);
        when(secondPlayer.getOutputStream()).thenReturn(outputSecondPlayer);

        Game game = new Game(firstPlayer, secondPlayer);
        game.run();

        //verify the output is as expected
        printerFirstPlayer.flush();
        assertEquals("1\npossible\n1\n2\n3\n4\n", outputFirstPlayer.toString());
        printerSecondPlayer.flush();
        assertEquals("2\npossible\n5\n6\n7\n8\n", outputSecondPlayer.toString());

    }
}