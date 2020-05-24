package fyp.ui.hath_wasi.Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;
import fyp.ui.hath_wasi.Game.Game;

import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterAscending;
import static fyp.ui.hath_wasi.Cards.Card.cardNumberSorterDescending;
import static fyp.ui.hath_wasi.Game.Game.getInstance;

public class ComputerPlayerExpert extends AbComputerPlayer {

    //get the current instance of the game and attributes of it
    Game game = getInstance();
    String trumpCategory = Game.getTrumps();
    Player SinglePlayer = Game.getSinglePlayer();
    Player cpu2Player = Game.getCpu2();
    Player humanPlayer = Game.getHumanPlayer();

    public ComputerPlayerExpert(String name, DeckOfCards cardDeck) {
        super(name, cardDeck);
    }

    //this method returns a card selected at random given the category of the card from the Card Deck
    @Override
    public Card selectRandomCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        Random rand = new Random();
        if (categoryOfCards.size() != 0) {
            return categoryOfCards.get(rand.nextInt(categoryOfCards.size()));
        } else return null;
    }

    //this method returns the highest card from the Card Deck given the category of the card
    @Override
    public Card selectTheHighestCardFromCategory(String category) {

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
        Collections.sort(CardDeck, cardNumberSorterDescending);
        return CardDeck.get(0);
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

        //gets all cards of trump category to an array list
        ArrayList<Card> trumpCards = getCategoryOfCards(trumpCategory);

        //check if this player has bid the trump for the game
        if (this.isTrumpCalled()) {
            //check if the number of cards in hand is more than 3
            if (CardDeck.size() > 3) {
                if (trumpCards.size() > 2) {
                    return selectTheHighestCardFromCategory(trumpCategory);
                } else {
                    final Card bestCard = selectHighestCard();
                    //check if the best card selected is not from trump category
                    if (bestCard.getCategory() != trumpCategory) {
                        return bestCard;
                    } else {
                        while (bestCard.getCategory() == trumpCategory) {
                            Card newBestCard = selectHighestCard();
                            if (newBestCard.getCategory() != trumpCategory) {
                                return newBestCard;
                            } else {
                                continue;
                            }
                        }
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
                    while (bestCard.getCategory() == trumpCategory) {
                        Card newBestCard = selectHighestCard();
                        if (newBestCard.getCategory() != trumpCategory) {
                            return newBestCard;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return null;
    }

    //this method is used to select the best card when you're the second player in the game round
    @Override
    public Card selectCard(Card Player1Card) {

        //get the playing type of the round using the first player's card type
        String Category = Player1Card.getCategory();

        //check if this player has bid the trump for the game
        if (this.isTrumpCalled()) {
            final Integer opponentCard = Player1Card.getCardId();
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
            final Integer teamMemberCard = Player1Card.getCardId();
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

        //get the playing type of the round using the first player's card type
        String Category = player1Card.getCategory();
        //check if this player is the single player
        if (this.getName() == SinglePlayer.getName()) {
            return selectBestCardSinglePlayer(Category, player1Card, player2Card);

        }
        //if this player is not the single player
        else {
            //check if either this player is cpu1 player and cpu2 player is the single player
            //or if the this player is the cpu2 player and human player is the single player
            if ((((this.getName() == Game.getCpu1().getName()) && (cpu2Player == SinglePlayer)) || ((this.getName() == cpu2Player.getName()) && (humanPlayer == SinglePlayer)))) {
                return selectBestCardTeamPlayer(Category, player1Card, player2Card);
            } else {
                return selectBestCardTeamPlayer(Category, player2Card, player1Card);
            }
        }
    }

    //select best card when you're the single player and the third player
    public Card selectBestCardSinglePlayer(String Category, Card opponent1Card, Card opponent2Card) {

        final Integer opponent1 = opponent1Card.getCardId();
        final Integer opponent2 = opponent2Card.getCardId();
        final String opponent1Cat = opponent1Card.getCategory();
        final String opponent2Cat = opponent2Card.getCategory();
        final boolean categoryMatch = opponent1Cat.equals(opponent2Cat);
        final Integer difference = opponent1.compareTo(opponent2);

        //check if both cards played are of the same type
        if (categoryMatch) {
            //check if opponent1 has a bigger card than opponent2
            if (difference < 0) {
                // check if this user has the calling card type
                if (CheckForCardType(Category)) {
                    final Card myCard = selectTheHighestCardFromCategory(Category);
                    //check if my highest card is lower than the opponents' highest card
                    if (myCard.getCardId() > opponent1) {
                        return selectSmallestCardFromCategory(Category);
                    }
                    //if my highest card is greater than the opponents' highest card
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
            } else if (difference > 0) {
                //opponent1 has a smaller card than opponent2
                // check if this user has the calling card type
                if (CheckForCardType(Category)) {
                    final Card myCard = selectTheHighestCardFromCategory(Category);
                    //if my highest card is lower than the opponent2's card
                    if (myCard.getCardId() > opponent2) {
                        return selectSmallestCardFromCategory(Category);
                    } else {
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
                if (opponent1Cat == trumpCategory) {
                    //check if this player has cards of trump category
                    if (CheckForCardType(trumpCategory)) {
                        final Card myNewCard = selectTheHighestCardFromCategory(trumpCategory);
                        //if my highest card is lower than the opponent2's card
                        if (myNewCard.getCardId() > opponent2) {
                            return selectSmallestCard();
                        } else {
                            return myNewCard;
                        }
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

        return null;
    }

    public Card selectBestCardTeamPlayer(String Category, Card myTeamCard, Card opponentCard) {

        final Integer myTeam = myTeamCard.getCardId();
        final Integer opponent = opponentCard.getCardId();
        final String myTeamCat = myTeamCard.getCategory();
        final String opponentCat = opponentCard.getCategory();
        final boolean categoryMatch = myTeamCat.equals(opponentCat);
        final Integer difference = myTeam.compareTo(opponent);

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
            //my player has a smaller card than opponent
            else if (difference > 0) {
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
                final Card myCard = selectTheHighestCardFromCategory(Category);
                //if my highest card is lower than the opponent's card
                if (myCard.getCardId() > opponent) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return myCard;
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
        return null;
    }
}
