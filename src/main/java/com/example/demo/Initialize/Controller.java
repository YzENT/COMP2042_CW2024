package com.example.demo.Initialize;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;
import javafx.util.Duration;

public class Controller {

	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

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
