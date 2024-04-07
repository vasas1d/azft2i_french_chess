package ekke.azft2i.azft2i_chess_twoplayer_20.board;
import static android.graphics.Color.argb;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ekke.azft2i.azft2i_chess_twoplayer_20.game.GameTurn;
import ekke.azft2i.azft2i_chess_twoplayer_20.R;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.GameActivity;

public class ChessBoard {

    private ChessPiece[][] board;
    GameActivity gameActivity;
    private ChessPiece selectedPiece = null;
    private Point attackFieldXYPosition = null;
    private boolean isFirstClick = true;
    private ImageView clickedPieceView = null;
    private static String allMoves;
    MoveValidator moveValidator = new MoveValidator();
    public GameTurn getTurn() {
        return turn;
    }
    GameTurn turn;
    public ChessBoard(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.turn = new GameTurn(gameActivity);
        initializeBoard();
        refreshTurnField();
        allMoves = "";
    }
    /**
     * Ez a paraméter nélküli metódus inicializálja a sakk táblán a figurákat.
     * Elhelyezi a fehér és fekete figurákat a kezdő pozíciókba a mátrixban.
     * A grafikus megjelenést nem kezeli.
     * A táblát reprezentáló 8x8-as mátrixban a figurákat a kezdő (x,y) pozíciókra helyezi.
     * A sorokat és oszlopokat nullától számozva az alábbiak szerint helyezi el:
     * - Fehér figurák:
     * * A fehér vezér az (0, 0) pozícióba kerül.
     * * A fehér huszárok az (0, 1) és (0, 6) pozíciókba kerülnek.
     * * A fehér futók az (0, 2) és (0, 5) pozíciókba kerülnek.
     * * A fehér királynő az (0, 3) pozícióba kerül.
     * * A fehér király az (0, 4) pozícióba kerül.
     * * A fehér gyalogok az (1, 0) és (1, 7) pozíciókba kerülnek.
     * - Fekete figurák:
     * * A fekete vezér az (7, 0) pozícióba kerül.
     * * A fekete huszárok az (7, 1) és (7, 6) pozíciókba kerülnek.
     * * A fekete futók az (7, 2) és (7, 5) pozíciókba kerülnek.
     * * A fekete királynő az (7, 3) pozícióba kerül.
     * * A fekete király az (7, 4) pozícióba kerül.
     * * A fekete gyalogok az (6, 0) és (6, 7) pozíciókba kerülnek.
     */
    private void initializeBoard() {
        board = new ChessPiece[8][8];

        // fehér bábuk
        board[0][0] = ChessPieceFactory.initializeChessPiece("R", 0, 0, Color.WHITE);
        board[0][1] = ChessPieceFactory.initializeChessPiece("N", 0, 1, Color.WHITE);
        board[0][2] = ChessPieceFactory.initializeChessPiece("B", 0, 2, Color.WHITE);
        board[0][3] = ChessPieceFactory.initializeChessPiece("Q", 0, 3, Color.WHITE);
        board[0][4] = ChessPieceFactory.initializeChessPiece("K", 0, 4, Color.WHITE);
        board[0][5] = ChessPieceFactory.initializeChessPiece("B", 0, 5, Color.WHITE);
        board[0][6] = ChessPieceFactory.initializeChessPiece("N", 0, 6, Color.WHITE);
        board[0][7] = ChessPieceFactory.initializeChessPiece("R", 0, 7, Color.WHITE);

        for (int i = 0; i < 8; i++) {
            board[1][i] = ChessPieceFactory.initializeChessPiece("P", 1, i, Color.WHITE);
        }

        // fekete bábuk
        board[7][0] = ChessPieceFactory.initializeChessPiece("R", 7, 0, Color.BLACK);
        board[7][1] = ChessPieceFactory.initializeChessPiece("N", 7, 1, Color.BLACK);
        board[7][2] = ChessPieceFactory.initializeChessPiece("B", 7, 2, Color.BLACK);
        board[7][3] = ChessPieceFactory.initializeChessPiece("Q", 7, 3, Color.BLACK);
        board[7][4] = ChessPieceFactory.initializeChessPiece("K", 7, 4, Color.BLACK);
        board[7][5] = ChessPieceFactory.initializeChessPiece("B", 7, 5, Color.BLACK);
        board[7][6] = ChessPieceFactory.initializeChessPiece("N", 7, 6, Color.BLACK);
        board[7][7] = ChessPieceFactory.initializeChessPiece("R", 7, 7, Color.BLACK);

        for (int i = 0; i < 8; i++) {
            board[6][i] = ChessPieceFactory.initializeChessPiece("P", 6, i, Color.BLACK);
        }

        // a kezdeti üres mezők
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }
    /**
     * Hozzáad egy új figurát az adott (x,y) pozícióhoz a táblán.
     *
     * @param x     Az X koordináta, ahova a bábut fel szeretnénk helyezni.
     * @param y     Az Y koordináta, ahova a bábut fel szeretnénk helyezni.
     * @param piece A figura objektum, amelyet fel szeretnénk helyezni.
     */
    public void addNewPiece(int x, int y, ChessPiece piece) {
        if (board[x][y] != null) {
            Log.d("ChessBoard", "HIBA - az adott mezőn figura van - addNewPiece()");
        } else {

            if (PawnPromotion(piece, x) == null) {
                board[x][y] = piece;
                //Log.d("ChessBoard", "addNewPiece() - Új "+board[x][y].getPieceName() +" a pályán "+x +","+y+" pozíción.");

            } else {
                board[x][y] = new King(x, y, piece.getColor());
            }
        }
    }

