package com.example.demo.Controller;

import java.util.Objects;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Controller class to manage the game levels and background entities.
 */
public class AudioController {

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
	 * Constructor to initialize empty constructor.
	 */
	public AudioController() {
		// Empty
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