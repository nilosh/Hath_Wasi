package fyp.ui.hath_wasi.Cards;

import java.util.ArrayList;
import java.util.Random;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.R;

public class DeckOfCards {

    ArrayList<Card> cardDeck = new ArrayList<>();

    static private Random randomCardGenerator;
    final int handSize = 12;

    // declare 36 cards of type Card
    Card card1, card2, card3, card4, card5, card6, card7, card8, card9, card10,
            card11, card12, card13, card14, card15,card16, card17, card18, card19, card20,
            card21, card22, card23, card24, card25, card26, card27, card28, card29, card30,
            card31, card32, card33, card34, card35, card36;

    //Assignment of attribute values to 36 cards
    public DeckOfCards() {
        card1 = new Card(1, false, "spades", R.drawable.spades_a, 14);
        card2 = new Card(2, false, "spades", R.drawable.spades_k, 13);
        card3 = new Card(3, false, "spades", R.drawable.spades_q, 12);
        card4 = new Card(4, false, "spades", R.drawable.spades_j, 11);
        card5 = new Card(5, false, "spades", R.drawable.spades10, 10);
        card6 = new Card(6, false, "spades", R.drawable.spades9, 9);
        card7 = new Card(7, false, "spades", R.drawable.spades8, 8);
        card8 = new Card(8, false, "spades", R.drawable.spades7, 7);
        card9 = new Card(9, false, "spades", R.drawable.spades6, 6);
        card10 = new Card(10, false, "hearts", R.drawable.hearts_a, 14);
        card11 = new Card(11, false, "hearts", R.drawable.hearts_k, 13);
        card12 = new Card(12, false, "hearts", R.drawable.hearts_q, 12);
        card13 = new Card(13, false, "hearts", R.drawable.hearts_j, 11);
        card14 = new Card(14, false, "hearts", R.drawable.hearts10, 10);
        card15 = new Card(15, false, "hearts", R.drawable.hearts9, 9);
        card16 = new Card(16, false, "hearts", R.drawable.hearts8, 8);
        card17 = new Card(17, false, "hearts", R.drawable.hearts7, 7);
        card18 = new Card(18, false, "hearts", R.drawable.hearts6, 6);
        card19 = new Card(19, false, "clubs", R.drawable.clubs_a, 14);
        card20 = new Card(20, false, "clubs", R.drawable.clubs_k, 13);
        card21 = new Card(21, false, "clubs", R.drawable.clubs_q, 12);
        card22 = new Card(22, false, "clubs", R.drawable.clubs_j, 11);
        card23 = new Card(23, false, "clubs", R.drawable.clubs10, 10);
        card24 = new Card(24, false, "clubs", R.drawable.clubs9, 9);
        card25 = new Card(25, false, "clubs", R.drawable.clubs8, 8);
        card26 = new Card(26, false, "clubs", R.drawable.clubs7, 7);
        card27 = new Card(27, false, "clubs", R.drawable.clubs6, 6);
        card28 = new Card(28, false, "diamonds", R.drawable.diamonds_a, 14);
        card29 = new Card(29, false, "diamonds", R.drawable.diamonds_k, 13);
        card30 = new Card(30, false, "diamonds", R.drawable.diamonds_q, 12);
        card31 = new Card(31, false, "diamonds", R.drawable.diamonds_j, 11);
        card32 = new Card(32, false, "diamonds", R.drawable.diamonds10, 10);
        card33 = new Card(33, false, "diamonds", R.drawable.diamonds9, 9);
        card34 = new Card(34, false, "diamonds", R.drawable.diamonds8, 8);
        card35 = new Card(35, false, "diamonds", R.drawable.diamonds7, 7);
        card36 = new Card(36, false, "diamonds", R.drawable.diamonds6, 6);

        //Add all 36 cards to the CardDeck one by one
        cardDeck.add(card1);
        cardDeck.add(card2);
        cardDeck.add(card3);
        cardDeck.add(card4);
        cardDeck.add(card5);
        cardDeck.add(card6);
        cardDeck.add(card7);
        cardDeck.add(card8);
        cardDeck.add(card9);

        cardDeck.add(card10);
        cardDeck.add(card11);
        cardDeck.add(card12);
        cardDeck.add(card13);
        cardDeck.add(card14);
        cardDeck.add(card15);
        cardDeck.add(card16);
        cardDeck.add(card17);
        cardDeck.add(card18);

        cardDeck.add(card19);
        cardDeck.add(card20);
        cardDeck.add(card21);
        cardDeck.add(card22);
        cardDeck.add(card23);
        cardDeck.add(card24);
        cardDeck.add(card25);
        cardDeck.add(card26);
        cardDeck.add(card27);

        cardDeck.add(card28);
        cardDeck.add(card29);
        cardDeck.add(card30);
        cardDeck.add(card31);
        cardDeck.add(card32);
        cardDeck.add(card33);
        cardDeck.add(card34);
        cardDeck.add(card35);
        cardDeck.add(card36);
    }

    //generate an ArrayList of hand size number of cards randomly and remove the relative index from the cardDeck
    public ArrayList<Card>DealingCards(){
        ArrayList<Card>hand = new ArrayList<Card>();
        randomCardGenerator = new Random();

        for(int i = 0; i < handSize; i++) {
            int index = randomCardGenerator.nextInt(cardDeck.size());
            hand.add(cardDeck.remove(index));
        }
        return hand;
        }
}
