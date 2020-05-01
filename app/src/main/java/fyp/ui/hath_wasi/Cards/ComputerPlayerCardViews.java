package fyp.ui.hath_wasi.Cards;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import fyp.ui.hath_wasi.R;

public class ComputerPlayerCardViews {

    private static ImageView[] comPlayer1CardArray;
    private static ImageView[] comPlayer2CardArray;
    private static ArrayList<Integer> comPlayer1Indexes;
    private static ArrayList<Integer> comPlayer2Indexes;
    static private Random randomNumber;


    public ComputerPlayerCardViews(Activity activity){

        this.randomNumber = new Random();

        this.comPlayer1CardArray = new ImageView[]{
            activity.findViewById(R.id.comPlayer1Card1),
            activity.findViewById(R.id.comPlayer1Card2),
            activity.findViewById(R.id.comPlayer1Card3),
            activity.findViewById(R.id.comPlayer1Card4),
            activity.findViewById(R.id.comPlayer1Card5),
            activity.findViewById(R.id.comPlayer1Card6),
            activity.findViewById(R.id.comPlayer1Card7),
            activity.findViewById(R.id.comPlayer1Card8),
            activity.findViewById(R.id.comPlayer1Card9),
            activity.findViewById(R.id.comPlayer1Card10),
            activity.findViewById(R.id.comPlayer1Card11),
            activity.findViewById(R.id.comPlayer1Card12),
        };


        this.comPlayer2CardArray = new ImageView[]{
            activity.findViewById(R.id.comPlayer2Card1),
                activity.findViewById(R.id.comPlayer2Card2),
                activity.findViewById(R.id.comPlayer2Card3),
                activity.findViewById(R.id.comPlayer2Card4),
                activity.findViewById(R.id.comPlayer2Card5),
                activity.findViewById(R.id.comPlayer2Card6),
                activity.findViewById(R.id.comPlayer2Card7),
                activity.findViewById(R.id.comPlayer2Card8),
                activity.findViewById(R.id.comPlayer2Card9),
                activity.findViewById(R.id.comPlayer2Card10),
                activity.findViewById(R.id.comPlayer2Card11),
                activity.findViewById(R.id.comPlayer2Card12),
        };

        initializeIndexes();
    }

    // This method animates the computer player card decks on start.
    public static void openAnimation(){

        // Declare variable to store no. of cards.
        final int cards = 12;

        // Set the initial position of the cards.
        for(int i = 0; i < cards; i++){
            comPlayer1CardArray[i].setX(comPlayer1CardArray[i].getX() + 100f);
            comPlayer2CardArray[i].setX(comPlayer2CardArray[i].getX() - 100f);
        }


        // Create animations for each card and translate it from the original position.
        AnimatorSet animatorSetA = new AnimatorSet();
        ArrayList<Animator> animationsA = new ArrayList<Animator>();

        AnimatorSet animatorSetB = new AnimatorSet();
        ArrayList<Animator> animationsB = new ArrayList<Animator>();

        // translate position of cards of ComPlayer1.
        for(int i = 0; i < cards; i++){
            ObjectAnimator p1 = ObjectAnimator.ofFloat(comPlayer1CardArray[i], "translationX", -100f);
            animationsA.add(p1);
        }

        // translate position of cards of ComPlayer2.
        for(int i = 0; i < cards; i++){
            ObjectAnimator p2 = ObjectAnimator.ofFloat(comPlayer2CardArray[i], "translationX", 100f);
            animationsB.add(p2);
        }


        // Play animations for ComPlayer1 card deck.
        animatorSetA.setDuration(200);
        animatorSetA.playSequentially(animationsA);
        animatorSetA.start();

        // Play animations for ComPlayer2 card deck.
        animatorSetB.setDuration(200);
        animatorSetB.playSequentially(animationsB);
        animatorSetB.start();

    }

    public static void hideCardFromPlayer1(){
        int indexOfComPlayer1 = randomNumber.nextInt(comPlayer1Indexes.size());
        int position = comPlayer1Indexes.remove(indexOfComPlayer1);
        comPlayer1CardArray[position].setVisibility(View.INVISIBLE);
    }

    public static void hideCardFromPlayer2(){
        int indexOfComPlayer2 = randomNumber.nextInt(comPlayer2Indexes.size());
        int position = comPlayer2Indexes.remove(indexOfComPlayer2);
        comPlayer2CardArray[position].setVisibility(View.INVISIBLE);
    }

    public static void makeAllCardsVisible(){
        for (int i = 0; i < 12; i++){

            comPlayer1CardArray[i].setVisibility(View.VISIBLE);
            comPlayer2CardArray[i].setVisibility(View.VISIBLE);
        }

        initializeIndexes();
    }


    public static void initializeIndexes(){
        comPlayer1Indexes = new ArrayList<Integer>(
                Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11)
        );

        comPlayer2Indexes = new ArrayList<Integer>(
                Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11)
        );
    }
}
