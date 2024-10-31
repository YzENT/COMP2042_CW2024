package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 50;
	private static final int SHIELD_Y_POSITION = 50;
	private final Group root;
	private final ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
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
