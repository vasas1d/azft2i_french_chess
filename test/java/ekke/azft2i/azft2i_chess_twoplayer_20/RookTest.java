package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
public class RookTest {
    @Test
    public void testRookGetPieceName() {
        Rook rook = new Rook(1, 1, Color.WHITE);
        assertEquals("Bástya", rook.getPieceName());
    }

    @Test
    public void testRookGetSymbol() {
        Rook rook = new Rook(1, 1, Color.WHITE);
        assertEquals("R", rook.getSymbol());
    }

    @Test
    public void testRookGetColor() {
        Rook whiteRook = new Rook(2, 2, Color.WHITE);
        assertEquals(Color.WHITE, whiteRook.getColor());

        Rook blackRook = new Rook(3, 3, Color.BLACK);
        assertEquals(Color.BLACK, blackRook.getColor());
    }

    @Test
    public void testRookGetImageFileName() {
        Rook whiteRook = new Rook(4, 4, Color.WHITE);
        assertEquals(R.drawable.piece_rook_white, whiteRook.getImageFileName());

        Rook blackRook = new Rook(5, 5, Color.BLACK);
        assertEquals(R.drawable.piece_rook_black_r180, blackRook.getImageFileName());
    }

    @Test
    public void testIsValidMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(3, 3, Color.WHITE);

        // azonos pozícióra való lépés
        assertFalse(rook.isValidMove(3, 3, board));

        // megfelelő lépések
        assertTrue(rook.isValidMove(3, 4, board)); // felfelé 1 lépés
        assertTrue(rook.isValidMove(3, 7, board)); // felfelé max lépés
        assertTrue(rook.isValidMove(3, 1, board)); // lefelé max lépés
        assertTrue(rook.isValidMove(4, 3, board)); // jobbra 1 lépés
        assertTrue(rook.isValidMove(7, 3, board)); // jobbra max lépés
        assertTrue(rook.isValidMove(1, 3, board)); // balra max lépés
        assertFalse(rook.isValidMove(4, 5, board)); // átlósan lépés
    }

    @Test
    public void testCapture() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(3, 3, Color.WHITE);

        // üres mező
        assertTrue(rook.isValidMove(3, 4, board));

        // átlósan ütés
        board[4][4] = new Pawn(4, 4, Color.BLACK);
        assertFalse(rook.isValidMove(4, 4, board));

        // szabályos ütések
        board[1][4] = new Pawn(3, 4, Color.BLACK);
        assertTrue(rook.isValidMove(3, 4, board));

        board[4][3] = new Pawn(4, 3, Color.BLACK);
        assertTrue(rook.isValidMove(4, 3, board));
    }

    @Test
    public void testOnlyOwnPieces() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(0, 0, Color.WHITE);
        // saját figura
        board[0][1] = new Pawn(0, 1, Color.WHITE);
        assertFalse(rook.isValidMove(0, 1, board));
    }
    @Test
    public void TestEmptyFields() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(0, 0, Color.WHITE);
        // nincs figura
       // board[0][2] = null;
        assertTrue(rook.isValidMove(0, 1, board));
        assertTrue(rook.isValidMove(0, 2, board));
        assertTrue(rook.isValidMove(0, 3, board));
        assertTrue(rook.isValidMove(0, 4, board));
        assertTrue(rook.isValidMove(0, 5, board));
        assertTrue(rook.isValidMove(0, 6, board));
        assertTrue(rook.isValidMove(0, 7, board));

        assertTrue(rook.isValidMove(1, 0, board));
        assertTrue(rook.isValidMove(2, 0, board));
        assertTrue(rook.isValidMove(3, 0, board));
        assertTrue(rook.isValidMove(4, 0, board));
        assertTrue(rook.isValidMove(5, 0, board));
        assertTrue(rook.isValidMove(6, 0, board));
        assertTrue(rook.isValidMove(7, 0, board));
    }

    // az átlós lépések nem megengedettek
    @Test
    public void testDiagonalMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(3, 3, Color.WHITE);

        assertFalse(rook.isValidMove(1, 1, board));
        assertFalse(rook.isValidMove(1, 5, board));
        assertFalse(rook.isValidMove(5, 1, board));
        assertFalse(rook.isValidMove(5, 5, board));
    }

    @Test
    public void testCaptureWithOwnAndEnemyPieces() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(3, 3, Color.WHITE);

        board[3][4] = new Pawn(3, 4, Color.WHITE);
        assertFalse(rook.isValidMove(3, 4, board));
        board[3][4] = new Pawn(3, 4, Color.BLACK);
        assertTrue(rook.isValidMove(3, 4, board));
    }
}
