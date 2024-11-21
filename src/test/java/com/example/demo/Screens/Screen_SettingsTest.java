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
class Screen_SettingsTest {

    private Screen_Settings settings;
    private Stage stage;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            settings = new Screen_Settings(stage, 800, 600);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testShow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            settings.show();
            Scene scene = stage.getScene();
            assertNotNull(scene, "Scene should be set when show() is called.");
            VBox root = (VBox) scene.getRoot();
            assertEquals("Settings", ((Text) root.getChildren().get(0)).getText(), "Title should match 'Settings'.");
            latch.countDown();
        });
        latch.await(); }

    @Test
    void testInitializeButtons() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button[] buttons = settings.initializeButtons();
            assertEquals(3, buttons.length, "There should be 3 buttons in settings menu.");
            assertEquals("Controls", buttons[0].getText(), "First button should be 'Controls'.");
            assertEquals("Volume", buttons[1].getText(), "Second button should be 'Volume'.");
            assertEquals("Back", buttons[2].getText(), "Third button should be 'Back'.");
            latch.countDown();
        });
        latch.await();
    }

}