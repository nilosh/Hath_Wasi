package fyp.ui.hath_wasi.Game;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.ComputerPlayerCardViews;
import fyp.ui.hath_wasi.Game.GameScores.GameScore;
import fyp.ui.hath_wasi.Game.GameScores.ScoreBoard;
import fyp.ui.hath_wasi.Game.GameSounds.Sounds;

import fyp.ui.hath_wasi.Messages.Message;
import fyp.ui.hath_wasi.Players.AbComputerPlayer;
import fyp.ui.hath_wasi.Players.Player;
import fyp.ui.hath_wasi.R;
import fyp.ui.hath_wasi.Screens.game_page;

public class Game {


    private static Game ourInstance;

    private static Player singlePlayer;
    private static Player teamPlayer1;
    private static Player teamPlayer2;

    private static Player humanPlayer;
    private static AbComputerPlayer cpu1;
    private static AbComputerPlayer cpu2;
    private static int singlePlayerScore;
    private static int teamScore;
    private static GameRound[] playedRounds;
    private static int numberOfRoundsPlayed;
    private static Player startPlayer;
    private static boolean invalidCardByHuman;
    private static Card com2Card;
    private static Player[] players;
    private static int playerTurnIndex;
    private static boolean gameFinish;
    private static Sounds sounds;

    private static String trumps;

    private static Activity activity;


    // Game constructors.
    private Game(Activity _activity, Player singlePlayer, Player teamPlayer1, Player teamPlayer2, Player humanPlayer,
                 AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps) {
        this.singlePlayer = singlePlayer;
        this.teamPlayer1 = teamPlayer1;
        this.teamPlayer2 = teamPlayer2;
        this.singlePlayerScore = 0;
        this.playedRounds = new GameRound[12];
        this.numberOfRoundsPlayed = 0;
        this.cpu1 = cpu1;
        this.cpu2 = cpu2;
        this.startPlayer = startPlayer;
        this.trumps = trumps;
        this.humanPlayer = humanPlayer;
        this.activity = _activity;
        this.invalidCardByHuman =  false;
        this.players = new Player[]{humanPlayer, cpu2, cpu1};
        this.playerTurnIndex = 0;
        this.gameFinish = false;
        this.sounds = new Sounds(activity);
        updateRoundNumber();


    }

    private Game(){}

    // this getter method for our instance checks if there is already a game loaded (playing a game)
    // returns a new game if the instance is null
    public static Game getInstance(Activity _activity, Player singlePlayer, Player teamPlayer1, Player teamPlayer2, Player humanPlayer, AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps) {


        if(ourInstance == null) {
            ourInstance = new Game(_activity, singlePlayer, teamPlayer1, teamPlayer2, humanPlayer, cpu1, cpu2, startPlayer, trumps);
        }
        return ourInstance;
    }

    public static Game getInstance() {

        return ourInstance;
    }


    Card c1, c2;

    // this method alters the game instance for the game to be played if the computer players select the trump.
    public void alterInstance(Player singlePlayer, Player teamPlayer1, AbComputerPlayer teamPlayer2, Player humanPlayer, AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps) {
        this.singlePlayer = singlePlayer;
        this.teamPlayer1 = teamPlayer1;
        this.teamPlayer2 = teamPlayer2;
        this.singlePlayerScore = 0;
        this.teamScore = 0;
        this.playedRounds = new GameRound[12];
        this.numberOfRoundsPlayed = 0;
        this.cpu1 = cpu1;
        this.cpu2 = cpu2;
        this.startPlayer = startPlayer;
        this.trumps = trumps;
        this.humanPlayer = humanPlayer;
        this.invalidCardByHuman =  false;
        this.gameFinish = false;
        updateRoundNumber();

    }

    //this method displays the round number text view on screen.
    public void updateRoundNumber(){

        final TextView roundNumber = (TextView) getActivity().findViewById(R.id.textViewRoundTag);
        roundNumber.setText(ScoreBoard.getNumberOfScores() + 1 + "/10 Rounds");

    }

