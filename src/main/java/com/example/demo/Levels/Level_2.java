package com.example.demo.Levels;

import com.example.demo.Actor.Plane_Boss;

public class Level_2 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private Plane_Boss planeBoss;
	private LevelView levelView;

	public Level_2(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (planeBoss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			planeBoss = new Plane_Boss(this.levelView);
			addEnemyUnit(planeBoss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		this.levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
		return this.levelView;
	}

}
