package com.example.demo.Levels;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
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
	 * Label to display the entry message
	 */
	private Label entryMessageLabel;

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
			killCounterLabel.setText(null);
		}
	}

	/**
	 * Initializes the timer counter.
	 */
	public void initializeTimerLabel() {
		timerLabel = new Label();
		timerLabel.setLayoutY(10);
		timerLabel.setStyle("-fx-font-family: '" + fontName + "'; " +
				"-fx-font-size: 20px; " +
				"-fx-text-fill: black;");
		root.getChildren().add(timerLabel);
	}

	/**
	 * Updates the timer label based on time remaining.
	 *
	 * @param secondsRemaining the time needed to survive in seconds
	 */
	public void updateTimerLabel(int secondsRemaining) {
		timerLabel.setText("Time Remaining :" + secondsRemaining);

		DoubleBinding centeredX = Bindings.createDoubleBinding(() ->
						(Main.getScreenWidth() - timerLabel.getWidth()) / 2,
				timerLabel.widthProperty());

		timerLabel.layoutXProperty().bind(centeredX);
	}

	/**
	 * Displays an entry message on the screen.
	 *
	 * @param message the message to display
	 */
	public void entryMessage(String message) {
		entryMessageLabel = new Label(message);
		entryMessageLabel.setStyle("-fx-font-family: '" + fontName + "'; " +
				"-fx-font-size: 60px; " +
				"-fx-text-fill: white;" +
				"-fx-background-color: black");
		entryMessageLabel.setVisible(false);
		root.getChildren().add(entryMessageLabel);

		DoubleBinding centeredX = Bindings.createDoubleBinding(() ->
						(Main.getScreenWidth() - entryMessageLabel.getWidth()) / 2,
				entryMessageLabel.widthProperty());

		DoubleBinding centeredY = Bindings.createDoubleBinding(() ->
						(Main.getScreenHeight() - entryMessageLabel.getHeight()) / 2,
				entryMessageLabel.heightProperty());

		entryMessageLabel.layoutXProperty().bind(centeredX);
		entryMessageLabel.layoutYProperty().bind(centeredY);

		PauseTransition waitMessageAppear = new PauseTransition(Duration.seconds(1));
		waitMessageAppear.setOnFinished(e -> {
			entryMessageLabel.setVisible(true);
			// should play audio (future)

			PauseTransition showMessage = new PauseTransition(Duration.seconds(1.5));
			showMessage.setOnFinished(ev -> {
				fadeObjectOnScreen(2.0, entryMessageLabel, () -> root.getChildren().remove(entryMessageLabel));
			});
			showMessage.play();

		});
		waitMessageAppear.play();
	}

	/**
	 * Fades the object on screen and executes an event after the fade transition.
	 *
	 * @param transition_Time the duration of the fade transition
	 * @param node the node that the transition needs to be applied to
	 * @param afterFadeEvent the event to execute after the fade transition
	 */
	public void fadeObjectOnScreen(double transition_Time, Node node, Runnable afterFadeEvent) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(transition_Time), node);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setOnFinished(event -> afterFadeEvent.run());
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

	/**
	 * Gets the timer label.
	 *
	 * @return the timer label
	 */
	public Label getTimerLabel() {
		return timerLabel;
	}

	/**
	 * Gets the entry message label.
	 *
	 * @return the custom message label
	 */
	public Label getEntryMessageLabel() {
		return entryMessageLabel;
	}
}