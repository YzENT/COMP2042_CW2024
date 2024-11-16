package com.example.demo.ActorsLogic;

import com.example.demo.Actor.Plane_User;
import com.example.demo.Initialize.Main;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.*;
import java.util.Map;

public class UserControls {

    private final Plane_User user;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;
    private static Runnable pauseGame;
    private Map<String, KeyCode> keyBindings;

    public UserControls(Plane_User user, Group root, List<ActiveActorDestructible> userProjectiles) {
        this.user = user;
        this.root = root;
        this.userProjectiles = userProjectiles;
        loadKeyBindings();
    }

    public void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == keyBindings.get("moveUp")) user.moveUp();
        if (kc == keyBindings.get("moveDown")) user.moveDown();
        if (kc == keyBindings.get("moveRight")) user.moveForward();
        if (kc == keyBindings.get("moveLeft")) user.moveBackward();
        if (kc == keyBindings.get("fire")) fireProjectile();
        if (kc == keyBindings.get("pause") && pauseGame != null) pauseGame.run();
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == keyBindings.get("moveUp") || kc == keyBindings.get("moveDown")) user.stopVerticalMovement();
        if (kc == keyBindings.get("moveLeft") || kc == keyBindings.get("moveRight")) user.stopHorizontalMovement();
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    public static void setPauseMenuRunback(Runnable showPauseMenu) {
        pauseGame = showPauseMenu;
    }

    private void loadKeyBindings() {
        keyBindings = Main.getKeyBindings();
    }
}
