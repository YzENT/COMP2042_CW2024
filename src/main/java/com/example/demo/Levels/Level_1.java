package com.example.demo.Levels;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.Plane_Enemy;

public class Level_1 extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Levels.Level_2";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 1;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private double spawnCooldown = 30;

    public Level_1(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			gameStatus(GameStatus.DEFEAT);
		}
		else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (spawnCooldown > 0) {
			spawnCooldown--;
			return;
		}

		int currentNumberOfEnemies = getCurrentNumberOfEnemies();

		if (currentNumberOfEnemies < TOTAL_ENEMIES && Math.random() < ENEMY_SPAWN_PROBABILITY) {
			double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
			ActiveActorDestructible newEnemy = new Plane_Enemy(getScreenWidth(), newEnemyInitialYPosition);
			addEnemyUnit(newEnemy);
			resetSpawnTimer();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	private void resetSpawnTimer() {
        spawnCooldown = Math.random() * 30;
	}
}
