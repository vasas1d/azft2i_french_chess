package ekke.azft2i.azft2i_chess_twoplayer_20.board;

import android.util.Log;

import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;

public class ChessPieceFactory {
    /**
     * Létrehoz egy sakkfigurát a megadott szimbólum és pozíció alapján.
     *
     * @param symbol     A figura szimbóluma, pl.: "R", "N", "B", "Q", "K", "P"
     * @param xPosition  Az X koordináta, a figura kezdőpozíciója
     * @param yPosition  Az Y koordináta, a figura kezdőpozíciója
     * @param color      A figura színe (Fehér vagy Fekete)
     * @return           A létrehozott `ChessPiece` példány
     * @throws IllegalArgumentException Ha a megadott szimbólum ismeretlen
     */
    public static ChessPiece initializeChessPiece(String symbol, int xPosition, int yPosition, Color color) {
        switch (symbol) {
            case "R":
                return new Rook(xPosition, yPosition, color);
            case "N":
                return new Knight(xPosition, yPosition, color);
            case "B":
                return new Bishop(xPosition, yPosition, color);
            case "Q":
                return new Queen(xPosition, yPosition, color);
            case "K":
                return new King(xPosition, yPosition, color);
            case "P":
                return new Pawn(xPosition, yPosition, color);
            default:
                Log.d("PieceFactory", "HIBA - initializeChessPiece()");
                throw new IllegalArgumentException("HIBA hibás - figura szimbólum: " + symbol);
        }
    }
}