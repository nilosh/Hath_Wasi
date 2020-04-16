package fyp.ui.hath_wasi.Game.GameScores;

public class ScoreBoard {

    private static ScoreBoard ourInstance;

    private GameScore scores[];
    private int numberOfScores;

    private ScoreBoard(){
        this.scores = new GameScore[10];
        this.numberOfScores = 0;
    }

    public static ScoreBoard getInstance(){
        if(ourInstance == null){
            ourInstance = new ScoreBoard();
        }

        return ourInstance;
    }

    public GameScore[] getScores() {
        return scores;
    }

    public void setScores(GameScore score) {
        this.scores[this.numberOfScores++] = score;
    }

    public int getNumberOfScores() {
        return numberOfScores;
    }

    public void setNumberOfScores(int numberOfScores) {
        this.numberOfScores = numberOfScores;
    }
}
