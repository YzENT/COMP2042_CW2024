package com.example.demo.Screens;

import com.example.demo.Initialize.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Screen_LoadingAnimation extends BaseScreen{

    private static final String TITLE_TEXT = "Loading";
    private static final double TITLE_SIZE = 50;
    private static String gameLevel;

    public Screen_LoadingAnimation(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        Text loadingText = new Text(TITLE_TEXT);
        loadingText.setFont(arcadeFont);
        loadingText.setStyle("-fx-font-size: " + TITLE_SIZE + "px;" + "-fx-fill: white;");
        StackPane loadingPane = new StackPane(loadingText);
        loadingPane.setStyle("-fx-background-color: black;");
        Scene loadingScene = new Scene(loadingPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(loadingScene);

        //add dots to loading text
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

        //pause so the text "loading" is displayed
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(actionEvent -> {
            startLevel(gameLevel);
            gameLevel = null; //just precaution in case it's somehow called again
        });
        pause.play();
        stopBGM();
    }

    private void startLevel(String levelClassName) {
        try {
            Controller controller = new Controller(this.stage);
            controller.goToLevel(levelClassName);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    @Override
    protected Button[] initializeButtons() {
        return null;
    }

    public static void setGameLevel(String levelToGo) {
        gameLevel = levelToGo;
    }

}
