package fyp.ui.hath_wasi.Players;

import fyp.ui.hath_wasi.Cards.Card;

public interface SelectingCard {

    public Card selectSmallestCardFromCategory(String category);
    public Card SelectTheHighestCardFromCategory(String category);
    public Card SelectRandomCardFromCategory(String category);
    public Card selectHighestCard();
    public Card selectSmallestCard();
    public Card selectCard();
    public Card selectCard(Card card);
    public Card selectCard(Card card1,Card card2);

}
