package edu.utep.cs.cs43381.tappydefender;

import android.content.Context;
import android.media.SoundPool;

public class SoundEffect {
        public enum Sound {
            START(R.raw.start),
            BUMP(R.raw.bump),
            DESTROYED(R.raw.destroyed),
            WIN(R.raw.win);

            public final int resourceId;
            private int soundId;

            Sound(int resourceId) {
                this.resourceId = resourceId;
            }
        }

        private final SoundPool soundPool;

        public SoundEffect(Context context) {
            soundPool = new SoundPool.Builder().setMaxStreams(Sound.values().length).build();
            for (Sound sound: Sound.values()) {
                sound.soundId = soundPool.load(context, sound.resourceId, 1);
            }
        }

        public void play(Sound sound) {
            soundPool.play(sound.soundId, 1, 1, 0, 0, 1);
        }


}
