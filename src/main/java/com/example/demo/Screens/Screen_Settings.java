package com.example.demo.Screens;

import com.example.demo.Screens.Settings.Controls;
import com.example.demo.Screens.Settings.Volume;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen_Settings extends BaseScreen {

    private static final String TITLE_TEXT = "Settings";
    private static final double TITLE_SIZE = 100;
    private static String prevScreen;

    public Screen_Settings(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();

        VBox vbox = new VBox(50, title);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        disableMouseInput(scene);
    }

    @Override
    protected Button[] initializeButtons() {
        Button volumeButton = createButton("Volume", this::goScreen_volumeSettings);
        Button controlsButton = createButton("Controls", this::goScreen_ControlsSettings);
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{volumeButton, controlsButton, backButton};
    }

    private void goScreen_volumeSettings() {
        goScreen(Volume.class);
        Volume.setPrevScreen(this.getClass().getName());
    }

    private void goScreen_ControlsSettings() {
        goScreen(Controls.class);
        Controls.setPrevScreen(this.getClass().getName());
    }

    private void goScreen_PreviousScreen() {
        goScreenPrevious(prevScreen);
    }

    public static void setPrevScreen(String previousClassSimplifiedName) {
        prevScreen = previousClassSimplifiedName;
    }
}
