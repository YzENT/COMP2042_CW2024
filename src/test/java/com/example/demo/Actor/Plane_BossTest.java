package com.example.demo.Actor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class Plane_BossTest {

    private Plane_Boss boss;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initializes JavaFX runtime
    }

    @BeforeEach
    void setUp() {
        boss = new Plane_Boss();
    }

    @Test
    void takeDamage() {
        assertEquals(50, boss.getHealth());

        boss.setShielded(false);
        boss.takeDamage();
        assertEquals(49, boss.getHealth());

        boss.setShielded(true);
        boss.takeDamage();
        assertEquals(49, boss.getHealth());
    }
}