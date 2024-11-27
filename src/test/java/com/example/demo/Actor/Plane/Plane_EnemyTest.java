package com.example.demo.Actor.Plane;

import com.example.demo.JavaFXBaseTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class Plane_EnemyTest {

    private Plane_Enemy enemy;

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