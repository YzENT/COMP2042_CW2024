package com.example.demo.Actor;

import com.example.demo.Actor.Plane.Plane_User;
import com.example.demo.JavaFXBaseTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class Plane_UserTest{

    private Plane_User user;

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