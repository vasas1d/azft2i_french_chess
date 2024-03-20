package ekke.azft2i.azft2i_chess_twoplayer_20;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;

public class WhitePawnPromotionActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.white_pawn_promotion);

        ImageView buttonKing = findViewById(R.id.imageKing);
        ImageView  buttonQueen = findViewById(R.id.imageQueen);
        ImageView  buttonBishop = findViewById(R.id.imageBishop);
        ImageView  buttonRook = findViewById(R.id.imageRook);
        ImageView  buttonKnight = findViewById(R.id.imageKnight);

        buttonKing.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PAWN_PROMOTION", "KING");
            Log.d("WhitePawnPromotionA","- király lett kiválasztva");
            startActivity(intent);
            finish();
        });

        buttonQueen.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PAWN_PROMOTION", "QUEEN");
            Log.d("WhitePawnPromotionA","- vezér lett kiválasztva");
            startActivity(intent);
            finish();
        });

        buttonBishop.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PAWN_PROMOTION", "BISHOP");
            Log.d("WhitePawnPromotionA","- futó lett kiválasztva");
            startActivity(intent);
            finish();
        });

        buttonRook.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PAWN_PROMOTION", "ROOK");
            Log.d("WhitePawnPromotionA","- bástya lett kiválasztva");
            startActivity(intent);
            finish();
        });

        buttonKnight.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PAWN_PROMOTION", "KNIGHT");
            Log.d("WhitePawnPromotionA","- huszár lett kiválasztva");
            startActivity(intent);
            finish();
        });
    }

}
