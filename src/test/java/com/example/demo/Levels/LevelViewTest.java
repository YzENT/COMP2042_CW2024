package com.example.demo.Levels;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class LevelViewTest {

    private LevelView levelView;
    private Group root;
    private Stage stage;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
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
            assertEquals("Kills :5/10", killCounterLabel.getText(), "Kill counter label should updated with the correct text.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testInitializeTimerCounter() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.initializeTimerLabel();
            Label timerLabel = levelView.getTimerLabel();
            assertNotNull(timerLabel, "Timer label should be initialized.");
            assertTrue(root.getChildren().contains(timerLabel), "Timer label should be added to root.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testUpdateTimerCounter() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.initializeTimerLabel();
            levelView.updateTimerLabel(20);
            Label timerLabel = levelView.getTimerLabel();
            assertEquals("Time Remaining :20", timerLabel.getText(), "Timer should be updated with the correct text.");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testEntryMessage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            levelView.entryMessage("Test message");
            Label messageOnScreen = levelView.getEntryMessageLabel();
            assertNotNull(messageOnScreen, "Message label should be initialized");
            assertFalse(messageOnScreen.isVisible(), "Message label should be hidden at first");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testObjectOnScreenFade() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            AtomicBoolean fadeComplete = new AtomicBoolean(false);
            levelView.fadeObjectOnScreen(0.1, root, () -> fadeComplete.set(true));

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        assertTrue(fadeComplete.get(), "AfterFadeEvent should run after the fade transition.");
                        latch.countDown();
                    });
                }
            }, 200); // Wait for fade transition duration
        });
        latch.await();
    }
}