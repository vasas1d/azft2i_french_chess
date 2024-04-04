package ekke.azft2i.azft2i_chess_twoplayer_20;
import ekke.azft2i.azft2i_chess_twoplayer_20.board.MoveValidator;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveValidatorTest {

    @Test
    public void test_isWinner_NoWinner() {
        MoveValidator validator = new MoveValidator();
        ChessPiece[][] board = new ChessPiece[8][8];

        board[0][0] = new Rook(0, 0, Color.WHITE);
        board[0][1] = new Knight(0, 1, Color.WHITE);
        board[0][2] = new Bishop(0, 2, Color.WHITE);
        board[0][3] = new Queen(0, 3, Color.WHITE);
        board[0][4] = new King(0, 4, Color.WHITE);
        board[0][5] = new Bishop(0, 5, Color.WHITE);
        board[0][6] = new Knight(0, 6, Color.WHITE);
        board[0][7] = new Rook(0, 7, Color.WHITE);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(1, i, Color.WHITE);
        }

        board[7][0] = new Rook(7, 0, Color.BLACK);
        board[7][1] = new Knight(7, 1, Color.BLACK);
        board[7][2] = new Bishop(7, 2, Color.BLACK);
        board[7][3] = new Queen(7, 3, Color.BLACK);
        board[7][4] = new King(7, 4, Color.BLACK);
        board[7][5] = new Bishop(7, 5, Color.BLACK);
        board[7][6] = new Knight(7, 6, Color.BLACK);
        board[7][7] = new Rook(7, 7, Color.BLACK);

        for (int i = 0; i < 8; i++) {
            board[7][i] = new Pawn(7, i, Color.BLACK);
        }

        Color winner = validator.isWinner(board);
        assertNull(winner);
    }
    @Test
    public void test_isWinner_WhiteWins() {
        MoveValidator validator = new MoveValidator();
        ChessPiece[][] board = new ChessPiece[8][8];
        board[0][4] = new King(0, 4, Color.BLACK);

        Color winner = validator.isWinner(board);
        assertEquals(Color.WHITE, winner);
    }
    @Test
    public void test_isWinner_BlackWins() {
        MoveValidator validator = new MoveValidator();
        ChessPiece[][] board = new ChessPiece[8][8];
        board[0][4] = new King(0, 4, Color.WHITE);

        Color winner = validator.isWinner(board);
        assertEquals(Color.BLACK, winner);
    }
}
