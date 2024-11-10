package com.example.demo.Screens;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Screen_MainMenu extends BaseScreen {

    private static final String TITLE_TEXT = "Sky Battle";
    private static final String FONT_PATH = "/com/example/demo/fonts/ARCADECLASSIC.ttf";
    private static final String BGM_PATH = "/com/example/demo/audio/bgm/Transformer - Scorponok.mp3";
    private static final double TITLE_SIZE = 150;
    private static final double BUTTON_FONT_SIZE = 50;
    private static final double SHADOW_RADIUS = 10;
    private static final double BUTTON_SCALE_NEW = 1.2;
    private static final double BUTTON_SCALE_OLD = 1.0;
    private static final double TRANSITION_DURATION = 0.5;

    private final DropShadow buttonShadow;

    public Screen_MainMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
        buttonShadow = createButtonShadow(SHADOW_RADIUS);
    }

    @Override
    public void show() {
        Text title = initializeTitle();
        Button[] buttons = initializeButtons();
        playBGM(BGM_PATH);
        setVolume(0.2);

        VBox vbox = new VBox(50, title);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
    }

    @Override
    protected Text initializeTitle() {
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

    @Override
    protected Button[] initializeButtons() {
        Button startButton = createButton("Play Game", this::goScreen_LevelSelection);
        Button settingsButton = createButton("Settings", this::goScreen_Settings);
        Button quitButton = createButton("Quit", () -> System.exit(0));
        return new Button[]{startButton, settingsButton, quitButton};
    }

    @Override
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

    private void setupFocusListener(Button button) {
        button.focusedProperty().addListener((focusProperty, wasFocused, isFocused) -> {
            if (isFocused) {
                addEffect(button);
            } else {
                removeEffect(button);
            }
        });
    }

    private void addEffect(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(TRANSITION_DURATION), button);
        st.setToX(BUTTON_SCALE_NEW);
        st.setToY(BUTTON_SCALE_NEW);
        st.setAutoReverse(true);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.play();

        button.setEffect(buttonShadow);
        button.setUserData(st); // Store transition in user data for easy retrieval in removeEffect
    }

    private void removeEffect(Button button) {
        ScaleTransition st = (ScaleTransition) button.getUserData();
        if (st != null) {
            st.stop();
        }
        button.setScaleX(BUTTON_SCALE_OLD);
        button.setScaleY(BUTTON_SCALE_OLD);
        button.setEffect(null);
        button.setUserData(null); //clear user data to avoid leaks
    }

    private void goScreen_LevelSelection() {
        goScreen(Screen_LevelSelection.class);
    }

    private void goScreen_Settings() {
        System.out.println("Visiting settings screen...");
    }
}
