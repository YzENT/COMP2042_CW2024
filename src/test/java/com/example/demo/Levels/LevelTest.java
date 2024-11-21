package com.example.demo.Levels;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class LevelTest {

    private Level_1 level1;
    private Level_2 level2;
    private Level_3 level3;
    private Stage stage;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            level1 = new Level_1(750, 1300);
            level2 = new Level_2(750, 1300);
            level3 = new Level_3(750, 1300);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testLevel1Initialization() {
        assertNotNull(level1, "Level 1 should be initialized");
        assertEquals(5, level1.getUser().getHealth(), "Player initial health should be 5");
    }

    @Test
    void testLevel2Initialization() {
        assertNotNull(level2, "Level 2 should be initialized");
        assertEquals(5, level2.getUser().getHealth(), "Player initial health should be 5");
    }

    @Test
    void testLevel3Initialization() {
        assertNotNull(level3, "Level 3 should be initialized");
        assertEquals(5, level3.getUser().getHealth(), "Player initial health should be 5");
    }

    @Test
    void testLevel1GameOver() {
        for (int i = 0; i < 5; i++) {
            level1.getUser().takeDamage();
        }
        level1.checkIfGameOver();
        assertTrue(level1.userIsDestroyed(), "User should be destroyed after taking 5 damage");
    }

    @Test
    void testLevel2GameOver() {
        for (int i = 0; i < 5; i++) {
            level2.getUser().takeDamage();
        }
        level2.checkIfGameOver();
        assertTrue(level2.userIsDestroyed(), "User should be destroyed after taking 5 damage");
    }

    @Test
    void testLevel3GameOver() {
        for (int i = 0; i < 5; i++) {
            level3.getUser().takeDamage();
        }
        level3.checkIfGameOver();
        assertTrue(level3.userIsDestroyed(), "User should be destroyed after taking 5 damage");
    }

    @Test
    void testLevel1AdvanceToNextLevel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            for (int i = 0; i < 5; i++) {
                level1.getUser().incrementKillCount();
            }
            level1.checkIfGameOver();
            assertTrue(level1.userHasReachedKillTarget(), "User should have reached the kill target to advance to the next level");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testLevel2AdvanceToNextLevel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            for (int i = 0; i < 20; i++) {
                level2.getUser().incrementKillCount();
            }
            level2.checkIfGameOver();
            assertTrue(level2.userHasReachedKillTarget(), "User should have reached the kill target to advance to the next level");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testLevel3AdvanceToNextLevel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            for (int i = 0; i < 1; i++) {
                level3.getUser().incrementKillCount();
            }
            level3.checkIfGameOver();
            assertTrue(level3.userHasReachedKillTarget(), "User should have reached the kill target to advance to the next level");
            latch.countDown();
        });
        latch.await();
    }
}