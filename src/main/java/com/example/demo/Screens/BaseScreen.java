package com.example.demo.Screens;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import com.example.demo.Initialize.Controller;

/**
 * Abstract class representing a base screen in the game.
 */
public abstract class BaseScreen {

    /**
     * The stage for the screen.
     */
    protected final Stage stage;

    /**
     * The width of the screen.
     */
    protected final int SCREEN_WIDTH;

    /**
     * The height of the screen.
     */
    protected final int SCREEN_HEIGHT;

    /**
     * Font for the arcade style.
     * Source: <a href="https://www.fontspace.com/press-start-2p-font-f11591">Link for arcade font</a>
     */
    protected static final Font arcadeFont = Font.loadFont(BaseScreen.class.getResourceAsStream("/com/example/demo/fonts/PressStart2P-vaV7.ttf"), 0);

    /**
     * The name of the font.
     */
    public static final String fontName = arcadeFont.getName();

    /**
     * The new scale for buttons when hovered.
     */
    private static final double BUTTON_SCALE_NEW = 1.2;

    /**
     * The old scale for buttons when not hovered.
     */
    private static final double BUTTON_SCALE_OLD = 1.0;

    /**
     * The duration of the transition effect.
     */
    private static final double TRANSITION_DURATION = 0.5;

    /**
     * The font size for buttons.
     */
    private static final double BUTTON_FONT_SIZE = 30;

    /**
     * The radius of the drop shadow effect for buttons.
     */
    private static final double SHADOW_RADIUS = 10;

    /**
     * The drop shadow effect for buttons.
     */
    private final DropShadow buttonShadow;

    /**
     * The controller for managing the screen.
     */
    private final Controller controller;

    /**
     * Cache for storing instances of screens.
     */
    private static final Map<Class<?>, BaseScreen> screenCache = new HashMap<>();

    /**
     * Constructor to initialize a BaseScreen object.
     *
     * @param stage the stage for the screen
     * @param SCREEN_WIDTH the width of the screen
     * @param SCREEN_HEIGHT the height of the screen
     */
    public BaseScreen(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.stage = stage;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.buttonShadow = createButtonShadow();
        this.controller = new Controller(stage);
    }

    /**
     * Abstract method to show the screen.
     */
    protected abstract void show();

    /**
     * Initializes the title text with a color transition.
     *
     * @param TITLE_TEXT the text of the title
     * @param TITLE_SIZE the size of the title text
     * @return the initialized title text
     */
    protected Text initializeTitle(String TITLE_TEXT, double TITLE_SIZE) {
        Text title = new Text(TITLE_TEXT);
        title.setFont(arcadeFont);
        title.setStyle("-fx-font-size: " + TITLE_SIZE + "px;");

        // Color transition
        FillTransition fillTransition = new FillTransition(Duration.seconds(1), title);
        fillTransition.setFromValue(Color.RED);
        fillTransition.setToValue(Color.MEDIUMVIOLETRED);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.play();

        return title;
    }

    /**
     * Abstract method to initialize buttons.
     *
     * @return an array of initialized buttons
     */
    protected abstract Button[] initializeButtons();

