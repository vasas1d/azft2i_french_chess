package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;

public class ChessPieceTest {

    @Test
    public void testGetXPosition() {
        Pawn pawn = new Pawn(2, 3, Color.WHITE);
        assertEquals(2, pawn.getXPosition());
    }
    @Test
    public void testGetYPosition() {
        Pawn pawn = new Pawn(2, 3, Color.WHITE);
        assertEquals(3, pawn.getYPosition());
    }
    @Test
    public void testSetXPosition() {
        Pawn pawn = new Pawn(2, 3, Color.WHITE);
        pawn.setXPosition(4);
        assertEquals(4, pawn.getXPosition());
    }
    @Test
    public void testSetYPosition() {
        Pawn pawn = new Pawn(2, 3, Color.WHITE);
        pawn.setYPosition(5);
        assertEquals(5, pawn.getYPosition());
    }
    @Test
    public void testSetColor() {
        Pawn pawn = new Pawn(2, 3, Color.WHITE);
        pawn.setColor(Color.BLACK);
        assertEquals(Color.BLACK, pawn.getColor());
    }
}
