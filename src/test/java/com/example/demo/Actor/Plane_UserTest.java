package com.example.demo.Actor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Plane_UserTest {

    private Plane_User user;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initializes JavaFX runtime
    }

    @BeforeEach
    void setUp() {
        user = new Plane_User(5);
    }

    @Test
    void takeDamage() {
        user.takeDamage();
        assertEquals(4, user.getHealth(), "User's health should reduce by 1 after taking damage");
    }

    @Test
    void killCount() {
        user.incrementKillCount();
        assertEquals(1, user.getNumberOfKills(), "User's kill count should increase by 1 after killing an enemy");
    }

}