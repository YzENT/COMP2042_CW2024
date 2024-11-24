package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class representing the main menu screen.
 */
public class Screen_MainMenu extends BaseScreen {

    /**
     * The text of the title.
     */
    private static final String TITLE_TEXT = "Sky Battle";

    /**
     * The size of the title text.
     */
    private static final double TITLE_SIZE = 100;

    /**
     * The path to the background music file.
     * Source: <a href="https://pixabay.com/music/upbeat-intense-electro-trailer-music-243987/">Link to Main Menu BGM</a>
     */
    private static final String BGM_PATH = "/com/example/demo/audio/bgm/intense-electro-trailer-music-243987.mp3";

    /**
     * Constructor to initialize the Screen_MainMenu.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Screen_MainMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the main menu screen.
     */
    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();
        playBGM(BGM_PATH);

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
     * Initializes the buttons for the main menu screen.
     *
     * @return an array of initialized buttons
     */
    @Override
    protected Button[] initializeButtons() {
        Button startButton = createButton("Play Game", this::goScreen_LevelSelection);
        Button settingsButton = createButton("Settings", this::goScreen_Settings);
        Button quitButton = createButton("Quit", () -> System.exit(0));
        return new Button[]{startButton, settingsButton, quitButton};
    }

    /**
     * Navigates to the level selection screen.
     */
    private void goScreen_LevelSelection() {
        Screen_LevelSelection.setPrevScreen(this.getClass().getName());
        goScreen(Screen_LevelSelection.class);
    }

    /**
     * Navigates to the settings screen.
     */
    private void goScreen_Settings() {
        Screen_Settings.setPrevScreen(this.getClass().getName());
        goScreen(Screen_Settings.class);
    }
}