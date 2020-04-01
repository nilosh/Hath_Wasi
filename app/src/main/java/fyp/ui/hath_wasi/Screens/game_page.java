package fyp.ui.hath_wasi.Screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.HashMap;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.DeckOfCards;
import fyp.ui.hath_wasi.Players.Player;
import fyp.ui.hath_wasi.R;

public class game_page extends AppCompatActivity {

    // Variable declaration.
    HashMap<Integer, Card> imageToCardMap;
    private static ImageView[] cardArray =new ImageView[12];
    String trump;

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


        // Create an instance of card and an instance of Player(for human player).
        DeckOfCards card = new DeckOfCards();
        Player human = new Player("Human Player", card);

        // Create two instances of players (for Computer Players).
        Player comPlayer1 = new Player("Computer Player 1", card);
        Player comPlayer2 = new Player("Computer Player 2", card);

        // for all the 12 cards of the human player, set the image resource (taken from the drawables folder)
        // using the getCardImagePathFromIndex method of Player class and map it to the imageView of the game_page.
        for (int i = 0; i < 12; i++){
            cardArray[i].setImageResource(human.getCardImagePathFromIndex(i));
        }

        //Map the correct card image to the human player's card deck.
        imageToCardMap = imageViewToCardMap(human, cardArray);

        //Open dialog box to select the trump.
        openDialog();


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

        // This method removes the user selected card from the user's card view
        // and doesn't allow the cards to be pressed when one card has been already placed.

        Log.println(Log.ERROR, "TAG", "Selected Image View ID: " + v.getId());
        Card selectedCard = imageToCardMap.get(v.getId());

        // remove the card from the user card deck.
        v.setVisibility(View.INVISIBLE);

//        Log.println(Log.ERROR, "TAG", "Should come here");
//        Log.println(Log.ERROR, "TAG", "Should come after this " + selectedCard.getCategory() + "ko error eka?");
//        Log.println(Log.ERROR, "TAG", "Should come after this" + selectedCard.getImageSource() + "path????");

        // Place the card on the playing set.
        ImageView image =findViewById(R.id.userSelectedCard);
        image.setImageResource(selectedCard.getImageSource());
        image.setVisibility(View.VISIBLE);

        cardTouch(false);

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
        // This method creates and allows the user to choose allow or decline selecting the trump.
        AlertDialog.Builder getChances = new AlertDialog.Builder(this);
        getChances.setMessage("Can you win 7 chances?")
                .setTitle("7 Chances?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTrump();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
        // This method allows the user to select the trump when they choose to select the trump.
        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(game_page.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.activity_popUpWindow, null);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle("Select your trump")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                trump = "Spades";
                                Log.d("TAG", "Spades selected: " + trump);
                                break;
                            case 1:
                                trump = "Hearts";
                                Log.d("TAG", "Hearts Selected: " + trump);
                                break;
                            case 2:
                                trump = "Clubs";
                                Log.d("TAG", "Clubs selected" + trump);
                                break;
                            case 3:
                                trump = "Diamonds";
                                Log.d("TAG", "Diamonds selected" + trump);
                                break;
                        }
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = chooseTrump.create();
        //dialog.setView(dialogLayout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

}
