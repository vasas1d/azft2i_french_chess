package ekke.azft2i.azft2i_chess_twoplayer_20.board;


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
import androidx.core.content.ContextCompat;
import ekke.azft2i.azft2i_chess_twoplayer_20.GameTurn;
import ekke.azft2i.azft2i_chess_twoplayer_20.R;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.*;
import ekke.azft2i.azft2i_chess_twoplayer_20.GameActivity;

public class ChessBoard {

    private ChessPiece[][] board;
    private GameActivity gameActivity; //  singletionba kellene tolni
    private ChessPiece selectedPiece = null;
    private Point attackFieldXYPosition = null;
    private boolean isFirstClick = true;
    private static String allMoves = "";
    private MoveValidator moveValidator = new MoveValidator();

    public GameTurn getTurn() {
        return turn;
    }

    private GameTurn turn;


// öndokumentáló!
    public ChessBoard(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.turn = new GameTurn(gameActivity);
        initializeBoard();
        refreshTurnField();
    }


    /**
     * Ez a paraméter nélküli metódus inicializálja a sakk táblán a figurákat.
     * Elhelyezi a fehér és fekete figurákat a kezdő pozíciókba a mátrixban.
     * A grafikus megjelenést nem kezeli.
     * A táblát reprezentáló 8x8-as mátrixban a figurákat a kezdő (x,y) pozíciókra helyezi.
     * A sorokat és oszlopokat nullától számozva az alábbiak szerint helyezi el:

     *    - Fehér figurák:
     *      * A fehér vezér az (0, 0) pozícióba kerül.
     *      * A fehér huszárok az (0, 1) és (0, 6) pozíciókba kerülnek.
     *      * A fehér futók az (0, 2) és (0, 5) pozíciókba kerülnek.
     *      * A fehér királynő az (0, 3) pozícióba kerül.
     *      * A fehér király az (0, 4) pozícióba kerül.
     *      * A fehér gyalogok az (1, 0) és (1, 7) pozíciókba kerülnek.

     *    - Fekete figurák:
     *      * A fekete vezér az (7, 0) pozícióba kerül.
     *      * A fekete huszárok az (7, 1) és (7, 6) pozíciókba kerülnek.
     *      * A fekete futók az (7, 2) és (7, 5) pozíciókba kerülnek.
     *      * A fekete királynő az (7, 3) pozícióba kerül.
     *      * A fekete király az (7, 4) pozícióba kerül.
     *      * A fekete gyalogok az (6, 0) és (6, 7) pozíciókba kerülnek.
     */
    private void initializeBoard() {
        board = new ChessPiece[8][8];

        // Bábuk elhelyezése a kezdő pozíciókra
        // Fehér bábuk
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

        // Fekete bábuk
        board[7][0] = new Rook(7, 0, Color.BLACK);
        board[7][1] = new Knight(7, 1, Color.BLACK);
        board[7][2] = new Bishop(7, 2, Color.BLACK);
        board[7][3] = new Queen(7, 3, Color.BLACK);
        board[7][4] = new King(7, 4, Color.BLACK);
        board[7][5] = new Bishop(7, 5, Color.BLACK);
        board[7][6] = new Knight(7, 6, Color.BLACK);
        board[7][7] = new Rook(7, 7, Color.BLACK);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(6, i, Color.BLACK);
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
            Log.d("ChessBoard", "hiba - az adott mezőn figura van - addNewPiece()");
        } else {
            board[x][y] = piece;
            //Log.d("ChessBoard", "addNewPiece() - Új "+board[x][y].getPieceName() +" a pályán "+x +","+y+" pozíción.");
        }
    }

    /**
     * Törli a bábut az adott pozícióról a táblán.
     *
     * @param x Az X koordináta, ahonnan a bábut törölni szeretnénk.
     * @param y Az Y koordináta, ahonnan a bábut törölni szeretnénk.
     */
    public void removePieceAt(int x, int y) {

        // ellenőrzések ??
        if (board[x][y] != null) {
            board[x][y] = null; // ezzel töröljük, majd ugye lefrissíti a drawpiece()
        }
        else Log.d("ChessBoard", "Hiba - removePieceAt()");
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
    //öndokumentáló kell
    public void movePiece(int startX, int startY, int endX, int endY) {
        // Ellenőrizzük, hogy a megadott pozíciók a tábla méretein belül vannak-e
        if (startX < 0 || startX > 7 || startY < 0 || startY > 7 || endX < 0 || endX > 7 || endY < 0 || endY > 7) {
            Log.d("ChessBoard", "HIBA - Érvénytelen pozíciók - movePiece()");
           // return false; // érvénytelen pozíciók
            return;
        }

        ChessPiece startPiece = board[startX][startY];
        ChessPiece endPosition = board[endX][endY];  // átveszi-e a nullt, ha null a tömben az érték ?

        // ellenőrizzük, hogy a kiindulási pozícióban létezik-e bábu
        if (startPiece == null) {
            Log.d("ChessBoard", "- Nincs figura a kattintott mezőn - movePiece()");
            return;
        }
        //ellenőrízzük ki lép
        if(turn.isWhiteMove() && startPiece.getColor().equals(Color.BLACK)
            || !turn.isWhiteMove() && startPiece.getColor().equals(Color.WHITE)){
            return;
        }
        //ellenőrízzük, hogy nem-e saját bábut akar leütni
        if(endPosition != null && endPosition.getColor().equals(startPiece.getColor())){
            return;
        }

        // ellenőrizzük, hogy a bábu szabályosan mozog-e
        // moveValidator
        if (!startPiece.isValidMove(endX, endY, board)) {
            Log.d("ChessBoard", "HIBA - Érvénytelen mozgás a validálás közben, a mozgás nem megtehető.- movePiece()");
            return;
        }else if(!moveValidator.isAttackMove(endX, endY, board,startPiece)){
            if(moveValidator.isAttackModeAvailable(board, turn.isWhiteMove())){
               return;
            }
        }

        /// lépés logolása textviewre a játék képernyőn
        String str;
        if (turn.isWhiteMove()) {
            str = turn.getTurnNumber()+". "+generateMoveString(startX,startY, endX, endY, board);

        } else {
            str = generateMoveString(startX,startY, endX, endY, board);
        }
        Log.d("ChessBoard","movePiece()->generateMoveString(): "+str);
        updatePlayersMovesText(str); // a lépések logolása az UI-ra itt történik
        /////

        updateAllMoves(str + " ");



                    // nincs figura a célmezőn, egyszerűen töröljük a kezdőpozícióban a peicet, majd újat ehlyezünk fel a célpozícióba
        if (endPosition == null) {
            removePieceAt(startX,startY);
            addNewPiece(endX,endY,startPiece);
            startPiece.setXPosition(endX);
            startPiece.setYPosition(endY);

            // kiemelni metódusba, mert ismétlődik, vagy mindkettőt a végére, ha már minden lefutott
            drawPieces(gameActivity.findViewById(R.id.chessBoard)); // rajzoljuk is ki
           // Log.d("ChessGame","Lépés üres mezőre: "+startX+","+startY+"->"+endX+","+endY);
            //turn.switchPlayer(); //isWhiteMove = !isWhiteMove;
            turn.isTurnEnd();
            refreshTurnField();// ki következik rész kiírása fehér vagy fekete

        }
        else {
            // tehát ellenséges figura van a célmezőn, mivel minden más esetet korábban lefedtünk
            if ( endPosition.getColor() == Color.WHITE ) {
                updateCapturedPiecesLayout(gameActivity.whiteCapturedPiecesLayout, endPosition);
              //  Log.d("ChessBoard", "Fehér figura a lelátóra helyezve- movePiece()");
            } else {
                updateCapturedPiecesLayout(gameActivity.blackCapturedPiecesLayout, endPosition);
             //   Log.d("ChessBoard", "Fekete figura a lelátóra helyezve - movePiece()");
            }

            //töröljük mindkét fugurát.
            removePieceAt(endX,endY);
            removePieceAt(startX, startY);
          //  Log.d("ChessGame","- movePiece() -figurák eltávolítva 2 mezőn: "+startX+","+startY+"-> "+endX+","+endY);


            // új figura hozzáadása, a start pozícióban álló szerinti figurát, az új pozícióba
            startPiece.setXPosition(endX);
            startPiece.setYPosition(endY);
            addNewPiece(endX,endY,startPiece);

            drawPieces(gameActivity.findViewById(R.id.chessBoard)); // rajzoljuk is ki
           // Log.d("ChessGame","- movePiece() -ütés figurára: "+startX+","+startY+"-> "+endX+","+endY);
            //turn.switchPlayer(); //isWhiteMove = !isWhiteMove; // Játékosváltás
            turn.isTurnEnd();
            refreshTurnField(); // ki következik rész kiírása fehér vagy fekete


        }
        //Log.d("ChessGame","- movePiece() - lefutott -"+startX+","+startY+"-> "+endX+","+endY);




        // döntetlen lett, itt kellene egy gv, h az nyer akinek több a bábja !
        boolean scoreTied =moveValidator.isGameTied(board, turn.isWhiteMove());
        if (scoreTied) {
            Log.d("ChessBoard","-movepiece() -- DÖNTETLEN -");
            refreshTurnFieldWithGameTied(); // logüzenet kiírása
            // lépjen át a döntetlen oldalra. és csak sleepelni a jelenlegit.
        }


        //;;;; győzelem, de menjünk akkor új lapra is
        Color winner = moveValidator.isWinner(board);
        if(winner != null){
            refreshTurnFieldWithWinner(winner);
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
                // imageView még üres ?
                if (imageView.getDrawable() == null) {
                    // üres, tehát beállítjuk az eltávolított figura képét
                    imageView.setImageResource(removedPiece.getImageFileName());
                    break;
                }
            }
        }
    }


    // inkább egy tostring kellene, a printboard helyett, vagy kuka ??
        public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(" - ");
                } else {
                    System.out.print(" " + board[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
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
     *
     * @throws IllegalArgumentException Ha a {@code chessBoardContainer} null értékű.
     */
    // túl sok mindent kezel, szétbontani
    @SuppressLint("ClickableViewAccessibility")
    public void drawPieces(FrameLayout chessBoardContainer) {
        // Törölje a sakktáblát, ha már létezik
        chessBoardContainer.removeAllViews();

// GridLayout a bábukhoz
        GridLayout chessBoard = new GridLayout(gameActivity);
        chessBoard.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        chessBoard.setColumnCount(8);
        chessBoard.setRowCount(8);
//
//        // Cellák számítása a képernyő méret alapján
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int cellSize = screenWidth / 8;




        // figurák hozzárendelése a kezdőpozíciókhoz
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                ImageView pieceView = new ImageView(gameActivity);

                // Kép pozicionálása az ImageView-ban
                pieceView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                // A bábu pozíciójának beállítása a GridLayout-ban
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
                    if (board[row][col] != null) {
                       // Log.d("ChessBoard", "Clicked on cell 1 (" + row + ", " + col + ")");
                        //ChessPiece clickedPiece = board[row][col];

                        if (isFirstClick) {
                            isFirstClick = false;
                            selectedPiece = board[row][col];
                            pieceView.setBackgroundColor(111);
                        } else {
                            isFirstClick = true;
                            attackFieldXYPosition = new Point(row, col);

                            movePiece(selectedPiece.getXPosition(), selectedPiece.getYPosition(), attackFieldXYPosition.x, attackFieldXYPosition.y);
                           // Log.d("ChessBoard", "pieceView.setOnClickListener 2. kattintás-- Mozgás történt figurás mezőre mezőre:"+this.selectedPiece.getXPosition()+","+this.selectedPiece.getYPosition()+"--> (" + row + ", " + col + ")");


                        }



                    } else {
                        // nincs figura a mezőn, ez itt kell-e ?? de ha nincs figura a mezőn akkor az tán nem is ez az ág
                        //Log.d("ChessBoard", "Clicked on empty cell 1(" + row + ", " + col + ")");
                        if (!isFirstClick){
                            isFirstClick = true;
                            this.attackFieldXYPosition = new Point(row, col);

                            movePiece(selectedPiece.getXPosition(), selectedPiece.getYPosition(), attackFieldXYPosition.x, attackFieldXYPosition.y);
                           // Log.d("ChessBoard", "pieceView.setOnClickListener -- Mozgás történt tán üres mezőre:"+this.selectedPiece.getXPosition()+","+this.selectedPiece.getYPosition()+"--> (" + row + ", " + col + ")");

                        }
                    }
                });




                pieceView.setOnLongClickListener(v -> {


                    if (board[row][col] != null) {
                       // Log.d("ChessBoard", "Óriásklikk clicked on cell 2(" + row + ", " + col + ")");
                        ChessPiece clickedPiece = board[row][col];

                        // Figura lehetséges lépései piros kerettel kiemelve
                        for (Point move : clickedPiece.getPossibleAllMoves(board)) {
                            int newX = move.x;
                            int newY = move.y;

                            // Ellenőrzés, hogy a lehetséges lépés érvényes-e
                            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                                ChessPiece endPiece = board[newX][newY];

                                // Ha a célpont mező üres, vagy az ellenfél bábuja van rajta
                                if (endPiece == null || endPiece.getColor() != clickedPiece.getColor()) {

                                    ImageView emptyView = (ImageView) chessBoard.getChildAt((7 - newX) * 8 + newY);

                                    // piros keret beállítása, tesztelés miatt

                                    emptyView.setBackgroundResource(R.color.pureRed);
                                    emptyView.setBackgroundColor(ContextCompat.getColor(gameActivity, R.color.pureRed));

                                }
                            }
                        }
                        return true; //
                    }
                    return false; //
                });


                if (piece != null) {
                    // Beállítjuk a bábu képét a pozíciója alapján
                    pieceView.setImageResource(piece.getImageFileName());

                    // hozzáfűzés a GridLayout-hoz
                    chessBoard.addView(pieceView);

                } else {
                    //  üres a cella esetén, hozzáadunk egy üres ImageView-ot
                    ImageView emptyView = new ImageView(gameActivity);
                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(cellSize, cellSize));

                    // hozzáfűzés a GridLayout-hoz és onclick beállítása
                    chessBoard.addView(emptyView);
                   // emptyView.setOnClickListener(v -> Log.d("ChessBoard", "Clicked on empty cell 2(" + row + ", " + col + ")"));
                    emptyView.setOnClickListener(v -> {
                       // Log.d("ChessBoard", "Clicked on empty cell #2(" + row + ", " + col + ")");
                        if (!isFirstClick){
                            isFirstClick = true;
                            this.attackFieldXYPosition = new Point(row, col);

                            movePiece(selectedPiece.getXPosition(), selectedPiece.getYPosition(), attackFieldXYPosition.x, attackFieldXYPosition.y);
                           // Log.d("ChessBoard", "Mozgás történt #2:"+this.selectedPiece.getXPosition()+","+this.selectedPiece.getYPosition()+"-->(" + row + ", " + col + ")");
                        }
                    });
                }
            }
        }
        chessBoardContainer.addView(chessBoard);
    }

