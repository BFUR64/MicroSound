package io.github.bfur64;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@NullMarked
public final class Sound {
    private final @Nullable AudioFormat audioFormat;
    private final byte @Nullable [] data;

    private String error = "";

    Sound(String resourceName) {
        AudioFormat audioFormat = null;
        byte[] data = null;

        try {
            InputStream inputStream = Sound.class.getResourceAsStream(resourceName);

            if (inputStream == null) {
                error = "Resource not found: " + resourceName;
            }
            else {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

                    audioFormat = audioInputStream.getFormat();
                    data = audioInputStream.readAllBytes();
                }

            }
        }
        catch (UnsupportedAudioFileException e) {
            error = "Unsupported audio file: " + resourceName;
        }
        catch (IOException e) {
            error = "Failed to read audio file: " + resourceName
                    + ": " + e.getMessage();
        }

        this.audioFormat = audioFormat;
        this.data = data;
    }

    public AudioInputStream stream() {
        AudioFormat audioFormat = Objects.requireNonNull(this.audioFormat);
        byte[] data = Objects.requireNonNull(this.data);

        long frameLength = data.length / audioFormat.getFrameSize();

        return new AudioInputStream(
            new ByteArrayInputStream(data),
            audioFormat,
            frameLength
        );
    }

    public boolean isValid() {
        return audioFormat != null && data != null;
    }

    public String getError() {
        return error;
    }
}
