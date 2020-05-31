package fyp.ui.hath_wasi.Game.GameScores;

import fyp.ui.hath_wasi.Screens.ChooseLevel;

public class GameScore {

    private int human;
    private int computerPlayer1;
    private int computerPlayer2;
    private final String level;

    public GameScore(int human, int cpu1, int cpu2) {
        this.human = human;
        this.computerPlayer1 = cpu1;
        this.computerPlayer2 = cpu2;
        this.level = ChooseLevel.getLevel();
    }


    public int getHuman() {
        return human;
    }

    public int getComputerPlayer1() {
        return computerPlayer1;
    }

    public int getComputerPlayer2() {
        return computerPlayer2;
    }

    public String getLevel() { return level; }

}
