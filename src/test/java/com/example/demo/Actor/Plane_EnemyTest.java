package com.example.demo.Actor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Plane_EnemyTest {

    private Plane_Enemy enemy;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initializes JavaFX runtime
    }

    @BeforeEach
    void setUp() {
        enemy = new Plane_Enemy(0, 0, 0.3);
    }

    @Test
    void takeDamage() {
        assertEquals(1, enemy.getHealth());
        enemy.takeDamage();
        assertEquals(0, enemy.getHealth(), "Enemy's health should be 0 after taking damage");
        assertTrue(enemy.isDestroyed(), "Enemy's should be destroyed when health is 0");
    }

}