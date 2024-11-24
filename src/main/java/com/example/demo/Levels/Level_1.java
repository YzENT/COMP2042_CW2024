package com.example.demo.Levels;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.Plane.Plane_Enemy;

/**
 * Class representing the first level of the game.
 */
public class Level_1 extends LevelParent {

	/**
     * The name of the background image for level 1.
     * Source: <a href="https://craftpix.net/freebies/free-futuristic-city-pixel-art-backgrounds/">Link to background</a>
     */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/level1.png";

	/**
	 * The class name of the next level.
	 */
	private static final String NEXT_LEVEL = "com.example.demo.Levels.Level_2";

	/**
	 * The total number of enemies in level 1.
	 */
	private static final int TOTAL_ENEMIES = 5;

	/**
	 * The number of kills required to advance to the next level.
	 */
	private static final int KILLS_TO_ADVANCE = 5;

	/**
	 * The probability of an enemy spawning.
	 */
	private static final double ENEMY_SPAWN_PROBABILITY = .20;

	/**
	 * The initial health of the player.
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * The fire rate of the enemy.
	 */
	private static final double ENEMY_FIRE_RATE = .01;

	/**
	 * The cooldown period for spawning enemies.
	 */
	private double spawnCooldown = 30;

	/**
	 * Constructor to initialize Level_1.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
	 */
	public Level_1(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
	}

	/**
	 * Checks if the game is over by evaluating the player's status.
	 * If the player is destroyed, the game status is set to defeat.
	 * If the player has reached the kill target, the game advances to the next level.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			gameStatus(GameStatus.DEFEAT);
			return;
		}
		if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units based on the spawn cooldown and probability.
	 * If the cooldown is greater than zero, it is decremented.
	 * If the current number of enemies is less than the total allowed and the random probability is met, a new enemy is spawned.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (spawnCooldown > 0) {
			spawnCooldown--;
			return;
		}

		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		if (currentNumberOfEnemies < TOTAL_ENEMIES && Math.random() < ENEMY_SPAWN_PROBABILITY) {
			double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
			ActiveActorDestructible newEnemy = new Plane_Enemy(getScreenWidth(), newEnemyInitialYPosition, ENEMY_FIRE_RATE);
			addEnemyUnit(newEnemy);
			resetSpawnTimer();
		}
	}

	/**
	 * Instantiates the view for the level.
	 *
	 * @return the level view
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Resets the spawn timer to a random value (0.0 - 30.0).
	 */
	private void resetSpawnTimer() {
		spawnCooldown = Math.random() * 30;
	}
}