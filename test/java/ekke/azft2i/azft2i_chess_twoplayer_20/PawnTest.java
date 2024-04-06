package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;

public class PawnTest {

    @Test
    public void testGetPieceName() {
        Pawn pawn = new Pawn(1, 1, Color.WHITE);
        assertEquals("Gyalog", pawn.getPieceName());
    }
    @Test
    public void testGetSymbol() {
        Pawn pawn = new Pawn(1, 1, Color.WHITE);
        assertEquals("P", pawn.getSymbol());
    }
    @Test
    public void testGetColor() {
        Pawn whitePawn = new Pawn(2, 2, Color.WHITE);
        assertEquals(Color.WHITE, whitePawn.getColor());

        Pawn blackPawn = new Pawn(3, 3, Color.BLACK);
        assertEquals(Color.BLACK, blackPawn.getColor());
    }

    @Test
    public void testGetImageFileName() {
        Pawn whitePawn = new Pawn(4, 4, Color.WHITE);
        assertEquals(R.drawable.piece_pawn_white, whitePawn.getImageFileName());

        Pawn blackPawn = new Pawn(5, 5, Color.BLACK);
        assertEquals(R.drawable.piece_pawn_black_r180, blackPawn.getImageFileName());
    }
    @Test
    public void testInitialMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(1, 1, Color.WHITE);

        assertTrue(pawn.isValidMove(2, 1, board)); // előre 1
        assertTrue(pawn.isValidMove(3, 1, board)); // előre 2
        assertFalse(pawn.isValidMove(4, 1, board)); // előre 3
        assertFalse(pawn.isValidMove(1, 3, board));
        assertFalse(pawn.isValidMove(1, 3, board));
    }

    @Test
    public void testCapture() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(3, 3, Color.WHITE);

        board[4][2] = new Pawn(4, 2, Color.BLACK);
        assertTrue(pawn.isValidMove(4, 2, board));

        board[4][4] = new Pawn(4, 4, Color.BLACK);
        assertTrue(pawn.isValidMove(4, 4, board));

        assertTrue(pawn.isValidMove(4, 3, board));
        board[4][3] = new Pawn(4, 3, Color.WHITE);
        assertFalse(pawn.isValidMove(4, 3, board));

        board[4][3] = new Pawn(4, 3, Color.BLACK);
        assertFalse(pawn.isValidMove(4, 3, board));
    }
    @Test
    public void testBlocked() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(4, 1, Color.WHITE);

        board[4][2] = new Pawn(4, 2, Color.WHITE);
        assertFalse(pawn.isValidMove(4, 2, board));

        board[4][3] = new Pawn(4, 3, Color.WHITE);
        assertFalse(pawn.isValidMove(4, 3, board));
    }

    @Test
    public void testPawnDiagonalCaptureBlack() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(4, 4, Color.BLACK);

        board[3][3] = new Pawn(3, 3, Color.WHITE);
        assertTrue(pawn.isValidMove(3, 3, board));

        board[3][3] = null;
        assertFalse(pawn.isValidMove(3, 3, board));
    }
}
