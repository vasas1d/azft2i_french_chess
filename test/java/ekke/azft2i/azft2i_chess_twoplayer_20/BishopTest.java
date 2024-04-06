package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
public class BishopTest {
    @Test
    public void testBishopGetPieceName() {
        Bishop bishop = new Bishop(1, 1, Color.WHITE);
        assertEquals("Futó", bishop.getPieceName());
    }

    @Test
    public void testBishopGetSymbol() {
        Bishop bishop = new Bishop(1, 1, Color.WHITE);
        assertEquals("B", bishop.getSymbol());
    }

    @Test
    public void testBishopGetColor() {
        Bishop whiteBishop = new Bishop(2, 2, Color.WHITE);
        assertEquals(Color.WHITE, whiteBishop.getColor());

        Bishop blackBishop = new Bishop(3, 3, Color.BLACK);
        assertEquals(Color.BLACK, blackBishop.getColor());
    }

    @Test
    public void testBishopGetImageFileName() {
        Bishop whiteBishop = new Bishop(4, 4, Color.WHITE);
        assertEquals(R.drawable.piece_bishop_white, whiteBishop.getImageFileName());

        Bishop blackBishop = new Bishop(5, 5, Color.BLACK);
        assertEquals(R.drawable.piece_bishop_black_r180, blackBishop.getImageFileName());
    }

        @Test
        public void isValidMoveReturnsCorrectValues() {
            ChessPiece[][] board = new ChessPiece[8][8];
            Bishop bishop = new Bishop(3, 3, Color.WHITE);

            assertFalse(bishop.isValidMove(3, 3, board));
            assertTrue(bishop.isValidMove(1, 1, board));
            assertTrue(bishop.isValidMove(5, 5, board));
            assertTrue(bishop.isValidMove(1, 5, board));
            assertTrue(bishop.isValidMove(5, 1, board));
            assertTrue(bishop.isValidMove(0, 0, board));
            assertTrue(bishop.isValidMove(7, 7, board));
            assertTrue(bishop.isValidMove(0, 6, board));
            assertFalse(bishop.isValidMove(7, 0, board));
            assertFalse(bishop.isValidMove(3, 4, board));
            assertTrue(bishop.isValidMove(4, 4, board));
        }

        @Test
        public void TestEmptyFields() {
            ChessPiece[][] board = new ChessPiece[8][8];
            Bishop bishop = new Bishop(0, 0, Color.WHITE);

            assertTrue(bishop.isValidMove(1, 1, board));
            assertTrue(bishop.isValidMove(2, 2, board));
            assertTrue(bishop.isValidMove(3, 3, board));
            assertTrue(bishop.isValidMove(4, 4, board));
            assertTrue(bishop.isValidMove(5, 5, board));
            assertTrue(bishop.isValidMove(6, 6, board));
            assertTrue(bishop.isValidMove(7, 7, board));

            assertFalse(bishop.isValidMove(1, 7, board));
            assertFalse(bishop.isValidMove(2, 6, board));
            assertFalse(bishop.isValidMove(3, 5, board));
            assertFalse(bishop.isValidMove(5, 3, board));
            assertFalse(bishop.isValidMove(6, 2, board));
            assertFalse(bishop.isValidMove(7, 1, board));
            assertFalse(bishop.isValidMove(3, 4, board));
            assertFalse(bishop.isValidMove(4, 3, board));
        }

        @Test
        public void testCapture() {
            ChessPiece[][] board = new ChessPiece[8][8];
            Bishop bishop = new Bishop(3, 3, Color.WHITE);

            assertTrue(bishop.isValidMove(1, 1, board));
            board[1][1] = new Pawn(1, 1, Color.WHITE);
            assertFalse(bishop.isValidMove(1, 1, board));
            board[1][1] = new Pawn(1, 1, Color.BLACK);
            assertTrue(bishop.isValidMove(1, 1, board));
            board[4][4] = new Pawn(4, 4, Color.BLACK);
            assertTrue(bishop.isValidMove(4, 4, board));
        }

        @Test
        public void testCapture2() {
            ChessPiece[][] board = new ChessPiece[8][8];
            Bishop bishop = new Bishop(3, 3, Color.WHITE);

            board[5][5] = new Pawn(5, 5, Color.WHITE);
            assertFalse(bishop.isValidMove(5, 5, board));
            board[5][5] = new Pawn(5, 5, Color.BLACK);
            assertTrue(bishop.isValidMove(5, 5, board));
        }

        @Test
        public void testFromCorner() {
            ChessPiece[][] board = new ChessPiece[8][8];
            Bishop bishop = new Bishop(0, 0, Color.WHITE);

            assertTrue(bishop.isValidMove(1, 1, board));
            assertTrue(bishop.isValidMove(2, 2, board));
            assertTrue(bishop.isValidMove(3, 3, board));
            assertTrue(bishop.isValidMove(4, 4, board));
            assertTrue(bishop.isValidMove(5, 5, board));
            assertTrue(bishop.isValidMove(6, 6, board));
            assertTrue(bishop.isValidMove(7, 7, board));

            bishop = new Bishop(0, 7, Color.WHITE);
            assertTrue(bishop.isValidMove(1, 6, board));
            assertTrue(bishop.isValidMove(2, 5, board));
            assertTrue(bishop.isValidMove(3, 4, board));
            assertTrue(bishop.isValidMove(4, 3, board));
            assertTrue(bishop.isValidMove(5, 2, board));
            assertTrue(bishop.isValidMove(6, 1, board));
            assertTrue(bishop.isValidMove(7, 0, board));

            bishop = new Bishop(7, 0, Color.WHITE);
            assertTrue(bishop.isValidMove(6, 1, board));
            assertTrue(bishop.isValidMove(5, 2, board));
            assertTrue(bishop.isValidMove(4, 3, board));
            assertTrue(bishop.isValidMove(3, 4, board));
            assertTrue(bishop.isValidMove(2, 5, board));
            assertTrue(bishop.isValidMove(1, 6, board));
            assertTrue(bishop.isValidMove(0, 7, board));

            bishop = new Bishop(7, 7, Color.WHITE);
            assertTrue(bishop.isValidMove(6, 6, board));
            assertTrue(bishop.isValidMove(5, 5, board));
            assertTrue(bishop.isValidMove(4, 4, board));
            assertTrue(bishop.isValidMove(3, 3, board));
            assertTrue(bishop.isValidMove(2, 2, board));
            assertTrue(bishop.isValidMove(1, 1, board));
            assertTrue(bishop.isValidMove(0, 0, board));
        }
    }
