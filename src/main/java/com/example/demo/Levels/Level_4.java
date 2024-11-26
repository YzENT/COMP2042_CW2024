package com.example.demo.Levels;

import com.example.demo.Actor.WeaponProjectiles.Projectile_Nuclear;
import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Controller.Main;

public class Level_4 extends LevelParent{

    /**
     * The name of the background image for level 3.
     * Source: <a href="https://craftpix.net/freebies/free-futuristic-city-pixel-art-backgrounds/">Link to background</a>
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/level4.png";

    /**
     * The path to the nuclear missile SFX.
     * Source: <a href="https://www.soundsnap.com/missile_shooting_light_1">Link to nuclear missile sfx</a>
     */
    private static final String NUCLEAR_SFX = "/com/example/demo/audio/sfx/355337-Missile_-Shot_1_-Light_Version.mp3";

    /**
     * The initial health of the player.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The cooldown period for spawning missiles.
     */
    private static double spawnCooldown = 30;

    /**
     * The targeted survival time in seconds.
     */
    private static final int SURVIVAL_SEC = 60;

    private static double survivedTime = 0;

    /**
     * Constructor to initialize Level_4.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public Level_4(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, 0);
        super.getLevelView().initializeTimerLabel();
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            gameStatus(GameStatus.DEFEAT);
            return;
        }

        if (timerRunsOut()) {
            gameStatus(GameStatus.VICTORY);
        } else {
            updateTimer();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (spawnCooldown > 0) {
            spawnCooldown--;
            return;
        }
        if (Math.random() < 0.01) {
            ActiveActorDestructible newMissile = new Projectile_Nuclear(Main.getScreenWidth(), Math.random() * Main.getScreenHeight());
            addEnemyProjectile(newMissile);
            super.shakeScreen(NUCLEAR_SFX);
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

    private void updateTimer() {
        survivedTime += getTimeline().getCycleDuration().toSeconds();
        getLevelView().updateTimerLabel(SURVIVAL_SEC - (int)survivedTime);
    }

    private boolean timerRunsOut() {
        return survivedTime >= SURVIVAL_SEC;
    }

}