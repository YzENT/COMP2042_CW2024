package com.example.demo.Initialize;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;
import javafx.util.Duration;

public class Controller {

	private final Stage stage;

	private static MediaPlayer mediaPlayer;
	private static double musicVolume = 0.5;
//	private static double sfxVolume = 0.5;

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

	public void playBGM(String musicPath) {
		if (mediaPlayer == null) {
			Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setVolume(musicVolume);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();
		}
	}

	public void stopBGM() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}

	public static void setMusicVolume(double volume) {
		musicVolume = volume;
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volume);
		}
	}

	public static double getMusicVolume() {
		return musicVolume;
	}

}
