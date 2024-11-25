package com.example.demo.ActorsLogic;

import com.example.demo.Actor.Plane.Plane_User;
import com.example.demo.JavaFXBaseTesting;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class UserControlsTest {

    private Plane_User userPlane;
    private UserControls userControls;
    private Group root;
    private List<ActiveActorDestructible> projectiles;

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
    public void testKeyPresses() throws InterruptedException {
        // Simulate pressing W for moving up
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false));
        Thread.sleep(200);
        assertEquals(-1, userPlane.getVerticalVelocityMultiplier(), "Plane should move up when W is pressed");

        // Simulate releasing W
        userControls.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.W, false, false, false, false));
        Thread.sleep(200);
        assertEquals(0, userPlane.getVerticalVelocityMultiplier(), "Plane should stop moving vertically when W is released");

        // Simulate pressing D for moving right
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false));
        Thread.sleep(200);
        assertEquals(1, userPlane.getHorizontalVelocityMultiplier(), "Plane should move right when D is pressed");

        // Simulate releasing D
        userControls.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.D, false, false, false, false));
        Thread.sleep(200);
        assertEquals(0, userPlane.getHorizontalVelocityMultiplier(), "Plane should stop moving horizontally when D is released");

        // Simulate pressing SPACE for firing
        userControls.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false));
        Thread.sleep(200);
        assertEquals(1, projectiles.size(), "Firing should create a projectile");
        assertTrue(root.getChildren().contains(projectiles.getFirst()), "Projectile should be added to the root node");
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