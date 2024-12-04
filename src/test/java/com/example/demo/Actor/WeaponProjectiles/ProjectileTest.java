package com.example.demo.Actor.WeaponProjectiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.example.demo.JavaFXBaseTesting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(JavaFXBaseTesting.class)
class ProjectileTest {

    private Projectile enemyProjectile;
    private Projectile userProjectile;

    @BeforeEach
    void setUp() {
        enemyProjectile = new Projectile("/images/weapons/userfire.png", 0, 0, 0, 1, 0) {
            @Override
            public void updatePosition() {
                // No-op for testing
            }

            @Override
            public void updateActor() {
                // No-op for testing
            }
        };

        userProjectile = new Projectile("/images/weapons/userfire.png", 0, 0, 0, 5, 0) {
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
        enemyProjectile.takeDamage();
        assertTrue(enemyProjectile.isDestroyed());
    }

    @Test
    void takeDamageHealthHigh() {
        //if health more than 1, take damage, won't be destroyed
        userProjectile.takeDamage();
        assertFalse(userProjectile.isDestroyed());
    }
}
