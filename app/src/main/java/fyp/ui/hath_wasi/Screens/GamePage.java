package fyp.ui.hath_wasi.Screens;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.ComputerPlayerCardViews;
import fyp.ui.hath_wasi.Cards.DeckOfCards;
import fyp.ui.hath_wasi.Game.Game;
import fyp.ui.hath_wasi.Game.GameSounds.Sounds;
import fyp.ui.hath_wasi.Game.SelectingTrumpComPlayer;
import fyp.ui.hath_wasi.Messages.Message;
import fyp.ui.hath_wasi.Players.AbComputerPlayer;
import fyp.ui.hath_wasi.Players.ComputerPlayerBeginner;
import fyp.ui.hath_wasi.Players.ComputerPlayerExpert;
import fyp.ui.hath_wasi.Players.ComputerPlayerIntermediate;
import fyp.ui.hath_wasi.Players.Player;
import fyp.ui.hath_wasi.R;

public class GamePage extends AppCompatActivity {

    // Variable declaration.
    static HashMap<Integer, Card> imageToCardMap;
    static AbComputerPlayer comPlayer1;
    static AbComputerPlayer comPlayer2;
    static Player human;
    private static ImageView[] cardArray = new ImageView[12];
    private static ComputerPlayerCardViews comPlayerCardViews;
    private static int roundNumber = 0;
    private static Sounds sounds;
    Switch beginnerSwitch = ChooseLevel.getBeginnerLevel();
    Switch intermediateSwitch = ChooseLevel.getIntermediateLevel();
    Switch expertSwitch = ChooseLevel.getExpertLevel();
    String trump;
    private boolean playerAsking = false;

    // This method moves the position of player's cards at the start of the game.
    private static void moveUpPlayerCards() {

        for (int i = 0; i < 12; i++) {
            cardArray[i].setY(cardArray[i].getY() - 100f);
        }
    }

    // This method initializes the Game
    public static void startGame(Switch beginnerSwitch, Switch intermediateSwitch, Switch expertSwitch) {

        // Create an instance of card and an instance of Player(for human player).
        DeckOfCards card = new DeckOfCards();
        human = new Player("Human Player", card);

        // Create instances of Computer Players.
        createComputerPlayer(card, beginnerSwitch, intermediateSwitch, expertSwitch);

        //comPlayer1.displayDetails();
        //comPlayer2.displayDetails();

        moveUpPlayerCards();
        ComputerPlayerCardViews.openAnimation();

        // uses Animations to sequentially send the cards.
        translateCardsSequentially();

        // Set round number to zero.
        roundNumber = 0;

        // for all the 12 cards of the human player, set the image resource (taken from the drawables folder)
        // using the getCardImagePathFromIndex method of Player class and map it to the imageView of the GamePage.
        imageToCardMap = imageViewToCardMap(human, cardArray);


        // Get Game Instance and set cards.
        Game game = Game.getInstance();
        Game.setCpu1(comPlayer1);
        Game.setCpu2(comPlayer2);
        Game.setHumanPlayer(human);
    }

    private static void translateCardsSequentially(){

        // uses Animations to sequentially send the cards to its original position.
        AnimatorSet s = new AnimatorSet();
        ArrayList<Animator> animations = new ArrayList<Animator>();

        for (int i = 0; i < 12; i++) {

            cardArray[i].setImageResource(human.getCardImagePathFromIndex(i));
            cardArray[i].setVisibility(View.VISIBLE);
            final int j = i;

            ObjectAnimator animator = ObjectAnimator.ofFloat(cardArray[j], "translationY", 100f);
            animations.add(animator);
        }

        s.setDuration(200);
        s.playSequentially(animations);
        s.start();
    }

    private static HashMap<Integer, Card> imageViewToCardMap(Player player, ImageView[] views) {

        // This method maps the image views of the player to the playing cards of that particular player.
        // A HashMap is used for this purpose. The HashMap contains the ID of the imageView and the player's card using the index
        // in the sorted array of cards.

        int numOfCards = player.getNoOfCards();
        HashMap<Integer, Card> imageToCardMap = new HashMap<>();

        for (int i = 0; i < numOfCards; i++) {
            imageToCardMap.put(views[i].getId(), player.getCardFromIndex(i));
        }

        return imageToCardMap;
    }

