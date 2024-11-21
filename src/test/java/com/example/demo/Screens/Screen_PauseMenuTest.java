package com.example.demo.Screens;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class Screen_PauseMenuTest {

    private Screen_PauseMenu pauseMenu;
    private Stage stage;
    private Scene mockGameScene;
    private boolean resumeCalled;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            pauseMenu = new Screen_PauseMenu(stage, 800, 600);
            mockGameScene = new Scene(new VBox(), 800, 600);
            Screen_PauseMenu.setScene(mockGameScene);
            Screen_PauseMenu.setRunback(() -> resumeCalled = true);
            resumeCalled = false;
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testShow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            pauseMenu.show();
            Scene scene = stage.getScene();
            assertNotNull(scene, "Scene should be set when show() is called.");
            VBox root = (VBox) scene.getRoot();
            assertEquals("Game Paused", ((Text) root.getChildren().get(0)).getText(), "Title should match 'Game Paused'.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testInitializeButtons() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button[] buttons = pauseMenu.initializeButtons();
            assertEquals(3, buttons.length, "There should be 3 buttons in the pause menu.");
            assertEquals("Resume Game", buttons[0].getText(), "First button should be 'Resume Game'.");
            assertEquals("Settings", buttons[1].getText(), "Second button should be 'Settings'.");
            assertEquals("Home", buttons[2].getText(), "Third button should be 'Home'.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testResumeGame() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            pauseMenu.show();
            Button resumeButton = pauseMenu.initializeButtons()[0];
            resumeButton.fire();
            assertTrue(resumeCalled, "Resume action should have been triggered.");
            assertEquals(mockGameScene, stage.getScene(), "Stage should be set back to the game scene.");
            latch.countDown();
        });
        latch.await();
    }
}