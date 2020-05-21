package fyp.ui.hath_wasi.Game.GameSounds;

import android.app.Activity;
import android.media.MediaPlayer;

import fyp.ui.hath_wasi.R;

public class Sounds {


    private static boolean onOff;
    private static Activity activity;
    private static MediaPlayer player;

    public Sounds(Activity activity) {
        onOff = true;
        Sounds.activity = activity;
        player = MediaPlayer.create(activity, R.raw.click_sound);
    }


    // Plays when the human player wins the game.
    public static void playWin() {
        if (onOff) {
            playSound(R.raw.win);
        }
    }

    // Plays when the human player loses the game.
    public static void playLost() {
        if (onOff) {
            playSound(R.raw.sadsound);
        }
    }

    // Plays when the card is clicked and put to play.
    public static void cardClick() {
        if (onOff) {
            playSound(R.raw.click_sound);
        }
    }

    // Plays when the cards are collected after played.
    public static void cardCollect() {
        if (onOff) {
            playSound(R.raw.card_collectt);

        }
    }


    // Play the sound.
    private static void playSound(int soundID) {
        final MediaPlayer mp = MediaPlayer.create(activity, soundID);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
    }
}
