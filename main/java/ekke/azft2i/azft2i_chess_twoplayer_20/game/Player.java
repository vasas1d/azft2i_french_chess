package ekke.azft2i.azft2i_chess_twoplayer_20.game;

import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color;

public class Player {

    private String playerNick;
    private Color playerColor;
    public Player(String name, Color color) {
        this.playerNick = name;
        this.playerColor = color;
    }
    public String getName() {
        return playerNick;
    }
    public void setPlayerName(String name) {
        playerNick = name;
    }
   public Color getPlayerColor() {
        return playerColor;
   }
}
