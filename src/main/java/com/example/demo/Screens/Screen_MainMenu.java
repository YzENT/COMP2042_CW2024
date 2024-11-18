package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen_MainMenu extends BaseScreen {

    private static final String TITLE_TEXT = "Sky Battle";
    private static final double TITLE_SIZE = 100;
    private static final String BGM_PATH = "/com/example/demo/audio/bgm/Transformer - Scorponok.mp3";

    public Screen_MainMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

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
    }

    @Override
    protected Button[] initializeButtons() {
        Button startButton = createButton("Play Game", this::goScreen_LevelSelection);
        Button settingsButton = createButton("Settings", this::goScreen_Settings);
        Button quitButton = createButton("Quit", () -> System.exit(0));
        return new Button[]{startButton, settingsButton, quitButton};
    }

    private void goScreen_LevelSelection() {
        Screen_LevelSelection.setPrevScreen(this.getClass().getName());
        goScreen(Screen_LevelSelection.class);
    }

    private void goScreen_Settings() {
        Screen_Settings.setPrevScreen(this.getClass().getName());
        goScreen(Screen_Settings.class);
    }
}
