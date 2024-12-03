package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class representing the pause menu screen.
 */
public class Screen_PauseMenu extends BaseScreen {

    /**
     * The text of the title.
     */
    private static final String TITLE_TEXT = "Game Paused";

    /**
     * The size of the title text.
     */
    private static final double TITLE_SIZE = 50;

    /**
     * The runnable to be executed when the game is resumed.
     */
    private static Runnable onResume;

    /**
     * The game scene to be resumed later.
     */
    private static Scene gameScene;

    /**
     * Constructor to initialize the Screen_PauseMenu.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Screen_PauseMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the pause menu screen.
     */
    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();
        pauseBGM();
        stopSFX();

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
     * Initializes the buttons for the pause menu screen.
     *
     * @return an array of initialized buttons
     */
    @Override
    protected Button[] initializeButtons() {
        Button resumeButton = createButton("Resume Game", this::resumeGame);
        Button settingsButton = createButton("Settings", this::goScreen_Settings);
        Button quitButton = createButton("Home", this::goScreen_MainMenu);
        return new Button[]{resumeButton, settingsButton, quitButton};
    }

    /**
     * Navigates to the settings screen.
     */
    private void goScreen_Settings() {
        Screen_Settings.setPrevScreen(this.getClass().getName());
        goScreen(Screen_Settings.class);
    }

    /**
     * Navigates to the main menu screen.
     */
    private void goScreen_MainMenu() {
        stopBGM();
        goScreen(Screen_MainMenu.class);
    }

    /**
     * Resumes the game by setting the scene back to the game scene and resuming background music.
     */
    private void resumeGame() {
        onResume.run();
        stage.setScene(gameScene);
        resumeBGM();
    }

    /**
     * Sets the game scene to be resumed later.
     *
     * @param scene the game scene to be set
     */
    public static void setScene(Scene scene) {
        gameScene = scene; // this is so that stage can set to the correct scene later if game is resumed
    }

    /**
     * Sets the runnable to be executed when the game is resumed.
     *
     * @param resume the runnable to be set
     */
    public static void setRunback(Runnable resume) {
        onResume = resume; // this is added so that it could run the code(LevelParent\resumeGame) directly without creating an object
    }
}