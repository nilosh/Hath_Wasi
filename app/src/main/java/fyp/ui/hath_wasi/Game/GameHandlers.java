package fyp.ui.hath_wasi.Game;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Cards.ComputerPlayerCardViews;
import fyp.ui.hath_wasi.Game.GameSounds.Sounds;
import fyp.ui.hath_wasi.Players.AbComputerPlayer;
import fyp.ui.hath_wasi.Players.Player;
import fyp.ui.hath_wasi.R;
import fyp.ui.hath_wasi.Screens.GamePage;

public class GameHandlers {

    private String player;
    private ImageView cardImage;
    private Integer imageAlpha;
    private Integer delayMilliseconds;

    public GameHandlers(final String player, final ImageView cardImage, final Animation animation,
                        final Integer imageAlpha, Integer delayMilliseconds) {

        GamePage.cardTouch(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cardImage.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onAnimationStart(Animation animation) {

                        Sounds.cardClick();

                        if (player.toLowerCase() == "com2") {
                            ComputerPlayerCardViews.hideCardFromPlayer2();
                        } else if (player.toLowerCase() == "com1") {
                            ComputerPlayerCardViews.hideCardFromPlayer1();
                        }

                        cardImage.setVisibility(View.VISIBLE);
                        cardImage.setImageAlpha(imageAlpha);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(player.toLowerCase() == "com1"){
                            handlerForCardTouch(1500 );
                            //GamePage.cardTouch(true);
                        }
                        else{
                            GamePage.cardTouch(false);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        }, delayMilliseconds);
    }

    public static void collectCards(final ImageView com1, final ImageView com2, final ImageView playerPlaceholder,
                                    Integer delayMilliseconds) {

        GamePage.cardTouch(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.println(Log.ERROR, "TAG", "Inside Collect Cards in Handler right before collect sound");
                Sounds.cardCollect();

                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);
                playerPlaceholder.setVisibility(View.INVISIBLE);

                handlerForCardTouch(4100);
                Log.println(Log.ERROR, "TAG", "Inside Collect Cards NOW SWITCHED ONNNNN");
            }
        }, delayMilliseconds);
    }

    private static void handlerForCardTouch(Integer delay){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GamePage.cardTouch(true);
                Log.println(Log.ERROR, "TAG", "++++++++++++++++++++++++NOW SWITCHED ON +++++++++++++++++++++++++++++++++++");
            }
        }, delay);
    }

    public static void handlerForScoreUpdate(final TextView placeholder, final String score){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                placeholder.setText(score);
                placeholder.setTypeface(Typeface.DEFAULT_BOLD);

                Handler subHandler = new Handler();
                subHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        placeholder.setTypeface(Typeface.DEFAULT);
                    }
                }, 2000);
            }
        }, 3000);
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void setCardImage(ImageView cardImage) {
        this.cardImage = cardImage;
    }

    public Integer getImageAlpha() {
        return imageAlpha;
    }

    public void setImageAlpha(Integer imageAlpha) {
        this.imageAlpha = imageAlpha;
    }

    public Integer getDelayMilliseconds() {
        return delayMilliseconds;
    }

    public void setDelayMilliseconds(Integer delayMilliseconds) {
        this.delayMilliseconds = delayMilliseconds;
    }
}
