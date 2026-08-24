[![License](https://img.shields.io/github/license/bfur64/microsound)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21%2B-blue)](https://openjdk.org/)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.bfur64/micro-sound)](https://central.sonatype.com/artifact/io.github.bfur64/micro-sound)

<h1 align="center">MicroSound</h1>

<h3 align="center">Small, opinionated sound playback library for Java 21+</h3>

## Quick Start

```java
MicroSound sound = new MicroSound();

Sound blockPlace = sound.load("/blockPlace.wav");
Sound lineClear = sound.load("/lineClear.wav");

sound.play(blockPlace, false);
sound.play(blockPlace, false);
sound.play(blockPlace, false);

Playback playback = sound.play(lineClear, false);

playback.pause();
playback.resume();

playback.stop();
playback.resume(); // Should not work as we stopped it
```

## Features

* Simple sound loading
* Multiple simultaneous sounds
* Automatic playback management
* Play, pause, resume, and stop
* Looping playback
* Graceful audio failures
* No application-level Java Sound exceptions
* Minimal API surface

## Why This Exists

MicroSound provides a simple abstraction over Java Sound for loading and playing audio without exposing the underlying `AudioSystem`, `Clip`, `AudioInputStream`, or audio device management to the application.

## Installation

### Kotlin

```kotlin
implementation("io.github.bfur64:microsound:0.2.0")
```

### Maven

```xml
<dependency>
    <groupId>io.github.bfur64</groupId>
    <artifactId>microsound</artifactId>
    <version>0.2.0</version>
</dependency>
```

## Usage

Create a `MicroSound` instance and load a sound from the classpath:

```java
MicroSound microSound = new MicroSound();

Sound sound = microSound.load("/sounds/line-clear.wav");
```

Play it:

```java
Playback playback = microSound.play(sound, false);

Playback can then be controlled independently:

playback.pause();
playback.resume();
playback.stop();
```

For continuous looping:

```java
Playback playback = microSound.play(sound, true);
Multiple Sounds
```

`MicroSound` manages its own collection of playback instances.

When a sound is played, `MicroSound` looks for an available `Playback`. If all existing playbacks are occupied, another one is created automatically.

This means multiple sounds can play simultaneously without manually managing `Clip` instances.

```java
Sound blockPlace = microSound.load("/sounds/block-place.wav");
Sound lineClear = microSound.load("/sounds/line-clear.wav");

microSound.play(blockPlace, false);
microSound.play(lineClear, false);
```

## Error Handling

`MicroSound` is designed not to make audio failures the application's problem.

Loading an invalid or unavailable resource does not require catching Java Sound exceptions:

```java
Sound sound = microSound.load("/sounds/missing.wav");

if (!sound.isValid()) {
    System.err.println(sound.getError());
}
```

Playback failures can be inspected through the returned Playback:

```java
Playback playback = microSound.play(sound, false);

if (!playback.isValid()) {
    System.err.println(playback.getError());
}
```

The library handles failures internally and exposes a readable error instead of forcing callers to deal directly with Java Sound's exception hierarchy.

## Requirements

* Java 21 or higher

## Tech Stack

- **Build Tool**: Gradle 9.7.0
- **Language**: Java 21

## Contributing

Make your branch and make a PR. I will review it
