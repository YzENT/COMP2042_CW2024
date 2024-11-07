package com.example.demo.Screens;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Screen_MainMenu extends BaseScreen{

    public Screen_MainMenu(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void show() {

        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/ARCADECLASSIC.ttf"), 0);

        playBGM("/com/example/demo/audio/bgm/Transformer - Scorponok.mp3");
        setVolume(0.2);

        Text title = new Text("Sky Battle");
        title.setFont(arcadeFont);
        title.setStyle("-fx-font-size: 150px;");

        //colour transition
        FillTransition fillTransition = new FillTransition(Duration.seconds(1), title);
        fillTransition.setFromValue(Color.RED);
        fillTransition.setToValue(Color.MEDIUMVIOLETRED);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.play();

        Button startButton = new Button("Play Game");
        Button settingsButton = new Button("Settings");
        Button quitButton = new Button("Quit");

        Button[] buttons = {startButton, settingsButton, quitButton};

        for (Button button : buttons) {
            buttonStyles(button);
            addEffect(button);
        }

        startButton.setOnAction(e -> goScreen_LevelSelection());
        settingsButton.setOnAction(e -> goScreen_Settings());
        quitButton.setOnAction(e -> System.exit(0));

        VBox vbox = new VBox(50, title);
        vbox.getChildren().addAll(buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
    }

    private void goScreen_LevelSelection() {
        Screen_LevelSelection screenLevelSelection = new Screen_LevelSelection(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
        screenLevelSelection.show();
    }

    private void goScreen_Settings() {
//        Settings settings = new Settings();
//        settings.start(stage);
        //TODO: implement settings screen
        System.out.println("Visiting settings screen...");
    }

    private void addEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(10);

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.75), button);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setAutoReverse(true);
        st.setCycleCount(ScaleTransition.INDEFINITE);

        button.setOnMouseEntered(e -> {
            st.play();
            button.setEffect(shadow);
        });
        button.setOnMouseExited(e -> {
            st.stop();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setEffect(null);
        });
        button.setOnMousePressed(e -> {
            st.pause();
            button.setScaleX(0.8);
            button.setScaleY(0.8);
        });
        button.setOnMouseReleased(e -> {
            st.play();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
    }

    private void buttonStyles(Button button) {
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 50px; " +
                        "-fx-font-family: 'ArcadeClassic';");
    }

}