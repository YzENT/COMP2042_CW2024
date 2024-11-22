package com.example.demo.Screens;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class Screen_LevelSelectionTest {

    private Screen_LevelSelection levelSelection;
    private Stage stage;

    @BeforeEach
    void setUp() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            levelSelection = new Screen_LevelSelection(stage, 800, 600);
            latch.countDown();
        });
    }

    @Test
    void testShow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelSelection.show();
            Scene scene = stage.getScene();
            VBox root = (VBox) scene.getRoot();
            assertEquals("Select Level", ((Text) root.getChildren().get(0)).getText(), "Title should match 'Select Level'.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testInitializeButtons() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button[] buttons = levelSelection.initializeButtons();
            assertEquals(4, buttons.length, "There should be 4 buttons in the level selection screen.");
            assertEquals("Level 1", buttons[0].getText(), "First button should be 'Level 1'.");
            assertEquals("Back", buttons[3].getText(), "Last button should be 'Back'.");
            latch.countDown();
        });
        latch.await();
    }
}
