package fyp.ui.hath_wasi.Game.GameScores;

public class ScoreBoard {

    private static ScoreBoard ourInstance;
    private static int numberOfScores;
    private static GameScore scores[];

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

    public static boolean gameFinish(){
        if(numberOfScores >= 10){
            return true;
        }

        else{
            return false;
        }
    }


    public GameScore[] getScores() {
        return scores;
    }

    public void setScores(GameScore score) {
        if(this.numberOfScores < 10){
            this.scores[this.numberOfScores++] = score;
        }

    }

    public static int[] getTotals() {
        int[] totals = new int[]{0, 0, 0};

        for (int i = 0; i < ScoreBoard.getNumberOfScores(); i++){
            totals[0] += scores[i].getHuman();
            totals[1] += scores[i].getComputerPlayer1();
            totals[2] += scores[i].getComputerPlayer2();
        }

        return totals;
    }


    public static int getNumberOfScores() {
        return numberOfScores;
    }

    public static void setNumberOfScores(int numberOfScores) {
        ScoreBoard.numberOfScores = numberOfScores;
    }
}
