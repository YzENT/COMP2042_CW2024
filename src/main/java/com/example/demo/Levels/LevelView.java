package com.example.demo.Levels;

import com.example.demo.GameOverImage;
import com.example.demo.HeartDisplay;
import com.example.demo.ShieldImage;
import com.example.demo.WinImage;
import javafx.scene.Group;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	private static final int SHIELD_X_POSITION = 50;
	private static final int SHIELD_Y_POSITION = 50;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final ShieldImage shieldImage;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
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
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void showShield() {

		// shieldImage only added to root if it does not exist
		// this is to ensure it does not render behind the background as the background is always rendered first
		if (!root.getChildren().contains(shieldImage)){
			root.getChildren().add(shieldImage);
		}

		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

}
