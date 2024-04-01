package ekke.azft2i.azft2i_chess_twoplayer_20.helper;

import android.os.Handler;
import android.util.Log;

import ekke.azft2i.azft2i_chess_twoplayer_20.GameActivity;

//öndokumentáló!!
public class ChessTimer {
    private final Handler handler;
    private final Runnable runnable;
    private long remainingTime;
    private boolean isRunning;
    private final GameActivity gameActivity;


    public ChessTimer(long totalTime, GameActivity gameActivity) {
        this.remainingTime = totalTime;
        this.isRunning = false;
        this.gameActivity = gameActivity;
        this.handler = new Handler();
        initializeClock(remainingTime);
        this.runnable = this::onTimerTick; // labdával 5 sor helyett 1 sorban
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
        }
    }

    private void onTimerTick() {
        updatePlayerClock();

        remainingTime -= 1000;
        if (remainingTime <= 0) {
            remainingTime = 0;
            stopTimer();
        }
        handler.postDelayed(runnable, 1000);

    }

    private void updatePlayerClock() {
        if (gameActivity != null) {
            gameActivity.runOnUiThread(() -> gameActivity.updatePlayerClock(getRemainingTime()));
        } else {
            Log.e("ChessTimer", "GameActivity null");
        }
    }

    private void initializeClock(long t){
        gameActivity.initAllPlayerClock(t);
    }

    public long getRemainingTime() {
        return remainingTime;
    }

}