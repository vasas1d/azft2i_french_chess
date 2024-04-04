package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
public class KnightTest {
    @Test
    public void testKnightGetPieceName() {
        Knight knight = new Knight(1, 1, Color.WHITE);
        assertEquals("Huszár", knight.getPieceName());
    }

    @Test
    public void testKnightGetSymbol() {
        Knight knight = new Knight(1, 1, Color.WHITE);
        assertEquals("N", knight.getSymbol());
    }

    @Test
    public void testKnightGetColor() {
        Knight whiteKnight = new Knight(2, 2, Color.WHITE);
        assertEquals(Color.WHITE, whiteKnight.getColor());

        Knight blackKnight = new Knight(3, 3, Color.BLACK);
        assertEquals(Color.BLACK, blackKnight.getColor());
    }

    @Test
    public void testKnightGetImageFileName() {
        Knight whiteKnight = new Knight(4, 4, Color.WHITE);
        assertEquals(R.drawable.piece_knight_white, whiteKnight.getImageFileName());

        Knight blackKnight = new Knight(5, 5, Color.BLACK);
        assertEquals(R.drawable.piece_knight_black_r180, blackKnight.getImageFileName());
    }

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(3, 3, Color.WHITE);

        assertFalse(knight.isValidMove(3, 3, board));
        assertTrue(knight.isValidMove(1, 2, board));
        assertTrue(knight.isValidMove(1, 4, board));
        assertTrue(knight.isValidMove(2, 1, board));
        assertTrue(knight.isValidMove(2, 5, board));
        assertTrue(knight.isValidMove(4, 1, board));
        assertTrue(knight.isValidMove(4, 5, board));
        assertTrue(knight.isValidMove(5, 2, board));
        assertTrue(knight.isValidMove(5, 4, board));
        assertFalse(knight.isValidMove(5, 5, board));
        assertFalse(knight.isValidMove(6, 6, board));
    }

    @Test
    public void testCapture() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(3, 3, Color.WHITE);

        assertTrue(knight.isValidMove(1, 2, board));

        board[1][2] = new Pawn(1, 2, Color.BLACK);
        assertTrue(knight.isValidMove(1, 2, board));

        board[1][2] = new Pawn(1, 2, Color.WHITE);
        assertFalse(knight.isValidMove(1, 2, board));
    }

    @Test
    public void testNoPieces() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(0, 0, Color.WHITE);

        assertTrue(knight.isValidMove(1, 2, board));
        assertTrue(knight.isValidMove(2, 1, board));
    }
    @Test
    public void testHussarMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(3, 3, Color.WHITE);

        assertTrue(knight.isValidMove(1, 2, board));
        assertTrue(knight.isValidMove(1, 4, board));
        assertTrue(knight.isValidMove(2, 1, board));
        assertTrue(knight.isValidMove(2, 5, board));
        assertTrue(knight.isValidMove(4, 1, board));
        assertTrue(knight.isValidMove(4, 5, board));
        assertTrue(knight.isValidMove(5, 2, board));
        assertTrue(knight.isValidMove(5, 4, board));
    }
    @Test
    public void testCapturePawnBlock() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(3, 3, Color.WHITE);

        // körbevéve fehér gyalogokkal
        board[2][1] = new Pawn(2, 1, Color.WHITE);
        board[1][2] = new Pawn(1, 2, Color.WHITE);
        board[1][4] = new Pawn(1, 4, Color.WHITE);
        board[2][5] = new Pawn(2, 5, Color.WHITE);
        board[4][5] = new Pawn(4, 5, Color.WHITE);
        board[5][4] = new Pawn(5, 4, Color.WHITE);
        board[5][2] = new Pawn(5, 2, Color.WHITE);
        board[4][1] = new Pawn(4, 1, Color.WHITE);

        assertFalse(knight.isValidMove(1, 0, board));
        assertFalse(knight.isValidMove(0, 1, board));
        assertFalse(knight.isValidMove(0, 5, board));
        assertFalse(knight.isValidMove(1, 6, board));
        assertFalse(knight.isValidMove(5, 6, board));
        assertFalse(knight.isValidMove(6, 5, board));
        assertFalse(knight.isValidMove(6, 1, board));
        assertFalse(knight.isValidMove(5, 0, board));
    }

}
