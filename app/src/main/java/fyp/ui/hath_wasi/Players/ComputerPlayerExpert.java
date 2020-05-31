package fyp.ui.hath_wasi.Players;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;
import fyp.ui.hath_wasi.Game.Game;

import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterAscending;
import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterDescending;
import static fyp.ui.hath_wasi.Game.Game.getCpu2;
import static fyp.ui.hath_wasi.Game.Game.getHumanPlayer;
import static fyp.ui.hath_wasi.Game.Game.getSinglePlayer;
import static fyp.ui.hath_wasi.Game.Game.getTrumps;

public class ComputerPlayerExpert extends AbComputerPlayer {

    public ComputerPlayerExpert(String name, DeckOfCards cardDeck) {
        super(name, cardDeck);
    }

    //this method returns the highest card from the Card Deck given the category of the card
    @Override
    public Card selectTheHighestCardFromCategory(String category) {

        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        return Collections.min(categoryOfCards);
    }

    //this method returns the smallest card from the Card Deck given the category of the card
    @Override
    public Card selectSmallestCardFromCategory(String category) {

        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        return Collections.max(categoryOfCards);
    }

    //this method returns the highest card from the hand irrespective of the category
    @Override
    public Card selectHighestCard() {
        Collections.sort(CardDeck, cardNumberSorterDescending);
        return CardDeck.get(0);
    }

    //this method returns the highest card from the hand irrespective of the category
    private Card selectNextHighestCard() {
        Collections.sort(CardDeck, cardNumberSorterDescending);
        return CardDeck.get(1);
    }


    //this method returns the smallest card from the hand irrespective of the category
    @Override
    public Card selectSmallestCard() {
        Collections.sort(CardDeck, cardNumberSorterAscending);
        return CardDeck.get(0);
    }

    //this method is used to select the best card when you're the first player in the game round
    @Override
    public Card selectCard() {

        String trumpCategory = getTrumps();
        //gets all cards of trump category to an array list
        ArrayList<Card> trumpCards = getCategoryOfCards(trumpCategory);

        //check if this player has bid the trump for the game
        if (this.isTrumpCalled()) {
            //check if the number of cards in hand is more than 3
            if (CardDeck.size() > 3) {
                if ((trumpCards.size() > 2) || (trumpCards.size() == 2)) {
                    return selectTheHighestCardFromCategory(trumpCategory);
                } else {
                    final Card bestCard = selectHighestCard();
                    //check if the best card selected is not from trump category
                    if (bestCard.getCategory() != trumpCategory) {
                        return bestCard;
                    } else {
                        return selectNextHighestCard();
                    }
                }
            } else {
                return selectHighestCard();
            }
        } else {
            //check if the number of cards in hand is less than 3
            if (CardDeck.size() < 3) {
                return selectHighestCard();
            } else {
                final Card bestCard = selectHighestCard();
                //check if the best card selected is not from trump category
                if (bestCard.getCategory() != trumpCategory) {
                    return bestCard;
                } else {
                    return selectNextHighestCard();
                }
            }
        }
    }

