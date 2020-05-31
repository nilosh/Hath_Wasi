package fyp.ui.hath_wasi.Game;

public class GameExceptions extends Exception {
    public GameExceptions(String cardType) {
        super(cardType);
    }

    public GameExceptions() {

    }
}
