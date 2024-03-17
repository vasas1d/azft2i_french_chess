package ekke.azft2i.azft2i_chess_twoplayer_20;

public class Player {

    private String playerNick;
    private int playerScore;

    public Player(String name) {
        this.playerNick = name;
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + playerNick + '\'' +
                ", score=" + playerScore +
                '}';
    }
}
