package com.example.demo.Levels;

import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import com.example.demo.ImageEntities.HeartDisplay;
import com.example.demo.Controller.Main;
import static com.example.demo.Screens.BaseScreen.fontName;

/**
 * Class representing the view for a level in the game.
 */
public class LevelView {

	/**
	 * X position for the heart display
	 */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/**
	 * Y position for the heart display
	 */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/**
	 * Root group of the scene
	 */
	private final Group root;

	/**
	 * Heart display object
	 */
	private final HeartDisplay heartDisplay;

	/**
	 * Label to display the kill counter
	 */
	private Label killCounterLabel;

	/**
	 * Label to display the timer counter
	 */
	private Label timerLabel;

	/**
	 * Constructor to initialize the LevelView.
	 *
	 * @param root the root group of the scene
	 * @param heartsToDisplay the number of hearts to display
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}

	/**
	 * Displays the heart display on the screen.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Updates the heart display to show the remaining hearts.
	 *
	 * @param heartsRemaining the number of hearts remaining
	 */
	public void displayHeartRemaining(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Initializes the kill counter label.
	 */
	public void initializeKillCounter() {
		killCounterLabel = new Label();
		killCounterLabel.setLayoutX(Main.getScreenWidth() - 300);
		killCounterLabel.setLayoutY(10);
		killCounterLabel.setStyle("-fx-font-family: '" + fontName + "'; " +
				"-fx-font-size: 20px; " +
				"-fx-text-fill: black;");
		root.getChildren().add(killCounterLabel);
	}

	/**
	 * Updates the kill counter label with the current number of kills.
	 *
	 * @param kills the current number of kills
	 * @param killRequired the number of kills required to advance
	 */
	public void updateKillCounter(int kills, int killRequired) {
		if (killRequired != 0) {
			killCounterLabel.setText("Kills :" + kills + "/" + killRequired);
		} else {
			killCounterLabel.setText("Kills :" + kills);
		}
	}

	public void initializeTimerLabel() {
		timerLabel = new Label();
		timerLabel.setLayoutX(500);
		timerLabel.setLayoutY(10);
		timerLabel.setStyle("-fx-font-family: '" + fontName + "'; " +
				"-fx-font-size: 20px; " +
				"-fx-text-fill: black;");
		root.getChildren().add(timerLabel);
	}

	public void updateTimerLabel(int secondsRemaining) {
		timerLabel.setText("Time Remaining :" + secondsRemaining);
	}

	/**
	 * Fades the screen and executes an event after the fade transition.
	 *
	 * @param transition_Time the duration of the fade transition
	 * @param afterFadeEvent the event to execute after the fade transition
	 */
	public void screenFade(double transition_Time, Runnable afterFadeEvent) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(transition_Time), root);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setOnFinished(event -> {
			root.getChildren().clear();
			afterFadeEvent.run();
		});
		fadeTransition.play();
	}

	/**
	 * Gets the heart display.
	 *
	 * @return the heart display
	 */
	public HeartDisplay getHeartDisplay() {
		return heartDisplay;
	}

	/**
	 * Gets the kill counter label.
	 *
	 * @return the kill counter label
	 */
	public Label getKillCounterLabel() {
		return killCounterLabel;
	}
}