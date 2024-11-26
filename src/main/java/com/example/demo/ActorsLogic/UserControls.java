package com.example.demo.ActorsLogic;

import java.util.*;
import java.util.Map;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.example.demo.Controller.Main;
import com.example.demo.Actor.Plane.Plane_User;
import com.example.demo.Levels.LevelParent;

/**
 * Class to handle user controls for the game.
 */
public class UserControls {

    /**
     * The user-controlled plane.
     */
    private final Plane_User user;

    /**
     * The root group of the scene.
     */
    private final Group root;

    /**
     * The list of user projectiles.
     */
    private final List<ActiveActorDestructible> userProjectiles;

    /**
     * Runnable to pause the game.
     */
    private static Runnable pauseGame;

    /**
     * Map of key bindings.
     */
    private static Map<String, KeyCode> keyBindings;

    /**
     * Set of active keys.
     */
    private final Set<KeyCode> activeKeys = new HashSet<>();

    /**
     * Indicates whether the user can fire a projectile.
     */
    private static boolean userCanFire = true;

    /**
     * The rate at which the user can fire projectiles.
     */
    private static final Duration FIRE_RATE = Duration.millis(100);

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
        checkActiveKey();
    }

    /**
     * Handles key press events.
     *
     * @param e the key event
     */
    public void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        activeKeys.add(kc);
        if (kc == keyBindings.get("Pause") && pauseGame != null) pauseGame.run();
    }

    /**
     * Handles key release events.
     *
     * @param e the key event
     */
    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        activeKeys.remove(kc);
    }

    /**
     * Fires a projectile from the user-controlled plane with a specified delay between each projectile.
     */
    private void fireProjectile() {
        if (!userCanFire) return;

        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);

        // Cooldown
        userCanFire = false;
        Timeline cooldown = new Timeline(
                new KeyFrame(FIRE_RATE, e -> userCanFire = true)
        );
        cooldown.setCycleCount(1);
        cooldown.play();
    }

    /**
     * Checks the active keys and performs actions based on the keys pressed.
     */
    private void checkActiveKey() {
        Timeline keyCheck = new Timeline(new KeyFrame(Duration.millis(LevelParent.getProgramDelay()), e -> {

            // Firing
            if (activeKeys.contains(keyBindings.get("Fire"))) {
                fireProjectile();
            }

            // Movement
            if (activeKeys.contains(keyBindings.get("Move_UP"))) user.moveUp();
            if (activeKeys.contains(keyBindings.get("Move_DOWN"))) user.moveDown();
            if (activeKeys.contains(keyBindings.get("Move_RIGHT"))) user.moveForward();
            if (activeKeys.contains(keyBindings.get("Move_LEFT"))) user.moveBackward();

            // Stop movement if keys are no longer pressed
            if (!activeKeys.contains(keyBindings.get("Move_UP")) && !activeKeys.contains(keyBindings.get("Move_DOWN"))) {
                user.stopVerticalMovement();
            }
            if (!activeKeys.contains(keyBindings.get("Move_LEFT")) && !activeKeys.contains(keyBindings.get("Move_RIGHT"))) {
                user.stopHorizontalMovement();
            }

        }));
        keyCheck.setCycleCount(Animation.INDEFINITE);
        keyCheck.play();
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