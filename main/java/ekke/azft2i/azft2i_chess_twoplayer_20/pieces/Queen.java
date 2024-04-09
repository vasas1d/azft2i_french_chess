package ekke.azft2i.azft2i_chess_twoplayer_20.pieces;

import ekke.azft2i.azft2i_chess_twoplayer_20.R;

public class Queen extends ChessPiece {

    public Queen(int xPosition, int yPosition, Color color) {
        super(xPosition, yPosition, color);
    }
    /**
     * A metódus visszaadja logikai érték formájában, hogy a vezér léphet-e az adott célmezőre.
     * A vezér vízszintesen, függőlegesen vagy átlósan mozoghat tetszés szerint bármennyi mezőt.
     *
     * @param newX Az új X koordináta, ahová a vezér lépni szeretne.
     * @param newY Az új Y koordináta, ahová a vezér lépni szeretne.
     * @return Igaz, ha a lépés megtehető, egyébként hamis.
     */
    @Override
    public boolean isValidMove(int newX, int newY, ChessPiece[][] board) {
        // ha a célmező ugyanaz, mint az aktuális pozíció, akkor nem léphet.
        if (newX == xPosition && newY == yPosition) {
            return false;
        }
        int deltaX = Math.abs(newX - xPosition);
        int deltaY = Math.abs(newY - yPosition);

        int directionX = 0;
        if (newX - xPosition > 0){
            directionX = 1;
        } else if (newX - xPosition < 0) {
            directionX = -1;
        }

        int directionY = 0;
        if (newY - yPosition > 0){
            directionY = 1;
        } else if (newY - yPosition < 0) {
            directionY = -1;
        }
        if(deltaX != 0){//ha x tengelyen mozog
            if(deltaY != 0 && deltaY != deltaX ) {
                return false;
            }
        }

        int j=yPosition + directionY;

        for (int i = xPosition + directionX; i != newX || j != newY; i = i + directionX) {

            if(j == 8 ||  j<0 || i ==8 || i < 0){
                j=7;
            }
            if (board[i][j] != null) {
                return false;
            }
            j = j + directionY;
        }

        if(board[newX][newY] != null && board[newX][newY].getColor() == color){
            return false;
        }

        return true;
    }
    @Override
    public String getSymbol() {
        return "Q";
    }

    @Override
    public String getPieceName() {
        return "Vezér";
    }

    /**
     * Visszaadja a bábu képének az "erőforrás" azonosítóját primitív int-ként.
     * A képnek az alkalmazás drawable mappájában kell lennie.
     * Ha a bábu fehér, akkor a fehér bábu képének az "erőforrás" azonosítója kerül visszaadásra,
     * ellenkező esetben a fekete bábu képének az "erőforrás" azonosítója.
     *
     * @return a bábu képének az erőforrás azonosítója (int) a képnek
     *         az alkalmazás erőforrás mappájában
     */
    @Override
    public int getImageFileName() {
        if (color == Color.WHITE) {
            return R.drawable.piece_queen_white;
        } else {
            return R.drawable.piece_queen_black_r180;
        }
    }


}