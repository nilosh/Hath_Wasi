package fyp.ui.hath_wasi.Players;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;

public class ComputerPlayerAggressive extends AbComputerPlayer {


    public ComputerPlayerAggressive(String name, DeckOfCards cardDeck ){
        super(name, cardDeck);
    }

    //this method returns a card selected at random given the category of the card from the Card Deck
    @Override
    public Card SelectRandomCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        Random rand = new Random();
        if(categoryOfCards.size() != 0) {
            return categoryOfCards.get(rand.nextInt(categoryOfCards.size()));
        }
        else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }
    }

    //this method returns the highest card from the Card Deck given the category of the card
    @Override
    public Card SelectTheHigHighestCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        Random rand = new Random();
        if(categoryOfCards.size() != 0) {
            return Collections.min(categoryOfCards);
        }
        else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }


    }

    //this method returns the smallest card from the Card Deck given the category of the card
    @Override
    public Card selectSmallestCardFromCategory(String category){
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        Random rand = new Random();
        if(categoryOfCards.size() != 0) {
            return Collections.max(categoryOfCards);

        }
        else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }

    }

    //this method returns the highest card from the deck irrespective of the category
    @Override
    public Card selectHighestCard(){

        return Collections.min(CardDeck);

    }
}