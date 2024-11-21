package com.example.demo.Screens;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.geometry.Pos;
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
class Screen_MainMenuTest {

    private Screen_MainMenu mainMenu;
    private Stage stage;

    @BeforeEach
    void setUp() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            mainMenu = new Screen_MainMenu(stage, 800, 600);
            latch.countDown();
        });
    }

    @Test
    void testShow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            mainMenu.show();
            Scene scene = stage.getScene();
            assertNotNull(scene, "Scene should be set when show() is called.");
            assertEquals(Pos.CENTER, ((VBox) scene.getRoot()).getAlignment(), "The main menu VBox should be center-aligned.");
            assertEquals("Sky Battle", ((Text) ((VBox) scene.getRoot()).getChildren().get(0)).getText(), "The title text should match the predefined value.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testInitializeButtons() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button[] buttons = mainMenu.initializeButtons();
            assertEquals(3, buttons.length, "Main menu should have three buttons.");
            assertEquals("Play Game", buttons[0].getText(), "First button should be 'Play Game'.");
            assertEquals("Settings", buttons[1].getText(), "Second button should be 'Settings'.");
            assertEquals("Quit", buttons[2].getText(), "Third button should be 'Quit'.");
            latch.countDown();
        });
        latch.await();
    }

}