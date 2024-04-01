package ekke.azft2i.azft2i_chess_twoplayer_20;



import android.util.Log;
import ekke.azft2i.azft2i_chess_twoplayer_20.helper.ChessTimer;


/**
 * A GameTurn osztály a játékban történő játékosváltások és körváltások kezelését végzi.
 * Az osztály által érhető el az aktuális kör illetve az aktuális cselekvő/lépő játékos.
 */
public class GameTurn {
    private int turnNumber;
    private boolean isWhiteMove;
    private final ChessTimer whitePlayerClock;
    private final ChessTimer blackPlayerClock;

    private static long whiteRemainingTime;
    private static long blackRemainingTime;
    long playerTime = 600000; //480000//720000; //

    /**
     * Az összes maradék idő beállítása a játékosoknak.
     * Kiszámolja és beállítja a játékosok maradék idejét
     * a játékidőből és a játékosok aktuális óráinak megmaradt idejéből.
     */
    private void setAllRemainingTime(){
        whiteRemainingTime = playerTime - whitePlayerClock.getRemainingTime();
        blackRemainingTime  = playerTime - blackPlayerClock.getRemainingTime();
    }
    /**
     * A getWhiteRemTime statikus metódus visszaadja a fehér játékos megmaradt idejét másodpercekben.
     * A GameTiedActivity initAllPlayerEndClock metódushívásnál paraméterként hívja a nézet az onCreate metódusban.
     *
     * @return A fehér játékos megmaradt ideje másodpercekben.(long)
     */
    public static long getWhiteRemTime() {return whiteRemainingTime;}
    /**
     * A getBlackRemTime statikus metódus visszaadja a fekete játékos megmaradt idejét másodpercekben.
     * A GameTiedActivity initAllPlayerEndClock metódushívásnál paraméterként hívja a nézet az onCreate metódusban.
     *
     * @return A fekete játékos megmaradt ideje másodpercekben.(long)
     */
    public static long getBlackRemTime() {return blackRemainingTime;}



    /**
     * Konstruktor az osztály létrehozásához.
     * Az osztály inicializálja az alapértelmezett értékeket:
     * Beállítja az első kört és
     * beállítja, hogy a fehér játékos kezdi a játszmát,
     * ezt az isWhiteMove változón keresztül végzi.
     */

    public GameTurn(GameActivity gameActivity) {
        this.turnNumber = 1;
        this.isWhiteMove = true;
        //Log.d("GameTurn","konstruktor lefutott ");
        this.whitePlayerClock = new ChessTimer(playerTime, gameActivity);
        this.blackPlayerClock = new ChessTimer(playerTime, gameActivity);
        setAllRemainingTime();
        startWhiteClock(whitePlayerClock);

    }





    /**
     * Paraméter nélküli metódus, visszadja éppen hányadik körnél tart a játszma.
     *
     * @return Az aktuális kör száma. (int)
     */
    public int getTurnNumber() {
       // Log.d("GameTurn","getTurnNumber() "+turnNumber);
        return turnNumber;
    }

    /**
     * isWhiteMove paraméter nélküli metódus logikai értékként visszadja, hogy a játékos-e az éppen cselekvő játékos.
     *
     * @return Igaz, ha a fehér játékos következik, hamis, ha a fekete.
     */
    public boolean isWhiteMove() {
        return isWhiteMove;
    }
    /**
     * Paraméter nélküli metódus,játékosváltás.
     * Az akutális játékos váltása, az isWhiteMove értékatdásával.
     */
    public void switchPlayer() {
        isWhiteMove = !isWhiteMove;
        if (isWhiteMove) {
            whitePlayerClock.startTimer();
            blackPlayerClock.stopTimer();
        } else {
            whitePlayerClock.stopTimer();
            blackPlayerClock.startTimer();
        }

        setAllRemainingTime();

    }
    public void startWhiteClock(ChessTimer playerClock) {
        playerClock.startTimer();
    }

    /**
     * Befejezi az aktuális kört és átadja a másik játékosnak a lépés jogát a switchPlayer() metódushívással.
     * Amennyiben a fekete játékos következik, akkor a forduló számát is növeli.
     */
    private void finishTurn() {
       // Log.d("GameTurn","endTurn() kör vége: "+turnNumber);
         Log.d("GameTurn","finishTurn fehér: "+whitePlayerClock.getRemainingTime());
        turnNumber++;
        switchPlayer();
        Log.d("GameTurn","finishTurn fekete: "+blackPlayerClock.getRemainingTime());
    }
    /**
     * Paraméter nélküli metódus, ellenőrzi, hogy a forduló véget ért-e.
     * Amennyiben a körnek vége, úgy befejezi az aktuális kört és a következő játékosra vált,
     * ezt az finishTurn() metódust hívással végzi. Ellenkező esetben, ha a körnek nincs vége, úgy
     * a lépés jogát átadja a switchPlayer() metódushívással a másik játékosnak.
     */
    public void isTurnEnd(){
        if (!isWhiteMove) {
            finishTurn();
        } else switchPlayer();
    }
}
