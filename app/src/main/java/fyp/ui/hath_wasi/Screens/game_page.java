package fyp.ui.hath_wasi.Screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;
import fyp.ui.hath_wasi.Game.Game;
import fyp.ui.hath_wasi.Game.SelectingTrumpComPlayer;
import fyp.ui.hath_wasi.Players.AbComputerPlayer;
import fyp.ui.hath_wasi.Players.ComputerPlayerAggressive;
import fyp.ui.hath_wasi.Players.Player;
import fyp.ui.hath_wasi.R;

public class game_page extends AppCompatActivity {

    // Variable declaration.
    HashMap<Integer, Card> imageToCardMap;
    private static ImageView[] cardArray = new ImageView[12];

    String trump = null;

    AbComputerPlayer comPlayer1;
    AbComputerPlayer comPlayer2;
    Player human;
    boolean playerAsking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides the default title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game_page);

        //Open dialog box to select the trump.
        openDialog();

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


        // Create an instance of card and an instance of Player(for human player).
        DeckOfCards card = new DeckOfCards();
        human = new Player("Human Player", card);

        // Create two instances of players (for Computer Players).
        comPlayer1 = new ComputerPlayerAggressive("Computer Player 1", card);
        comPlayer2 = new ComputerPlayerAggressive("Computer Player 2", card);

        comPlayer1.displayDetails();
        comPlayer2.displayDetails();

        AnimatorSet s = new AnimatorSet();
        ArrayList<Animator> animations = new ArrayList<Animator>();


        // for all the 12 cards of the human player, set the image resource (taken from the drawables folder)
        // using the getCardImagePathFromIndex method of Player class and map it to the imageView of the game_page.
        for (int i = 0; i < 12; i++){

            cardArray[i].setImageResource(human.getCardImagePathFromIndex(i));

            final int j = i;

            ObjectAnimator animator = ObjectAnimator.ofFloat(cardArray[j], "translationY", 100f);
            animations.add(animator);

        }

        s.setDuration(200);
        s.playSequentially(animations);

        s.start();

        //Map the correct card image to the human player's card deck.
        imageToCardMap = imageViewToCardMap(human, cardArray);


        //create the game with the starting player set as human
        Game game =  Game.getInstance(this, human, comPlayer1, comPlayer2, human, comPlayer1, comPlayer2, human, trump);



    }

    private static HashMap<Integer, Card>imageViewToCardMap(Player player, ImageView[] views){

        // This method maps the image views of the player to the playing cards of that particular player.
        // A HashMap is used for this purpose. The HashMap contains the ID of the imageView and the player's card using the index
        // in the sorted array of cards.

        int numOfCards =player.getNoOfCards();
        HashMap<Integer, Card> imageToCardMap = new HashMap<>();

        for(int i = 0; i < numOfCards; i++){
            imageToCardMap.put(views[i].getId(), player.getCardFromIndex(i));
            Log.println(Log.ERROR, "TAG", "Adding Cards - " + views[i].getId() + " Card type and details - " + player.getCardFromIndex(i).getCategory() + " " + player.getCardFromIndex(i).getCardId());

        }

        return imageToCardMap;
    }


    public void cardSelected(View v){

        cardTouch(false);

        // This method removes the user selected card from the user's card view
        // and doesn't allow the cards to be pressed when one card has been already placed.

        Log.println(Log.ERROR, "TAG", "Selected Image View ID: " + v.getId());
        final Card selectedCard = imageToCardMap.get(v.getId());

        // remove the card from the user card deck.
        //v.setVisibility(View.INVISIBLE);

//        Log.println(Log.ERROR, "TAG", "Should come here");
//        Log.println(Log.ERROR, "TAG", "Should come after this " + selectedCard.getCategory() + "ko error eka?");
//        Log.println(Log.ERROR, "TAG", "Should come after this" + selectedCard.getImageSource() + "path????");


        final Game game = Game.getInstance();

        game.setTrumps(trump);
        game.playNextMove(selectedCard);

        // If player selects a valid card.
        if(game.getInvalidCardByHuman() == false){

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
                public void onAnimationRepeat(Animation animation) {

                }
            });

//            final int topPosition = image.getHeight();
//            int leftPosition = image.getLeft();
//            int rightPosition = image.getRight();

            human.getPlayerCards().remove(selectedCard);
            human.setNumberOfCardsRemaining(human.getNumberOfCardsRemaining()-1);

            //remove the card from the user card deck
            v.setVisibility(View.INVISIBLE);

        }

    }



    public static void cardTouch(boolean onOrOff){

        //This method sets the cards of the user un-clickable.
        for(int i = 0; i < 12; i++){
            cardArray[i].setClickable(onOrOff);
        }
    }


//    @Override
//    public void onBackPressed(){
//    super.onBackPressed();
//    finish();
//    }


    public void openDialog(){
        // This method creates and allows the players to choose allow or decline selecting the trump.
        AlertDialog.Builder getChances = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        getChances.setMessage("Can you win 7 chances?")
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerAsking = true;
                        selectTrump();
                    }
                })

                // If human player selects no, then the bidding chance is passed to the computer players.
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(trump == null){
                            if(SelectingTrumpComPlayer.getChances(comPlayer2))
                            {
                                trump = SelectingTrumpComPlayer.getTrump(comPlayer2);
                                Toast.makeText(getApplicationContext(), "Computer Player 2 Selected Trump" +
                                        trump, Toast.LENGTH_LONG).show();
                            }
                            else if(SelectingTrumpComPlayer.getChances(comPlayer1))
                                {
                                    trump = SelectingTrumpComPlayer.getTrump(comPlayer1);
                                    Toast.makeText(getApplicationContext(), "Computer Player 1 Selected the Trump" +
                                            trump, Toast.LENGTH_LONG).show();
                                }
                            else{
                                openDialog();
                            }
                        }
                    }
                });

        AlertDialog dialog = getChances.create();
        // Sets whether this dialog is canceled when touched outside the window's bounds.
        dialog.setCanceledOnTouchOutside(false);
        // Sets whether this dialog is cancelable with the back button on the hardware.
        dialog.setCancelable(false);
        dialog.show();
    }

    public void selectTrump(){
        final TextView textViewTrump = (TextView) findViewById(R.id.trumpSelected);
        // This method allows the user to select the trump when they choose to select the trump.
        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(game_page.this, R.style.AlertDialogStyle);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle("Select your trump")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textViewTrump.setVisibility(View.VISIBLE);
                        switch (which){
                            case 0:
                                trump = "Spades";
                                Log.d("TAG", "Spades Selected: " + trump);
                                textViewTrump.setText("♠");
                                break;
                            case 1:
                                trump = "Hearts";
                                Log.d("TAG", "Hearts Selected: " + trump);
                                textViewTrump.setText("♥");
                                break;
                            case 2:
                                trump = "Clubs";
                                Log.d("TAG", "Clubs Selected" + trump);
                                textViewTrump.setText("♣");
                                break;
                            case 3:
                                trump = "Diamonds";
                                Log.d("TAG", "Diamonds Selected" + trump);
                                textViewTrump.setText("♦");
                                break;
                        }
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "Inside on click : " + trump);
                        if(trump == null || trump.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Please select a trump to continue!",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "The Trump Selected: " + trump);
                            selectTrump();
                        }
                        else{
                            dialog.dismiss();
                        }

                    }
                });

        AlertDialog dialog = chooseTrump.create();
        //dialog.setView(dialogLayout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

}
