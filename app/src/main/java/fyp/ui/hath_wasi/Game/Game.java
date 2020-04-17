package fyp.ui.hath_wasi.Game;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Game.GameScores.GameScore;
import fyp.ui.hath_wasi.Game.GameScores.ScoreBoard;
import fyp.ui.hath_wasi.Players.AbComputerPlayer;
import fyp.ui.hath_wasi.Players.Player;
import fyp.ui.hath_wasi.R;
import fyp.ui.hath_wasi.Screens.game_page;
import com.airbnb.lottie.LottieAnimationView;

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

    private static String trumps;
    public Activity activity;


    // Game constructors.
    private Game(Activity _activity, Player singlePlayer, Player teamPlayer1, Player teamPlayer2, Player humanPlayer,
    AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps){
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
        this.activity = _activity;
        this.invalidCardByHuman = false;
    }

    private Game(){}

    // this getter method for our instance checks if there is already a game loaded (playing a game)
    // returns a new game if the instance is null
    public static Game getInstance(Activity _activity, Player singlePlayer, Player teamPlayer1, Player teamPlayer2, Player humanPlayer, AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps){

        if(ourInstance == null){
            ourInstance = new Game(_activity, singlePlayer, teamPlayer1, teamPlayer2, humanPlayer, cpu1, cpu2, startPlayer, trumps);
        }

        return ourInstance;
    }


    public static Game getInstance(){
        return ourInstance;
    }

    Card c1, c2;


    // this method alters the game instance for the game to be played if the computer players select the trump.
    public void alterInstance(AbComputerPlayer singlePlayer, Player teamPlayer1, AbComputerPlayer teamPlayer2, Player humanPlayer, AbComputerPlayer cpu1,
        AbComputerPlayer cpu2, AbComputerPlayer startPlayer, String trump){

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
        this.trumps = trump;
        this.humanPlayer = humanPlayer;
        this.invalidCardByHuman = false;

    }

    public void playNextMove(Card selectedCard){

        // declare three variables to hold the imageViews of the playing cards
        // of the three players.
        final ImageView com1 = this.activity.findViewById(R.id.com1Card);
        final ImageView com2 = this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);


        Log.println( Log.ERROR, "TAG", "startPlayer.getName() :" + startPlayer.getName()  );
        Log.println( Log.ERROR, "TAG", "this.numberOfRoundsPlayed :" + this.numberOfRoundsPlayed);


        // If it is the first round of the game and the start player is not an abstract com player
        // Or the last round winner is not Com Player 1 and last round player is not Com Player 2.
        if( (this.numberOfRoundsPlayed == 0 && startPlayer.getName() != "Computer Player 1" && startPlayer.getName() != "Computer Player 2") ||
                ((this.numberOfRoundsPlayed > 0 && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() != "Computer Player 1" &&
                this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() != "Computer Player 2") ) ) {


            try{

                Log.d("TAG", "Entered previous winner Human");
                Log.println(Log.ERROR, "TAG", "Try block 1...");

                GameRound gameRound = new GameRound(this.cpu1, this.cpu1.selectSmallestCardFromCategory(selectedCard.getCategory()),
                        this.cpu2, this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory()),
                        this.humanPlayer, selectedCard, selectedCard.getCategory(), trumps);

                // increment the number of rounds played.
                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                invalidCardByHuman =false;

                // Store the round winner in a variable.
                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Log.println(Log.ERROR, "TAG", "first if condition" + winner.getName());
                Log.println(Log.ERROR, "TAG", "print after");

                // Set the image resource of the selected cards to the cards that are being played and make them invisible
                com1.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer1Card().getImageSource());
                com2.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer2Card().getImageSource());
                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);

                // remove the Computer player 1 and Computer Player 2 selected cards from the card decks of the computer player 1 and Computer Player 2.
                // And update the number of cards remaining for Com Player 1 and Com Player 2.
                this.cpu1.getCardDeck().remove(this.cpu1.selectSmallestCardFromCategory(selectedCard.getCategory()));
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining() - 1);

                this.cpu2.getCardDeck().remove(this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory()));
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining() - 1);

                //cpu1.displayDetails();
                //cpu2.displayDetails();

                // Set animations.
                final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);

                // Allows to delay the animations associated with the particular card.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        com2.startAnimation(animationRl);
                        animationRl.setAnimationListener(new Animation.AnimationListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationStart(Animation animation) {
                                // Make ComPlayer2 card visible.
                                com2.setVisibility(View.VISIBLE);
                                com2.setImageAlpha(1000);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //com2.setScaleX(com2.getScaleX());

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
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
                                // Make ComPlayer1 Card visibile.
                                com1.setVisibility(View.VISIBLE);
                                com1.setImageAlpha(1000);
                            }

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 3000);


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
                else{

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            com1.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            game_page.cardTouch(true);
                        }
                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 1, at the very end" );

            } catch (Exception e){

                // decrement the added number of played.
                numberOfRoundsPlayed--;

                Log.println( Log.ERROR, "TAG", "on the first catch block" );
                popUpDialog("Invalid Card type!", "Card Selection");
                // human player played an invalid card, so allow to play again.
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);

            }
        }

        // Else if the first round of the game and the start player is ComPlayer 1
        // Or the last round's winner is ComPlayer 1.
        else if(( (this.numberOfRoundsPlayed == 0 && (startPlayer.getName() == "Computer Player 1") ) ||
                ((this.numberOfRoundsPlayed > 0 && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() == "Computer Player 1")))){

            try{
                Log.println(Log.ERROR, "TAG", "inside the second condition");

                // If human player card is valid.
                if(this.invalidCardByHuman == false){
                    // Com Player 2 (right side)
                    // Play the smallest card from the category
                    com2Card = this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory());
                    Log.println(Log.ERROR, "TAG", "inside the if condition of the second condition");

                }

                Log.println(Log.ERROR, "TAG", "outside if condition inside second condition");


                // Start game round from Com Player 2.
                // Creates new game round object.
                GameRound gameRound = new GameRound(this.cpu1, c1,
                        this.cpu2, com2Card,
                        this.humanPlayer, selectedCard, c1.getCategory(), trumps);

                Log.println(Log.ERROR, "TAG", "after game round object");

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                // Get image source for Card played by Com Player 2 and set to Image Resource.
                com2.setImageResource(com2Card.getImageSource());
                com2.setVisibility(View.INVISIBLE);

                // Set Animations.
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
                                // Com Player 2 card make visibile.
                                com2.setVisibility(View.VISIBLE);
                                com2.setImageAlpha(1000);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //com2.setScaleX(com2.getScaleX());

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 2500);


                // remove the played cards from the card decks of computer player 1 & 2.
                // update the number of remaining card for both players.
                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(com2Card);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                // get winner of the round.
                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Log.println(Log.ERROR, "TAG", "second if condition" + winner.getName());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);

                Log.println( Log.ERROR, "TAG", "inside try block 2" );

                // If this round's winner is a CPU player.
                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){
                    moveForwardWithCpuWin();
                }
                else{
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final MediaPlayer player = MediaPlayer.create(activity, R.raw.card_collectt);
                            player.start();

                            com1.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            game_page.cardTouch(true);
                        }
                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 2, at the very end" );

            } catch (Exception e){
                Log.d("TAG", "on the second catch block");

                popUpDialog("Invalid Card type!", "Card Selection");
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }


        }
        // If neither the start of the game and the start player is neither Com Player 1 or Com Player 2
        // Or neither the last round winner is Com Player 1 or Com Player 2.
        else{
            try{
                Log.println(Log.ERROR, "TAG", "Try block 3..");

                // Create new game round object.
                GameRound gameRound = new GameRound(this.cpu1, c1,
                        this.cpu2, c2,
                        this.humanPlayer, selectedCard, c2.getCategory(), trumps);

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                // calls method to set the image views of the playing cards for com players 1 & 2.
                setComputerCardsToImageView(c1, c2, com1, com2);

                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(c2);
                this.cpu2.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                // Get winner of this round.
                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Log.println(Log.ERROR, "TAG", "third if condition" + winner.getName());

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

                        final MediaPlayer player = MediaPlayer.create(activity, R.raw.card_collectt);
                        player.start();

                        com1.setVisibility(View.INVISIBLE);
                        com2.setVisibility(View.INVISIBLE);
                        playerPlaceholder.setVisibility(View.INVISIBLE);
                        game_page.cardTouch(true);
                    }
                }, 6000);

                Log.println( Log.ERROR, "TAG", "inside try block 3" );

                // If this round's winner is a Computer Player.
                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();
                }

                else{
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final MediaPlayer player = MediaPlayer.create(activity, R.raw.card_collectt);
                            player.start();

                            com1.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            game_page.cardTouch(true);
                        }
                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 3, at the very end" );
            } catch (Exception e){
                Log.d("TAG", "on the third catch block");
                popUpDialog("Invalid Card type!", "Card Selection");

                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }
        }
    }

    // This method sets the images to the image views and makes them visible.
    public void setComputerCardsToImageView(Card cardLeft, Card cardRight, final ImageView leftView, final ImageView rightView){

        leftView.setImageResource(cardLeft.getImageSource());
        rightView.setImageResource(cardRight.getImageSource());
        leftView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.VISIBLE);
    }


    // This method plays the game for CPU players.
    public void moveForwardWithCpuWin() {
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
                    // set the image views of com player 2 and human player to invisible.
                    playerPlaceholder.setVisibility(View.INVISIBLE);
                    com2.setVisibility(View.INVISIBLE);
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
                            com1.setImageAlpha(1000);
                        }

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }, 6000);

            // Next player is Human Player.
            // Set cardTouch to true.
            game_page.cardTouch(true);

            // Else if the player is Com Player 2.
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    playerPlaceholder.setVisibility(View.INVISIBLE);

                    // Play the card for com player 2.
                    c2 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
                    // Play the card for com player 1.
                    c1 = cpu1.selectSmallestCardFromCategory(c2.getCategory());

                    // Set image resources of the cards played and make them invisible.
                    com2.setImageResource(c2.getImageSource());
                    com1.setImageResource(c1.getImageSource());
                    com2.setVisibility(View.INVISIBLE);
                    com1.setVisibility(View.INVISIBLE);

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
                                    com2.setVisibility(View.VISIBLE);
                                    com2.setImageAlpha(1000);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    //com2.setScaleX(com2.getScaleX());

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
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
                                    com1.setVisibility(View.VISIBLE);
                                    com1.setImageAlpha(1000);
                                }

                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }, 3000);

                    // Let Human player play.
                    game_page.cardTouch(true);
                }
            }, 5000);
        }
    }


    public void moveForwardWithCpuWin(Player player) {

        final AnimatorSet animatorSet = new AnimatorSet();

        final ImageView com1 = this.activity.findViewById(R.id.com1Card);
        final ImageView com2 = this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);


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
                            com1.setImageAlpha(1000);
                        }

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }, 6000);

            game_page.cardTouch(true);

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
                                    com2.setVisibility(View.VISIBLE);
                                    com2.setImageAlpha(1000);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
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
                                    com1.setVisibility(View.VISIBLE);
                                    com1.setImageAlpha(1000);
                                }

                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }, 3000);

                    game_page.cardTouch(true);

                }
            }, 5000);
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
        dialog.show();

    }


    // This method updates the score on the score bar of the game page during the game.
    //Also if a team wins the game, or if the game is draw, an animation will be displayed in the perspective of the human player.
    public void updateScore(Player winningPlayer ){


        if(this.singlePlayer.getName() == winningPlayer.getName()){
            final TextView playerScorePlaceHolder =  this.activity.findViewById(R.id.textViewMyScore);
            int previousScore = Integer.parseInt((String) playerScorePlaceHolder.getText());
            previousScore++;
            this.singlePlayerScore++;
            String score =  Integer.toString(previousScore);
            playerScorePlaceHolder.setText(score);

            if (this.singlePlayerScore == 7){

                if(this.singlePlayer.getName() == "Computer Player 1"){
                    ScoreBoard.getInstance().setScores(new GameScore(0,2,0));
                    losingAnimation();
                    getToastMessage(false);
                }
                else if(this.singlePlayer.getName() == "Computer Player 2"){
                    ScoreBoard.getInstance().setScores(new GameScore(0,0,2));
                    losingAnimation();
                    getToastMessage(false);
                }
                else {
                    ScoreBoard.getInstance().setScores(new GameScore(2,0,0));
                    winningAnimation();
                    getToastMessage(true);
                }

            }

        }else {
            final TextView playerScorePlaceHolder =  this.activity.findViewById(R.id.textViewOpponentScore);
            int previousScore = Integer.parseInt((String) playerScorePlaceHolder.getText());
            previousScore++;
            this.teamScore++;
            String score =  Integer.toString(previousScore);
            playerScorePlaceHolder.setText(score);

            if (this.teamScore == 7){

                if(this.singlePlayer.getName() == "Computer Player 1"){
                    ScoreBoard.getInstance().setScores(new GameScore(1,0,1));
                    winningAnimation();
                    getToastMessage(true);
                }
                else if(this.singlePlayer.getName() == "Computer Player 2"){
                    ScoreBoard.getInstance().setScores(new GameScore(1,1,2));
                    winningAnimation();
                    getToastMessage(true);
                }
                else {
                    ScoreBoard.getInstance().setScores(new GameScore(0,1,1));
                    losingAnimation();
                    getToastMessage(false);
                }
            }
        }

        if (this.teamScore == 6 && this.singlePlayerScore == 6){
            LottieAnimationView anim = this.activity.findViewById(R.id.draw);
            Toast.makeText(activity.getApplicationContext(), "Game: Draw", Toast.LENGTH_LONG).show();
            anim.setVisibility(LottieAnimationView.VISIBLE);
            ScoreBoard.getInstance().setScores(new GameScore(0,0,0));
        }
    }

    public void winningAnimation(){
        LottieAnimationView anim1 = this.activity.findViewById(R.id.confetti1);
        anim1.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView anim2 = this.activity.findViewById(R.id.confetti2);
        anim2.setVisibility(LottieAnimationView.VISIBLE);

    }

    public void getToastMessage(boolean result){
        if(result == true){
            Toast.makeText(activity.getApplicationContext(), "Your team won the Game!", Toast.LENGTH_LONG).show();
        }

        else{
            Toast.makeText(activity.getApplicationContext(), "Your team lost the Game!", Toast.LENGTH_LONG).show();
        }
    }

    public void losingAnimation(){
        LottieAnimationView anim1 = this.activity.findViewById(R.id.sadface);
        anim1.setVisibility(LottieAnimationView.VISIBLE);
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

    public static boolean getInvalidCardByHuman() {
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

    public static String getTrumps() {
        return trumps;
    }

    public static void setTrumps(String trumps) {
        Game.trumps = trumps;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