    public void  playNextMove(Card selectedCard){

        game_page.cardTouch(false);

        // declare three variables to hold the imageViews of the playing cards
        // of the three players.
        final ImageView com1 =  this.activity.findViewById(R.id.com1Card);
        final ImageView com2 =  this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder =  this.activity.findViewById(R.id.playCard);

        Log.println( Log.ERROR, "TAG", "startPlayer.getName() :" + startPlayer.getName()  );
        Log.println( Log.ERROR, "TAG", "this.numberOfRoundsPlayed :" + this.numberOfRoundsPlayed);

        // If it is the first round of the game and the start player is not an abstract com player
        // Or the last round winner is not Com Player 1 and last round player is not Com Player 2.
        if( (this.numberOfRoundsPlayed == 0 && startPlayer.getName() != "Computer Player 1" && startPlayer.getName() != "Computer Player 2" )||
                (( this.numberOfRoundsPlayed > 0 && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() != "Computer Player 1"
                        && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() != "Computer Player 2") )){

            try {

                game_page.cardTouch(false);

                Log.println( Log.ERROR, "TAG", "Try block 1.." );
                Log.println(Log.ERROR, "TAG", "passing trumps in block 1 : " + this.trumps);

                Card card1 = this.cpu1.SelectTheHighestCardFromCategory(selectedCard.getCategory());
                Card card2 = this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory());

                GameRound gameRound = new GameRound( this.cpu1,card1,
                        this.cpu2, card2,
                        this.humanPlayer, selectedCard, selectedCard.getCategory() , this.trumps );

                // increment the number of rounds played.
                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                invalidCardByHuman = false;

                // Store the round winner in a variable.
                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                // Set the image resource of the selected cards to the cards that are being played and make them invisible
                com1.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer1Card().getImageSource());
                com2.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer2Card().getImageSource());
                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);

                // remove the Computer player 1 and Computer Player 2 selected cards from the card decks of the computer player 1 and Computer Player 2.
                // And update the number of cards remaining for Com Player 1 and Com Player 2.
                this.cpu1.getCardDeck().remove(card1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(card2);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                cpu1.displayDetails();
                cpu2.displayDetails();

                // Set animations.
                final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);



                // Allows to delay the animations associated with the particular card.
                GameHandlers gameHandler = new GameHandlers("com2", com2, animationRl, sounds, 1000, 1500);


                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        com2.startAnimation(animationRl);
//                        animationRl.setAnimationListener(new Animation.AnimationListener() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//                                sounds.cardClick();
//
//                                ComputerPlayerCardViews.hideCardFromPlayer2();
//
//                                com2.setVisibility(View.VISIBLE);
//                                com2.setImageAlpha(1000);
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) { }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) { }
//                        });
//                    }
//                }, 1500);

                gameHandler = new GameHandlers("com1", com1, animationLr, sounds, 1000, 3000);

