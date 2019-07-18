package edu.utep.cs.cs43381.tappydefender;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends Activity {
    private TDView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        gameView= new TDView(this, size.x,size.y);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
