package com.example.demo.Levels;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class LevelViewTest {

    private LevelView levelView;
    private Group root;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            root = new Group();
            levelView = new LevelView(root, 5);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testShowHeartDisplay() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.showHeartDisplay();
            assertTrue(root.getChildren().contains(levelView.getHeartDisplay().getContainer()),"HeartDisplay container should be added to the root.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testDisplayHeartRemaining() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.showHeartDisplay();
            levelView.displayHeartRemaining(3);
            assertEquals(3, levelView.getHeartDisplay().getContainer().getChildren().size(), "The number of displayed hearts should match the heartsRemaining value.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testInitializeKillCounter() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.initializeKillCounter();
            Label killCounterLabel = levelView.getKillCounterLabel();
            assertNotNull(killCounterLabel, "Kill counter label should be initialized.");
            assertTrue(root.getChildren().contains(killCounterLabel),"Kill counter label should be added to the root.");
            assertEquals("Kills:", killCounterLabel.getText(), "Kill counter label should have the initial text.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testUpdateKillCounter() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.initializeKillCounter();
            levelView.updateKillCounter(5, 10);
            Label killCounterLabel = levelView.getKillCounterLabel();
            assertEquals("Kills :5/10", killCounterLabel.getText(), "Kill counter label should update with the correct text.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testScreenFade() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            AtomicBoolean fadeComplete = new AtomicBoolean(false);
            levelView.screenFade(0.1, () -> fadeComplete.set(true));

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        assertTrue(fadeComplete.get(), "AfterFadeEvent should run after the fade transition.");
                        assertTrue(root.getChildren().isEmpty(), "Root should be cleared after the fade.");
                        latch.countDown();
                    });
                }
            }, 200); // Wait for fade transition duration
        });
        latch.await();
    }
}