package fyp.ui.hath_wasi.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import fyp.ui.hath_wasi.R;

public class instructions_page extends AppCompatActivity {

    EditText etText;
    ImageView imgSearch;
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.println(Log.ERROR,"Tag","on create");


        // hides the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_instructions_page);


        etText = findViewById(R.id.search_bar);
        imgSearch = findViewById(R.id.search_button);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextView[] textArray = new TextView[14];

                //Log.println(Log.ERROR,"Tag","after array");

                textArray[0] = findViewById(R.id.TextView1);
                textArray[1] = findViewById(R.id.TextView2);
                textArray[2] = findViewById(R.id.TextView3);
                textArray[3] = findViewById(R.id.TextView4);
                textArray[4] = findViewById(R.id.TextView5);
                textArray[5] = findViewById(R.id.TextView6);
                textArray[6] = findViewById(R.id.TextView7);
                textArray[7] = findViewById(R.id.TextView8);
                textArray[8] = findViewById(R.id.TextView9);
                textArray[9] = findViewById(R.id.TextView10);
                textArray[10] = findViewById(R.id.TextView11);
                textArray[11] = findViewById(R.id.TextView12);
                textArray[12] = findViewById(R.id.TextView13);
                textArray[13] = findViewById(R.id.TextView14);

                //Log.println(Log.ERROR,"Tag","after array items");

                String textToHighlight, replaceWith, originalText, modifiedText;

                for (int i = 0; i < 14; i++) {
                    //Log.println(Log.ERROR,"Tag","inside for loop");

                    tvText = textArray[i];
                    textToHighlight = etText.getText().toString();
                    replaceWith = "<span style= 'background-color: #ffc107'>" + textToHighlight + "</span>";
                    originalText = tvText.getText().toString();
                    modifiedText = originalText.replaceAll(textToHighlight,replaceWith);
                    tvText.setText(Html.fromHtml(modifiedText,1));
                }
            }
        });





        }
    }



