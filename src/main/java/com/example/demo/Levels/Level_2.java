package com.example.demo.Levels;

import com.example.demo.Actor.Plane_Boss;

public class Level_2 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 10;
	private Plane_Boss planeBoss;

	private static double spawnCooldown = 30;

	public Level_2(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (spawnCooldown > 0) return; //prevent error if planeBoss = null
		if (userIsDestroyed()) {
			gameStatus(GameStatus.DEFEAT);
		}
		else if (planeBoss.isDestroyed()) {
			gameStatus(GameStatus.VICTORY);
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (spawnCooldown > 0) spawnCooldown--;
		if (getCurrentNumberOfEnemies() == 0 && spawnCooldown <= 0) {
			addEnemyUnit(createBoss());
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private Plane_Boss createBoss() {
		planeBoss = new Plane_Boss();
		getRoot().getChildren().addAll(planeBoss.getShieldImage(),
				planeBoss.getHealthBar());
		planeBoss.setRemoveHealthBar(() -> getRoot().getChildren().remove(planeBoss.getHealthBar()));
		return planeBoss;
	}

}
