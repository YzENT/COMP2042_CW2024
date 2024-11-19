package com.example.demo.Levels;

import com.example.demo.ImageEntities.HeartDisplay;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private final Group root;
	private final HeartDisplay heartDisplay;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}
	
	public void displayHeartRemaining(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

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

}
