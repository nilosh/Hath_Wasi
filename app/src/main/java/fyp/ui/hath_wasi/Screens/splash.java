package fyp.ui.hath_wasi.Screens;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import fyp.ui.hath_wasi.R;


public class splash extends AppCompatActivity {

    // define variables
    private Handler mHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);

        // Set the activity to a handler and start the handler with a delay.
        // This will load the page and leave it for a given number of miliseconds on the screen and automatically destroys.
        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), fyp.ui.hath_wasi.Screens.game_page.class));
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 2200 );

    }


    // Destroys the instance of splash created.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null && mRunnable != null)
            mHandler.removeCallbacks(mRunnable);
    }
}