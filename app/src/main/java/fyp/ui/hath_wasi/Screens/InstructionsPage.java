package fyp.ui.hath_wasi.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import fyp.ui.hath_wasi.R;

public class InstructionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hides the default title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_instructions);

        String url = "file:///android_asset/instructions.html";

        WebView view = (WebView) this.findViewById(R.id.webView);
        view.setBackgroundColor(0);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
}
