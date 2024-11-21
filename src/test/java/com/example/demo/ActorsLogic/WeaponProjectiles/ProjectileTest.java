package com.example.demo.ActorsLogic.WeaponProjectiles;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectileTest {

    private Projectile proj1;
    private Projectile proj2;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initializes JavaFX runtime
    }

    @BeforeEach
    void setUp() {
        proj1 = new Projectile("/com/example/demo/images/weapons/userfire.png", 0, 0, 0, 1, 0) {
            @Override
            public void updatePosition() {
                // No-op for testing
            }

            @Override
            public void updateActor() {
                // No-op for testing
            }
        };

        proj2 = new Projectile("/com/example/demo/images/weapons/userfire.png", 0, 0, 0, 5, 0) {
            @Override
            public void updatePosition() {
                // No-op for testing
            }

            @Override
            public void updateActor() {
                // No-op for testing
            }
        };
    }

    @Test
    void takeDamageHealthLow() {
        //if health at 1, take damage, will be destroyed
        proj1.takeDamage();
        assertTrue(proj1.isDestroyed());
    }

    @Test
    void takeDamageHealthHigh() {
        //if health more than 1, take damage, won't be destroyed
        proj2.takeDamage();
        assertFalse(proj2.isDestroyed());
    }
}