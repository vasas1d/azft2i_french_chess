// https://www.geeksforgeeks.org/how-to-use-webview-in-android/

package ekke.azft2i.azft2i_chess_twoplayer_20;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.content.Intent;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ChessRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_rules);

        WebView webView = findViewById(R.id.chessRulesWebView);
        webView.setBackgroundColor(Color.TRANSPARENT); // a scollview háttere áttetsző
        webView.loadUrl("file:///android_res/raw/chess_rules.html"); // itt töltjük be a HTML fájlt
        webView.setWebViewClient(new WebViewClient()); // a linkre kattintva is az alkalmazásban marad

        Button backButton = findViewById(R.id.chess_rules_back_Button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChessRulesActivity.this, GameMainActivity.class);
            startActivity(intent);
        });
    }
}
