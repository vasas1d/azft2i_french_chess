package ekke.azft2i.azft2i_chess_twoplayer_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        TextView helloUserTextView = findViewById(R.id.hello_user_msg);
        helloUserTextView.setText(getString(R.string.hello_user_msg));

        Button exitButtonFirstPage = findViewById(R.id.exitButtonFirstPage);
        exitButtonFirstPage.setOnClickListener(view -> finish());
    }
    public void onScreenTapped(View view) {
        Intent intent = new Intent(this, GameMainActivity.class);
        startActivity(intent);
    }
}