package com.example.demo.Initialize;

import javafx.util.Duration;
import java.util.Objects;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;

public class Controller {

	private final Stage stage;

	private static MediaPlayer mediaPlayer;
	private AudioClip audioClip;
	private static double musicVolume = 0.5;
	private static double sfxVolume = 0.5;

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
		if (mediaPlayer != null) return; //if instanced, return

		Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setVolume(musicVolume);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}

	public void stopBGM() {
		if (mediaPlayer == null) return; //if null no need to stop anything

		mediaPlayer.stop();
		mediaPlayer = null;
	}

	public void pauseBGM() {
		if (mediaPlayer == null) return;
		mediaPlayer.pause();
	}

	public void resumeBGM() {
		if (mediaPlayer == null) return;
		mediaPlayer.play();
	}

	public void playSFX(String sfxPath) {
		audioClip = new AudioClip(Objects.requireNonNull(getClass().getResource(sfxPath)).toExternalForm());
		audioClip.setVolume(sfxVolume);
		audioClip.play();
	}

	public void stopSFX() {
		if (audioClip == null) return;

		audioClip.stop();
		audioClip = null;
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

	public static void setSfxVolume(double volume) {
		sfxVolume = volume;
	}

	public static double getSfxVolume() {
		return sfxVolume;
	}

	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public AudioClip getAudioClip() {
		return audioClip;
	}

}
