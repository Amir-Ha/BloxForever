package a_s.bloxforever;

/**
 * Created by Sami_Abishai on 24-Aug-15.
 */
public class Score {
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
