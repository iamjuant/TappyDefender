package edu.utep.cs.cs43381.tappydefender;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class ScoreRecorder {
    private static final String PREF_FILE = "highScore";
    private static final String PREF_KEY = "fastestTime";
    private static final long DEFAULT_VALUE = Long.MAX_VALUE;
    private static ScoreRecorder theInstance;

    private final SharedPreferences sharedPref;

    private ScoreRecorder(Context ctx) {
        sharedPref = ctx.getSharedPreferences(PREF_FILE, MODE_PRIVATE);
    }

    public void store(long time) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("fastestTime", time);
        editor.commit();

    }

    public long retrieve() {
        return sharedPref.getLong("fastestTime", Long.MAX_VALUE);

    }

    public static ScoreRecorder instance(@Nullable Context ctx) {
        if (theInstance == null) {
            theInstance = new ScoreRecorder(ctx);
        }
        return theInstance;
    }

}
