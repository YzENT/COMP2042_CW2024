package com.example.demo.ActorsLogic;

import com.example.demo.Actor.Plane_User;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.Map;
import java.io.IOException;

public class UserControls {

    private final Plane_User user;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;
    private static Runnable pauseGame;
    private Map<String, KeyCode> keyBindings;
    private static final String config_Path = "/com/example/demo/keyConfigs.properties";

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
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getResourceAsStream(config_Path)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Key configuration file not found.");
            }
            properties.load(inputStream);

            keyBindings = properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> (String) e.getKey(),
                            e -> KeyCode.valueOf(((String) e.getValue()).toUpperCase())
                    ));
        } catch (IOException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error loading key bindings: " + e.getMessage());
            alert.show();
            alert.setOnCloseRequest(event -> System.exit(-1));
        }
    }
}
