package com.example.demo.Screens;

import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Screen_Settings extends BaseScreen {

    private static final String TITLE_TEXT = "Settings";
    private static final String FONT_PATH = "/com/example/demo/fonts/ARCADECLASSIC.ttf";
    private static final double TITLE_SIZE = 100;
    private static final double BUTTON_FONT_SIZE = 50;
    private static final double SHADOW_RADIUS = 10;
    private static final double BUTTON_SCALE_NEW = 1.2;
    private static final double BUTTON_SCALE_OLD = 1.0;
    private static final double TRANSITION_DURATION = 0.5;

    private final DropShadow buttonShadow;

    public Screen_Settings(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
        buttonShadow = createButtonShadow(SHADOW_RADIUS);
    }

    @Override
    public void show() {
        Text title = initializeTitle();
        Button[] buttons = initializeButtons();
        Slider volumeSlider = initializeVolumeSlider();

        VBox vbox = new VBox(50, title, volumeSlider);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        disableMouseInput(scene);
    }

    @Override
    protected Text initializeTitle() {
        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 0);
        Text title = new Text(TITLE_TEXT);
        title.setFont(arcadeFont);
        title.setStyle("-fx-font-size: " + TITLE_SIZE + "px;");

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
        Button backButton = createButton("Back", this::goScreen_MainMenu);
        return new Button[]{backButton};
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

    private void setupFocusListener(Node node) {
        node.focusedProperty().addListener((focusProperty, wasFocused, isFocused) -> {
            if (isFocused) {
                addEffect(node);
            } else {
                removeEffect(node);
            }
        });
    }

    private void addEffect(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(TRANSITION_DURATION), node);
        st.setToX(BUTTON_SCALE_NEW);
        st.setToY(BUTTON_SCALE_NEW);
        st.setAutoReverse(true);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.play();

        node.setEffect(buttonShadow);
        node.setUserData(st);
    }

    private void removeEffect(Node node) {
        ScaleTransition st = (ScaleTransition) node.getUserData();
        if (st != null) {
            st.stop();
        }
        node.setScaleX(BUTTON_SCALE_OLD);
        node.setScaleY(BUTTON_SCALE_OLD);
        node.setEffect(null);
        node.setUserData(null); //clear user data to avoid leaks
    }

    private Slider initializeVolumeSlider() {
        Slider volumeSlider = new Slider(0, 100, returnMediaVolume() * 100);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setBlockIncrement(1);
        volumeSlider.setMaxWidth((double)SCREEN_WIDTH / 2);

        volumeSlider.setStyle(
                "-fx-control-inner-background: black; " +
                        "-fx-tick-label-fill: white; " +
                        "-fx-tick-mark-fill: white; " +
                        "-fx-font-size: " + BUTTON_FONT_SIZE / 2 + "px; "
        );

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setMediaVolume(newValue.doubleValue() / 100);
        });

        setupFocusListener(volumeSlider);

        return volumeSlider;
    }

    private void goScreen_MainMenu() {
        goScreen(Screen_MainMenu.class);
    }
}