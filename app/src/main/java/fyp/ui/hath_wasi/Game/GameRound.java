package fyp.ui.hath_wasi.Game;

import android.util.Log;
import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Players.Player;

public class GameRound {

    private Card compPlayer1Card;
    private Card compPlayer2Card;
    private Card playerCard;
    private Player winner;

    public GameRound(Player human, Card humanSelectedCard, Card cpu1, Card cpu2, String trumps){


    }

    private String trump;

    // throws an exception if the human selected card is not a card from the play type
    // when the human player have cards of the play type in the deck.
    public GameRound(Player cpuPlayer1, Card cpu1, Player cpuPlayer2, Card cpu2, Player human, Card humanSelectedCard,
                     String playType, String trumps) throws GameExceptions{

        this.compPlayer1Card = cpu1;
        this.compPlayer2Card = cpu2;
        this.playerCard = humanSelectedCard;

        trump = trumps.toLowerCase();
        Log.println( Log.ERROR, "TAG", "inside the constructor game round" );

        //String playType = cpu1.getCategory();

        //validate the human players card
        if(humanSelectedCard.getCategory() != playType){

            Log.println( Log.ERROR, "TAG", "Human selected card is not the play type." );
            //check if the human has any cards of the play type
            //if so thrown an error

            if(human.CheckForCardType(playType)){

                Log.println( Log.ERROR, "TAG", "throwing" );
                throw new GameExceptions("Card Type" + playType + "Exist");
            }

        }

        // initialize three integer type variables to store the number of the card played.
        // Get the number of the card played if the category of the card is of play type.
        int cardNo1 = 0, cardNo2 = 0, cardNo3 = 0;

        Log.println( Log.ERROR, "TAG", "-----------------------------      human card category " + humanSelectedCard.getCategory()+ "     trump category ------"+ trump);

        if(cpu1.getCategory() == trump || cpu2.getCategory() == trump || humanSelectedCard.getCategory() == trump){

            if(cpu1.getCategory() == trump){
                cardNo1 = cpu1.getNumber();
            }

            if(cpu2.getCategory() == trump){
                cardNo2 = cpu2.getNumber();
            }

            if(humanSelectedCard.getCategory() == trump){
                cardNo3 = humanSelectedCard.getNumber();
            }

            chooseWinner(cardNo1, cardNo2, cardNo3, cpuPlayer1, cpuPlayer2, human);
        }

        // if the card played is not a trump.
        // check if the card category is of play type.
        else{
            if(cpu1.getCategory() == playType){
                cardNo1 = cpu1.getNumber();
            }

            if(cpu2.getCategory() == playType){
                cardNo2 = cpu2.getNumber();
            }

            if(humanSelectedCard.getCategory() == playType){
                cardNo3 = humanSelectedCard.getNumber();
            }

            chooseWinner(cardNo1, cardNo2, cardNo3, cpuPlayer1, cpuPlayer2, human);
        }


    }


    // this method chooses the winner depending on the cards played.
    // the player who has played the highest card wins the round.
    public void chooseWinner(int card1, int card2, int card3, Player cpuPlayer1, Player cpuPlayer2, Player human) {

        Log.println(Log.ERROR, "TAG", "card1: " + card1 + " card2: " + card2 + " card3: " + card3);


        if(card1 > card2 && card1 > card3){
            Log.println(Log.ERROR,"Tag","CPU1 ============================> winner");
            this.winner = cpuPlayer1;
        }

        else if(card2 > card1 && card2 > card3){
            Log.println(Log.ERROR,"Tag","CPU1 ============================> winner");
            this.winner = cpuPlayer2;
        }

        else if( card3 > card1 && card3 > card2){
            Log.println(Log.ERROR,"Tag","CPU1 ============================> winner");
            this.winner = human;
        }
    }


    public Card getCompPlayer1Card() {
        return compPlayer1Card;
    }

    public void setCompPlayer1Card(Card compPlayer1Card) {
        this.compPlayer1Card = compPlayer1Card;
    }

    public Card getCompPlayer2Card() {
        return compPlayer2Card;
    }

    public void setCompPlayer2Card(Card compPlayer2Card) {
        this.compPlayer2Card = compPlayer2Card;
    }

    public Card getPlayerCard() {
        return playerCard;
    }

    public void setPlayerCard(Card playerCard) {
        this.playerCard = playerCard;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
