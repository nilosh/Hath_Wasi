package fyp.ui.hath_wasi;

import java.util.ArrayList;
import java.util.Random;

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
        card1 = new Card(1, false, "spades", R.drawable.spades_a);
        card2 = new Card(2, false, "spades", R.drawable.spades_k);
        card3 = new Card(3, false, "spades", R.drawable.spades_q);
        card4 = new Card(4, false, "spades", R.drawable.spades_j);
        card5 = new Card(5, false, "spades", R.drawable.spades6);
        card6 = new Card(6, false, "spades", R.drawable.spades7);
        card7 = new Card(7, false, "spades", R.drawable.spades8);
        card8 = new Card(8, false, "spades", R.drawable.spades9);
        card9 = new Card(9, false, "spades", R.drawable.spades10);

        card10 = new Card(10, false, "clubs", R.drawable.clubs_a);
        card11 = new Card(11, false, "clubs", R.drawable.clubs_k);
        card12 = new Card(12, false, "clubs", R.drawable.clubs_q);
        card13 = new Card(13, false, "clubs", R.drawable.clubs_j);
        card14 = new Card(14, false, "clubs", R.drawable.clubs6);
        card15 = new Card(15, false, "clubs", R.drawable.clubs7);
        card16 = new Card(16, false, "clubs", R.drawable.clubs8);
        card17 = new Card(17, false, "clubs", R.drawable.clubs9);
        card18 = new Card(18, false, "clubs", R.drawable.clubs10);

        card19 = new Card(19, false, "diamonds", R.drawable.diamonds_a);
        card20 = new Card(20, false, "diamonds", R.drawable.diamonds_k);
        card21 = new Card(21, false, "diamonds", R.drawable.diamonds_q);
        card22 = new Card(22, false, "diamonds", R.drawable.diamonds_j);
        card23 = new Card(23, false, "diamonds", R.drawable.diamonds6);
        card24 = new Card(24, false, "diamonds", R.drawable.diamonds7);
        card25 = new Card(25, false, "diamonds", R.drawable.diamonds8);
        card26 = new Card(26, false, "diamonds", R.drawable.diamonds9);
        card27 = new Card(27, false, "diamonds", R.drawable.diamonds10);

        card28 = new Card(28, false, "hearts", R.drawable.hearts_a);
        card29 = new Card(29, false, "hearts", R.drawable.hearts_k);
        card30 = new Card(30, false, "hearts", R.drawable.hearts_q);
        card31 = new Card(31, false, "hearts", R.drawable.hearts_j);
        card32 = new Card(32, false, "hearts", R.drawable.hearts6);
        card33 = new Card(33, false, "hearts", R.drawable.hearts7);
        card34 = new Card(34, false, "hearts", R.drawable.hearts8);
        card35 = new Card(35, false, "hearts", R.drawable.hearts9);
        card36 = new Card(36, false, "hearts", R.drawable.hearts10);

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
