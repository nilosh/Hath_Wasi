package fyp.ui.hath_wasi.Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;

import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterDescending;

public class ComputerPlayerIntermediate extends AbComputerPlayer {
    public ComputerPlayerIntermediate(String name, DeckOfCards cardDeck) {
        super(name, cardDeck);
    }

    //this method returns a card selected at random given the category of the card from the Card Deck
    @Override
    public Card selectRandomCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        Random rand = new Random();
        if (categoryOfCards.size() != 0) {
            return categoryOfCards.get(rand.nextInt(categoryOfCards.size()));
        } else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }
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

    //this method returns the highest card from the hand irrespective of the category
    @Override
    public Card selectHighestCard() {

        Collections.sort(CardDeck, cardNumberSorterDescending);
        return CardDeck.get(0);
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
