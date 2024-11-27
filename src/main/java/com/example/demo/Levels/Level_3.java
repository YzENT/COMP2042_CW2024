package com.example.demo.Levels;

import com.example.demo.Actor.Plane.Plane_Boss;

/**
 * Class representing the third level of the game.
 */
public class Level_3 extends LevelParent {

	/**
	 * The name of the background image for level 3.
	 * Source: <a href="https://craftpix.net/freebies/free-futuristic-city-pixel-art-backgrounds/">Link to background</a>
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/level3.png";

	/**
	 * The class name of the next level.
	 */
	private static final String NEXT_LEVEL = "com.example.demo.Levels.Level_4";

	/**
	 * The custom message to appear on screen when user first enters level.
	 */
	private static final String MESSAGE_ON_SCREEN = "Defeat the Boss";

	/**
	 * The initial health of the player.
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * The boss plane for this level.
	 */
	private Plane_Boss planeBoss;

	/**
	 * The cooldown period for spawning enemies.
	 */
	private static double spawnCooldown = 30;

	/**
	 * Constructor to initialize Level_3.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
	 */
	public Level_3(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, 0, MESSAGE_ON_SCREEN);
	}

	/**
	 * Checks if the game is over by evaluating the player's and boss's status.
	 * If the player is destroyed, the game status is set to defeat.
	 * If the boss is destroyed, the game advances to the next level.
	 */
	@Override
	protected void checkIfGameOver() {
		if (spawnCooldown > 0) return; // prevent error if planeBoss = null
		if (userIsDestroyed()) {
			gameStatus(GameStatus.DEFEAT);
			return;
		}
		if (planeBoss.isDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units based on the spawn cooldown.
	 * If the cooldown is greater than zero, it is decremented.
	 * If there are no current enemies and the cooldown is zero or less, a boss is spawned.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (spawnCooldown > 0) spawnCooldown--;
		if (getCurrentNumberOfEnemies() == 0 && spawnCooldown <= 0) {
			addEnemyUnit(createBoss());
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
	 * Creates and initializes the boss plane.
	 *
	 * @return the boss plane
	 */
	protected Plane_Boss createBoss() {
		planeBoss = new Plane_Boss();
		getRoot().getChildren().addAll(
				planeBoss.getShieldImage(),
				planeBoss.getHealthBar(),
				planeBoss.getExplosionImage()
		);
		return planeBoss;
	}
}