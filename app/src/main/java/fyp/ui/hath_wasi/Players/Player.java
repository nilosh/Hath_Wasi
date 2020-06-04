package fyp.ui.hath_wasi.Players;

import java.util.ArrayList;
import java.util.Collections;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;

public class Player {
    protected int numberOfCardsRemaining;
    ArrayList<Card> CardDeck = new ArrayList();
    //Attributes of Player
    private final String name;
    private final int score;
    private final boolean trumpCalled;


    //Constructor of the player class
    public Player(String name, DeckOfCards cardDeck) {
        this.name = name;
        this.score = 0;
        this.trumpCalled = false;
        this.numberOfCardsRemaining = 12;
        this.CardDeck = cardDeck.DealingCards();
        //Sort the card deck in hand
        Collections.sort(this.CardDeck);

    }

    public Card getCardFromIndex(int index) {
        return CardDeck.get(index);
    }

    public int getCardImagePathFromIndex(int index) {
        return getCardFromIndex(index).getImageSource();
    }

    public String getName() {
        return name;
    }


    public boolean isTrumpCalled() {
        return trumpCalled;
    }


    //returns the number of cards in hand
    public int getNoOfCards() {
        return this.CardDeck.size();
    }


    //take a card type as an input and returns true if the given card type exist within the player's hand of cards
    public boolean CheckForCardType(String checkType) {
        for (int i = 0; i < this.numberOfCardsRemaining; i++) {
            if (CardDeck.get(i).getCategory() == checkType) {
                return true;
            }
        }
        return false;
    }


    public int getNumberOfCardsRemaining() {
        return numberOfCardsRemaining;
    }

    public void setNumberOfCardsRemaining(int numberOfCardsRemaining) {
        this.numberOfCardsRemaining = numberOfCardsRemaining;
    }

    //returns an arrayList of cards
    public ArrayList<Card> getCardDeck() {
        return CardDeck;
    }


}


