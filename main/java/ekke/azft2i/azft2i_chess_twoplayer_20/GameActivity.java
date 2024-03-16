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
import androidx.appcompat.app.AppCompatActivity;
import ekke.azft2i.azft2i_chess_twoplayer_20.board.ChessBoard;


public class GameActivity extends AppCompatActivity {



    ChessBoard chessBoard;
    private boolean isWhiteMoveGameActivity;
    boolean isWhiteDrawOffer = false;
    boolean isBlackDrawOffer = false;

    public LinearLayout whiteCapturedPiecesLayout;
    public LinearLayout blackCapturedPiecesLayout;

    private TextView whiteClock;
    private TextView blackClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // nevek és színek kinyerése az intentből
        String whitePlayerName = getIntent().getStringExtra("WHITE_PLAYER_NAME");
        String blackPlayerName = getIntent().getStringExtra("BLACK_PLAYER_NAME");

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

            // akt akt. újraindítása
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        findViewById(R.id.blackRestartGameButton).setOnClickListener((l) ->
        {
            // akt akt. újraindítása
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        Button whiteResignButton = findViewById(R.id.whiteResignButton);
        whiteResignButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameMainActivity.class);
            startActivity(intent);
        });
        Button blackResignButton = findViewById(R.id.blackResignButton);
        blackResignButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameMainActivity.class);
            startActivity(intent);
        });

        whiteOfferDrawButton.setOnClickListener(view -> {
            String user = chessBoard.getTurn().isWhiteMove() ? getString(R.string.white_next_turn) : getString(R.string.black_next_turn);

            if (isBlackDrawOffer) {
                Log.d("GameActivity","- whiteOfferDrawButton - Fehér elfogadta a döntetlent");
                String drawOffered = getString(R.string.draw_offered_text);
                whitePlayerTextView.setText(drawOffered);
                blackPlayerTextView.setText(drawOffered);

                startActivity(new Intent(this, GameTiedActivity.class));
            }
            else if (!isWhiteDrawOffer){
                Log.d("GameActivity","- whiteOfferDrawButton - Fehér döntetlen ajánlat");
                isWhiteDrawOffer = !isWhiteDrawOffer;
                whitePlayerTextView.setText(R.string.draw_offer_text);
                whiteOfferDrawButton.setTextColor(Color.RED);
                blackOfferDrawButton.setTextColor(Color.RED);
            }
            else {
                Log.d("GameActivity","- whiteOfferDrawButton - Fehér döntetlen ajánlat visszavonva");
                isWhiteDrawOffer = !isWhiteDrawOffer;
                whitePlayerTextView.setText(user);
                whiteOfferDrawButton.setTextColor(Color.WHITE);
                blackOfferDrawButton.setTextColor(Color.WHITE);
            }
        });




        blackOfferDrawButton.setOnClickListener(view -> {
            String user = chessBoard.getTurn().isWhiteMove() ? getString(R.string.white_next_turn) : getString(R.string.black_next_turn);

            if (isWhiteDrawOffer) {
                Log.d("GameActivity","- blackOfferDrawButton - Fekete elfogadta a döntetlent");
                String drawOffered = getString(R.string.draw_offered_text);
                whitePlayerTextView.setText(drawOffered);
                blackPlayerTextView.setText(drawOffered);

                startActivity(new Intent(this, GameTiedActivity.class));
            }
            else if (!isBlackDrawOffer){
                Log.d("GameActivity","- blackOfferDrawButton - Fekete döntetlen ajánlat");
                isBlackDrawOffer = !isBlackDrawOffer;
                blackPlayerTextView.setText(R.string.draw_offer_text);
                whiteOfferDrawButton.setTextColor(Color.RED);
                blackOfferDrawButton.setTextColor(Color.RED);
            }
            else {
                Log.d("GameActivity","- blackOfferDrawButton - Fekete döntetlen ajánlat visszavonva");
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
}
