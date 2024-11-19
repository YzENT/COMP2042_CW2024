package com.example.demo.Screens.Settings;

import com.example.demo.Initialize.Controller;
import com.example.demo.Screens.Screen_Settings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        Slider volumeSlider = new Slider(0, 1, Controller.getMusicVolume());
        volumeSlider.setBlockIncrement(0.1);
        volumeSlider.setMaxWidth((double) SCREEN_WIDTH / 4);

        volumeSlider.valueProperty().addListener((volumeLevel, oldVolume, newVolume) -> {
            Controller.setMusicVolume(newVolume.doubleValue());
        });

        Text volumeLabel = new Text("Music");
        volumeLabel.setFill(Color.WHITE);
        volumeLabel.setFont(arcadeFont);
        volumeLabel.setStyle("-fx-font-size: 30px;");

        HBox sliderContainer = new HBox(100, volumeLabel, volumeSlider);
        sliderContainer.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(sliderContainer);
        vbox.setAlignment(Pos.CENTER);

        setupFocusListener(volumeSlider);

        return vbox;
    }

    private void goScreen_PreviousScreen() {
        goScreenPrevious(prevScreen);
    }

    public static void setPrevScreen(String prevScreen) {
        Volume.prevScreen = prevScreen;
    }
}
