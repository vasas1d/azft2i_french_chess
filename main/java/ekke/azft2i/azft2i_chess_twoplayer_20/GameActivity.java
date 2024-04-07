package ekke.azft2i.azft2i_chess_twoplayer_20;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.ImageView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import ekke.azft2i.azft2i_chess_twoplayer_20.board.ChessBoard;
import ekke.azft2i.azft2i_chess_twoplayer_20.game.Player;
import ekke.azft2i.azft2i_chess_twoplayer_20.helper.GameResults;
import ekke.azft2i.azft2i_chess_twoplayer_20.helper.SnackbarHelper;

public class GameActivity extends AppCompatActivity {



    ChessBoard chessBoard;
    private boolean isWhiteMoveGameActivity;
    boolean isWhiteDrawOffer = false;
    boolean isBlackDrawOffer = false;
     public LinearLayout whiteCapturedPiecesLayout;
    public LinearLayout blackCapturedPiecesLayout;

    private TextView whiteClock;
    private TextView blackClock;

    Player playerWhite;
    Player playerBlack;

    public Player getPlayerWhite() {
        return playerWhite;
    }
    public Player getPlayerBlack() {
        return playerBlack;
    }

    @Override
    public void onBackPressed() {
        // az alapértelmezett backbutton működés tiltása
        Log.d("GameActivity","- onBackPressed  - Vissza gomb használat letiltva!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // nevek és színek kinyerése az intentből
        String whitePlayerName = getIntent().getStringExtra("WHITE_PLAYER_NAME");
        String blackPlayerName = getIntent().getStringExtra("BLACK_PLAYER_NAME");

        playerWhite = new Player(whitePlayerName, ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color.WHITE);
        playerBlack = new Player(blackPlayerName, ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color.BLACK);

        // nevek megjelenítése az előző nézetből kapott sztringek alapján
        TextView whitePlayerNameTextView4White = findViewById(R.id.whitePlayerNameTextView4White);
        whitePlayerNameTextView4White.setText(whitePlayerName);
        TextView blackPlayerNameTextView4White = findViewById(R.id.blackPlayerNameTextView4Black);
        blackPlayerNameTextView4White.setText(blackPlayerName);

        // állapotjelzők megjelenítése
        TextView whitePlayerTextView = findViewById(R.id.whitePlayerTextView);
        TextView blackPlayerTextView = findViewById(R.id.blackPlayerTextView);
        String activeUser = isWhiteMoveGameActivity ? getString(R.string.white_next_turn) : getString(R.string.black_next_turn);
        whitePlayerTextView.setText(activeUser);
        blackPlayerTextView.setText(activeUser);

        // döntetlen gombok
        Button blackOfferDrawButton = findViewById(R.id.blackOfferDraw);
        Button whiteOfferDrawButton = findViewById(R.id.whiteOfferDraw);

        // órák
        whiteClock = findViewById(R.id.whiteTimeTextView);
        blackClock = findViewById(R.id.blackTimeTextView);

        findViewById(R.id.whiteRestartGameButton).setOnClickListener((l) ->
        {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        findViewById(R.id.blackRestartGameButton).setOnClickListener((l) ->
        {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        Button whiteResignButton = findViewById(R.id.whiteResignButton);
        whiteResignButton.setOnClickListener(view -> {
                        Intent intent = new Intent(this, GameResultActivity.class);
                        GameResults.BLACK_SCORE++;
                        intent.putExtra("WHITE_PLAYER_NAME", String.valueOf(playerWhite.getName()));
                        intent.putExtra("WHITE_NICK_SCORE", String.valueOf(GameResults.WHITE_SCORE));
                        intent.putExtra("BLACK_PLAYER_NAME", String.valueOf(playerBlack.getName()));
                        intent.putExtra("BLACK_NICK_SCORE", String.valueOf(GameResults.BLACK_SCORE));
                        intent.putExtra("GAME_SCORE","FEHÉR FELADTA");
                        Log.d("GameActivity","- whiteResignButton onclick - Fehér feladás");
                        startActivity(intent);
        });

        Button blackResignButton = findViewById(R.id.blackResignButton);
        blackResignButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameResultActivity.class);
            GameResults.WHITE_SCORE++;
            intent.putExtra("WHITE_PLAYER_NAME", String.valueOf(playerWhite.getName()));
            intent.putExtra("WHITE_NICK_SCORE", String.valueOf(GameResults.WHITE_SCORE));
            intent.putExtra("BLACK_PLAYER_NAME", String.valueOf(playerBlack.getName()));
            intent.putExtra("BLACK_NICK_SCORE", String.valueOf(GameResults.BLACK_SCORE));
            intent.putExtra("GAME_SCORE","FEKETE FELADTA");
            Log.d("GameActivity","- whiteResignButton onclick - Fekete feladás");
            startActivity(intent);
        });

        whiteOfferDrawButton.setOnClickListener(view -> {
            String user = chessBoard.getTurn().isWhiteMove() ? getString(R.string.white_next_turn) : getString(R.string.black_next_turn);

            if (!chessBoard.getTurn().isWhiteMove()){
                Log.d("GameActivity","- whiteOfferDrawButton - Csak saját körben");
                Snackbar.make(view, "Csak saját körben", Snackbar.LENGTH_SHORT).show();

            }
            else if ( isBlackDrawOffer && !isWhiteDrawOffer) {
                Log.d("GameActivity","- whiteOfferDrawButton - Fehér elfogadta a döntetlent");
                String drawOffered = getString(R.string.draw_offered_text);
                whitePlayerTextView.setText(drawOffered);
                blackPlayerTextView.setText(drawOffered);

                // intentben átadjuk a következő nézetnek a meccs adatait
                Intent intent = new Intent(this, GameResultActivity.class);
                intent.putExtra("WHITE_PLAYER_NAME", String.valueOf(playerWhite.getName()));
                intent.putExtra("WHITE_NICK_SCORE", String.valueOf(GameResults.WHITE_SCORE));
                intent.putExtra("BLACK_PLAYER_NAME", String.valueOf(playerBlack.getName()));
                intent.putExtra("BLACK_NICK_SCORE", String.valueOf(GameResults.BLACK_SCORE));
                intent.putExtra("GAME_SCORE","DÖNTETLEN");
                startActivity(intent);
            }
            else if (!isWhiteDrawOffer ){
                Log.d("GameActivity","- whiteOfferDrawButton - Fehér döntetlen ajánlat elküldve");
                SnackbarHelper.showTopSnackBar( view, "Fehér játékos döntetlen ajánlatot tett");

                isWhiteDrawOffer = !isWhiteDrawOffer;
                whitePlayerTextView.setText(R.string.draw_offer_text);
                whiteOfferDrawButton.setTextColor(Color.RED);
                blackOfferDrawButton.setTextColor(Color.RED);
            }
            else {
                Log.d("GameActivity","- whiteOfferDrawButton - Fehér döntetlen ajánlat visszavonva");
                Snackbar.make(view, "Döntetlen ajánlat visszavonva.", Snackbar.LENGTH_SHORT).show();
                isWhiteDrawOffer = !isWhiteDrawOffer;
                whitePlayerTextView.setText(user);
                whiteOfferDrawButton.setTextColor(Color.WHITE);
                blackOfferDrawButton.setTextColor(Color.WHITE);
            }
        });




        blackOfferDrawButton.setOnClickListener(view -> {
            String user = chessBoard.getTurn().isWhiteMove() ? getString(R.string.white_next_turn) : getString(R.string.black_next_turn);

            if (chessBoard.getTurn().isWhiteMove()){
                Log.d("GameActivity","- blackOfferDrawButton - Csak saját körben");
                SnackbarHelper.showTopSnackBar( view, "Csak saját körben");
            }
            else  if (isWhiteDrawOffer && !isBlackDrawOffer) {
                Log.d("GameActivity","- blackOfferDrawButton - Fekete elfogadta a döntetlent");
                String drawOffered = getString(R.string.draw_offered_text);
                whitePlayerTextView.setText(drawOffered);
                blackPlayerTextView.setText(drawOffered);

                // intentben átadjuk a következő nézetnek a meccs adatait
                Intent intent = new Intent(this, GameResultActivity.class);
                intent.putExtra("WHITE_PLAYER_NAME", String.valueOf(playerWhite.getName()));
                intent.putExtra("WHITE_NICK_SCORE", String.valueOf(GameResults.WHITE_SCORE));
                intent.putExtra("BLACK_PLAYER_NAME", String.valueOf(playerBlack.getName()));
                intent.putExtra("BLACK_NICK_SCORE", String.valueOf(GameResults.BLACK_SCORE));
                intent.putExtra("GAME_SCORE","DÖNTETLEN");
                startActivity(intent);
            }
            else if (!isBlackDrawOffer){
                Log.d("GameActivity","- blackOfferDrawButton - Fekete döntetlen ajánlat elküldve");
                Snackbar.make(view, "Fekete játékos döntetlen ajánlatot tett", Snackbar.LENGTH_SHORT).show();
                isBlackDrawOffer = !isBlackDrawOffer;
                blackPlayerTextView.setText(R.string.draw_offer_text);
                whiteOfferDrawButton.setTextColor(Color.RED);
                blackOfferDrawButton.setTextColor(Color.RED);
            }
            else {
                Log.d("GameActivity","- blackOfferDrawButton - Fekete döntetlen ajánlat visszavonva");
                SnackbarHelper.showTopSnackBar( view, "Döntetlen ajánlat visszavonva");
                isBlackDrawOffer = !isBlackDrawOffer;
                blackPlayerTextView.setText(user);
                whiteOfferDrawButton.setTextColor(Color.WHITE);
                blackOfferDrawButton.setTextColor(Color.WHITE);
            }
        });


        chessBoard = new ChessBoard(this);
        isWhiteMoveGameActivity = chessBoard.getTurn().isWhiteMove();

        // figurák kirajzolása a sakktáblára
        FrameLayout chessBoardContainer = findViewById(R.id.chessBoard);
        chessBoard.drawPieces(chessBoardContainer);
        initializeCapturedPiecesLayouts(); // leütött figurák helyének megjelenítése

        // játékosok felparaméterezése intentből a névvel és a színnel
        playerWhite = new Player(whitePlayerName, ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color.WHITE);
        playerBlack = new Player(blackPlayerName, ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color.BLACK);
    }

    public void openGameResultActivity(int whiteScore, int blackScore, String result){
        Intent intent = new Intent(this, GameResultActivity.class);
        GameResults.WHITE_SCORE += whiteScore;
        GameResults.BLACK_SCORE += blackScore;
        intent.putExtra("WHITE_PLAYER_NAME", String.valueOf(playerWhite.getName()));
        intent.putExtra("WHITE_NICK_SCORE", String.valueOf(GameResults.WHITE_SCORE));
        intent.putExtra("BLACK_PLAYER_NAME", String.valueOf(playerBlack.getName()));
        intent.putExtra("BLACK_NICK_SCORE", String.valueOf(GameResults.BLACK_SCORE));
        intent.putExtra("GAME_SCORE",result);
        startActivity(intent);
    }


    /**
     * Beállítja a leütött figurák megjelenítéséhez tartozó UI elemeket.
     * Ez a paraméter nélküli metódus beállítja a gameactivity nézeten a tábláról lekerülő figurák megjelenítésére szolgáló részt.
     * Létrehozza és hozzáadja az elrendezésekhez a vizuális megjelenítéshez.
     */
    private void initializeCapturedPiecesLayouts() {
        this.blackCapturedPiecesLayout = findViewById(R.id.blackCapturedPiecesLayout);
        for (int i = 0; i < 24; i++) {
            ImageView capturedPieceImageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            );
            capturedPieceImageView.setLayoutParams(layoutParams);
            capturedPieceImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            blackCapturedPiecesLayout.addView(capturedPieceImageView);
        }
        this.whiteCapturedPiecesLayout = findViewById(R.id.whiteCapturedPiecesLayout);
        for (int i = 0; i < 24; i++) {
            ImageView capturedPieceImageView = new ImageView(this);
            capturedPieceImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            capturedPieceImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            whiteCapturedPiecesLayout.addView(capturedPieceImageView);
        }
    }
    /**
     * Beállítja a játékos óráját a nézeten a paraméterben kapott érték(idő) alapján.
     * A metódus idő kijelzésére alkalmas (perc-másodperc) formátumban beállítja a játékos óráját
     * a kapott idő alapján és az aktuális játékos színétől függően a fehér vagy fekete órát.
     *
     * @param time A beállítandó érték(idő) milliszekundumban. (long)
     */
    public void updatePlayerClock(long time) {
        long min = time / 60000;
        long sec = (time % 60000) / 1000;

        @SuppressLint("DefaultLocale") String str = String.format("%02d:%02d", min, sec);
        if (chessBoard.getTurn().isWhiteMove()){
            whiteClock.setText(str);
        } else
            blackClock.setText(str);
    }
    /**
     * Beállítja az összes játékos óráját a nézeten a paraméterben kapott érték(idő) alapján.
     * A metódus idő kijelzésére alkalmas (perc-másodperc) formátumban beállítja az összes játékos
     * óráját a paraméterben  kapott értékre. A
     *
     * @param time A beállítandó érték(idő) milliszekundumban. (long)
     */
    public void initAllPlayerClock(long time) {
        long min = time / 60000;
        long sec = (time % 60000) / 1000;
        @SuppressLint("DefaultLocale") String str = String.format("%02d:%02d", min, sec);
        whiteClock.setText(str);
        blackClock.setText(str);
    }


    //hibás
//        private void offerDraw(String result, Player whitePlayer, Player blackPlayer) {
//        whitePlayer.increaseScore(1);
//        blackPlayer.increaseScore(1);
//
//        Intent intent = new Intent(this, GameTiedActivity.class);
//        intent.putExtra("WHITE_PLAYER_NAME", String.valueOf(whitePlayer.getName()));
//        intent.putExtra("WHITE_NICK_SCORE", String.valueOf(whitePlayer.getScore()));
//        intent.putExtra("BLACK_PLAYER_NAME", String.valueOf(blackPlayer.getName()));
//        intent.putExtra("BLACK_NICK_SCORE", String.valueOf(blackPlayer.getScore()));
//        intent.putExtra("GAME_SCORE", result);
//        startActivity(intent);
//    }


}
