package com.example.demo.Controller;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import com.example.demo.Levels.LevelParent;

/**
 * Controller class to start/move on the game's level.
 */
public class LevelController {

    /**
     * The primary stage for this application.
     */
    private final Stage stage;

    /**
     * Constructor to initialize the Controller with a Stage.
     *
     * @param stage the primary stage for this application
     */
    public LevelController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Loads and transitions to a specified game level.
     *
     * @param className the fully qualified name of the level class
     * @throws ClassNotFoundException if the class cannot be located
     * @throws NoSuchMethodException if the constructor cannot be found
     * @throws SecurityException if a security violation occurs
     * @throws InstantiationException if the class cannot be instantiated
     * @throws IllegalAccessException if the constructor is inaccessible
     * @throws IllegalArgumentException if the constructor arguments are invalid
     * @throws InvocationTargetException if the constructor throws an exception
     */
    public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
        Scene scene = myLevel.initializeScene();
        stage.setScene(scene);
        myLevel.startGame();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), scene.getRoot());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}