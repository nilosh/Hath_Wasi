package fyp.ui.hath_wasi.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import fyp.ui.hath_wasi.R;

public class ChooseLevel extends AppCompatActivity {

    private static Switch beginnerLevel;
    private static Switch intermediateLevel;
    private static Switch expertLevel;

    public static Switch getBeginnerLevel() {
        return beginnerLevel;
    }

    public void setBeginnerLevel(Switch beginnerLevel) {
        ChooseLevel.beginnerLevel = beginnerLevel;
    }

    public static Switch getIntermediateLevel() {
        return intermediateLevel;
    }

    public void setIntermediateLevel(Switch intermediateLevel) {
        ChooseLevel.intermediateLevel = intermediateLevel;
    }

    public static Switch getExpertLevel() {
        return expertLevel;
    }

    public void setExpertLevel(Switch expertLevel) {
        ChooseLevel.expertLevel = expertLevel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hides the default title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choose_level);

        // Map the switches in the XML to the switch objects created.
        beginnerLevel = findViewById(R.id.switchBeginner);

        intermediateLevel = findViewById(R.id.switchIntermediate);

        expertLevel = findViewById(R.id.switchExpert);

        // Sets listeners to the switches to check which one is checked.
        beginnerLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    intermediateLevel.setChecked(false);
                    expertLevel.setChecked(false);
                } else {
                    intermediateLevel.setChecked(true);
                }


            }
        });

        expertLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    beginnerLevel.setChecked(false);
                    intermediateLevel.setChecked(false);
                } else {
                    beginnerLevel.setChecked(true);
                }
            }
        });

        intermediateLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    beginnerLevel.setChecked(false);
                    expertLevel.setChecked(false);
                }
                else{
                    expertLevel.setChecked(true);
                }
            }
        });


    }

    // This method is called when the button to play the game is pressed.
    public void play_game(View view) {

        finish();
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    // Gets the chosen level and returns a string pertaining to that level
    // Returns "b" for Beginner, "i" for Intermediate, "e" for Expert levels.
    public static String getLevel(){

        String levelString = null;

        if(beginnerLevel.isChecked()){
            levelString = "b".toUpperCase();
        }
        else if(intermediateLevel.isChecked()){
            levelString = "i".toUpperCase();
        }

        else if(expertLevel.isChecked()){
            levelString = "e".toUpperCase();
        }

        return levelString;
    }

}
