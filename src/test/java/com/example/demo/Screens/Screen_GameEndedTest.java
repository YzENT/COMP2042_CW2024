package com.example.demo.Screens;

import com.example.demo.JavaFXBaseTesting;
import com.example.demo.Levels.LevelParent;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class Screen_GameEndedTest {

    private Screen_GameEnded gameEnded;
    private Stage stage;

    @BeforeEach
    void setUp() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            gameEnded = new Screen_GameEnded(stage, 800, 600);
            latch.countDown();
        });
    }

    @Test
    void testVictory() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            gameEnded.setResults(LevelParent.GameStatus.VICTORY);
            gameEnded.show();
            Scene scene = stage.getScene();
            VBox root = (VBox) scene.getRoot();
            assertEquals("Victory", ((Text) root.getChildren().get(0)).getText(), "Title text should match 'Victory'.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testDefeat() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            gameEnded.setResults(LevelParent.GameStatus.DEFEAT);
            gameEnded.show();
            Scene scene = stage.getScene();
            VBox root = (VBox) scene.getRoot();
            assertEquals("Defeat", ((Text) root.getChildren().get(0)).getText(), "Title text should match 'Defeat'.");
            latch.countDown();
        });
        latch.await();
    }
}
