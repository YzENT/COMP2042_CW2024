package com.example.demo.Levels;

import com.example.demo.Boss;

public class Level_2 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private Boss boss;
	private LevelEntities levelEntities;

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
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			boss = new Boss(this.levelEntities);
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelEntities instantiateLevelView() {
		this.levelEntities = new LevelEntities(getRoot(), PLAYER_INITIAL_HEALTH);
		return this.levelEntities;
	}

}
