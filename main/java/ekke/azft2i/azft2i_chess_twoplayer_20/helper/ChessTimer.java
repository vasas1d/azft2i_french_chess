package ekke.azft2i.azft2i_chess_twoplayer_20.helper;

import android.os.Handler;
import android.util.Log;

import ekke.azft2i.azft2i_chess_twoplayer_20.GameActivity;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color;

//öndokumentáló!!
public class ChessTimer {

    private final Color color;
    private final Handler handler;
    private final Runnable runnable;
    private long remainingTime;
    private boolean isRunning;
    private final GameActivity gameActivity;


    public ChessTimer(long totalTime, GameActivity gameActivity, Color color) {
        this.remainingTime = totalTime;
        this.isRunning = false;
        this.gameActivity = gameActivity;
        this.handler = new Handler();
        initializeClock(remainingTime);
        this.runnable = this::onTimerTick; // labdával 5 sor helyett 1 sorban
        this.color = color;
    }

    public void startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000);
            isRunning = true;
        }
    }

    public void stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable);
            isRunning = false;
            int whiteScore = color.equals(Color.WHITE) ? 0 : 1;
            int blackScore = color.equals(Color.WHITE) ? 1 : 0;
            //gameActivity.openGameResultActivity(whiteScore,blackScore, "Győzelem - idő lejárt");
        }
    }

    private void onTimerTick() {

        remainingTime -= 1000;
        if (remainingTime <= 0) {
            remainingTime = 0;
            stopTimer();
        }
        updatePlayerClock();

        handler.postDelayed(runnable, 1000);

    }

    private void updatePlayerClock() {
        if (gameActivity != null) {
            gameActivity.runOnUiThread(() -> gameActivity.updatePlayerClock(getRemainingTime()));
        } else {
            Log.e("ChessTimer", "GameActivity null");
        }
    }

    private void initializeClock(long t) {
        gameActivity.initAllPlayerClock(t);
    }

    public long getRemainingTime() {
        return remainingTime;
    }

}