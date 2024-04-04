package ekke.azft2i.azft2i_chess_twoplayer_20;
import org.junit.Test;
import static org.junit.Assert.*;

import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.ChessPiece;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;

public class KingTest {
    @Test
    public void testKingGetPieceName() {
        King king = new King(1, 1, Color.WHITE);
        assertEquals("Király", king.getPieceName());
    }

    @Test
    public void testKingGetSymbol() {
        King king = new King(1, 1, Color.WHITE);
        assertEquals("K", king.getSymbol());
    }

    @Test
    public void testKingGetColor() {
        King whiteKing = new King(2, 2, Color.WHITE);
        assertEquals(Color.WHITE, whiteKing.getColor());

        King blackKing = new King(3, 3, Color.BLACK);
        assertEquals(Color.BLACK, blackKing.getColor());
    }

    @Test
    public void testKingGetImageFileName() {
        King whiteKing = new King(3, 3, Color.WHITE);
        assertEquals(R.drawable.piece_king_white, whiteKing.getImageFileName());

        King blackKing = new King(4, 4, Color.BLACK);
        assertEquals(R.drawable.piece_king_black_r180, blackKing.getImageFileName());
    }

    @Test
    public void testIsValidMove1() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(3, 3, Color.WHITE);
// azonos pozícióra való lépés
        assertFalse(king.isValidMove(3, 3, board));
// megfelelő lépések
        assertTrue(king.isValidMove(3, 4, board)); // felfelé 1 lépés
        assertTrue(king.isValidMove(4, 3, board)); // jobbra 1 lépés
        assertTrue(king.isValidMove(4, 4, board)); // átlósan 1 lépés
        assertTrue(king.isValidMove(2, 3, board)); // balra 1 lépés
        assertTrue(king.isValidMove(2, 4, board)); // átlósan 1 lépés visszafelé
// hibás lépések
        assertFalse(king.isValidMove(3, 5, board)); // több mint 1 lépés felfelé
        assertFalse(king.isValidMove(5, 3, board)); // több mint 1 lépés jobbra
        assertFalse(king.isValidMove(5, 5, board)); // több mint 1 lépés átlósan
        assertFalse(king.isValidMove(1, 3, board)); // több mint 1 lépés balra
        assertFalse(king.isValidMove(1, 5, board)); // több mint 1 lépés átlósan visszafelé
    }
    @Test
    public void testCapture() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(3, 3, Color.WHITE);

        // felfelé
        board[3][4] = new Pawn(3, 4, Color.BLACK);
        assertTrue(king.isValidMove(3, 4, board));

        // lefelé
        board[3][2] = new Pawn(3, 2, Color.BLACK);
        assertTrue(king.isValidMove(3, 2, board));

        // jobbra
        board[4][3] = new Pawn(4, 3, Color.BLACK);
        assertTrue(king.isValidMove(4, 3, board));

        // balra
        board[2][3] = new Pawn(2, 3, Color.BLACK);
        assertTrue(king.isValidMove(2, 3, board));

        // ék-i irányban
        board[4][4] = new Pawn(4, 4, Color.BLACK);
        assertTrue(king.isValidMove(4, 4, board));

        // ény-i irányban
        board[2][4] = new Pawn(2, 4, Color.BLACK);
        assertTrue(king.isValidMove(2, 4, board));

        // dk-i irányban
        board[4][2] = new Pawn(4, 2, Color.BLACK);
        assertTrue(king.isValidMove(4, 2, board));

        // dny-i irányban
        board[2][2] = new Pawn(2, 2, Color.BLACK);
        assertTrue(king.isValidMove(2, 2, board));
    }
    // csak saját figurák veszik körbe
    @Test
    public void testOnlyOwnPieces() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(3, 3, Color.WHITE);

        board[3][4] = new Pawn(3, 4, Color.WHITE);
        assertFalse(king.isValidMove(3, 4, board));

        board[3][2] = new Pawn(3, 2, Color.WHITE);
        assertFalse(king.isValidMove(3, 2, board));

        board[4][3] = new Pawn(4, 3, Color.WHITE);
        assertFalse(king.isValidMove(4, 3, board));

        board[2][3] = new Pawn(2, 3, Color.WHITE);
        assertFalse(king.isValidMove(2, 3, board));

        board[4][4] = new Pawn(4, 4, Color.WHITE);
        assertFalse(king.isValidMove(4, 4, board));

        board[2][4] = new Pawn(2, 4, Color.WHITE);
        assertFalse(king.isValidMove(2, 4, board));

        board[4][2] = new Pawn(4, 2, Color.WHITE);
        assertFalse(king.isValidMove(4, 2, board));

        board[2][2] = new Pawn(2, 2, Color.WHITE);
        assertFalse(king.isValidMove(2, 2, board));
    }
    // saját és ellenséges figura teszt
    @Test
    public void testCaptureOwnAndEnemyPieces() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(4, 4, Color.WHITE);

        board[4][5] = new Pawn(4, 5, Color.BLACK);
        board[4][6] = new Pawn(4, 6, Color.WHITE);
        assertTrue(king.isValidMove(4, 5, board));
        assertFalse(king.isValidMove(4, 6, board));

        board[4][3] = new Pawn(4, 3, Color.BLACK);
        board[4][2] = new Pawn(4, 2, Color.WHITE);
        assertTrue(king.isValidMove(4, 3, board));
        assertFalse(king.isValidMove(4, 2, board));

        board[5][4] = new Pawn(5, 4, Color.BLACK);
        board[6][4] = new Pawn(6, 4, Color.WHITE);
        assertTrue(king.isValidMove(5, 4, board));
        assertFalse(king.isValidMove(6, 4, board));

        board[3][4] = new Pawn(3, 4, Color.BLACK);
        board[2][4] = new Pawn(2, 4, Color.WHITE);
        assertTrue(king.isValidMove(3, 4, board));
        assertFalse(king.isValidMove(2, 4, board));

        board[5][5] = new Pawn(5, 5, Color.BLACK);
        board[6][6] = new Pawn(6, 6, Color.WHITE);
        assertTrue(king.isValidMove(5, 5, board));
        assertFalse(king.isValidMove(6, 6, board));

        board[3][5] = new Pawn(3, 5, Color.BLACK);
        board[2][6] = new Pawn(2, 6, Color.WHITE);
        assertTrue(king.isValidMove(3, 5, board));
        assertFalse(king.isValidMove(2, 6, board));

        board[5][3] = new Pawn(5, 3, Color.BLACK);
        board[6][2] = new Pawn(6, 2, Color.WHITE);
        assertTrue(king.isValidMove(5, 3, board));
        assertFalse(king.isValidMove(6, 2, board));

        board[3][3] = new Pawn(3, 3, Color.BLACK);
        board[2][2] = new Pawn(2, 2, Color.WHITE);
        assertTrue(king.isValidMove(3, 3, board));
        assertFalse(king.isValidMove(2, 2, board));
    }

}


