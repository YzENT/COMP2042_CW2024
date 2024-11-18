package com.example.demo.Screens;

import com.example.demo.Levels.LevelParent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen_GameEnded extends BaseScreen{

    private static String TITLE_TEXT;
    private static String TITLE_COLOUR;

    public Screen_GameEnded(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

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
    }

    private Text initializeTitle(String TITLE_TEXT, String TITLE_COLOR) {
        Text title = new Text(TITLE_TEXT);
        title.setFont(arcadeFont);
        title.setStyle(
                "-fx-font-size: " + 70 + "px;" +
                        "-fx-fill: " + TITLE_COLOR + ";"
        );
        return title;
    }

    @Override
    protected Button[] initializeButtons() {
        Button playAgainButton = createButton("Play Again", this::goScreen_LevelSelection);
        Button homeButton = createButton("Home", this::goScreen_MainMenu);
        return new Button[]{playAgainButton, homeButton};
    }

    private void goScreen_LevelSelection() {
        Screen_LevelSelection.setPrevScreen("com.example.demo.Screens.Screen_MainMenu"); //user doesn't need to return back to game end screen
        goScreen(Screen_LevelSelection.class);
    }

    private void goScreen_MainMenu() {
        goScreen(Screen_MainMenu.class);
    }

    public void setResults(LevelParent.GameStatus result) {
        switch (result) {
            case VICTORY:
                TITLE_TEXT = "Victory";
                TITLE_COLOUR = "green";
                break;
            case DEFEAT:
                TITLE_TEXT = "Defeat";
                TITLE_COLOUR = "red";
                break;
            default:
                TITLE_TEXT = "null";
                TITLE_COLOUR = "white";
                break;
        }
    }
}
