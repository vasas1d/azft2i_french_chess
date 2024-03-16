package ekke.azft2i.azft2i_chess_twoplayer_20.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarHelper {
    /**
     * Megjeleníti a paraméterben kapott szöveget egy Snackbar emelben a
     * nézet tetején 180 fokban elforgatva. a fekete játékosnak szánt felugró
     * értesítésének kijelzésére szlgál
     *
     * @param view A nézet, amelyhez a Snackbar kapcsolódik.
     * @param message A Snackbar-on megjelenő üzenet.
     */
    public static void showTopSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setRotation(180);

        @SuppressLint("RestrictedApi")
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        Snackbar.SnackbarLayout.LayoutParams params = (Snackbar.SnackbarLayout.LayoutParams) snackbarLayout.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        snackbarLayout.setLayoutParams(params);

        snackbar.show();
    }

}
