package com.example.demo.ActorsLogic;

import java.util.*;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.example.demo.Initialize.Main;
import com.example.demo.Actor.Plane.Plane_User;
import javafx.util.Duration;

/**
 * Class to handle user controls for the game.
 */
public class UserControls {

    private final Plane_User user;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;
    private static Runnable pauseGame;
    private static Map<String, KeyCode> keyBindings;
    private static boolean canFire = true;
    private static final Duration FIRE_RATE = Duration.millis(300);

    /**
     * Constructor to initialize UserControls.
     *
     * @param user the user-controlled plane
     * @param root the root group of the scene
     * @param userProjectiles the list of user projectiles
     */
    public UserControls(Plane_User user, Group root, List<ActiveActorDestructible> userProjectiles) {
        this.user = user;
        this.root = root;
        this.userProjectiles = userProjectiles;
        loadKeyBindings();
    }

    /**
     * Handles key press events.
     *
     * @param e the key event
     */
    public void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == keyBindings.get("Move_UP")) user.moveUp();
        if (kc == keyBindings.get("Move_DOWN")) user.moveDown();
        if (kc == keyBindings.get("Move_RIGHT")) user.moveForward();
        if (kc == keyBindings.get("Move_LEFT")) user.moveBackward();
        if (kc == keyBindings.get("Fire")) fireProjectile();
        if (kc == keyBindings.get("Pause") && pauseGame != null) pauseGame.run();
    }

    /**
     * Handles key release events.
     *
     * @param e the key event
     */
    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == keyBindings.get("Move_UP") || kc == keyBindings.get("Move_DOWN")) user.stopVerticalMovement();
        if (kc == keyBindings.get("Move_LEFT") || kc == keyBindings.get("Move_RIGHT")) user.stopHorizontalMovement();
    }

    /**
     * Fires a projectile from the user-controlled plane with a 300-millisecond delay between each projectile.
     */
    private void fireProjectile() {
        if (!canFire) return;

        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);

        // Cooldown
        canFire = false;
        Timeline cooldown = new Timeline(
                new KeyFrame(FIRE_RATE, e -> canFire = true)
        );
        cooldown.setCycleCount(1);
        cooldown.play();
    }

    /**
     * Sets the runnable to show the pause menu.
     *
     * @param showPauseMenu the runnable to show the pause menu
     */
    public static void setPauseMenuRunback(Runnable showPauseMenu) {
        pauseGame = showPauseMenu;
    }

    /**
     * Loads the key bindings from the main configuration.
     */
    public static void loadKeyBindings() {
        keyBindings = Main.getKeyBindings();
    }

    /**
     * Sets the key bindings.
     *
     * @param keyBindings the key bindings map
     */
    public static void setKeyBindings(Map<String, KeyCode> keyBindings) {
        UserControls.keyBindings = keyBindings;
    }
}