    /**
     * Creates a button with the specified text and action.
     *
     * @param text the text of the button
     * @param action the action to perform when the button is clicked
     * @return the created button
     */
    protected Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: " + BUTTON_FONT_SIZE + "px; " +
                        "-fx-font-family: '" + fontName + "'; "
        );
        button.setOnAction(e -> {
            action.run();

            //https://github.com/YzENT/Auntys-Kopitiam/blob/main/assets/SharedAssets/MouseClick.mp3
            playSFX("/com/example/demo/audio/sfx/MouseClick.mp3");
        });
        setupFocusListener(button);
        return button;
    }

    /**
     * Sets up a focus listener for the specified node.
     *
     * @param node the node to set up the focus listener for
     */
    protected void setupFocusListener(Node node) {
        node.focusedProperty().addListener((focusProperty, wasFocused, isFocused) -> {
            if (isFocused) {
                addEffect(node);
            } else {
                removeEffect(node);
            }
        });
    }

    /**
     * Adds a scaling effect to the specified node.
     *
     * @param node the node to add the effect to
     */
    protected void addEffect(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(TRANSITION_DURATION), node);
        st.setToX(BUTTON_SCALE_NEW);
        st.setToY(BUTTON_SCALE_NEW);
        st.setAutoReverse(true);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.play();

        node.setEffect(buttonShadow);
        node.setUserData(st);
    }

    /**
     * Removes the scaling effect from the specified node.
     *
     * @param node the node to remove the effect from
     */
    protected void removeEffect(Node node) {
        ScaleTransition st = (ScaleTransition) node.getUserData();
        if (st != null) {
            st.stop();
        }
        node.setScaleX(BUTTON_SCALE_OLD);
        node.setScaleY(BUTTON_SCALE_OLD);
        node.setEffect(null);
        node.setUserData(null); // Clear user data to avoid leaks
    }

    /**
     * Plays background music.
     *
     * @param musicPath the path to the music file
     */
    protected void playBGM(String musicPath) {
        controller.playBGM(musicPath);
    }

    /**
     * Stops the background music.
     */
    protected void stopBGM() {
        controller.stopBGM();
    }

    /**
     * Pauses the background music.
     */
    protected void pauseBGM() {
        controller.pauseBGM();
    }

    /**
     * Resumes the background music.
     */
    protected void resumeBGM() {
        controller.resumeBGM();
    }

    /**
     * Plays a sound effect.
     *
     * @param sfxPath the path to the sound effect file
     */
    protected void playSFX(String sfxPath) {
        controller.playSFX(sfxPath);
    }

    /**
     * Stops the sound effect.
     */
    protected void stopSFX() {
        controller.stopSFX();
    }

    /**
     * Navigates to the specified screen.
     *
     * @param screenClass the class of the screen to navigate to
     */
    protected void goScreen(Class<?> screenClass) {
        try {
            BaseScreen screen = screenCache.get(screenClass);
            if (screen == null) {
                screen = (BaseScreen) screenClass.getConstructor(Stage.class, int.class, int.class)
                        .newInstance(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
                screenCache.put(screenClass, screen);
            }
            screen.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    /**
     * Navigates to the previous screen.
     *
     * @param previousClass the class name of the previous screen
     */
    protected void goScreenPrevious(String previousClass) {
        try {
            Class<?> goPrevious = Class.forName(previousClass);
            goScreen(goPrevious);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    /**
     * Starts the specified level.
     *
     * @param levelClassName the class name of the level to start
     */
    protected void startLevel(String levelClassName) {
        try {
            controller.goToLevel(levelClassName);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

    /**
     * Creates a drop shadow effect for buttons.
     *
     * @return the created drop shadow effect
     */
    protected DropShadow createButtonShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(SHADOW_RADIUS);
        return shadow;
    }

    /**
     * Disables mouse input for the specified scene.
     *
     * @param scene the scene to disable mouse input for
     */
    protected void disableMouseInput(Scene scene) {
        scene.addEventFilter(MouseEvent.ANY, Event::consume);
    }

    /**
     * Configures scene to allow WASD keys to mimic arrow keys globally.
     *
     * @param scene the Scene to configure
     */
    public void enableWASDNavigation(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            KeyEvent newEvent = null;

            switch (code) {
                case W -> newEvent = new KeyEvent(
                        event.getEventType(), event.getCharacter(), event.getText(), KeyCode.UP,
                        event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown()
                );
                case A -> newEvent = new KeyEvent(
                        event.getEventType(), event.getCharacter(), event.getText(), KeyCode.LEFT,
                        event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown()
                );
                case S -> newEvent = new KeyEvent(
                        event.getEventType(), event.getCharacter(), event.getText(), KeyCode.DOWN,
                        event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown()
                );
                case D -> newEvent = new KeyEvent(
                        event.getEventType(), event.getCharacter(), event.getText(), KeyCode.RIGHT,
                        event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown()
                );
            }

            if (newEvent != null) {
                event.consume(); // Consume the original WASD key press

                // Send the new event to the currently focused node
                if (scene.getFocusOwner() != null) {
                    scene.getFocusOwner().fireEvent(newEvent);
                }
            }

        });
    }
}