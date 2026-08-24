package io.github.bfur64;

import org.jspecify.annotations.NullMarked;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

@NullMarked
public class Playback {
    private final Clip clip;

    private boolean available = true;

    private boolean paused;

    public Playback() throws LineUnavailableException {
        this.clip = AudioSystem.getClip();

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.START) {
                available = false;
            } else if (event.getType() == LineEvent.Type.STOP && !paused) {
                available = true;
            }
        });
    }

    public void play(Sound sound, boolean loop) {
        try {
            if (clip.isOpen()) {
                clip.close();
            }

            available = false;
            paused = false;

            clip.open(sound.stream());
            clip.setFramePosition(0);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }
        catch (LineUnavailableException | IOException ignored) {}
    }

    public void stop() {
        clip.stop();
        clip.setFramePosition(0);
        paused = false;
    }

    public void pause() {
        paused = true;
        clip.stop();
    }

    public void resume() {
        if (!clip.isOpen() || !paused) {
            return;
        }

        paused = false;
        clip.start();
    }

    public boolean isAvailable() {
        return available;
    }
}