//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        com1.startAnimation(animationLr);
//                        animationLr.setAnimationListener(new Animation.AnimationListener() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//                                sounds.cardClick();
//                                ComputerPlayerCardViews.hideCardFromPlayer1();
//
//                                com1.setVisibility(View.VISIBLE);
//                                com1.setImageAlpha(1000);
//                            }
//
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onAnimationEnd(Animation animation) { }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) { }
//                        });
//                    }
//                }, 3000);

                // Update score on the score bar with a delay.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);

                // if this round's winner is a Computer Player.
                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();

                }
                // Else, make all the played cards invisible (from the last round)
                // and set cardTouch true.
                else {

                    GameHandlers.collectCards(sounds, com1, com2, playerPlaceholder, 6000);

//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            sounds.cardCollect();
//
//                            com1.setVisibility(View.INVISIBLE);
//                            com2.setVisibility(View.INVISIBLE);
//                            playerPlaceholder.setVisibility(View.INVISIBLE);
//                            game_page.cardTouch(true);
//
//                        }
//                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 1, at the very end" );

            } catch (Exception e) {

                // decrement the added number of played.
                numberOfRoundsPlayed--;

                Log.println( Log.ERROR, "TAG", "on the first catch block" );
                popUpDialog(Message.getMessageSelectValidCard(), "Card Selection");

                // human player played an invalid card, so allow to play again.
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);

            }
        }

        // Else if the first round of the game and the start player is ComPlayer 1
        // Or the last round's winner is ComPlayer 1.
        else if(( (this.numberOfRoundsPlayed == 0 &&  (startPlayer.getName() == "Computer Player 1") )||
                ((this.numberOfRoundsPlayed > 0 && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() == "Computer Player 1" )))){

            try {

                // If human player card is valid.
                if(this.invalidCardByHuman == false) {
                    // Com Player 2 (right side)
                    // Play the smallest card from the category
                    com2Card = this.cpu2.SelectTheHighestCardFromCategory(selectedCard.getCategory());
                    Log.println(Log.ERROR, "TAG", "inside the if condition");
                }

                Log.println(Log.ERROR, "TAG", "outside if condition");

                // Start game round from Com Player 2.
                // Creates new game round object.
                GameRound gameRound = new GameRound( this.cpu1, c1,
                        this.cpu2, com2Card,
                        this.humanPlayer, selectedCard,c1.getCategory() ,this.trumps );

                Log.println(Log.ERROR, "TAG", "after game round object");

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                // Get image source for Card played by Com Player 2 and set to Image Resource.
                com2.setImageResource(com2Card.getImageSource());
                com2.setVisibility(View.INVISIBLE);

                // Set Animations.
                final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);

                GameHandlers gameHandler = new GameHandlers("com2", com2, animationRl, sounds, 1000, 2500);

                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        com2.startAnimation(animationRl);
//                        animationRl.setAnimationListener(new Animation.AnimationListener() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//
//                                sounds.cardClick();
//                                ComputerPlayerCardViews.hideCardFromPlayer2();
//                                // Com Player 2 card make visible.
//                                com2.setVisibility(View.VISIBLE);
//                                com2.setImageAlpha(1000);
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) { }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) { }
//                        });
//                    }
//                }, 2500);


                // remove the played cards from the card decks of computer player 1 & 2.
                // update the number of remaining card for both players.
                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(com2Card);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                // get winner of the round.
                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);

                Log.println( Log.ERROR, "TAG", "inside try block 2" );


                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();

                }
                else {

                    GameHandlers.collectCards(sounds, com1, com2, playerPlaceholder, 6000);

//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            sounds.cardCollect();
//                            com1.setVisibility(View.INVISIBLE);
//                            com2.setVisibility(View.INVISIBLE);
//                            playerPlaceholder.setVisibility(View.INVISIBLE);
//                            game_page.cardTouch(true);
//                        }
//                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 2, at the very end" );


            } catch (Exception e) {
                Log.d("TAG", "on the second catch block");

                popUpDialog(Message.getMessageSelectValidCard(), "Card Selection");
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }


        }
        // If neither the start of the game and the start player is neither Com Player 1 or Com Player 2
        // Or neither the last round winner is Com Player 1 or Com Player 2.
        else{

            try {

                Log.println( Log.ERROR, "TAG", "Try block 3.." );
                Log.println(Log.ERROR, "TAG", "passing trumps in block 1 : " + this.trumps);

                // Create new game round object.
                GameRound gameRound = new GameRound( this.cpu1, c1,
                        this.cpu2, c2,
                        this.humanPlayer, selectedCard,c2.getCategory() ,this.trumps );

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                if(numberOfRoundsPlayed == 0) {
                    // calls method to set the image views of the playing cards for com players 1 & 2.
                    setComputerCardsToImageView(c1, c2, com1, com2);
                }

                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(c2);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                // Get winner of this round.
                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sounds.cardCollect();

                        com1.setVisibility(View.INVISIBLE);
                        com2.setVisibility(View.INVISIBLE);
                        playerPlaceholder.setVisibility(View.INVISIBLE);

                        if(winner.getName() != "Computer Player 1" && winner.getName() != "Computer Player 2") {
                            game_page.cardTouch(true);
                        }

                    }
                }, 3000);

                Log.println( Log.ERROR, "TAG", "inside try block 3" );

                // If this round's winner is a Computer Player.
                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();

                }
                else {

                    GameHandlers.collectCards(sounds, com1, com2, playerPlaceholder, 6000);
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            com1.setVisibility(View.INVISIBLE);
//                            com2.setVisibility(View.INVISIBLE);
//                            playerPlaceholder.setVisibility(View.INVISIBLE);
//                            game_page.cardTouch(true);
//
//                        }
//                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 3, at the very end" );

            }
            catch (Exception e) {
                Log.d("TAG", "on the third catch block");
                popUpDialog(Message.getMessageSelectValidCard(), "Card Selection");

                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }

        }

    }

    // This method sets the images to the image views and makes them visible.
    public void setComputerCardsToImageView(Card cardLeft, Card cardRight, final ImageView leftView, final ImageView rightView){

        // hide the card from the computer player's card deck.
        ComputerPlayerCardViews.hideCardFromPlayer1();
        ComputerPlayerCardViews.hideCardFromPlayer2();

        // set image source to played card set & make them visible.
        leftView.setImageResource(cardLeft.getImageSource());
        rightView.setImageResource(cardRight.getImageSource());

        leftView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.VISIBLE);
    }


    // This method plays the game for CPU players.
    public void moveForwardWithCpuWin(){

        game_page.cardTouch(false);

        //move forward only if the current game is not finished
        if(numberOfRoundsPlayed < 12 && gameFinish == false) {

            final AnimatorSet animatorSet = new AnimatorSet();

            final ImageView com1 = this.activity.findViewById(R.id.com1Card);
            final ImageView com2 = this.activity.findViewById(R.id.com2Card);
            final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

            Log.println(Log.ERROR, "TAG", "Late night testing2 ");

            // Get the last game round to a variable.
            final GameRound pr = this.playedRounds[numberOfRoundsPlayed - 1];

            // If the last game round winner is the CPU Player 1.
            if (this.playedRounds[numberOfRoundsPlayed - 1].getWinner().getName() == cpu1.getName()) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(gameFinish == false) {

                            // set the image views of com player 2 and human player to invisible.
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);

                            Sounds.cardCollect();

                            // Play the card for this round, map it to the image view and make it visible.
                            c1 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
                            com1.setImageResource(c1.getImageSource());
                            com1.setVisibility(View.VISIBLE);

                            // Set animations.
                            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);

                            com1.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    sounds.cardClick();
                                    ComputerPlayerCardViews.hideCardFromPlayer1();
                                    com1.setImageAlpha(1000);
                                }

                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onAnimationEnd(Animation animation) { }

                                @Override
                                public void onAnimationRepeat(Animation animation) { }

                            });
                        }

                        // Next player is Human Player.
                        // Set cardTouch to true.
                        game_page.cardTouch(true);
                    }

                }, 6000);


                // Else if the player is Com Player 2.
            } else {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (gameFinish == false) {

                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            // Play the card for com player 2.
                            c2 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
                            // Play the card for com player 1.
                            c1 = cpu1.selectSmallestCardFromCategory(c2.getCategory());

                            // Set image resources of the cards played and make them invisible. Play sounds.
                            com2.setImageResource(c2.getImageSource());
                            com1.setImageResource(c1.getImageSource());
                            com2.setVisibility(View.INVISIBLE);
                            com1.setVisibility(View.INVISIBLE);
                            Sounds.cardCollect();

                            // Set animations for the played cards.
                            final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                            final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    com2.startAnimation(animationRl);
                                    animationRl.setAnimationListener(new Animation.AnimationListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            sounds.cardClick();
                                            ComputerPlayerCardViews.hideCardFromPlayer2();

                                            com2.setVisibility(View.VISIBLE);
                                            com2.setImageAlpha(1000);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) { }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) { }
                                    });
                                }
                            }, 1500);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    com1.startAnimation(animationLr);
                                    animationLr.setAnimationListener(new Animation.AnimationListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            sounds.cardClick();

                                            com1.setVisibility(View.VISIBLE);
                                            com1.setImageAlpha(1000);
                                            ComputerPlayerCardViews.hideCardFromPlayer1();
                                        }

                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onAnimationEnd(Animation animation) { }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) { }
                                    });

                                    // Let Human player play.
                                    game_page.cardTouch(true);

                                }
                            }, 3000);

                        }
                    }
                }, 6000);

            }
        }
    }

    public void moveForwardWithCpuWin(Player player){

        if(this.numberOfRoundsPlayed < 12) {

            final ImageView com1 = this.activity.findViewById(R.id.com1Card);
            final ImageView com2 = this.activity.findViewById(R.id.com2Card);
            final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

            Log.println(Log.ERROR, "TAG", "Late night testing2 ");

            if (player.getName() == cpu1.getName()) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        playerPlaceholder.setVisibility(View.INVISIBLE);
                        com2.setVisibility(View.INVISIBLE);

                        c1 = cpu1.selectHighestCard();
                        com1.setImageResource(c1.getImageSource());
                        com1.setVisibility(View.VISIBLE);

                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);

                        com1.startAnimation(animation);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationStart(Animation animation) {
                                sounds.cardClick();
                                ComputerPlayerCardViews.hideCardFromPlayer1();
                                com1.setImageAlpha(1000);
                            }

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationEnd(Animation animation) { }

                            @Override
                            public void onAnimationRepeat(Animation animation) { }
                        });

                        game_page.cardTouch(true);

                    }
                }, 2000);

            } else {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playerPlaceholder.setVisibility(View.INVISIBLE);

                        c2 = cpu2.selectHighestCard();
                        c1 = cpu1.selectSmallestCardFromCategory(c2.getCategory());

                        com2.setImageResource(c2.getImageSource());
                        com1.setImageResource(c1.getImageSource());
                        com2.setVisibility(View.INVISIBLE);
                        com1.setVisibility(View.INVISIBLE);

                        final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                        final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                com2.startAnimation(animationRl);
                                animationRl.setAnimationListener(new Animation.AnimationListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        sounds.cardClick();
                                        ComputerPlayerCardViews.hideCardFromPlayer2();

                                        com2.setVisibility(View.VISIBLE);
                                        com2.setImageAlpha(1000);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) { }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) { }
                                });
                            }
                        }, 1500);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                com1.startAnimation(animationLr);
                                animationLr.setAnimationListener(new Animation.AnimationListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        sounds.cardClick();
                                        ComputerPlayerCardViews.hideCardFromPlayer1();

                                        com1.setVisibility(View.VISIBLE);
                                        com1.setImageAlpha(1000);
                                    }

                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onAnimationEnd(Animation animation) { }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) { }
                                });
                                game_page.cardTouch(true);

                            }
                        }, 3000);

                    }
                }, 5000);

            }
        }
    }


    // This method updates the score on the score bar of the game page during the game.
    //Also if a team wins the game, or if the game is draw, an animation will be displayed in the perspective of the human player.
    public void updateScore(Player winningPlayer ){

        if(this.singlePlayer.getName() == winningPlayer.getName()){
            final TextView playerScorePlaceHolder =  this.activity.findViewById(R.id.textViewMyScore);
            int previousScore = Integer.parseInt((String) playerScorePlaceHolder.getText());
            previousScore++;
            this.singlePlayerScore++;
            final String score =  Integer.toString(previousScore);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playerScorePlaceHolder.setText(score);
                }
            }, 4000);



            if (this.singlePlayerScore == 7){

                if(this.singlePlayer.getName() == "Computer Player 1"){
                    ScoreBoard.getInstance().setScores(new GameScore(0,2,0));
                    losingAnimation();
                    getToastMessage(false);
                    openDialog();
                }
                else if(this.singlePlayer.getName() == "Computer Player 2"){
                    ScoreBoard.getInstance().setScores(new GameScore(0,0,2));
                    losingAnimation();
                    getToastMessage(false);
                    openDialog();
                }
                else {
                    ScoreBoard.getInstance().setScores(new GameScore(2,0,0));
                    winningAnimation();
                    getToastMessage(true);
                    openDialog();
                }

            }

        }else {
            final TextView playerScorePlaceHolder =  this.activity.findViewById(R.id.textViewOpponentScore);
            int previousScore = Integer.parseInt((String) playerScorePlaceHolder.getText());
            previousScore++;
            this.teamScore++;
            final String score =  Integer.toString(previousScore);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playerScorePlaceHolder.setText(score);
                }
            }, 4000);

            if (this.teamScore == 7){

                if(this.singlePlayer.getName() == "Computer Player 1"){
                    ScoreBoard.getInstance().setScores(new GameScore(1,0,1));
                    winningAnimation();
                    getToastMessage(true);
                    openDialog();
                }
                else if(this.singlePlayer.getName() == "Computer Player 2"){
                    ScoreBoard.getInstance().setScores(new GameScore(1,1,0));
                    winningAnimation();
                    getToastMessage(true);
                    openDialog();
                }
                else {
                    ScoreBoard.getInstance().setScores(new GameScore(0,1,1));
                    losingAnimation();
                    getToastMessage(false);
                    openDialog();
                }
            }
        }

        if (this.teamScore == 6 && this.singlePlayerScore == 6){
            this.gameFinish = true;
            LottieAnimationView anim = this.activity.findViewById(R.id.draw);
            anim.setVisibility(LottieAnimationView.VISIBLE);
            Toast.makeText(activity.getApplicationContext(), Message.getToastDrawGame(), Toast.LENGTH_LONG).show();
            ScoreBoard.getInstance().setScores(new GameScore(0,0,0));
            openDialog();

        }
    }

    // This method is used to switch on animations and off animations as required.
    public void winnigOrLoosingAnimationOff(){
        LottieAnimationView anim1 = this.activity.findViewById(R.id.confetti1);
        anim1.setVisibility(LottieAnimationView.INVISIBLE);

        LottieAnimationView anim2 = this.activity.findViewById(R.id.confetti2);
        anim2.setVisibility(LottieAnimationView.INVISIBLE);

        LottieAnimationView anim3 = this.activity.findViewById(R.id.sadface);
        anim3.setVisibility(LottieAnimationView.INVISIBLE);

        LottieAnimationView anim = this.activity.findViewById(R.id.draw);
        anim.setVisibility(LottieAnimationView.INVISIBLE);
    }

    public void winningAnimation(){
        this.gameFinish = true;
        Sounds.playWin();
        LottieAnimationView anim1 = this.activity.findViewById(R.id.confetti1);
        anim1.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView anim2 = this.activity.findViewById(R.id.confetti2);
        anim2.setVisibility(LottieAnimationView.VISIBLE);
    }

    public void losingAnimation(){
        this.gameFinish = true;
        LottieAnimationView anim1 = this.activity.findViewById(R.id.sadface);
        anim1.setVisibility(LottieAnimationView.VISIBLE);
        Sounds.playLost();
    }

    public void getToastMessage(boolean result){
        if(result == true){
            Toast.makeText(activity.getApplicationContext(), Message.getToastWinGame(), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(activity.getApplicationContext(), Message.getToastLostGame(), Toast.LENGTH_LONG).show();
        }
    }

    // Pop up dialog box
    public void popUpDialog(String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity,R.style.AlertDialogStyle);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();

    }



    public void createNewGame(Player human, AbComputerPlayer comPlayer1, AbComputerPlayer comPlayer2){

        //first turn off any winning or loosing animations
        //then set the score labels to 0
        winnigOrLoosingAnimationOff();

        final TextView scoreLabel1 =  this.activity.findViewById(R.id.textViewMyScore);
        final TextView scoreLabel2 =  this.activity.findViewById(R.id.textViewOpponentScore);
        scoreLabel1.setText("0");
        scoreLabel2.setText("0");

        final TextView scoreLabel = (TextView) activity.findViewById(R.id.textViewMyTeam);
        final TextView myLabel = (TextView) activity.findViewById(R.id.textViewOpponent);

        Log.println( Log.ERROR, "TAG", "Player turn index : " + playerTurnIndex);

        // Increment the playerTurnIndex and set it to checkIndex.
        int checkIndex = ++playerTurnIndex;

        Log.println( Log.ERROR, "TAG", "Check index index : " + checkIndex);

        // Make all the cards visible.
        ComputerPlayerCardViews.makeAllCardsVisible();

        //create new card-deck and player instances for the new game.
        game_page.startGame();

        Game game = new Game();

        //for player two (CPU) given the chance first
        if(checkIndex % 3 == 1){
            if(SelectingTrumpComPlayer.getChances(cpu2)){

                Log.println(Log.ERROR, "TAG", "Player 2 Before selecting trumps" + this.trumps);

                this.trumps = SelectingTrumpComPlayer.getTrump(cpu2);
                // pass trump to the interface as string.
                passTrumpToTheInterface(this.trumps);

                Log.println(Log.ERROR, "TAG", "Player 2 After selecting trumps: " + this.trumps);

                Toast.makeText(activity.getApplicationContext(), Message.getToastComPlayer2SelectedTrump() + this.trumps,
                        Toast.LENGTH_LONG).show();
                scoreLabel.setText(comPlayer2.getName());
                myLabel.setText("My Team");

                //alter game instance and move forward with cpu2 player as the start player.
                game.alterInstance( cpu2, humanPlayer, cpu1, humanPlayer, cpu1, cpu2, cpu2, this.trumps);

                Log.println(Log.ERROR, "TAG", "in the new instance trumps: " + this.trumps);

                game_page.cardTouch(true);

                moveForwardWithCpuWin(cpu2);
            }
            else{
                // increment the variable checkIndex.
                checkIndex ++;
            }

        }

        //for player one (CPU) given the chance
        if(checkIndex % 3 == 2){

            // if com player 1 selects the trump, get the trump selected.
            if(SelectingTrumpComPlayer.getChances(cpu1)){

                //Log.println(Log.ERROR, "TAG", "Player 1 Before selecting trumps: " + this.trumps);

                this.trumps = SelectingTrumpComPlayer.getTrump(cpu1);
                passTrumpToTheInterface(this.trumps);

                //Log.println(Log.ERROR, "TAG", "Player 1 After selecting trumps: " + this.trumps);

                Toast.makeText(activity.getApplicationContext(), Message.getToastComPlayer1SelectedTrump() + this.trumps,
                        Toast.LENGTH_LONG).show();
                scoreLabel.setText(comPlayer1.getName());
                myLabel.setText("My Team");

                game.alterInstance( cpu1, humanPlayer, cpu2, humanPlayer, cpu1, cpu2, cpu1, this.trumps);

                Log.println(Log.ERROR, "TAG", "in the new instance trumps: " + this.trumps);

                game_page.cardTouch(false);
                moveForwardWithCpuWin(cpu1);

            }
            else {
                checkIndex++;
            }

        }

        // for human player given the chance.
        if(checkIndex % 3 == 0){

            // let player choose if he/she can win trumps.
            chooseWinningPossibilityDialog( human,  comPlayer1,  comPlayer2);
            scoreLabel.setText("My Team");
            myLabel.setText("Opponent");

            game.alterInstance( humanPlayer, cpu1, cpu2, humanPlayer, cpu1, cpu2, humanPlayer, this.trumps);
        }
    }


    // This methods allows the player to check if he/she can win 7 chances from this game.
    public void chooseWinningPossibilityDialog(final Player human, final AbComputerPlayer comPlayer1, final AbComputerPlayer comPlayer2){
        AlertDialog.Builder getChances = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
        getChances.setMessage(Message.getMessageWinSevenChances())
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTrump();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createNewGame( human,  comPlayer1,  comPlayer2);
                    }
                });

        AlertDialog dialog = getChances.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();
    }


    // This methods allows the user to select the trump category.
    private void selectTrump(){
        this.trumps = null;
        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle("Select your Trump.")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passTrumpToTheInterface(which);
                        game_page.cardTouch(true);
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (trumps == null || trumps.isEmpty()) {
                            Toast.makeText(activity.getApplicationContext(), Message.getToastChooseTrump(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "the trump selected: " + trumps);
                            selectTrump();
                        }
                        else{
                            dialog.dismiss();
                        }

                    }
                });

        AlertDialog dialog = chooseTrump.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();
    }

    // This method is used to set the card visibility to the interface.
    public void setCardVisibility(boolean visibility){
        final ImageView com1 = this.activity.findViewById(R.id.com1Card);
        final ImageView com2 = this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

        if(visibility == false){
            com1.setVisibility(View.INVISIBLE);
            com2.setVisibility(View.INVISIBLE);
            playerPlaceholder.setVisibility(View.INVISIBLE);
        }
        else {
            com1.setVisibility(View.VISIBLE);
            com2.setVisibility(View.VISIBLE);
            playerPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    // This dialog box allows user to select the user to select if he/she wants to play the next round of the game.
    public void openDialog(){

        if(ScoreBoard.getInstance().gameFinish()){
            ourInstance = null;
            Intent intent = new Intent(activity, fyp.ui.hath_wasi.Screens.score_page.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
        else {

            Log.println(Log.ERROR, "TAG", "Inside openDialog");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCardVisibility(false);
                    AlertDialog.Builder getChances = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
                    getChances.setMessage(Message.getMessagePlayNextRound())
                            .setTitle("♠ ♥ ♣ ♦")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createNewGame(humanPlayer, cpu1, cpu2);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ourInstance = null;
                                    activity.finish();
                                }


                            });

                    AlertDialog dialog = getChances.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                    dialog.show();
                }
            }, 3000);
            Log.println(Log.ERROR, "TAG", "Exiting openDialog");
        }

    }

    // This method will pass the selected trump to the game interface (using string).
    public void passTrumpToTheInterface(String which){

        final TextView textViewTrump = (TextView) activity.findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which){
            case "spades":
                this.trumps = "spades";
                Log.d( "TAG", "Spades Selected: " + trumps);
                textViewTrump.setText("♠");
                break;
            case "hearts":
                this.trumps = "hearts";
                Log.d( "TAG", "Hearts Selected: " + trumps);
                textViewTrump.setText("♥");
                break;
            case "clubs":
                this.trumps = "clubs";
                Log.d( "TAG", "Clubs Selected: " + trumps);
                textViewTrump.setText("♣");
                break;
            case "diamonds":
                this.trumps = "diamonds";
                Log.d( "TAG", "Diamonds Selected: " + trumps);
                textViewTrump.setText("♦");
                break;
        }
    }

    // This method will pass the selected trump to the game interface (using int).
    public void passTrumpToTheInterface(int which){

        final TextView textViewTrump = (TextView) activity.findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which){
            case 0:
                this.trumps = "spades";
                Log.d( "TAG", "Spades Selected: " + this.trumps);
                textViewTrump.setText("♠");
                break;
            case 1:
                this.trumps = "hearts";
                Log.d( "TAG", "Hearts Selected: " + this.trumps);
                textViewTrump.setText("♥");
                break;
            case 2:
                this.trumps = "clubs";
                Log.d( "TAG", "Clubs Selected: " + this.trumps);
                textViewTrump.setText("♣");
                break;
            case 3:
                this.trumps = "diamonds";
                Log.d( "TAG", "Diamonds Selected: " + this.trumps);
                textViewTrump.setText("♦");
                break;
        }
    }


    public static Game getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Game ourInstance) {
        Game.ourInstance = ourInstance;
    }

    public static Player getSinglePlayer() {
        return singlePlayer;
    }

    public static void setSinglePlayer(Player singlePlayer) {
        Game.singlePlayer = singlePlayer;
    }

    public static Player getTeamPlayer1() {
        return teamPlayer1;
    }

    public static void setTeamPlayer1(Player teamPlayer1) {
        Game.teamPlayer1 = teamPlayer1;
    }

    public static Player getTeamPlayer2() {
        return teamPlayer2;
    }

    public static void setTeamPlayer2(Player teamPlayer2) {
        Game.teamPlayer2 = teamPlayer2;
    }

    public static Player getHumanPlayer() {
        return humanPlayer;
    }

    public static void setHumanPlayer(Player humanPlayer) {
        Game.humanPlayer = humanPlayer;
    }

    public static AbComputerPlayer getCpu1() {
        return cpu1;
    }

    public static void setCpu1(AbComputerPlayer cpu1) {
        Game.cpu1 = cpu1;
    }

    public static AbComputerPlayer getCpu2() {
        return cpu2;
    }

    public static void setCpu2(AbComputerPlayer cpu2) {
        Game.cpu2 = cpu2;
    }

    public static int getSinglePlayerScore() {
        return singlePlayerScore;
    }

    public static void setSinglePlayerScore(int singlePlayerScore) {
        Game.singlePlayerScore = singlePlayerScore;
    }

    public static int getTeamScore() {
        return teamScore;
    }

    public static void setTeamScore(int teamScore) {
        Game.teamScore = teamScore;
    }

    public static GameRound[] getPlayedRounds() {
        return playedRounds;
    }

    public static void setPlayedRounds(GameRound[] playedRounds) {
        Game.playedRounds = playedRounds;
    }

    public static int getNumberOfRoundsPlayed() {
        return numberOfRoundsPlayed;
    }

    public static void setNumberOfRoundsPlayed(int numberOfRoundsPlayed) {
        Game.numberOfRoundsPlayed = numberOfRoundsPlayed;
    }

    public static Player getStartPlayer() {
        return startPlayer;
    }

    public static void setStartPlayer(Player startPlayer) {
        Game.startPlayer = startPlayer;
    }

    public static boolean isInvalidCardByHuman() {
        return invalidCardByHuman;
    }

    public static void setInvalidCardByHuman(boolean invalidCardByHuman) {
        Game.invalidCardByHuman = invalidCardByHuman;
    }

    public static Card getCom2Card() {
        return com2Card;
    }

    public static void setCom2Card(Card com2Card) {
        Game.com2Card = com2Card;
    }

    public static Player[] getPlayers() {
        return players;
    }

    public static void setPlayers(Player[] players) {
        Game.players = players;
    }

    public static int getPlayerTurnIndex() {
        return playerTurnIndex;
    }

    public static void setPlayerTurnIndex(int playerTurnIndex) {
        Game.playerTurnIndex = playerTurnIndex;
    }

    public static boolean isGameFinish() {
        return gameFinish;
    }

    public static void setGameFinish(boolean gameFinish) {
        Game.gameFinish = gameFinish;
    }

    public static Sounds getSounds() {
        return sounds;
    }

    public static void setSounds(Sounds sounds) {
        Game.sounds = sounds;
    }

    public static String getTrumps() {
        return trumps;
    }

    public static void setTrumps(String trumps) {
        Game.trumps = trumps;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        Game.activity = activity;
    }

    public Card getC1() {
        return c1;
    }

    public void setC1(Card c1) {
        this.c1 = c1;
    }

    public Card getC2() {
        return c2;
    }

    public void setC2(Card c2) {
        this.c2 = c2;
    }
}
