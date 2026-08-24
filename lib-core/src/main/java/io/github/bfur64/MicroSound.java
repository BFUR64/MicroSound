package io.github.bfur64;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class MicroSound {
    private final ArrayList<Playback> playbacks = new ArrayList<>();

    public Sound load(String resourceName) throws UnsupportedAudioFileException, IOException {
        return new Sound(resourceName);
    }

    public Playback play(Sound sound, boolean loop) throws LineUnavailableException, IOException {
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
}
