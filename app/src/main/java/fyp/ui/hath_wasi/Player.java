package fyp.ui.hath_wasi;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    //Attributes of Player
    private String name;
    private int score;
    protected int numberOfCardsRemaining;

    ArrayList <Card> CardDeck = new ArrayList();


    //instance of a Player
    public Player(String name, DeckOfCards cardDeck ) {
        this.name = name;
        this.score = 0;
        this.numberOfCardsRemaining = 12;
        this.CardDeck = cardDeck.DealingCards();
        //Sort the card deck in hand
        Collections.sort(this.CardDeck);

    }

    public Card getCardFromIndex(int index){
        return CardDeck.get(index);
    }

    public int getCardImagePathFromIndex(int index){
        return getCardFromIndex(index).getImageSource();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // returns an arrayList of 12 cards
    public ArrayList<Card> getPlayerCards(){
        return CardDeck;
    }

    public int getNoOfCards(){
        return this.CardDeck.size();
    }


    //take a card type as an input and return true if the given card type exist within the players cards
    public boolean CheckForCardType(String checkType){
        for (int i = 0; i < this.numberOfCardsRemaining; i++){
            if(CardDeck.get(i).getCategory() == checkType){
                return true;
            }
        }
        return false;
    }


}


