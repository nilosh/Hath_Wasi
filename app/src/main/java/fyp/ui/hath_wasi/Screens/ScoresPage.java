package fyp.ui.hath_wasi.Screens;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import fyp.ui.hath_wasi.Game.GameScores.GameScore;
import fyp.ui.hath_wasi.Game.GameScores.ScoreBoard;
import fyp.ui.hath_wasi.R;

public class ScoresPage extends AppCompatActivity {


    private static final TextView[][] score = new TextView[10][4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides the status bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_score_page);

        //get the game scores into score variable.
        //this variable consists of gameScore objects that each old marks of every player for an entire game round.
        ScoreBoard scoreBoard = ScoreBoard.getInstance();
        GameScore[] scores = scoreBoard.getScores();

        //selectScorePage(beginnerSwitch, intermediateSwitch, expertSwitch);

        //find the text views and map them to the array values.
        score[0][0] = findViewById(R.id.g1_level);
        score[0][1] = findViewById(R.id.game1_player);
        score[0][2] = findViewById(R.id.game1_cpu1);
        score[0][3] = findViewById(R.id.game1_cpu2);

        score[1][0] = findViewById(R.id.g2_level);
        score[1][1] = findViewById(R.id.game2_player);
        score[1][2] = findViewById(R.id.game2_cpu1);
        score[1][3] = findViewById(R.id.game2_cpu2);

        score[2][0] = findViewById(R.id.g3_level);
        score[2][1] = findViewById(R.id.game3_player);
        score[2][2] = findViewById(R.id.game3_cpu1);
        score[2][3] = findViewById(R.id.game3_cpu2);

        score[3][0] = findViewById(R.id.g4_level);
        score[3][1] = findViewById(R.id.game4_player);
        score[3][2] = findViewById(R.id.game4_cpu1);
        score[3][3] = findViewById(R.id.game4_cpu2);

        score[4][0] = findViewById(R.id.g5_level);
        score[4][1] = findViewById(R.id.game5_player);
        score[4][2] = findViewById(R.id.game5_cpu1);
        score[4][3] = findViewById(R.id.game5_cpu2);

        score[5][0] = findViewById(R.id.g6_level);
        score[5][1] = findViewById(R.id.game6_player);
        score[5][2] = findViewById(R.id.game6_cpu1);
        score[5][3] = findViewById(R.id.game6_cpu2);

        score[6][0] = findViewById(R.id.g7_level);
        score[6][1] = findViewById(R.id.game7_player);
        score[6][2] = findViewById(R.id.game7_cpu1);
        score[6][3] = findViewById(R.id.game7_cpu2);

        score[7][0] = findViewById(R.id.g8_level);
        score[7][1] = findViewById(R.id.game8_player);
        score[7][2] = findViewById(R.id.game8_cpu1);
        score[7][3] = findViewById(R.id.game8_cpu2);

        score[8][0] = findViewById(R.id.g9_level);
        score[8][1] = findViewById(R.id.game9_player);
        score[8][2] = findViewById(R.id.game9_cpu1);
        score[8][3] = findViewById(R.id.game9_cpu2);

        score[9][0] = findViewById(R.id.g10_level);
        score[9][1] = findViewById(R.id.game10_player);
        score[9][2] = findViewById(R.id.game10_cpu1);
        score[9][3] = findViewById(R.id.game10_cpu2);


        //iterate through the text view objects and add the marks.
        for (int i = 0; i < 10; i++) {

            //If the game is not yet played.
            if (i >= ScoreBoard.getNumberOfScores()) {
                score[i][1].setText(" - ");
                score[i][2].setText(" - ");
                score[i][3].setText(" - ");
            }

            // if the game is played already.
            else {
                score[i][0].setText(String.valueOf(scores[i].getLevel()));
                score[i][1].setText(String.valueOf(scores[i].getHuman()));
                score[i][2].setText(String.valueOf(scores[i].getComputerPlayer1()));
                score[i][3].setText(String.valueOf(scores[i].getComputerPlayer2()));
            }
        }

        if (ScoreBoard.getNumberOfScores() > 0) {
            setMedal();
            setTotalScore();
        }


    }


    public void setTotalScore(){

        int[] scores = ScoreBoard.getTotals();

        TextView[] scoreTotals = new TextView[]{this.findViewById(R.id.playerTotal), this.findViewById(R.id.comPlayer1Total),
                this.findViewById(R.id.comPlayer2Total)};

        for(int i=0; i < scoreTotals.length; i++ ){
            scoreTotals[i].setText(String.valueOf(scores[i]));
            scoreTotals[i].setVisibility(View.VISIBLE);
        }

    }

    public void setMedal() {
        int[] scores = ScoreBoard.getTotals();


        LottieAnimationView[] medals = new LottieAnimationView[]{this.findViewById(R.id.playerMedal),
                this.findViewById(R.id.cpu1Medal), this.findViewById(R.id.cpu2Medal)};

        LottieAnimationView playerMedal = this.findViewById(R.id.playerMedal);
        playerMedal.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView cpu1Medal = this.findViewById(R.id.cpu1Medal);
        cpu1Medal.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView cpu2Medal = this.findViewById(R.id.cpu2Medal);
        cpu2Medal.setVisibility(LottieAnimationView.VISIBLE);

        int max = scores[0];


        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > max) {
                max = scores[i];
                medals[i - 1].setVisibility(View.INVISIBLE);
            } else if (scores[i] < max) {
                medals[i].setVisibility(View.INVISIBLE);
            }
        }

        if (max == scores[2]) {
            if (max > scores[0]) {
                medals[0].setVisibility(View.INVISIBLE);
            }
        }

    }

}
