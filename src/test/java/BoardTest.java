import onBoard.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest{
    @Test
    public void simpleTest(){
        Board b = new Board();
        assertEquals(b.getClass(), Board.class);
    }
}