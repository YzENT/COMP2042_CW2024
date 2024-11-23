package com.example.demo.Levels;

import com.example.demo.Actor.Plane_Enemy;
import com.example.demo.ActorsLogic.ActiveActorDestructible;

/**
 * Class representing the second level of the game.
 */
public class Level_2 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/level2.png";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.Level_3";
    private static final int KILLS_TO_ADVANCE = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = .5;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double ENEMY_FIRE_RATE = .05;
    private double spawnCooldown = 30;

    /**
     * Constructor to initialize Level_2.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public Level_2(double screenHeight, double screenWidth) {
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
     * If the random probability is met, a new enemy is spawned.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (spawnCooldown > 0) {
            spawnCooldown--;
            return;
        }

        if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
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