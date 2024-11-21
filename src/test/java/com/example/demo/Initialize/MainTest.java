package com.example.demo.Initialize;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class MainTest {

    private Main main;
    private Stage stage;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            main = new Main();
            stage = new Stage();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void start() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> main.start(stage), "Application should start without throwing exceptions");
            assertEquals("Sky Battle", stage.getTitle(), "Stage title should be 'Sky Battle'");
            assertFalse(stage.isResizable(), "Stage should not be resizable");
            assertEquals(1300, stage.getWidth(), "Stage width should be 1300");
            assertEquals(750, stage.getHeight(), "Stage height should be 750");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void ensureConfigFileExists() throws IOException, InterruptedException {
        String configPath = Main.getConfigPath();
        File configFile = new File(configPath);

        // Delete the config file if it exists to test creation
        if (configFile.exists()) {
            Files.delete(Paths.get(configPath));
        }

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            main.ensureConfigFileExists();
            assertTrue(configFile.exists(), "Config file should be created if it does not exist");
            assertTrue(configFile.length() > 0, "Config file should not be empty");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void setKeyBindingsDefault() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            main.setKeyBindings();
            Map<String, KeyCode> keyBindings = Main.getKeyBindings();
            assertNotNull(keyBindings, "Key bindings should be loaded");
            assertEquals(KeyCode.SPACE, keyBindings.get("Fire"), "Key binding for 'Fire' should be SPACE");
            assertEquals(KeyCode.S, keyBindings.get("Move_DOWN"), "Key binding for 'Move_DOWN' should be S");
            assertEquals(KeyCode.A, keyBindings.get("Move_LEFT"), "Key binding for 'Move_LEFT' should be A");
            assertEquals(KeyCode.D, keyBindings.get("Move_RIGHT"), "Key binding for 'Move_RIGHT' should be D");
            assertEquals(KeyCode.W, keyBindings.get("Move_UP"), "Key binding for 'Move_UP' should be W");
            assertEquals(KeyCode.ESCAPE, keyBindings.get("Pause"), "Key binding for 'Pause' should be ESCAPE");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void getConfigPath() {
        assertNotNull(Main.getConfigPath(), "Config path should not be null");
        assertTrue(Main.getConfigPath().endsWith("/Documents/SkyBattle_20617094/keyConfigs.properties"), "Config path should end with the expected file name");
    }
}