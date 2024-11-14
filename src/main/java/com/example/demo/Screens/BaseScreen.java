package com.example.demo.Screens;

import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BaseScreen {

    protected final Stage stage;
    protected final int SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT;
    protected static MediaPlayer mediaPlayer;

    protected static final String FONT_PATH = "/com/example/demo/fonts/ARCADECLASSIC.ttf";
    private static final double BUTTON_SCALE_NEW = 1.2;
    private static final double BUTTON_SCALE_OLD = 1.0;
    private static final double TRANSITION_DURATION = 0.5;
    private static final double BUTTON_FONT_SIZE = 50;
    private static final double SHADOW_RADIUS = 10;
    private final DropShadow buttonShadow;

    private static final Map<Class<?>, BaseScreen> screenCache = new HashMap<>();

    private static double musicVolume = 0.5;
    private static double sfxVolume = 0.5;

    public BaseScreen(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.stage = stage;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.buttonShadow = createButtonShadow();
    }

    protected abstract void show();

    protected Text initializeTitle(String TITLE_TEXT, double TITLE_SIZE) {
        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 0);
        Text title = new Text(TITLE_TEXT);
        title.setFont(arcadeFont);
        title.setStyle("-fx-font-size: " + TITLE_SIZE + "px;");

        //colour transition
        FillTransition fillTransition = new FillTransition(Duration.seconds(1), title);
        fillTransition.setFromValue(Color.RED);
        fillTransition.setToValue(Color.MEDIUMVIOLETRED);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.play();

        return title;
    }

    protected abstract Button[] initializeButtons();

    protected Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: " + BUTTON_FONT_SIZE + "px; " +
                        "-fx-font-family: 'ArcadeClassic';"
        );
        button.setOnAction(e -> action.run());
        setupFocusListener(button);
        return button;
    }

    protected void setupFocusListener(Node node) {
        node.focusedProperty().addListener((focusProperty, wasFocused, isFocused) -> {
            if (isFocused) {
                addEffect(node);
            } else {
                removeEffect(node);
            }
        });
    }

    protected void addEffect(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(TRANSITION_DURATION), node);
        st.setToX(BUTTON_SCALE_NEW);
        st.setToY(BUTTON_SCALE_NEW);
        st.setAutoReverse(true);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.play();

        node.setEffect(buttonShadow);
        node.setUserData(st);
    }

    protected void removeEffect(Node node) {
        ScaleTransition st = (ScaleTransition) node.getUserData();
        if (st != null) {
            st.stop();
        }
        node.setScaleX(BUTTON_SCALE_OLD);
        node.setScaleY(BUTTON_SCALE_OLD);
        node.setEffect(null);
        node.setUserData(null); //clear user data to avoid leaks
    }

    protected void playBGM(String musicPath) {
        if (mediaPlayer == null) {
            Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(musicVolume);
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

    protected static void setMusicVolume(double volume) {
        musicVolume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    protected static double getMusicVolume() {
        return musicVolume;
    }

    protected void goScreen(Class<?> screenClass) {
        try {
            BaseScreen screen = screenCache.get(screenClass);
            if (screen == null) {
                screen = (BaseScreen) screenClass.getConstructor(Stage.class, int.class, int.class)
                        .newInstance(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
                screenCache.put(screenClass, screen);
            }
            screen.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    protected void goScreenPrevious(String previousClass) {
        try {
            Class<?> goPrevious = Class.forName(previousClass);
            goScreen(goPrevious);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    protected DropShadow createButtonShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(SHADOW_RADIUS);
        return shadow;
    }

    protected void disableMouseInput(Scene scene) {
        scene.addEventFilter(MouseEvent.ANY, Event::consume);
    }
}
