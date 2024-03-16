package ekke.azft2i.azft2i_chess_twoplayer_20.pieces;

import ekke.azft2i.azft2i_chess_twoplayer_20.R;

public class Bishop extends ChessPiece {

    public Bishop(int xPosition, int yPosition, Color color) {
        super(xPosition, yPosition, color);
    }

    /**
     * Ellenőrzi, hogy a futó léphet-e az adott célmezőre.
     * A futó az átlón mozoghat, bármennyi mezőt megtéve de csak a saját színén.
     *
     * @param newX Az új X koordináta, ahová a futó lépni szeretne.
     * @param newY Az új Y koordináta, ahová a futó lépni szeretne.
     * @return Igaz, ha a lépés megtehető, egyébként hamis.
     */
    @Override
    public boolean isValidMove(int newX, int newY, ChessPiece[][] board) {
        // A futó csak átlósan tud lépni bármennyi mezőt
        int deltaX = Math.abs(newX - xPosition);
        int deltaY = Math.abs(newY - yPosition);
        if (deltaX != deltaY) {
            return false; // ha nem átlós irányú a lépés
        }

        int directionX = (newX - xPosition > 0) ? 1 : -1;
        int directionY = (newY - yPosition > 0) ? 1 : -1;
        int i;
        int j=yPosition;

        for (i = xPosition + directionX; i != newX && j != newY; i = i + directionX) {
            j = j + directionY;
            if (board[i][j] != null) {
                return false;
            }
        }

        if(board[newX][newY] != null && board[newX][newY].getColor() == color){
            return false;
        }

        return true;
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public String getPieceName() {
        return "Futó";
    }

    /**
     * Visszaadja a bábu képének az "erőforrás" azonosítóját primitív int-ként.
     * A képnek az alkalmazás drawable mappájában kell lennie.
     * Ha a bábu fehér, akkor a fehér bábu képének az "erőforrás" azonosítója kerül visszaadásra,
     * ellenkező esetben a fekete bábu képének az "erőforrás" azonosítója.
     *
     * @return a bábu képének az erőforrás azonosítója (int) a képnek
     * az alkalmazás erőforrás mappájában
     */
    @Override
    public int getImageFileName() {
        if (color == Color.WHITE) {
            return R.drawable.piece_bishop_white;
        } else {
            //return R.drawable.piece_bishop_black;
            return R.drawable.piece_bishop_black_r180;
        }
    }

    @Override
    public boolean isWhite() {
        return false;
    }
}
