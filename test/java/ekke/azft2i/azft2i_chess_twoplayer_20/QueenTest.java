package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
public class QueenTest {
    @Test
    public void testQueenGetPieceName() {
        Queen queen = new Queen(1, 1, Color.WHITE);
        assertEquals("Vezér", queen.getPieceName());
    }

    @Test
    public void testQueenGetSymbol() {
        Queen queen = new Queen(1, 1, Color.WHITE);
        assertEquals("Q", queen.getSymbol());
    }

    @Test
    public void testQueenGetColor() {
        Queen whiteQueen = new Queen(2, 2, Color.WHITE);
        assertEquals(Color.WHITE, whiteQueen.getColor());

        Queen blackQueen = new Queen(3, 3, Color.BLACK);
        assertEquals(Color.BLACK, blackQueen.getColor());
    }

    @Test
    public void testQueenGetImageFileName() {
        Queen whiteQueen = new Queen(4, 4, Color.WHITE);
        assertEquals(R.drawable.piece_queen_white, whiteQueen.getImageFileName());

        Queen blackQueen = new Queen(5, 5, Color.BLACK);
        assertEquals(R.drawable.piece_queen_black_r180, blackQueen.getImageFileName());
    }

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(3, 3, Color.WHITE);

        assertFalse(queen.isValidMove(3, 3, board));

        assertTrue(queen.isValidMove(3, 0, board));
        assertTrue(queen.isValidMove(3, 1, board));
        assertTrue(queen.isValidMove(3, 2, board));
        assertTrue(queen.isValidMove(3, 4, board));
        assertTrue(queen.isValidMove(3, 5, board));
        assertTrue(queen.isValidMove(3, 6, board));
        assertTrue(queen.isValidMove(3, 7, board));

        assertTrue(queen.isValidMove(0, 3, board));
        assertTrue(queen.isValidMove(1, 3, board));
        assertTrue(queen.isValidMove(2, 3, board));
        assertTrue(queen.isValidMove(4, 3, board));
        assertTrue(queen.isValidMove(5, 3, board));
        assertTrue(queen.isValidMove(6, 3, board));
        assertTrue(queen.isValidMove(7, 3, board));

        assertTrue(queen.isValidMove(0, 0, board));
        assertTrue(queen.isValidMove(1, 1, board));
        assertTrue(queen.isValidMove(2, 2, board));
        assertTrue(queen.isValidMove(4, 4, board));
        assertTrue(queen.isValidMove(5, 5, board));
        assertTrue(queen.isValidMove(6, 6, board));
        assertTrue(queen.isValidMove(7, 7, board));

        assertTrue(queen.isValidMove(1, 5, board));
        assertTrue(queen.isValidMove(5, 1, board));
        assertTrue(queen.isValidMove(2, 4, board));
        assertTrue(queen.isValidMove(4, 2, board));
        assertTrue(queen.isValidMove(0, 6, board));
        assertTrue(queen.isValidMove(6, 0, board));

    }

    @Test
    public void testCapture() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(3, 3, Color.WHITE);

        // üres mező
        assertTrue(queen.isValidMove(3, 4, board));

        // átlósan ütés
        board[4][4] = new Pawn(4, 4, Color.BLACK);
        assertTrue(queen.isValidMove(4, 4, board));

        // szabályos ütések
        board[3][4] = new Pawn(3, 4, Color.BLACK);
        assertTrue(queen.isValidMove(3, 4, board));

        board[4][3] = new Pawn(4, 3, Color.BLACK);
        assertTrue(queen.isValidMove(4, 3, board));

        // szabálytalan ütés, saját szín
        board[3][4] = new Pawn(3, 4, Color.WHITE);
        assertFalse(queen.isValidMove(3, 4, board));
        // szabálytalan ütés, nem elérhető mező
        board[4][6] = new Pawn(4, 6, Color.BLACK);
        assertFalse(queen.isValidMove(4, 6, board));

    }

    @Test
    public void testCorner() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(0, 0, Color.WHITE);

        assertTrue(queen.isValidMove(0, 1, board));
        assertTrue(queen.isValidMove(0, 2, board));
        assertTrue(queen.isValidMove(0, 3, board));
        assertTrue(queen.isValidMove(0, 4, board));
        assertTrue(queen.isValidMove(0, 5, board));
        assertTrue(queen.isValidMove(0, 6, board));
        assertTrue(queen.isValidMove(0, 7, board));

        assertTrue(queen.isValidMove(1, 0, board));
        assertTrue(queen.isValidMove(2, 0, board));
        assertTrue(queen.isValidMove(3, 0, board));
        assertTrue(queen.isValidMove(4, 0, board));
        assertTrue(queen.isValidMove(5, 0, board));
        assertTrue(queen.isValidMove(6, 0, board));
        assertTrue(queen.isValidMove(7, 0, board));

        assertTrue(queen.isValidMove(1, 1, board));
        assertTrue(queen.isValidMove(2, 2, board));
        assertTrue(queen.isValidMove(3, 3, board));
        assertTrue(queen.isValidMove(4, 4, board));
        assertTrue(queen.isValidMove(5, 5, board));
        assertTrue(queen.isValidMove(6, 6, board));
        assertTrue(queen.isValidMove(7, 7, board));

        assertFalse(queen.isValidMove(1, 7, board));
        assertFalse(queen.isValidMove(2, 6, board));
        assertFalse(queen.isValidMove(3, 5, board));
        assertTrue(queen.isValidMove(4, 4, board));
        assertFalse(queen.isValidMove(5, 3, board));
        assertFalse(queen.isValidMove(6, 2, board));
        assertFalse(queen.isValidMove(7, 1, board));
    }
}
