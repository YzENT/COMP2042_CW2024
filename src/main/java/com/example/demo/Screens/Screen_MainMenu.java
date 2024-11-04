package com.example.demo.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen_MainMenu extends BaseScreen{

    public Screen_MainMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void show() {

        playBGM("/com/example/demo/audio/bgm/Transformer - Scorponok.mp3");
        setVolume(0.2);

        Text title = new Text("Sky Battle");
        title.setStyle("-fx-fill:white;");

        Button startButton = new Button("Play Game");
        Button settingsButton = new Button("Settings");
        Button quitButton = new Button("Quit");

        startButton.setOnAction(e -> goScreen_LevelSelection());
        settingsButton.setOnAction(e -> goScreen_Settings());
        quitButton.setOnAction(e -> System.exit(0));

        VBox vbox = new VBox(50, title, startButton, settingsButton, quitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
    }

    private void goScreen_LevelSelection() {
        Screen_LevelSelection screenLevelSelection = new Screen_LevelSelection(this.stage, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        screenLevelSelection.show();
    }

    private void goScreen_Settings() {
//        Settings settings = new Settings();
//        settings.start(stage);
        //TODO: implement settings screen
        System.out.println("Visiting settings screen...");
    }

}
