package fyp.ui.hath_wasi.Game;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
                        final Sounds soundPlayed, final Integer imageAlpha, Integer delayMilliseconds) {

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
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        }, delayMilliseconds);
    }



    public static void collectCards(final Sounds sound, final ImageView com1, final ImageView com2, final ImageView playerPlaceholder,
                                    Integer delayMilliseconds) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Sounds.cardCollect();

                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);
                playerPlaceholder.setVisibility(View.INVISIBLE);
                GamePage.cardTouch(true);

            }
        }, delayMilliseconds);
    }


    public static void collectCardsWithWinner(final Sounds sound, final ImageView com1, final ImageView com2, final ImageView playerPlaceholder,
                                    Integer delayMilliseconds, final Player winner) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Sounds.cardCollect();

                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);
                playerPlaceholder.setVisibility(View.INVISIBLE);

                if (winner.getName() != "Computer Player 1" && winner.getName() != "Computer Player 2") {
                    GamePage.cardTouch(true);
                }

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
