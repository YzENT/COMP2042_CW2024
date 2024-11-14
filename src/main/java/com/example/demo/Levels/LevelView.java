package com.example.demo.Levels;

import com.example.demo.ImageEntities.GameOverImage;
import com.example.demo.ImageEntities.HeartDisplay;
import com.example.demo.ImageEntities.ShieldImage;
import com.example.demo.ImageEntities.WinImage;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private ShieldImage shieldImage;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
		gameOverImage.showGameOverImage();

	}
	
	public void displayHeartRemaining(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void showShield(double bossX, double bossY) {

		// shieldImage only created if it does not exist
		// this is to ensure it does not render behind the background as the background is always rendered first
		if (!root.getChildren().contains(shieldImage)){
			this.shieldImage = new ShieldImage();
			root.getChildren().add(shieldImage);
		}

		// dynamic shield coordinates
		shieldImage.setLayoutX(bossX);
		shieldImage.setLayoutY(bossY + shieldImage.getFitHeight()/4);
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void screenFade(double transition_Time, Runnable afterFadeEvent) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(transition_Time), root);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setOnFinished(event -> {
			root.getChildren().clear();
			root.setOpacity(1.0); //reset opacity
			afterFadeEvent.run();
		});
		fadeTransition.play();
	}

}
