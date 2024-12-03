package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;

/**
 * Class representing the screen displayed when the game ends.
 */
public class Screen_GameEnded extends BaseScreen {

    /**
     * The text of the title.
     */
    private static String TITLE_TEXT;

    /**
     * The color of the title text.
     */
    private static String TITLE_COLOUR;

    /**
     * Constructor to initialize the Screen_GameEnded.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public Screen_GameEnded(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Displays the game end screen.
     */
    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_COLOUR);
        Button[] buttons = initializeButtons();

        VBox vbox = new VBox(70, title);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: BLACK;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        disableMouseInput(scene);
        enableWASDNavigation(scene);
    }

    /**
     * Initializes the title text with the specified text and color.
     *
     * @param TITLE_TEXT the text of the title
     * @param TITLE_COLOR the color of the title text
     * @return the initialized title text
     */
    private Text initializeTitle(String TITLE_TEXT, String TITLE_COLOR) {
        Text title = new Text(TITLE_TEXT);
        title.setFont(arcadeFont);
        title.setStyle(
                "-fx-font-size: " + 70 + "px;" +
                        "-fx-fill: " + TITLE_COLOR + ";"
        );
        return title;
    }

    /**
     * Initializes the buttons for the game end screen.
     *
     * @return an array of initialized buttons
     */
    @Override
    protected Button[] initializeButtons() {
        Button playAgainButton = createButton("Play Again", this::goScreen_LevelSelection);
        Button homeButton = createButton("Home", this::goScreen_MainMenu);
        return new Button[]{playAgainButton, homeButton};
    }

    /**
     * Navigates to the level selection screen.
     */
    private void goScreen_LevelSelection() {
        Screen_LevelSelection.setPrevScreen("com.example.demo.Screens.Screen_MainMenu"); // user doesn't need to return back to game end screen
        goScreen(Screen_LevelSelection.class);
    }

    /**
     * Navigates to the main menu screen.
     */
    private void goScreen_MainMenu() {
        stopBGM();
        goScreen(Screen_MainMenu.class);
    }

    /**
     * Sets the results of the game and updates the title text and color accordingly.
     *
     * @param result the game status (VICTORY or DEFEAT)
     */
    public void setResults(LevelParent.GameStatus result) {
        switch (result) {
            case VICTORY:
                TITLE_TEXT = "Victory";
                TITLE_COLOUR = "green";

                //https://pixabay.com/music/main-title-victory-epic-music-155790/
                playBGM("/com/example/demo/audio/bgm/victory-epic-music-155790.mp3");
                break;
            case DEFEAT:
                TITLE_TEXT = "Defeat";
                TITLE_COLOUR = "red";

                //https://www.youtube.com/watch?v=AtPrjYp75uA
                playBGM("/com/example/demo/audio/bgm/miaw miaw miaw song sad (lyrics video visual).mp3");
                break;
            default:
                TITLE_TEXT = "null";
                TITLE_COLOUR = "white";
                break;
        }
    }
}