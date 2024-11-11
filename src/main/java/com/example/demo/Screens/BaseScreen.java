package com.example.demo.Screens;

import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
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

    protected abstract void show();

    protected abstract Text initializeTitle();

    protected abstract Button[] initializeButtons();

    protected abstract Button createButton(String text, Runnable action);

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

    protected static void setMediaVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    protected static double returnMediaVolume() {
        double volume = 0;
        if (mediaPlayer != null) {
            volume = mediaPlayer.getVolume();
        }
        return volume;
    }

    protected void goScreen(Class<?> screenClass) {
        try {
            BaseScreen screen = (BaseScreen) screenClass.getConstructor(Stage.class, int.class, int.class)
                    .newInstance(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
            screen.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    protected DropShadow createButtonShadow(double SHADOW_RADIUS) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(SHADOW_RADIUS);
        return shadow;
    }

    protected void disableMouseInput(Scene scene) {
        scene.addEventFilter(MouseEvent.ANY, Event::consume);
    }
}
