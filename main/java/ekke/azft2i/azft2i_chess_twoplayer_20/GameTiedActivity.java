package ekke.azft2i.azft2i_chess_twoplayer_20;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ekke.azft2i.azft2i_chess_twoplayer_20.board.ChessBoard;


public class GameTiedActivity extends AppCompatActivity {


    TextView wClockAfterMatch;
    TextView bClockAfterMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tied);

        Button exitButton = findViewById(R.id.exitButton);
        Button rematchButton = findViewById(R.id.rematchButton);
        Button newGameButton = findViewById(R.id.newGameButton);

        TextView chessMovedTextView = findViewById(R.id.chessOpeningsTextView);
        chessMovedTextView.setText(ChessBoard.getAllMoves()); // lépések printelése static metódussal

        wClockAfterMatch = findViewById(R.id.playerClockWhite);
        bClockAfterMatch = findViewById(R.id.playerClockBlack);
        initAllPlayerEndClock(GameTurn.getWhiteRemTime(), GameTurn.getBlackRemTime()); // órák printelése static metódussal

        // nevek és színek intentből
        String whitePlayerName = getIntent().getStringExtra("WHITE_PLAYER_NAME");
        String blackPlayerName = getIntent().getStringExtra("BLACK_PLAYER_NAME");
        TextView whitePlayerNameTextView = findViewById(R.id.whitePlayerNameTextView);
        whitePlayerNameTextView.setText(whitePlayerName);
        TextView blackPlayerNameTextView = findViewById(R.id.blackPlayerNameTextView);
        blackPlayerNameTextView.setText(blackPlayerName);

        exitButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameTiedActivity.this, GameMainActivity.class);
            startActivity(intent);
        });

        rematchButton.setOnClickListener(view -> {

            // rossz, nem hozza be a neveket még
            Intent intent = new Intent(GameTiedActivity.this, GameActivity.class);
            startActivity(intent);
        });

        newGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameTiedActivity.this, GameStartActivity.class);
            startActivity(intent);
        });

    }
    /**
     * A játékos megmaradt idejét jelző órájának inicializálása.
     * Beállítja a megfelelő perc - másodperc formátumban a fehér és fekete játékos
     * óráinak értékét a nézeten az komponenseket reprezentáló TextView elemekben.
     *
     * @param whiteTime A fehér játékos megmaradt ideje milliszekundumban long értékként megadva.
     * @param blackTime A fekete játékos megmaradt ideje milliszekundumban long értékként megadva.
     */
    public void initAllPlayerEndClock(long whiteTime, long blackTime) {
        long minutesWhite = whiteTime / 60000;
        long secondsWhite = (whiteTime % 60000) / 1000;
        String strWhite = String.format("%02d:%02d", minutesWhite, secondsWhite);
        wClockAfterMatch.setText(strWhite);

        long minutesBlack = blackTime / 60000;
        long secondsBlack = (blackTime % 60000) / 1000;
        String strBlack = String.format("%02d:%02d", minutesBlack, secondsBlack);
        bClockAfterMatch.setText(strBlack);
    }

}
