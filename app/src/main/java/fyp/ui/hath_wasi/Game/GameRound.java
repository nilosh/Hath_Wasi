package fyp.ui.hath_wasi.Game;

import android.util.Log;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Players.Player;

public class GameRound {

    private Card compPlayer1Card;
    private Card compPlayer2Card;
    private Card playerCard;
    private Player winner;


    public GameRound(Player human, Card humanSelectedCard, Card cpu1, Card cpu2, String trumps) {
    }

    // throws an exception if the human selected card is not a card from the play type
    // when the human player have cards of the play type in the deck.
    public GameRound(Player cpuPlayer1, Card cpu1, Player cpuPlayer2, Card cpu2, Player human, Card humanSelectedCard,
                     String playType, String trumps) throws GameExceptions {

        this.compPlayer1Card = cpu1;
        this.compPlayer2Card = cpu2;
        this.playerCard = humanSelectedCard;

        String trump = trumps.toLowerCase();

        //String playType = cpu1.getCategory();

        //validate the human players card
        if (humanSelectedCard.getCategory() != playType) {

            //check if the human has any cards of the play type
            //if so thrown an error

            if (human.CheckForCardType(playType)) {

                throw new GameExceptions("Card Type" + playType + "Exist");
            }

        }

        // initialize three integer type variables to store the number of the card played.
        // Get the number of the card played if the category of the card is of play type.
        int cardNo1 = 0, cardNo2 = 0, cardNo3 = 0;

        if (cpu1.getCategory() == trump || cpu2.getCategory() == trump || humanSelectedCard.getCategory() == trump) {

            if (cpu1.getCategory() == trump) {
                cardNo1 = cpu1.getNumber();
            }

            if (cpu2.getCategory() == trump) {
                cardNo2 = cpu2.getNumber();
            }

            if (humanSelectedCard.getCategory() == trump) {
                cardNo3 = humanSelectedCard.getNumber();
            }

            chooseWinner(cardNo1, cardNo2, cardNo3, cpuPlayer1, cpuPlayer2, human);
        }

        // if the card played is not a trump.
        // check if the card category is of play type.
        else {
            if (cpu1.getCategory() == playType) {
                cardNo1 = cpu1.getNumber();
            }

            if (cpu2.getCategory() == playType) {
                cardNo2 = cpu2.getNumber();
            }

            if (humanSelectedCard.getCategory() == playType) {
                cardNo3 = humanSelectedCard.getNumber();
            }

            chooseWinner(cardNo1, cardNo2, cardNo3, cpuPlayer1, cpuPlayer2, human);
        }


    }


    // this method chooses the winner depending on the cards played.
    // the player who has played the highest card wins the round.
    private void chooseWinner(int card1, int card2, int card3, Player cpuPlayer1, Player cpuPlayer2, Player human) {

        if (card1 > card2 && card1 > card3) {
            this.winner = cpuPlayer1;
        } else if (card2 > card1 && card2 > card3) {
            this.winner = cpuPlayer2;
        } else if (card3 > card1 && card3 > card2) {
            this.winner = human;
        }
    }


    public Card getCompPlayer1Card() {
        return compPlayer1Card;
    }

    public Card getCompPlayer2Card() {
        return compPlayer2Card;
    }

    public Player getWinner() {
        return winner;
    }

}
