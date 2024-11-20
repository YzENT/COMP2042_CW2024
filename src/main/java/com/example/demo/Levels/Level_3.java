package com.example.demo.Levels;

import com.example.demo.Actor.Plane_Boss;

public class Level_3 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/level3.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private Plane_Boss planeBoss;

	private static double spawnCooldown = 30;

	public Level_3(double screenHeight, double screenWidth) {
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
		updateKillCounter();
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

	private void updateKillCounter() {
		getLevelView().updateKillCounter(getUser().getNumberOfKills(), 1);
	}

	private Plane_Boss createBoss() {
		planeBoss = new Plane_Boss();
		getRoot().getChildren().addAll(planeBoss.getShieldImage(),
				planeBoss.getHealthBar(),
				planeBoss.getExplosionImage());
		planeBoss.setRemoveHealthBar(() -> getRoot().getChildren().remove(planeBoss.getHealthBar()));
		return planeBoss;
	}

}
