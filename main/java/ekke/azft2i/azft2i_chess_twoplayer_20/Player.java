package ekke.azft2i.azft2i_chess_twoplayer_20;

import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color;

public class Player {

    private String playerNick;
    private int playerScore;
    private Color playerColor;





    public Player(String name, Color color) {
        this.playerNick = name;
        this.playerColor = color;
        this.playerScore = 0;
    }
    public String getName() {
        return playerNick;
    }

    public void setName(String name) {
        this.playerNick = name;
    }

    public int getScore() {
        return playerScore;
    }

    public void setScore(int score) {
        this.playerScore = score;
    }

    public void increaseScore(int points) {
        this.playerScore += points;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    @Override
    public String toString() {
        return "Játékos{" +
                "név='" + playerNick + '\'' +
                ", pontszám=" + playerScore +
                '}';
    }
}
