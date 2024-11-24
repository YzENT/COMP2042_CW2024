package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.Screens.Settings.Controls;
import com.example.demo.Screens.Settings.Volume;

/**
 * Class representing the settings screen.
 */
public class Screen_Settings extends BaseScreen {

    private static final String TITLE_TEXT = "Settings";
    private static final double TITLE_SIZE = 50;
    private static String prevScreen;

    /**
     * Constructor to initialize the Screen_Settings.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Screen_Settings(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the settings screen.
     */
    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();

        VBox vbox = new VBox(70, title);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        disableMouseInput(scene);
        enableWASDNavigation(scene);
    }

    /**
     * Initializes the buttons for the settings screen.
     *
     * @return an array of initialized buttons
     */
    @Override
    protected Button[] initializeButtons() {
        Button controlsButton = createButton("Controls", this::goScreen_ControlsSettings);
        Button volumeButton = createButton("Volume", this::goScreen_volumeSettings);
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{controlsButton, volumeButton, backButton};
    }

    /**
     * Navigates to the volume settings screen.
     */
    private void goScreen_volumeSettings() {
        goScreen(Volume.class);
        Volume.setPrevScreen(this.getClass().getName());
    }

    /**
     * Navigates to the controls settings screen.
     */
    private void goScreen_ControlsSettings() {
        goScreen(Controls.class);
        Controls.setPrevScreen(this.getClass().getName());
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
     * @param previousClassSimplifiedName the class name of the previous screen
     */
    public static void setPrevScreen(String previousClassSimplifiedName) {
        prevScreen = previousClassSimplifiedName;
    }
}