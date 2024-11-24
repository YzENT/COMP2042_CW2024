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

/**
 * Controller class to manage the game levels and background entities.
 */
public class Controller {

	/**
	 * The primary stage for this application.
	 */
	private final Stage stage;

	/**
	 * The media player for background music.
	 */
	private static MediaPlayer mediaPlayer;

	/**
	 * The audio clip for sound effects.
	 */
	private AudioClip audioClip;

	/**
	 * The volume level for background music (value should be between 0.0 to 1.0).
	 */
	private static double musicVolume = 0.5;

	/**
	 * The volume level for sound effects (value should be between 0.0 to 1.0).
	 */
	private static double sfxVolume = 0.5;

	/**
	 * Constructor to initialize the Controller with a Stage.
	 *
	 * @param stage the primary stage for this application
	 */
	public Controller(Stage stage) {
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

	/**
	 * Plays background music from the specified file path.
	 *
	 * @param musicPath the path to the music file
	 */
	public void playBGM(String musicPath) {
		if (mediaPlayer != null) return; //if instanced, return

		Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setVolume(musicVolume);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}

	/**
	 * Stops the background music if it is playing.
	 */
	public void stopBGM() {
		if (mediaPlayer == null) return; //if null no need to stop anything

		mediaPlayer.stop();
		mediaPlayer = null;
	}

	/**
	 * Pauses the background music if it is playing.
	 */
	public void pauseBGM() {
		if (mediaPlayer == null) return;
		mediaPlayer.pause();
	}

	/**
	 * Resumes the background music if it is paused.
	 */
	public void resumeBGM() {
		if (mediaPlayer == null) return;
		mediaPlayer.play();
	}

	/**
	 * Plays a sound effect from the specified file path.
	 *
	 * @param sfxPath the path to the sound effect file
	 */
	public void playSFX(String sfxPath) {
		audioClip = new AudioClip(Objects.requireNonNull(getClass().getResource(sfxPath)).toExternalForm());
		audioClip.setVolume(sfxVolume);
		audioClip.play();
	}

	/**
	 * Stops the sound effect if it is playing.
	 */
	public void stopSFX() {
		if (audioClip == null) return;

		audioClip.stop();
		audioClip = null;
	}

	/**
	 * Sets the volume for the background music.
	 *
	 * @param volume the volume level (value should be between 0.0 to 1.0)
	 */
	public static void setMusicVolume(double volume) {
		musicVolume = volume;
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volume);
		}
	}

	/**
	 * Gets the current volume level for the background music.
	 *
	 * @return the current music volume level
	 */
	public static double getMusicVolume() {
		return musicVolume;
	}

	/**
	 * Sets the volume for the sound effects.
	 *
	 * @param volume the volume level (0.0 to 1.0)
	 */
	public static void setSfxVolume(double volume) {
		sfxVolume = volume;
	}

	/**
	 * Gets the current volume level for the sound effects.
	 *
	 * @return the current sound effects volume level
	 */
	public static double getSfxVolume() {
		return sfxVolume;
	}

	/**
	 * Gets the current MediaPlayer instance.
	 *
	 * @return the current MediaPlayer instance
	 */
	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	/**
	 * Gets the current AudioClip instance.
	 *
	 * @return the current AudioClip instance
	 */
	public AudioClip getAudioClip() {
		return audioClip;
	}

}