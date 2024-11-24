package com.example.demo.Screens;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class representing the loading animation screen.
 */
public class Screen_LoadingAnimation extends BaseScreen {

    /**
     * The text of the title.
     */
    private static final String TITLE_TEXT = "Loading";

    /**
     * The size of the title text.
     */
    private static final double TITLE_SIZE = 50;

    /**
     * The class name of the game level to be loaded.
     */
    private static String gameLevel;

    /**
     * Constructor to initialize the Screen_LoadingAnimation.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Screen_LoadingAnimation(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the loading animation screen.
     */
    @Override
    public void show() {
        Text loadingText = new Text(TITLE_TEXT);
        loadingText.setFont(arcadeFont);
        loadingText.setStyle("-fx-font-size: " + TITLE_SIZE + "px;" + "-fx-fill: white;");
        StackPane loadingPane = new StackPane(loadingText);
        loadingPane.setStyle("-fx-background-color: black;");
        Scene loadingScene = new Scene(loadingPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(loadingScene);

        // Add dots to loading text
        StringBuilder dots = new StringBuilder();
        Timeline loadingAnimation = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            if (dots.length() < 3) {
                dots.append(".");
            } else {
                dots.setLength(0);
            }
            loadingText.setText(TITLE_TEXT + dots);
        }));
        loadingAnimation.setCycleCount(Timeline.INDEFINITE);
        loadingAnimation.play();

        // Pause so the text "loading" is displayed
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(actionEvent -> {
            startLevel(gameLevel);
            gameLevel = null; // Just precaution in case it's somehow called again
        });
        pause.play();
    }

    /**
     * Initializes the buttons for the loading animation screen.
     *
     * @return an array of initialized buttons (null)
     */
    @Override
    protected Button[] initializeButtons() {
        return null;
    }

    /**
     * Sets the game level to be loaded.
     *
     * @param levelToGo the class name of the level to load
     */
    public static void setGameLevel(String levelToGo) {
        gameLevel = levelToGo;
    }
}