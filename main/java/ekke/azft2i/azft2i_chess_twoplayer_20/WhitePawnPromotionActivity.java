package ekke.azft2i.azft2i_chess_twoplayer_20;

import android.os.Bundle;
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
        });

        buttonQueen.setOnClickListener(v -> {

        });

        buttonBishop.setOnClickListener(v -> {

        });

        buttonRook.setOnClickListener(v -> {

        });

        buttonKnight.setOnClickListener(v -> {
        });
    }

}
