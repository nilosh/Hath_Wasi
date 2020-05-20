package fyp.ui.hath_wasi.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import fyp.ui.hath_wasi.R;

public class ChooseLevel extends AppCompatActivity {

    private static Switch beginnerLevel;
    private static Switch expertLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hides the default title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choose_level);

        beginnerLevel = (Switch) findViewById(R.id.switchBeginner);

        expertLevel = (Switch) findViewById(R.id.switchExpert);

        beginnerLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    expertLevel.setChecked(false);
                }
                else{
                    expertLevel.setChecked(true);
                }
            }
        });

        expertLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    beginnerLevel.setChecked(false);
                }

                else{
                    beginnerLevel.setChecked(true);
                }
            }
        });


    }

    public void play_game(View view){

        finish();
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    public static Switch getBeginnerLevel() {
        return beginnerLevel;
    }

    public void setBeginnerLevel(Switch beginnerLevel) {
        this.beginnerLevel = beginnerLevel;
    }

    public static Switch getExpertLevel() {
        return expertLevel;
    }

    public void setExpertLevel(Switch expertLevel) {
        this.expertLevel = expertLevel;
    }
}
