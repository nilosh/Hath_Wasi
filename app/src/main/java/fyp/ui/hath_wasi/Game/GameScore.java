package fyp.ui.hath_wasi.Game;

public class GameScore {

    int human;
    int computerPlayer1;
    int computerPlayer2;

    public GameScore(int human, int cpu1, int cpu2){
        this.human = human;
        this.computerPlayer1 = cpu1;
        this.computerPlayer2 = cpu2;
    }


    public int getHuman() {
        return human;
    }

    public void setHuman(int human) {
        this.human = human;
    }

    public int getComputerPlayer1() {
        return computerPlayer1;
    }

    public void setComputerPlayer1(int computerPlayer1) {
        this.computerPlayer1 = computerPlayer1;
    }

    public int getComputerPlayer2() {
        return computerPlayer2;
    }

    public void setComputerPlayer2(int computerPlayer2) {
        this.computerPlayer2 = computerPlayer2;
    }
}
