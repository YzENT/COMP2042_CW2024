package com.example.demo.ActorsLogic;

import java.util.*;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.example.demo.Initialize.Main;
import com.example.demo.Actor.Plane_User;

public class UserControls {

    private final Plane_User user;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;
    private static Runnable pauseGame;
    private static Map<String, KeyCode> keyBindings;

    public UserControls(Plane_User user, Group root, List<ActiveActorDestructible> userProjectiles) {
        this.user = user;
        this.root = root;
        this.userProjectiles = userProjectiles;
        loadKeyBindings();
    }

    public void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == keyBindings.get("Move_UP")) user.moveUp();
        if (kc == keyBindings.get("Move_DOWN")) user.moveDown();
        if (kc == keyBindings.get("Move_RIGHT")) user.moveForward();
        if (kc == keyBindings.get("Move_LEFT")) user.moveBackward();
        if (kc == keyBindings.get("Fire")) fireProjectile();
        if (kc == keyBindings.get("Pause") && pauseGame != null) pauseGame.run();
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == keyBindings.get("Move_UP") || kc == keyBindings.get("Move_DOWN")) user.stopVerticalMovement();
        if (kc == keyBindings.get("Move_LEFT") || kc == keyBindings.get("Move_RIGHT")) user.stopHorizontalMovement();
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    public static void setPauseMenuRunback(Runnable showPauseMenu) {
        pauseGame = showPauseMenu;
    }

    public static void loadKeyBindings() {
        keyBindings = Main.getKeyBindings();
    }
}
