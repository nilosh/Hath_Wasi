package fyp.ui.hath_wasi.Game;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import fyp.ui.hath_wasi.Cards.Card;
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

    private static String trumps;
    public Activity activity;


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

    public void playNextMove(Card selectedCard){

        final ImageView com1 = this.activity.findViewById(R.id.com1Card);
        final ImageView com2 = this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);


        if( (this.numberOfRoundsPlayed == 0 && !(startPlayer instanceof AbComputerPlayer )) ||((this.playedRounds[numberOfRoundsPlayed - 1].getWinner().getName() != "Computer Player 1" &&
                this.playedRounds[numberOfRoundsPlayed - 1].getWinner().getName() != "Computer Player 2")) ) {


            try{

                Log.d("TAG", "Entered previous winner Human");
                Log.println(Log.ERROR, "TAG", "Try block 1...");

                GameRound gameRound = new GameRound(this.cpu1, this.cpu1.selectSmallestCardFromCategory(selectedCard.getCategory()),
                        this.cpu2, this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory()),
                        this.humanPlayer, selectedCard, selectedCard.getCategory(), trumps);

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                invalidCardByHuman =false;

                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Log.println(Log.ERROR, "TAG", "print after");

                com1.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer1Card().getImageSource());
                com2.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer1Card().getImageSource());
                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);


                this.cpu1.getCardDeck().remove(this.cpu1.selectSmallestCardFromCategory(selectedCard.getCategory()));
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining() - 1);

                this.cpu2.getCardDeck().remove(this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory()));
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining() - 1);

                cpu1.displayDetails();
                cpu2.displayDetails();

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


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);


                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();
                }

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

                numberOfRoundsPlayed--;

                Log.println( Log.ERROR, "TAG", "on the first catch block" );
                //popUpDialog("Invalid Card type!", "Card Selection");
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);

            }
        }


        else if(( (this.numberOfRoundsPlayed == 0 && (startPlayer.getName() == "Computer Player 1") ) || ((this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() == "Computer Player 1")))){

            try{
                Log.println(Log.ERROR, "TAG", "inside the second condition");

                if(this.invalidCardByHuman == false){
                    com2Card = this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory());
                    Log.println(Log.ERROR, "TAG", "inside the if condition of the second condition");

                }

                Log.println(Log.ERROR, "TAG", "outside if condition inside second condition");


                GameRound gameRound = new GameRound(this.cpu1, c1,
                        this.cpu2, com2Card,
                        this.humanPlayer, selectedCard, c1.getCategory(), trumps);

                Log.println(Log.ERROR, "TAG", "after game round object");

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                com2.setImageResource(com2Card.getImageSource());
                com2.setVisibility(View.INVISIBLE);

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
                }, 2500);


                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(com2Card);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

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

                Log.println( Log.ERROR, "TAG", "inside the try 2, at the very end" );

            } catch (Exception e){
                Log.d("TAG", "on the second catch block");

                //popUpDialog("Invalid Card type!", "Card Selection");
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }


        }

        else{
            try{
                Log.println(Log.ERROR, "TAG", "Try block 3..");

                GameRound gameRound = new GameRound(this.cpu1, c1,
                        this.cpu2, c2,
                        this.humanPlayer, selectedCard, c2.getCategory(), trumps);

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                setComputerCardsToImageView(c1, c2, com1, com2);

                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(c2);
                this.cpu2.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //updateScore(winner);
                    }
                }, 2500);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        com1.setVisibility(View.INVISIBLE);
                        com2.setVisibility(View.INVISIBLE);
                        playerPlaceholder.setVisibility(View.INVISIBLE);
                        game_page.cardTouch(true);
                    }
                }, 6000);

                Log.println( Log.ERROR, "TAG", "inside try block 3" );

                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();
                }

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

                Log.println( Log.ERROR, "TAG", "inside the try 3, at the very end" );
            } catch (Exception e){
                Log.d("TAG", "on the third catch block");
                //popUpDialog("Invalid Card type!", "Card Selection");

                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }
        }
    }

    public void setComputerCardsToImageView(Card cardLeft, Card cardRight, final ImageView leftView, final ImageView rightView){

        leftView.setImageResource(cardLeft.getImageSource());
        rightView.setImageResource(cardRight.getImageSource());
        leftView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.VISIBLE);
    }


    public void moveForwardWithCpuWin() {
        final AnimatorSet animatorSet = new AnimatorSet();

        final ImageView com1 = this.activity.findViewById(R.id.com1Card);
        final ImageView com2 = this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

        Log.println(Log.ERROR, "TAG", "Late night testing2 ");

        final GameRound pr = this.playedRounds[numberOfRoundsPlayed - 1];

        if (this.playedRounds[numberOfRoundsPlayed - 1].getWinner().getName() == cpu1.getName()) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playerPlaceholder.setVisibility(View.INVISIBLE);
                    com2.setVisibility(View.INVISIBLE);

                    c1 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
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

                    c2 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
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
