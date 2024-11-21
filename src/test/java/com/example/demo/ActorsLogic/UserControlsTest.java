package com.example.demo.ActorsLogic;

import com.example.demo.Actor.Plane_User;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserControlsTest {

    private Plane_User userPlane;
    private UserControls userControls;
    private Group root;
    private List<ActiveActorDestructible> projectiles;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initializes JavaFX runtime
    }

    @BeforeEach
    public void setUp() {
        // Initialize test objects
        userPlane = new Plane_User(100); // Plane with initial health of 100
        root = new Group();
        projectiles = new ArrayList<>();
        userControls = new UserControls(userPlane, root, projectiles);

        // Simulate key bindings (normally loaded from Main)
        UserControls.loadKeyBindings();
        Map<String, KeyCode> mockKeyBindings = Map.of(
                "Move_UP", KeyCode.W,
                "Move_DOWN", KeyCode.S,
                "Move_LEFT", KeyCode.A,
                "Move_RIGHT", KeyCode.D,
                "Fire", KeyCode.SPACE,
                "Pause", KeyCode.ESCAPE
        );
        UserControls.setKeyBindings(mockKeyBindings);
    }

    @Test
    public void testKeyPresses() {
        // Simulate pressing W for moving up
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false));
        assertEquals(-1, userPlane.getVerticalVelocityMultiplier(), "Plane should move up when W is pressed");

        // Simulate pressing S for moving down
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false));
        assertEquals(1, userPlane.getVerticalVelocityMultiplier(), "Plane should move down when S is pressed");

        // Simulate releasing W
        userControls.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.W, false, false, false, false));
        assertEquals(0, userPlane.getVerticalVelocityMultiplier(), "Plane should stop moving vertically when W is released");

        // Simulate releasing S
        userControls.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.S, false, false, false, false));
        assertEquals(0, userPlane.getVerticalVelocityMultiplier(), "Plane should stop moving vertically when S is released");

        // Simulate pressing D for moving right
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false));
        assertEquals(1, userPlane.getHorizontalVelocityMultiplier(), "Plane should move right when D is pressed");

        // Simulate releasing D
        userControls.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.D, false, false, false, false));
        assertEquals(0, userPlane.getHorizontalVelocityMultiplier(), "Plane should stop moving horizontally when D is released");

        // Simulate pressing A for moving left
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false));
        assertEquals(-1, userPlane.getHorizontalVelocityMultiplier(), "Plane should move left when A is pressed");

        // Simulate releasing A
        userControls.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.A, false, false, false, false));
        assertEquals(0, userPlane.getHorizontalVelocityMultiplier(), "Plane should stop moving horizontally when A is released");

        // Simulate pressing SPACE for firing
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false));
        assertEquals(1, projectiles.size(), "Firing should create a projectile");
        assertTrue(root.getChildren().contains(projectiles.get(0)), "Projectile should be added to the root node");
    }

    @Test
    public void testPauseKey() {
        // Set a mock Runnable for pause
        final boolean[] pauseTriggered = {false};
        UserControls.setPauseMenuRunback(() -> pauseTriggered[0] = true);

        // Simulate pressing ESC for pause
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        assertTrue(pauseTriggered[0], "Pause action should be triggered when ESC is pressed");
    }

}