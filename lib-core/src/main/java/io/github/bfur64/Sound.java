package io.github.bfur64;

import org.jspecify.annotations.NullMarked;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

@NullMarked
public class Sound {
    private final AudioFormat format;
    private final byte[] data;

    public Sound(String resourceName) throws UnsupportedAudioFileException, IOException {
        this(AudioSystem.getAudioInputStream(
            new BufferedInputStream(
                Objects.requireNonNull(
                    Sound.class.getResourceAsStream(resourceName)
        ))));
    }

    public Sound(AudioInputStream inputStream) throws IOException {
        this.format = inputStream.getFormat();
        this.data = inputStream.readAllBytes();
    }

    public AudioInputStream stream() {
        long frameLength = data.length / format.getFrameSize();

        return new AudioInputStream(
            new ByteArrayInputStream(data),
            format,
            frameLength
        );
    }
}
