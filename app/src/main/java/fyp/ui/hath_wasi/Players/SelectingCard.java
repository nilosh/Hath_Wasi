package fyp.ui.hath_wasi.Players;

import fyp.ui.hath_wasi.Cards.Card;

public interface SelectingCard {

    public Card selectSmallestCardFromCategory(String category);
    public Card SelectTheHigHighestCardFromCategory(String category);
    public Card SelectRandomCardFromCategory(String category);
    public Card selectHighestCard();
}
