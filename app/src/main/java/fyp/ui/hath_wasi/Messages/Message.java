package fyp.ui.hath_wasi.Messages;

public class Message {

    private static final String messagePlayNextRound = "Would you like to play the next round?" ;
    private static final String messageSelectValidCard = "Please select a valid card from your card deck!";
    private static final String toastLostGame = "Oops! Your team lost the Game!";
    private static final String toastWinGame = "Congratulations! Your team won the Game";
    private static final String toastDrawGame = "Game: Draw!";
    private static final String toastChooseTrump = "Please choose a trump to continue.";
    private static final String toastComPlayer1SelectedTrump = "Computer Player 1 selected the trump as ";
    private static final String toastComPlayer2SelectedTrump = "Computer Player 2 selected the trump as ";
    private static final String messageWinSevenChances = "Can you win 7 Chances?";
    private static final String messageSelectTrump = "Select your Trump.";
    private static final String toastReshufflingCards = "Game Round Cancelled. Reshuffling Cards ";

    public Message() {

    }

    public static String getMessagePlayNextRound() {
        return messagePlayNextRound;
    }

    public static String getMessageSelectValidCard() {
        return messageSelectValidCard;
    }

    public static String getToastLostGame() {
        return toastLostGame;
    }

    public static String getToastWinGame() {
        return toastWinGame;
    }

    public static String getToastDrawGame() {
        return toastDrawGame;
    }

    public static String getToastChooseTrump() {
        return toastChooseTrump;
    }

    public static String getToastComPlayer1SelectedTrump() {
        return toastComPlayer1SelectedTrump;
    }

    public static String getToastComPlayer2SelectedTrump() {
        return toastComPlayer2SelectedTrump;
    }

    public static String getMessageWinSevenChances() {
        return messageWinSevenChances;
    }

    public static String getMessageSelectTrump() {
        return messageSelectTrump;
    }

    public static String getToastReshufflingCards() {
        return toastReshufflingCards;
    }
}

