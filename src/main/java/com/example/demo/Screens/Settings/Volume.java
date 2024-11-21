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

public class Volume extends Screen_Settings {

    private static final String TITLE_TEXT = "Volume";
    private static final double TITLE_SIZE = 50;
    private static String prevScreen;

    public Volume(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

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

    @Override
    protected Button[] initializeButtons() {
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{backButton};
    }

    private VBox initializeVolumeSlider() {
        HBox musicSlider = createSlider("Music", Controller.getMusicVolume(), Controller::setMusicVolume);
        HBox sfxSlider = createSlider("SFX", Controller.getSfxVolume(), Controller::setSfxVolume);

        VBox vbox = new VBox(40, musicSlider, sfxSlider);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

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

    private void goScreen_PreviousScreen() {
        goScreenPrevious(prevScreen);
    }

    public static void setPrevScreen(String prevScreen) {
        Volume.prevScreen = prevScreen;
    }
}
