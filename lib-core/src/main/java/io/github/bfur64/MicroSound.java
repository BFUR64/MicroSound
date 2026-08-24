package io.github.bfur64;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public final class MicroSound {
    private final List<Playback> playbacks = new ArrayList<>();

    private String error = "";

    public Sound load(String resourceName) {
        return new Sound(resourceName);
    }

    public Playback play(Sound sound, boolean loop) {
        for (Playback playback : playbacks) {
            if (!playback.isValid()) {
                error = playback.getError();
                return playback;
            }

            if (playback.isAvailable()) {
                playback.play(sound, loop);
                error = playback.getError();
                return playback;
            }
        }

        Playback playback = new Playback();
        playbacks.add(playback);
        playback.play(sound, loop);

        if (!playback.isValid()) {
            error = playback.getError();
        }

        return playback;
    }

    public String getError() {
        return error;
    }
}