    //this method is used to select the best card when you're the second player in the game round
    @Override
    public Card selectCard(Card Player1Card) {

        Log.println(Log.ERROR, "Tag", "------- inside selectCard(card) of EXPERT PLAYER -------");
        //get the playing type of the round using the first player's card type
        String Category = Player1Card.getCategory();
        String trumpCategory = getTrumps();

        //check if this player has bid the trump for the game
        if (this.isTrumpCalled()) {
            final int opponentCard = Player1Card.getCardId();
            //check if this player has the play type
            if (CheckForCardType(Category)) {
                final Card myCard = selectTheHighestCardFromCategory(Category);
                //check if my highest card is lower than the opponent's card
                if (myCard.getCardId() > opponentCard) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return myCard;
                }
            } else {
                //check if the play type is same as the trump
                if (Category == trumpCategory) {
                    return selectSmallestCard();
                } else {
                    return selectSmallestCardFromCategory(trumpCategory);
                }
            }
            // when this player has not bid for the game
        } else {
            final int teamMemberCard = Player1Card.getCardId();
            //check if this player has the play type
            if (CheckForCardType(Category)) {
                final Card myCard = selectTheHighestCardFromCategory(Category);
                //check if this player's highest card is lower than player 1's card
                if (myCard.getCardId() > teamMemberCard) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return myCard;
                }
            } else {
                return selectSmallestCard();
            }
        }
    }

    //this method is used to select which method to select when you're the third player to choose the card according to the player type
    @Override
    public Card selectCard(Card player1Card, Card player2Card) {

        Log.println(Log.ERROR, "Tag", "------- inside selectCard(card, card) of EXPERT PLAYER -------");
        //get the playing type of the round using the first player's card type

        Player singlePlayer = getSinglePlayer();
        String trumpCategory = getTrumps();
        Player cpu2Player = getCpu2();
        Player humanPlayer = getHumanPlayer();
        String Category = player1Card.getCategory();
        Log.println(Log.ERROR, "Tag", "------- category is " + Category);
        Log.println(Log.ERROR, "Tag", "------- this player = " + this.getName() + " ------- single player =  " + singlePlayer.getName());
        Log.println(Log.ERROR, "Tag", " ------- get Trumps in this round --> trump category =  " + trumpCategory);

        //check if this player is the single player
        if (this.getName() == singlePlayer.getName()) {
            Log.println(Log.ERROR, "Tag", "------- inside selectCard(card) of EXPERT PLAYER ------- inside single player if");
            return selectBestCardSinglePlayer(Category, player1Card, player2Card);

        }
        //if this player is not the single player
        else {
            Log.println(Log.ERROR, "Tag", "------- inside selectCard(card) of EXPERT PLAYER ------- inside single player else");
            //check if either this player is cpu1 player and cpu2 player is the single player
            //or if the this player is the cpu2 player and human player is the single player
            if ((((this.getName() == Game.getCpu1().getName()) && (cpu2Player == singlePlayer)) || ((this.getName() == cpu2Player.getName()) && (humanPlayer == singlePlayer)))) {
                return selectBestCardTeamPlayer(Category, player1Card, player2Card);
            } else {
                return selectBestCardTeamPlayer(Category, player2Card, player1Card);
            }
        }
    }

    //select best card when you're the single player and the third player
    private Card selectBestCardSinglePlayer(String Category, Card opponent1Card, Card opponent2Card) {

        final int opponent1 = opponent1Card.getCardId();
        final int opponent2 = opponent2Card.getCardId();
        final String opponent1Cat = opponent1Card.getCategory();
        final String opponent2Cat = opponent2Card.getCategory();
        final boolean categoryMatch = opponent1Cat.equals(opponent2Cat);
        String trumpCategory = getTrumps();

        //check if both cards played are of the same type
        if (categoryMatch) {
            // check if this user has the calling card type
            if (CheckForCardType(Category)) {
                final Card myCard = selectTheHighestCardFromCategory(Category);
                //check if my highest card is lower than the opponents' cards
                if ((myCard.getCardId() > opponent1) || (myCard.getCardId() > opponent2)) {
                    return selectSmallestCardFromCategory(Category);
                }
                //if my highest card is greater than the opponents' cards
                else {
                    return myCard;
                }
            } else {
                //calling category type is not available
                //check if this player has cards of trump category
                if (CheckForCardType(trumpCategory)) {
                    return selectSmallestCardFromCategory(trumpCategory);
                }
                //if trump category is not available
                else {
                    return selectSmallestCard();
                }
            }
        } else {
            //Category of both opponents don't match
            // check if this user has the calling card type
            if (CheckForCardType(Category)) {
                final Card myCard = selectTheHighestCardFromCategory(Category);
                //if my highest card is lower than the opponent1's card
                if (myCard.getCardId() > opponent1) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return myCard;
                }
            } else {
                //calling category type is not available
                //check if opponent2's card is of trump category
                if (opponent2Cat == trumpCategory) {
                    //check if this player has cards of trump category
                    if (CheckForCardType(trumpCategory)) {
                        final Card myNewCard = selectTheHighestCardFromCategory(trumpCategory);
                        //if my highest card is lower than the opponent2's card
                        if (myNewCard.getCardId() > opponent2) {
                            return selectSmallestCard();
                        } else {
                            return myNewCard;
                        }
                    } else {
                        return selectSmallestCard();
                    }
                }
                //when opponent2's card is not of trump category
                else {
                    if (CheckForCardType(trumpCategory)) {
                        return selectSmallestCardFromCategory(trumpCategory);
                    }
                    //if trump category is not available
                    else {
                        return selectSmallestCard();
                    }
                }
            }
        }
    }

    private Card selectBestCardTeamPlayer(String Category, Card myTeamCard, Card opponentCard) {

        final Integer myTeam = myTeamCard.getCardId();
        final Integer opponent = opponentCard.getCardId();
        final String myTeamCat = myTeamCard.getCategory();
        final String opponentCat = opponentCard.getCategory();
        final boolean categoryMatch = myTeamCat.equals(opponentCat);
        final int difference = myTeam.compareTo(opponent);
        String trumpCategory = getTrumps();

        //check if both cards played are of the same type
        if (categoryMatch) {
            //check if myTeam has a bigger card than opponent
            if (difference < 0) {
                // check if this user has the calling card type
                if (CheckForCardType(Category)) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return selectSmallestCard();
                }
            }
            //my team player has a smaller card than opponent
            else {
                // check if this user has the calling card type
                if (CheckForCardType(Category)) {
                    final Card myCard = selectTheHighestCardFromCategory(Category);
                    //check if my highest card is lower than the opponent's card
                    if (myCard.getCardId() > opponent) {
                        return selectSmallestCardFromCategory(Category);
                    } else {
                        return myCard;
                    }
                } else {
                    //check if this user has cards of trump category
                    if (CheckForCardType(trumpCategory)) {
                        return selectSmallestCardFromCategory(trumpCategory);
                    }
                    //if trump category is not available
                    else {
                        return selectSmallestCard();
                    }
                }
            }
        } else {
            //category of both players don't match
            // check if this user has the calling card type
            if (CheckForCardType(Category)) {
                //check if opponent's card is of calling type
                if (opponentCat == Category) {
                    final Card myCard = selectTheHighestCardFromCategory(Category);
                    //if my highest card is lower than the opponent's card
                    if (myCard.getCardId() > opponent) {
                        return selectSmallestCardFromCategory(Category);
                    } else {
                        return myCard;
                    }
                } else {
                    return selectSmallestCardFromCategory(Category);
                }
            } else {
                //calling category type is not available
                //check if opponent's card is of trump category
                if (opponentCat == trumpCategory) {
                    //check if this player has cards of trump category
                    if (CheckForCardType(trumpCategory)) {
                        final Card myNewCard = selectTheHighestCardFromCategory(trumpCategory);
                        //if my highest card is lower than the opponent2's card
                        if (myNewCard.getCardId() > opponent) {
                            return selectSmallestCard();
                        } else {
                            return myNewCard;
                        }
                    } else {
                        return selectSmallestCard();
                    }
                }
                //when opponent's card is not of trump category
                else {
                    if (CheckForCardType(trumpCategory)) {
                        return selectSmallestCardFromCategory(trumpCategory);
                    }
                    //if trump category is not available
                    else {
                        return selectSmallestCard();
                    }
                }
            }
        }
    }
}
