package com.example.demo.Screens.Settings;

import com.example.demo.Initialize.Main;
import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class ControlsTest {

    private Controls controls;
    private Stage stage;

    @BeforeEach
    void setUp() throws InterruptedException {
        Main mainInstance = new Main();
        Map<String, KeyCode> mockKeyBindings = new HashMap<>();
        mockKeyBindings.put("Move_UP", KeyCode.W);
        mockKeyBindings.put("Move_DOWN", KeyCode.S);
        mockKeyBindings.put("Move_LEFT", KeyCode.A);
        mockKeyBindings.put("Move_RIGHT", KeyCode.D);
        mockKeyBindings.put("Pause", KeyCode.ESCAPE);
        mainInstance.setKeyBindings();

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            controls = new Controls(stage, 800, 600);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testResetKeyBindings_RestoresDefaultBindings() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controls.resetKeybinds();
            Map<String, KeyCode> keyBindings = Main.getKeyBindings();
            assertEquals(KeyCode.SPACE, keyBindings.get("Fire"), "Default Fire key binding should be SPACE");
            assertEquals(KeyCode.W, keyBindings.get("Move_UP"), "Default Move_UP key binding should be W");
            latch.countDown();
        });
        latch.await();
    }
}