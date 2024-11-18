package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen_LevelSelection extends BaseScreen {

    private static final String TITLE_TEXT = "Select Level";
    private static final double TITLE_SIZE = 50;
    private static String prevScreen;

    public Screen_LevelSelection(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

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

    @Override
    protected Button[] initializeButtons() {
        Button level1Button = createButton("Level 1", () -> goScreen_LoadingAnimation("com.example.demo.Levels.Level_1"));
        Button level2Button = createButton("Level 2", () -> goScreen_LoadingAnimation("com.example.demo.Levels.Level_2"));
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{level1Button, level2Button, backButton};
    }

    private void goScreen_LoadingAnimation(String levelClassName) {
        Screen_LoadingAnimation.setGameLevel(levelClassName);
        goScreen(Screen_LoadingAnimation.class);
    }

    private void goScreen_PreviousScreen() {
        goScreenPrevious(prevScreen);
    }

    public static void setPrevScreen(String prevScreen) {
        Screen_LevelSelection.prevScreen = prevScreen;
    }
}