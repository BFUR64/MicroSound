package io.github.bfur64;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

@NullMarked
public final class Playback {
    private final @Nullable Clip clip;

    private boolean available = true;

    private boolean paused;

    private boolean valid = true;
    private String error = "";

    Playback() {
        Clip clip = null;

        try {
            clip = AudioSystem.getClip();
        }
        catch (LineUnavailableException e) {
            valid = false;
            error = "Speakers unavailable: " + e.getMessage();
        }

        this.clip = clip;

        if (clip != null) {
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.START) {
                    available = false;
                }
                else if (event.getType() == LineEvent.Type.STOP && !paused) {
                    available = true;
                }
            });
        }
    }

    void play(Sound sound, boolean loop) {
        try {
            if (clip == null) {
                return;
            }

            error = "";

            if (!sound.isValid()) {
                error = sound.getError();
                return;
            }

            if (clip.isOpen()) {
                clip.close();
            }

            available = false;
            paused = false;

            clip.open(sound.stream());
            clip.setFramePosition(0);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else {
                clip.start();
            }
        }
        catch (LineUnavailableException e) {
            valid = false;
            error = "Speakers unavailable: " + e.getMessage();
        }
        catch (IOException e) {
            valid = false;
            error = "Reading error: " + e.getMessage();
        }
    }

    public void stop() {
        if (clip == null) {
            return;
        }

        clip.stop();
        clip.setFramePosition(0);
        paused = false;
        available = true;
    }

    public void pause() {
        if (clip == null) {
            return;
        }

        paused = true;
        clip.stop();
    }

    public void resume() {
        if (clip == null || !clip.isOpen() || !paused) {
            return;
        }

        paused = false;
        clip.start();
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isValid() {
        return valid;
    }

    public String getError() {
        return error;
    }
}
