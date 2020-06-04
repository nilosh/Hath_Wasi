package fyp.ui.hath_wasi.Players;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;

public class ComputerPlayerBeginner extends AbComputerPlayer {


    public ComputerPlayerBeginner(String name, DeckOfCards cardDeck) {
        super(name, cardDeck);
    }

    //this method returns the highest card from the Card Deck given the category of the card
    @Override
    public Card selectTheHighestCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        Random rand = new Random();
        if (categoryOfCards.size() != 0) {
            return Collections.min(categoryOfCards);
        } else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }


    }

    //this method returns the smallest card from the Card Deck given the category of the card
    @Override
    public Card selectSmallestCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        Random rand = new Random();
        if (categoryOfCards.size() != 0) {
            return Collections.max(categoryOfCards);

        } else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }

    }

    //this method returns the highest card according to the card id from the hand irrespective of the category
    @Override
    public Card selectHighestCard() {

        return Collections.min(CardDeck);

    }

    @Override
    public Card selectSmallestCard() {
        return null;
    }

    //this method is used to select a card when you're the first player in the game round
    @Override
    public Card selectCard() {
        return selectHighestCard();
    }

    //this method is used to select a card when you're the second player in the game round
    @Override
    public Card selectCard(Card card) {
        //derive the play type of the round
        String category = card.getCategory();
        return selectSmallestCardFromCategory(category);
    }

    //this method is used to select a card when you're the third player in the game round
    @Override
    public Card selectCard(Card card1, Card card2) {
        //derive the play type of the round
        String category = card1.getCategory();
        return selectTheHighestCardFromCategory(category);
    }
}