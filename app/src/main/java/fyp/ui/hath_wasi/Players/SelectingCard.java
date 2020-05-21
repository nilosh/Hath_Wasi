package fyp.ui.hath_wasi.Players;

import fyp.ui.hath_wasi.Cards.Card;

public interface SelectingCard {

    Card selectSmallestCardFromCategory(String category);

    Card SelectTheHighestCardFromCategory(String category);

    Card SelectRandomCardFromCategory(String category);

    Card selectHighestCard();

    Card selectSmallestCard();

    Card selectCard();

    Card selectCard(Card card);

    Card selectCard(Card card1, Card card2);

}
