package com.example.demo.Screens;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;

import java.util.Objects;

public abstract class BaseScreen {

    protected final Stage stage;
    protected final int SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT;
    protected static MediaPlayer mediaPlayer;

    public BaseScreen(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.stage = stage;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public abstract void show();

    protected void playBGM(String musicPath) {
        if (mediaPlayer == null) {
            Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    protected void stopBGM() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    protected void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

}
