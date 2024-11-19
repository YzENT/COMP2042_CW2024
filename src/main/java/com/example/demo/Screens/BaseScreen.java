package com.example.demo.Screens;

import com.example.demo.Initialize.Controller;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseScreen {

    protected final Stage stage;
    protected final int SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT;

    //https://www.fontspace.com/press-start-2p-font-f11591
    protected static final Font arcadeFont = Font.loadFont(BaseScreen.class.getResourceAsStream("/com/example/demo/fonts/PressStart2P-vaV7.ttf"), 0);
    protected static final String fontName = arcadeFont.getName();
    private static final double BUTTON_SCALE_NEW = 1.2;
    private static final double BUTTON_SCALE_OLD = 1.0;
    private static final double TRANSITION_DURATION = 0.5;
    private static final double BUTTON_FONT_SIZE = 30;
    private static final double SHADOW_RADIUS = 10;
    private final DropShadow buttonShadow;
    private final Controller controller;

    private static final Map<Class<?>, BaseScreen> screenCache = new HashMap<>();

    public BaseScreen(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.stage = stage;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.buttonShadow = createButtonShadow();
        this.controller = new Controller(stage);
    }

    protected abstract void show();

    protected Text initializeTitle(String TITLE_TEXT, double TITLE_SIZE) {
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
                        "-fx-font-family: '" + fontName + "'; "
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
        controller.playBGM(musicPath);
    }

    protected void stopBGM() {
        controller.stopBGM();
    }

    protected void pauseBGM() {
        controller.pauseBGM();
    }

    protected void resumeBGM() {
        controller.resumeBGM();
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

    protected void startLevel(String levelClassName) {
        try {
            controller.goToLevel(levelClassName);
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