    //This method sets the cards of the user clickable or un-clickable.
    public static void cardTouch(boolean onOrOff) {

        for (int i = 0; i < 12; i++) {
            cardArray[i].setClickable(onOrOff);
        }
    }

    // Create two instances of players according to the player type(for Computer Players).
    public static void createComputerPlayer(DeckOfCards card, Switch beginnerSwitch, Switch intermediateSwitch, Switch expertSwitch) {
        if (beginnerSwitch.isChecked()) {
            comPlayer1 = new ComputerPlayerBeginner("Computer Player 1", card);
            comPlayer2 = new ComputerPlayerBeginner("Computer Player 2", card);

        } else if (expertSwitch.isChecked()) {
            comPlayer1 = new ComputerPlayerExpert("Computer Player 1", card);
            comPlayer2 = new ComputerPlayerExpert("Computer Player 2", card);

        } else if (intermediateSwitch.isChecked()) {
            comPlayer1 = new ComputerPlayerIntermediate("Computer Player 1", card);
            comPlayer2 = new ComputerPlayerIntermediate("Computer Player 2", card);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides the default title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game_page);

        // Add the player card Image View to an array (to initialize).
        cardArray[0] = findViewById(R.id.playerCard1);
        cardArray[1] = findViewById(R.id.playerCard2);
        cardArray[2] = findViewById(R.id.playerCard3);
        cardArray[3] = findViewById(R.id.playerCard4);
        cardArray[4] = findViewById(R.id.playerCard5);
        cardArray[5] = findViewById(R.id.playerCard6);
        cardArray[6] = findViewById(R.id.playerCard7);
        cardArray[7] = findViewById(R.id.playerCard8);
        cardArray[8] = findViewById(R.id.playerCard9);
        cardArray[9] = findViewById(R.id.playerCard10);
        cardArray[10] = findViewById(R.id.playerCard11);
        cardArray[11] = findViewById(R.id.playerCard12);

        sounds = new Sounds(this);
        cardTouch(false);
        comPlayerCardViews = new ComputerPlayerCardViews(this);

        // Start the Game.
        startGame(beginnerSwitch, intermediateSwitch, expertSwitch);

        //Open dialog box to select the trump.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openDialog();
            }
        }, 3000);

        //create the game with the starting player set as human.
        Game game = Game.getInstance(this, human, comPlayer1, comPlayer2, human, comPlayer1, comPlayer2, human, trump);

    }

    // This method handles the user selected card. It removes the user selected card from the user's card view
    // and doesn't allow the cards to be pressed when one card has been already placed.
    public void cardSelected(View v) {

        cardTouch(false);

        final Card selectedCard = imageToCardMap.get(v.getId());

        final Game game = Game.getInstance();

        game.playNextMove(selectedCard);

        // If player selects a valid card.
        if (Game.isInvalidCardByHuman() == false) {

            final ImageView image = findViewById(R.id.playCard);
            image.setImageResource(selectedCard.getImageSource());
            image.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.sample_anim);

            image.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onAnimationStart(Animation animation) {
                    image.setImageAlpha(1000);
                    Sounds.cardClick();
                }

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onAnimationEnd(Animation animation) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageAlpha(0);
                        }
                    }, 5700);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });

            human.getCardDeck().remove(selectedCard);
            human.setNumberOfCardsRemaining(human.getNumberOfCardsRemaining() - 1);

            //remove the card from the user card deck.
            v.setVisibility(View.INVISIBLE);

        }

    }

    // On Device back button pressed.
    @Override
    public void onBackPressed() {
        Game.setOurInstance(null);
        finish();
    }

    // This method creates and allows the players to choose allow or decline selecting the trump.
    private void openDialog() {

        Game game = Game.getInstance();

        AlertDialog.Builder getChances = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        getChances.setMessage(Message.getMessageWinSevenChances())
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // set player asking to true (human player chose to select the trump)
                        playerAsking = true;
                        selectTrump();
                    }
                })

                // If human player selects no, then the bidding chance is passed to the computer players.
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        final TextView scoreLabel1 = findViewById(R.id.textViewMyTeam);
                        final TextView scoreLabel2 = findViewById(R.id.textViewOpponent);

                        // if human player passed the trump selection to a computer player, let the com player 2 select the trump.
                        Game game = Game.getInstance();
                        if (playerAsking == false) {

                            if (SelectingTrumpComPlayer.getChances(comPlayer2)) {

                                trump = SelectingTrumpComPlayer.getTrump(comPlayer2);
                                Game.setTrumps(trump);
                                passTrumpToTheInterface(trump);
                                playerAsking = true;

                                Toast.makeText(getApplicationContext(), Message.getToastComPlayer2SelectedTrump() + trump, Toast.LENGTH_LONG).show();

                                scoreLabel1.setText(comPlayer2.getName());
                                cardTouch(false);
                                scoreLabel2.setText("My Team");

                                // If com player 2 selects the trump, alter the game instance and let com player 2 start the game.
                                game.alterInstance(comPlayer2, human, comPlayer1, human, comPlayer1, comPlayer2, comPlayer2, trump);
                                game.moveForwardWithCpuWin(comPlayer2);
                            }

                            // else check if com player 1 an select the trump, if yes let com player 1 start the game.
                            else if (SelectingTrumpComPlayer.getChances(comPlayer1)) {
                                trump = SelectingTrumpComPlayer.getTrump(comPlayer1);
                                Game.setTrumps(trump);
                                passTrumpToTheInterface(trump);
                                playerAsking = true;

                                Toast.makeText(getApplicationContext(), Message.getToastComPlayer1SelectedTrump() + trump, Toast.LENGTH_LONG).show();

                                scoreLabel1.setText(comPlayer1.getName());
                                cardTouch(false);
                                scoreLabel2.setText("My Team");

                                game.alterInstance(comPlayer1, human, comPlayer2, human, comPlayer1, comPlayer2, comPlayer1, trump);
                                game.moveForwardWithCpuWin(comPlayer1);
                            }

                            // else ask the human player again.
                            else {
                                Toast.makeText(getApplicationContext(), Message.getToastReshufflingCards(), Toast.LENGTH_LONG).show();
                                openDialog();

                                DeckOfCards card = new DeckOfCards();
                                human = new Player("Human Player", card);

                                createComputerPlayer(card, beginnerSwitch, intermediateSwitch, expertSwitch);

                                // create animation for player cards.
                                translateCardsSequentially();

                                imageToCardMap = imageViewToCardMap(human, cardArray);

                                //create the game with the starting player set as human.
                                game.alterInstance(human, comPlayer1, comPlayer2, human, comPlayer1, comPlayer2, human, trump);
                            }
                        }
                    }
                });

        AlertDialog dialog = getChances.create();
        // Sets whether this dialog is canceled when touched outside the window's bounds.
        dialog.setCanceledOnTouchOutside(false);
        // Sets whether this dialog is cancelable with the back button on the hardware.
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();
    }

    // Asks the user to select the trump.
    private void selectTrump() {

        // This method allows the user to select the trump when they choose to select the trump.
        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(GamePage.this, R.style.AlertDialogStyle);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle(Message.getMessageSelectTrump())
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passTrumpToTheInterface(which);

                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (trump == null || trump.isEmpty()) {
                            Toast.makeText(getApplicationContext(), Message.getToastChooseTrump(),
                                    Toast.LENGTH_SHORT).show();
                            selectTrump();

                        } else {
                            dialog.dismiss();
                            cardTouch(true);
                        }
                    }
                });

        AlertDialog dialog = chooseTrump.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();

    }

    //Passes trump to interface using int because of on checked item selected by human player.
    private void passTrumpToTheInterface(int which) {

        Game game = Game.getInstance();

        final TextView textViewTrump = findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);

        switch (which) {
            case 0:
                trump = "spades".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♠");
                break;
            case 1:
                trump = "hearts".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♥");
                break;
            case 2:
                trump = "clubs".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♣");
                break;
            case 3:
                trump = "diamonds".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♦");
                break;
        }
    }

    // Passes trump to interface using String ( when computer players select the trump).
    private void passTrumpToTheInterface(String which) {

        Game game = Game.getInstance();

        final TextView textViewTrump = findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);

        switch (which) {
            case "spades":
                trump = "spades".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♠");
                break;
            case "hearts":
                trump = "hearts".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♥");
                break;
            case "clubs":
                trump = "clubs".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♣");
                break;
            case "diamonds":
                trump = "diamonds".toLowerCase();
                Game.setTrumps(trump);
                textViewTrump.setText("♦");
                break;
        }
    }
}
