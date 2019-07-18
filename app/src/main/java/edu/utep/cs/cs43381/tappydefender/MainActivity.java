package edu.utep.cs.cs43381.tappydefender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor editor;
        SharedPreferences prefs = getSharedPreferences("HiScores",MODE_PRIVATE);


        Button button= findViewById(R.id.playButton);
       final TextView textFastestTime = findViewById(R.id.textHighScore);
        button.setOnClickListener(View->{startActivity(new Intent(this,GameActivity.class));
        finish();
            });

        long fastestTime = prefs.getLong("fastestTime",Long.MAX_VALUE);
        textFastestTime.setText("Fastest Time: "+ fastestTime);

    }

}