    /**
     * Törli a bábut az adott pozícióról a táblán.
     *
     * @param x Az X koordináta, ahonnan a bábut törölni szeretnénk.
     * @param y Az Y koordináta, ahonnan a bábut törölni szeretnénk.
     */
    public void removePieceAt(int x, int y) {
        if (board[x][y] != null) {
            board[x][y] = null; // ezzel töröljük, majd lefrissíti a drawpiece()
        } else Log.d("ChessBoard", "Hiba - removePieceAt()");
    }
    /**
     * Paraméter nélküli static metódus, ami visszaadja az összes megtett lépést reprezentáló szöveget karakterlánc formában.
     *
     * @return (String)
     */
    public static String getAllMoves() {
        return allMoves;
    }
    /**
     * Static metódus ami hozzáfűzi a bemeneti szöveget az allMoves attribútumhoz, ami az összes megtett lépést reprezentálja.
     *
     * @param str A egy teljes kör lépéseit szövegesen reprezentáló karakterlánc.
     */
    public static void updateAllMoves(String str) {
        allMoves += str;
    }

    /**
     * A movePiece metódus egy bábu mozgatását végzi a sakktáblán a megadott pozíciók alapján.
     * Ellenőrzi az érvényes pozíciókat, a bábu létezését, színét, valamint a szabályos mozgást és ütést.
     * A bábu mozgatása során frissíti a játékos lépéseinek listáját, a lépések logolását, és frissíti a UI-t.
     * Ellenőrzi, továbbá, hogy a játéknak vége van-e.
     *
     * @param startX A bábu kezdő oszlopának indexe (0-tól kezdve)
     * @param startY A bábu kezdő sorának indexe (0-tól kezdve)
     * @param endX   A bábu végcél oszlopának indexe (0-tól kezdve)
     * @param endY   A bábu végcél sorának indexe (0-tól kezdve)
     */
    public void movePiece(int startX, int startY, int endX, int endY) {
        // ellenőrizzük, hogy a megadott pozíciók a tábla méretein belül vannak-e
        if (startX < 0 || startX > 7 || startY < 0 || startY > 7 || endX < 0 || endX > 7 || endY < 0 || endY > 7) {
            Log.d("ChessBoard", "HIBA - Érvénytelen pozíciók - movePiece()");
            return;
        }

        ChessPiece startPiece = board[startX][startY];
        ChessPiece endPosition = board[endX][endY];

        // ellenőrizzük, hogy a kiindulási pozícióban létezik-e bábu
        if (startPiece == null) {
            Log.d("ChessBoard", "- Nincs figura a kattintott mezőn - movePiece()");
            return;
        }
        // ellenőrízzük ki lép
        if (turn.isWhiteMove() && startPiece.getColor().equals(Color.BLACK)
                || !turn.isWhiteMove() && startPiece.getColor().equals(Color.WHITE)) {
            return;
        }
        // ellenőrizzük, hogy nem-e saját bábut akar leütni
        if (endPosition != null && endPosition.getColor().equals(startPiece.getColor())) {
            return;
        }

        // ellenőrizzük, hogy a bábu szabályosan mozog-e // moveValidator
        if (!startPiece.isValidMove(endX, endY, board)) {
            Log.d("ChessBoard", "HIBA - Érvénytelen mozgás a validálás közben, a mozgás nem megtehető.- movePiece()");
            return;
        } else if (!moveValidator.isAttackMove(endX, endY, board, startPiece)) {
            if (moveValidator.isAttackModeAvailable(board, turn.isWhiteMove())) {
                return;
            }
        }
        /// lépés logolása textviewre a játék képernyőn
        String str;
        if (turn.isWhiteMove()) {
            str = turn.getTurnNumber() + ". " + generateMoveString(startX, startY, endX, endY, board);

        } else {
            str = generateMoveString(startX, startY, endX, endY, board);
        }
        Log.d("ChessBoard", "movePiece()->generateMoveString(): " + str);
        updatePlayersMovesText(str); // a lépések logolása az UI-ra
        updateAllMoves(str + " ");

        // nincs figura a célmezőn, egyszerűen töröljük a kezdőpozícióban a figurát, majd újat helyezünk fel a célpozícióba
        if (endPosition == null) {
            removePieceAt(startX, startY);
            addNewPiece(endX, endY, startPiece);
            startPiece.setXPosition(endX);
            startPiece.setYPosition(endY);

            drawPieces(gameActivity.findViewById(R.id.chessBoard));
            Log.d("ChessGame","Lépés üres mezőre: "+startX+","+startY+"->"+endX+","+endY);
            turn.isTurnEnd();
            refreshTurnField();
        } else {
            // tehát ellenséges figura van a célmezőn, mivel minden más esetet korábban lefedtünk
            if (endPosition.getColor() == Color.WHITE) {
                updateCapturedPiecesLayout(gameActivity.whiteCapturedPiecesLayout, endPosition);
                Log.d("ChessBoard", "Fehér figura leütve- movePiece()");
            } else {
                updateCapturedPiecesLayout(gameActivity.blackCapturedPiecesLayout, endPosition);
                Log.d("ChessBoard", "Fekete figura leütve - movePiece()");
            }

            //töröljük mindkét figurát.
            removePieceAt(endX, endY);
            removePieceAt(startX, startY);
            Log.d("ChessGame","- movePiece() -Figurák eltávolítva 2 mezőn: "+startX+","+startY+"-> "+endX+","+endY);

            // új figura hozzáadása
            startPiece.setXPosition(endX);
            startPiece.setYPosition(endY);
            addNewPiece(endX, endY, startPiece);

            drawPieces(gameActivity.findViewById(R.id.chessBoard));
            turn.isTurnEnd();
            refreshTurnField();
        }
        // döntetlen lett
        boolean scoreTied = moveValidator.isGameTied(board, turn.isWhiteMove());
        if (scoreTied) {
            Log.d("ChessBoard", "-movepiece() -- DÖNTETLEN -");
            refreshTurnFieldWithGameTied(); // logüzenet kiírása
            gameActivity.openGameResultActivity(0, 0, "DÖNTETLEN");
        }
        // győzelem
        Color winner = moveValidator.isWinner(board);
        if (winner != null) {
            refreshTurnFieldWithWinner(winner);
            int whiteScore = winner.equals(Color.WHITE) ? 1 : 0;
            int blackScore = winner.equals(Color.BLACK) ? 1 : 0;
            gameActivity.openGameResultActivity(whiteScore, blackScore, "GYŐZELEM");
        }
    }
    /**
     * Segédmetódus a removePieceAt(int x, int y) metódus használatához.
     * a capturedPiecesLayout LinearLayout-ban megjeleníteni a sakktábláról leütött figurákat.
     * Az eltávolított bábu képét mindig az következő üres ImageView-ba helyezi el.
     *
     * @param capturedPiecesLayout A LinearLayout, amelyben az eltárolt bábuk képeit jelenítjük meg.
     * @param removedPiece         Az eltávolított bábu, amelynek a képét meg kell jeleníteni.
     */
    private void updateCapturedPiecesLayout(LinearLayout capturedPiecesLayout, ChessPiece removedPiece) {
        if (capturedPiecesLayout != null) {
            for (int i = 0; i < 24; i++) {
                ImageView imageView = (ImageView) capturedPiecesLayout.getChildAt(i);
                if (imageView.getDrawable() == null) {
                    // üres, tehát beállítjuk az eltávolított figura képét
                    imageView.setImageResource(removedPiece.getImageFileName());
                    break;
                }
            }
        }
    }
    /**
     * Kirajzolja a bábukat a grafikus sakktáblára.
     * A metódus létrehoz egy GridLayout-ot a bábuk megjelenítéséhez a sakktáblán. Ha már létezik
     * tartalom a megadott {@code chessBoardContainer} FrameLayout-ben, akkor azt törli, mielőtt
     * újra kirajzolná a bábukat.
     *
     * @param chessBoardContainer A FrameLayout, amely tartalmazza a sakktáblát. Nem lehet null.
     *                            A metódus hiba nélkül fog működni, ha a {@code chessBoardContainer}
     *                            egy üres FrameLayout, de nem tudja kezelni a null értéket.
     * @throws IllegalArgumentException Ha a {@code chessBoardContainer} null értékű.
     */
    @SuppressLint("ClickableViewAccessibility")
    public void drawPieces(FrameLayout chessBoardContainer) {
        // sakktábla törlése, ha már létezik
        chessBoardContainer.removeAllViews();

        // GridLayout a bábukhoz
        GridLayout chessBoard = new GridLayout(gameActivity);
        chessBoard.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        chessBoard.setColumnCount(8);
        chessBoard.setRowCount(8);

        // cellák számítása a képernyő méret alapján
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int cellSize = screenWidth / 8;

        // figurák hozzárendelése a kezdőpozíciókhoz
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                ImageView pieceView = new ImageView(gameActivity);

                // kép pozicionálása az ImageView-ban
                pieceView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                // a bábu pozíciójának beállítása a GridLayout-ban
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellSize;
                params.height = cellSize;
                params.rowSpec = GridLayout.spec(7 - i);
                params.columnSpec = GridLayout.spec(j);
                pieceView.setLayoutParams(params);

                // https://stackoverflow.com/questions/15732307/how-to-tell-which-button-was-clicked-in-onclick
                // onclick hozzáadása minden ImageView-hez
                final int row = i;
                final int col = j;

                pieceView.setOnClickListener(v -> {
                    TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
                    String activeUser = turn.isWhiteMove() ? "Fehér következik" : "Fekete következik";
                    whitePlayerTextView.setText(activeUser);
                    Color actualPlayerColor = turn.isWhiteMove() ? Color.WHITE : Color.BLACK;

                    if (board[row][col] != null && board[row][col].getColor() == actualPlayerColor) {
                        if (isFirstClick) {
                            clickedPieceView = pieceView;
                            isFirstClick = false;
                            selectedPiece = board[row][col];
                            pieceView.setBackgroundColor(argb(128, 0, 255, 0));
                        } else {
                            if ( selectedPiece != null){
                                if (board[row][col].getColor() == actualPlayerColor){
                                    isFirstClick = true;
                                    clickedPieceView.setBackgroundColor(argb(0, 0, 0, 0));
                                } else {
                                    attackFieldXYPosition = new Point(row, col);
                                    movePiece(selectedPiece.getXPosition(), selectedPiece.getYPosition(), attackFieldXYPosition.x, attackFieldXYPosition.y);
                                    isFirstClick = true;
                                    clickedPieceView.setBackgroundColor(argb(0, 0, 0, 0));
                                }
                            }
                        }

                    } else {
                        if (!isFirstClick) {
                            isFirstClick = true;
                            this.attackFieldXYPosition = new Point(row, col);
                            movePiece(selectedPiece.getXPosition(), selectedPiece.getYPosition(), attackFieldXYPosition.x, attackFieldXYPosition.y);
                            clickedPieceView.setBackgroundColor(argb(0, 0, 0, 0));
                        }
                    }
                });

                if (piece != null) {
                    // beállítjuk a bábu képét a pozíciója alapján
                    pieceView.setImageResource(piece.getImageFileName());
                    // hozzáfűzés a GridLayout-hoz
                    chessBoard.addView(pieceView);
                } else {
                    //  üres cella esetén, hozzáadunk egy üres ImageView-t
                    ImageView emptyView = new ImageView(gameActivity);
                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(cellSize, cellSize));
                    // hozzáfűzés a GridLayout-hoz és onclick beállítása
                    chessBoard.addView(emptyView);
                    emptyView.setOnClickListener(v -> {
                        if (!isFirstClick) {
                            isFirstClick = true;
                            this.attackFieldXYPosition = new Point(row, col);
                            movePiece(selectedPiece.getXPosition(), selectedPiece.getYPosition(), attackFieldXYPosition.x, attackFieldXYPosition.y);
                        }
                    });
                }
            }
        }
        chessBoardContainer.addView(chessBoard);
    }
    /**
     * A PawnPromotion metódus ellenőrzi, hogy a megadott gyalog elérte-e az alapvonalat
     * a játék során, és így szükség van-e átalakulásra. Ha a gyalog elérte az alapvonalat,
     * akkor visszatér a gyalog színével, ami alapjána játék folyamán a gyalog átalakulása történik.
     *
     * @param piece Az a sakkfigura, amelyről eldöntjük, hogy átalakul-e
     * @param endX Az X koordináta, ahova a figura lépett
     * @return A figura színe (WHITE vagy BLACK), ha átalakulásra kerül sor, egyébként null
     */
    private Color PawnPromotion(ChessPiece piece, int endX) {
        if (!piece.getSymbol().equals("P")) {
            return null;
        }
        if (piece.getColor() == Color.WHITE && endX == 7) {
            return Color.WHITE;
        }
        if (piece.getColor() == Color.BLACK && endX == 0) {
            return Color.BLACK;
        }
        return null;
    }
    /**
     * Az aktuális aktív játékos kijelzésére szolgáló szövegét állítja be a megfelelő TextView-kon a felhasználói felületen.
     */
    private void refreshTurnField() {
        TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = gameActivity.findViewById(R.id.blackPlayerTextView);
        String activeUser = turn.isWhiteMove() ? "Fehér következik" : "Fekete következik";
        whitePlayerTextView.setText(activeUser);
        blackPlayerTextView.setText(activeUser);

        if (turn.isWhiteMove()) {
            blackPlayerTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            whitePlayerTextView.setBackgroundColor(android.graphics.Color.WHITE);
            whitePlayerTextView.setTextColor(android.graphics.Color.BLACK);
        } else {
            whitePlayerTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            blackPlayerTextView.setBackgroundColor(android.graphics.Color.BLACK);
            blackPlayerTextView.setTextColor(android.graphics.Color.WHITE);
        }
    }
    /**
     * Frissíti a játékfelületen megjelenő két kijelző TextView-t a győztes alapján.
     * Az aktív játékos színét és szövegét állítja be a fehér és fekete játékosnak megfelelő
     * TextView elemekre a felhasználói felületen, amikor győzelem történt.
     *
     * @param winnerColor A győztes játékos színe (WHITE vagy BLACK)
     */
    private void refreshTurnFieldWithWinner(Color winnerColor) {
        TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = gameActivity.findViewById(R.id.blackPlayerTextView);
        String activeUser = "Győztes :" + winnerColor.toString();
        whitePlayerTextView.setText(activeUser);
        blackPlayerTextView.setText(activeUser);
    }
    /**
     * Frissíti a játékfelületen megjelenő két kijelző TextView-t döntetlen eredmény esetén.
     */
    private void refreshTurnFieldWithGameTied() {
        TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = gameActivity.findViewById(R.id.blackPlayerTextView);
        String gameTiedStr = "Játék vége - döntetlen";
        whitePlayerTextView.setText(gameTiedStr);
        blackPlayerTextView.setText(gameTiedStr);
    }
    /**
     * A generateMoveString metódus létrehoz egy sakk lépést szöveges formátumban a paraméterként kapott értékek alapján.
     * A bemeneti paraméterek alapján a kimeneti értékben visszaadja a mozgó figura szimbulómát, kivéve gyalog esetén,
     * továbbá hozzáfűzi a kiindulású mező nevét sakkmezőre alakított formában. Ezt követően különséget tesz ütés és
     * üres mezőre lépés között, ennek megfelelően "x" vagy "-" jellel választja el a kiinduló és vég mezőket.
     * Mindezek után a végpontot reprezentáló sakkmező értéket is hzzáadja a kimeneti értékhez.
     *
     * @param startX A kiinduló pozíció X koordinátája.
     * @param startY A kiinduló pozíció Y koordinátája.
     * @param endX   A vég pozíció X koordinátája.
     * @param endY   A vég pozíció Y koordinátája.
     * @param board  Az aktuális állást reprezentáló sakktábla.
     * @return A lépés szöveges reprezentációja.
     */
    private String generateMoveString(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        ChessPiece piece = board[startX][startY];
        String move = (piece.getSymbol().equals("P")) ? "" : piece.getSymbol();
        String startLineSymbol = convertToChessField(startY);
        String endLineSymbol = convertToChessField(endY);
        move += startLineSymbol;
        move += (startX + 1);

        if (board[endX][endY] == null) {
            move += "-";
        } else if (piece.getColor() != board[endX][endY].getColor()) {
            move += "x";
        }
        move += endLineSymbol + (endX + 1);
        return move;
    }
    /**
     * Az convertToChessField metódus átalakítja a kapott pozíciót egy az értéket reprezentáló betűre.
     * Ez az új érték a mezőt reprezentáló első érték lesz a felhasználó felé való visszajelzésekkor.
     *
     * @param pos A pozíció, amelyet átalakítunk a kívánt betűre. 0-tól 7-ig terjedő érték lehet.
     * @return Az átalakított betű string értékként, ami reprezentációja az adott mező első kordinátáját.
     * @throws IllegalArgumentException Ha a bemenő érték nem 0 és 7 között van.
     */
    private String convertToChessField(int pos) {
        if (pos < 0 || pos > 7) {
            throw new IllegalArgumentException("A bemenő értéknek 0 és 7 között kell lennie.");
        }
        char chessLine = (char) ('a' + pos);
        return String.valueOf(chessLine);
    }
    /**
     * Az updatePlayersMovesText metódus frissíti a játékosok lépéseinek szöveges kijelzését a GameActivity nézeten.
     *
     * @param move Az aktuális lépés(string), amelyet hozzá kell adni a játékosok lépéseinek kijelzésére szlgáló TextView elemhez.
     */
    private void updatePlayersMovesText(String move) {
        TextView whitePlayerMovesText = gameActivity.findViewById(R.id.whitePlayerMovesText);
        TextView blackPlayerMovesText = gameActivity.findViewById(R.id.blackPlayerMovesText);
        whitePlayerMovesText.append(move + " ");
        blackPlayerMovesText.append(move + " ");
    }
}