// öndokumentáló!
    private void refreshTurnField(){
        TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = gameActivity.findViewById(R.id.blackPlayerTextView);
        String activeUser = turn.isWhiteMove() ? "Fehér következik" : "Fekete következik";
        whitePlayerTextView.setText(activeUser);
        blackPlayerTextView.setText(activeUser);

        if(turn.isWhiteMove()){
            blackPlayerTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            whitePlayerTextView.setBackgroundColor(android.graphics.Color.WHITE);
            whitePlayerTextView.setTextColor(android.graphics.Color.BLACK);
        }else{
            whitePlayerTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            blackPlayerTextView.setBackgroundColor(android.graphics.Color.BLACK);
            blackPlayerTextView.setTextColor(android.graphics.Color.WHITE);
        }
    }


    //mezőfrissítés győzemere
    private void refreshTurnFieldWithWinner(Color winnerColor){
        TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = gameActivity.findViewById(R.id.blackPlayerTextView);
        String activeUser = "Győztes :" +winnerColor.toString();
        whitePlayerTextView.setText(activeUser);
        blackPlayerTextView.setText(activeUser);

    }
    //mezőfrissítés döntetlenre
    private void refreshTurnFieldWithGameTied(){
        TextView whitePlayerTextView = gameActivity.findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = gameActivity.findViewById(R.id.blackPlayerTextView);
        String gameTiedStr = "Játék vége - döntetlen" ;
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
     * @param endX A vég pozíció X koordinátája.
     * @param endY A vég pozíció Y koordinátája.
     * @param board Az aktuális állást reprezentáló sakktábla.
     * @return A lépés szöveges reprezentációja.
     */
    private String generateMoveString(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        ChessPiece piece= board[startX][startY];

        String move = (piece.getSymbol().equals("P")) ? "" : piece.getSymbol();
        String startLineSymbol = convertToChessField(startY);
        String endLineSymbol = convertToChessField(endY);
        move += startLineSymbol;
        move += (startX+1);


        //     move += (board[endX][endY] == null) ? "-" : "x"; // kell-e a colorra szűrni itt ?
        if (board[endX][endY] == null){
            move +="-";
        }
        else if (piece.getColor() != board[endX][endY].getColor()){
            move +="x";
        }
        move += endLineSymbol + (endX+1);

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
        TextView  whitePlayerMovesText = gameActivity.findViewById(R.id.whitePlayerMovesText);
        TextView  blackPlayerMovesText = gameActivity.findViewById(R.id.blackPlayerMovesText);
        whitePlayerMovesText.append(move + " ");
        blackPlayerMovesText.append(move + " ");
    }




}