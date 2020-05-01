package fyp.ui.hath_wasi.Players;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;
import fyp.ui.hath_wasi.Game.Game;

import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterAscending;
import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterDescending;
import static fyp.ui.hath_wasi.Game.Game.getInstance;

public class ComputerPlayerHard extends AbComputerPlayer {

    public ComputerPlayerHard(String name, DeckOfCards cardDeck) {
        super(name, cardDeck);
    }

    //this method returns a card selected at random given the category of the card from the Card Deck
    @Override
    public Card SelectRandomCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        Random rand = new Random();
        if (categoryOfCards.size() != 0) {
            return categoryOfCards.get(rand.nextInt(categoryOfCards.size()));
        } else return null;
    }

    //this method returns the highest card from the Card Deck given the category of the card
    @Override
    public Card SelectTheHighestCardFromCategory(String category) {

        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        if (categoryOfCards.size() != 0) {
            return Collections.min(categoryOfCards);
        } else return null;
    }

    //this method returns the smallest card from the Card Deck given the category of the card
    @Override
    public Card selectSmallestCardFromCategory(String category) {

        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        if (categoryOfCards.size() != 0) {
            return Collections.max(categoryOfCards);
        } else return null;
    }

    //this method returns the highest card from the hand irrespective of the category
    @Override
    public Card selectHighestCard() {
        Collections.sort(CardDeck,cardNumberSorterDescending);
        return CardDeck.get(0);
    }

    //this method returns the smallest card from the hand irrespective of the category
    @Override
    public Card selectSmallestCard() {
        Collections.sort(CardDeck,cardNumberSorterAscending);
        return CardDeck.get(0);
    }

    //get the current instance of the game and attributes of it
    Game game = getInstance();
    String trumpCategory = game.getTrumps();

    //this method is used to select the best card when you're the first player in the game round
    @Override
    public Card selectCard(){

        //gets all cards of trump category to an array list
        ArrayList<Card> trumpCards = getCategoryOfCards(trumpCategory);

        //check if this player has bid the trump for the game
        if(this.isTrumpCalled()==true){
            //check if the number of cards in hand is more than 3
            if(CardDeck.size() > 3) {
                if (trumpCards.size() > 2) { return SelectTheHighestCardFromCategory(trumpCategory); }
                else {
                    final Card bestCard = selectHighestCard();
                    //check if the best card selected is not from trump category
                    if (bestCard.getCategory() != trumpCategory) { return bestCard;}
                    else {
                        while (bestCard.getCategory() == trumpCategory) {
                            Card newBestCard = selectHighestCard();
                            if (newBestCard.getCategory() != trumpCategory) { return newBestCard; }
                            else {
                                continue;
                            }
                        }
                    }
                }
            } else {return selectHighestCard();}
        } else{
            //check if the number of cards in hand is less than 3
            if(CardDeck.size()< 3){return selectHighestCard(); }
            else{
                final Card bestCard = selectHighestCard();
                //check if the best card selected is not from trump category
                if (bestCard.getCategory() != trumpCategory) { return bestCard;}
                else {
                    while (bestCard.getCategory() == trumpCategory) {
                        Card newBestCard = selectHighestCard();
                        if (newBestCard.getCategory() != trumpCategory) { return newBestCard; }
                        else { continue; }
                    }
                }
            }
        }
        return null;
    }

    //this method is used to select the best card when you're the second player in the game round
    @Override
    public Card selectCard(Card Player1Card){
        return null;
    }

    //this method is used to select the best card when you're the Third player in the game round
    @Override
    public Card selectCard(Card player1Card, Card player2Card){
        return null;
    }

}
