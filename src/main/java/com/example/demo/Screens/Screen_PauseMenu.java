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
    private Runnable onResume;

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
        Button resumeButton = createButton("Resume", this::resumeGame);
        return new Button[]{resumeButton};
    }

    private void resumeGame() {
        System.out.println("Resume logic here TODO!");
    }
}