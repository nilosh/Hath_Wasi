package fyp.ui.hath_wasi.Cards;

import java.util.Comparator;

public class Card implements Comparable<Card> {

    // The following two methods are called when you sort an array list of cards in the ascending and descending order according to the card number.
    //Done using importing Comparator that allows to implement a custom Comparator method.
    public static Comparator<Card> cardNumberSorterAscending = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            int cardNumber1 = o1.getNumber();
            int cardNumber2 = o2.getNumber();
            // ascending order
            return cardNumber1 - cardNumber2;
        }
    };
    public static Comparator<Card> cardNumberSorterDescending = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            int cardNumber1 = o1.getNumber();
            int cardNumber2 = o2.getNumber();
            // descending order
            return cardNumber2 - cardNumber1;
        }
    };
    private final int cardId;
    private boolean trump;
    private String category;
    private final int imageSource;
    private final int number;

    // Constructor for Card Class.
    public Card(int cardId, boolean trump, String category, int imageSource, int number) {
        this.cardId = cardId;
        this.trump = trump;
        this.category = category;
        this.imageSource = imageSource;
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageSource() {
        return imageSource;
    }

    public int getCardId() {
        return cardId;
    }


    // This method is automatically called when you sort an array list.
    // Done using implementing Comparable that allows to implement a custom compareTo method.

    public int getNumber() {
        return number;
    }

    public int compareTo(Card card) {
        if (this.cardId > card.getCardId()) {
            return 1;
        } else if (this.cardId < card.getCardId()) {
            return -1;
        } else {
            return 0;
        }
    }


}
