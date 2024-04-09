package ekke.azft2i.azft2i_chess_twoplayer_20.helper;
import android.os.Handler;
import android.util.Log;
import ekke.azft2i.azft2i_chess_twoplayer_20.GameActivity;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color;
/**
 * A ChessTimer osztály egy sakkórát reprezentál. Mindkét játékosnak külön
 * ChessTimer osztálya szolgál az idő kijelzésére.
 */
public class ChessTimer {

    final Color color;
    private final Handler handler;
    private final Runnable runnable;
    private long remainingTime;
    private boolean isRunning;
    private final GameActivity gameActivity;

    /**
     * Konstruktor a ChessTimer osztályhoz.
     * @param totalTime A játékosok rendelkezésére álló időkeret másodpercben megadva.
     * @param gameActivity A nézet, ahol az óra működik.
     * @param color A játékos színe (fehér vagy fekete).
     */
    public ChessTimer(long totalTime, GameActivity gameActivity, Color color) {
        this.remainingTime = totalTime;
        this.isRunning = false;
        this.gameActivity = gameActivity;
        this.handler = new Handler();
        initializeClock(remainingTime);
        this.runnable = this::onTimerTick;
        this.color = color;
    }
    /**
     * Az óra indítása.
     */
    public void startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000);
            isRunning = true;
        }
    }
    /**
     * Az óra leállítása.
     */
    public void stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable);
            isRunning = false;
        }
    }
    /**
     * Az visszelévő idejének csökkentése.
     */
    private void onTimerTick() {

        remainingTime -= 1000;
        if (remainingTime <= 0) {
            remainingTime = 0;
            stopTimer();
        }
        updatePlayerClock();
        handler.postDelayed(runnable, 1000);
    }
    /**
     * A játékos órájának frissítése a nézeten.
     */
    private void updatePlayerClock() {
        if (gameActivity != null) {
            gameActivity.runOnUiThread(() -> gameActivity.updatePlayerClock(getRemainingTime()));
        } else {
            Log.e("ChessTimer", "GameActivity null");
        }
    }
    /**
     * Az óra inicializálása a megadott időkerettel.
     * @param t Az beállítandó időkeret.
     */
    private void initializeClock(long t) {
        gameActivity.initAllPlayerClock(t);
    }
    /**
     * Visszaadja a még hátralévő időt.
     * @return A még hátralévő idő milliszekundumban.
     */
    public long getRemainingTime() {
        return remainingTime;
    }
}