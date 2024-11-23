package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class representing the level selection screen.
 */
public class Screen_LevelSelection extends BaseScreen {

    private static final String TITLE_TEXT = "Select Level";
    private static final double TITLE_SIZE = 50;
    private static String prevScreen;

    /**
     * Constructor to initialize the Screen_LevelSelection.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Screen_LevelSelection(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the level selection screen.
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
    }

    /**
     * Initializes the buttons for the level selection screen.
     *
     * @return an array of initialized buttons
     */
    @Override
    protected Button[] initializeButtons() {
        Button level1Button = createButton("Level 1", () -> goScreen_LoadingAnimation("com.example.demo.Levels.Level_1"));
        Button level2Button = createButton("Level 2", () -> goScreen_LoadingAnimation("com.example.demo.Levels.Level_2"));
        Button level3Button = createButton("Level 3", () -> goScreen_LoadingAnimation("com.example.demo.Levels.Level_3"));
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{level1Button, level2Button, level3Button, backButton};
    }

    /**
     * Navigates to the loading animation screen for the specified level.
     *
     * @param levelClassName the class name of the level to load
     */
    private void goScreen_LoadingAnimation(String levelClassName) {
        Screen_LoadingAnimation.setGameLevel(levelClassName);
        goScreen(Screen_LoadingAnimation.class);
        stopBGM();
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
        Screen_LevelSelection.prevScreen = prevScreen;
    }
}