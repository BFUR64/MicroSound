package io.github.bfur64;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

@NullMarked
public class MicroSound {
    private final ArrayList<Playback> playbacks = new ArrayList<>();

    public Sound load(String resourceName) throws UnsupportedAudioFileException, IOException {
        return new Sound(resourceName);
    }

    public @Nullable Playback play(Sound sound, boolean loop) {
        try {
            if (playbacks.isEmpty()) {
                playbacks.add(new Playback());
            }

            for (Playback playback : playbacks) {
                if (playback.isAvailable()) {
                    playback.play(sound, loop);
                    return playback;
                }
            }

            Playback playback = new Playback();
            playbacks.add(playback);
            playback.play(sound, loop);

            return playback;
        }
        catch (LineUnavailableException e) {
            return null;
        }
    }
}
