package com.example.demo.Actor;

import com.example.demo.JavaFXBaseTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(JavaFXBaseTesting.class)
class Plane_BossTest {

    private Plane_Boss boss;

    @BeforeEach
    void setUp() {
        boss = new Plane_Boss();
    }

    @Test
    void takeDamage() {
        assertEquals(50, boss.getHealth());

        boss.setShielded(false);
        boss.takeDamage();
        assertEquals(49, boss.getHealth(), "Boss' health should reduce by 1");

        boss.setShielded(true);
        boss.takeDamage();
        assertEquals(49, boss.getHealth(), "Boss' health should not reduce as shield is active");
    }
}