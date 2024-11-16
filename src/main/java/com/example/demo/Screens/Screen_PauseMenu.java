package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen_PauseMenu extends BaseScreen{

    private static final String TITLE_TEXT = "Game Paused";
    private static final double TITLE_SIZE = 100;
    private static Runnable onResume;
    private static Scene gameScene;

    public Screen_PauseMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
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
        Button resumeButton = createButton("Resume Game", this::resumeGame);
        Button settingsButton = createButton("Settings", this::goScreen_Settings);
        Button quitButton = createButton("Home", this::goScreen_MainMenu);
        return new Button[]{resumeButton, settingsButton, quitButton};
    }

    private void goScreen_Settings() {
        Screen_Settings.setPrevScreen(this.getClass().getName());
        goScreen(Screen_Settings.class);
    }

    private void goScreen_MainMenu() {
        goScreen(Screen_MainMenu.class);
    }

    private void resumeGame() {
        onResume.run();
        stage.setScene(gameScene);
    }

    public static void setScene(Scene scene) {
        gameScene = scene; //this is so that stage can set to the correct scene later if game is resumed
    }

    public static void setRunback(Runnable resume) {
        onResume = resume; //this is added so that it could run the code(LevelParent\resumeGame) directly without creating an object
    }
}
