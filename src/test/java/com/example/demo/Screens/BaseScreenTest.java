package com.example.demo.Screens;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class BaseScreenTest {

    private BaseScreen baseScreen;
    private Stage stage;

    @BeforeEach
    void setUp() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            baseScreen = new BaseScreen(stage, 800, 600) {
                @Override
                protected void show() {
                    // No-op
                }

                @Override
                protected Button[] initializeButtons() {
                    return new Button[0];
                }
            };
            latch.countDown();
        });
    }

    @Test
    void testCreateButton() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button button = baseScreen.createButton("Test Button", () -> System.out.println("Clicked"));
            assertEquals("Test Button", button.getText(), "Button should have the correct text.");
            assertTrue(button.getStyle().contains("transparent"), "Button should have the correct style.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testAddAndRemoveEffect() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button button = baseScreen.createButton("Effect Test", () -> {});
            baseScreen.addEffect(button);
            assertNotNull(button.getEffect(), "Button should have a DropShadow effect.");
            baseScreen.removeEffect(button);
            assertNull(button.getEffect(), "Button effect should be removed.");
            latch.countDown();
        });
        latch.await();
    }

}