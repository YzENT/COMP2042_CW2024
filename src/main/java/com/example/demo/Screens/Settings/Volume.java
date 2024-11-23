package com.example.demo.Screens.Settings;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.Initialize.Controller;
import com.example.demo.Screens.Screen_Settings;

/**
 * Class representing the volume settings screen.
 */
public class Volume extends Screen_Settings {

    private static final String TITLE_TEXT = "Volume";
    private static final double TITLE_SIZE = 50;
    private static String prevScreen;

    /**
     * Constructor to initialize the Volume screen.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Volume(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the volume settings screen.
     */
    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();
        VBox musicVolumeSlider = initializeVolumeSlider();

        VBox vbox = new VBox(70, title, musicVolumeSlider);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        disableMouseInput(scene);
    }

    /**
     * Initializes the buttons for the volume settings screen.
     *
     * @return an array of initialized buttons
     */
    @Override
    protected Button[] initializeButtons() {
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{backButton};
    }

    /**
     * Initializes the volume sliders for music and SFX.
     *
     * @return a VBox containing the volume sliders
     */
    private VBox initializeVolumeSlider() {
        HBox musicSlider = createSlider("Music", Controller.getMusicVolume(), Controller::setMusicVolume);
        HBox sfxSlider = createSlider("SFX", Controller.getSfxVolume(), Controller::setSfxVolume);

        VBox vbox = new VBox(40, musicSlider, sfxSlider);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    /**
     * Creates a slider for adjusting volume.
     *
     * @param labelText the label text for the slider
     * @param initialValue the initial value of the slider
     * @param valueUpdater the consumer to update the volume value
     * @return an HBox containing the slider and its label
     */
    protected HBox createSlider(String labelText, double initialValue, Consumer<Double> valueUpdater) {
        HBox sliderContainer = new HBox(20);
        sliderContainer.setAlignment(Pos.CENTER);
        sliderContainer.setMaxSize(400, 400);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-family: '" + fontName + "'; " +
                "-fx-font-size: 30px; " +
                "-fx-text-fill: white;");
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPrefWidth(200);

        Slider slider = new Slider(0, 1, initialValue);
        slider.setBlockIncrement(0.1);
        slider.setPrefWidth(200);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueUpdater.accept(newValue.doubleValue());
        });
        setupFocusListener(slider);

        sliderContainer.getChildren().addAll(label, slider);
        return sliderContainer;
    }

    /**
     * Navigates to the previous screen.
     */
    private void goScreen_PreviousScreen() {
        goScreenPrevious(prevScreen);
    }

    /**
     * Sets the previous screen class name.
     *
     * @param prevScreen the class name of the previous screen
     */
    public static void setPrevScreen(String prevScreen) {
        Volume.prevScreen = prevScreen;
    }
}