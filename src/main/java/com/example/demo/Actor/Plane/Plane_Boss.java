package com.example.demo.Actor.Plane;

import java.util.*;
import javafx.scene.control.ProgressBar;
import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.WeaponProjectiles.Projectile_Boss;
import com.example.demo.ImageEntities.ExplosionImage;
import com.example.demo.ImageEntities.ShieldImage;

/**
 * Class representing the boss plane in the game.
 */
public class Plane_Boss extends Plane {

	private static final String IMAGE_NAME = "/com/example/demo/images/actors/bossplane.png";

	private static final double INITIAL_X_POSITION = 1000;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50;
	private static final double BOSS_FIRE_RATE = .3;
	private static final double BOSS_SHIELD_PROBABILITY = .05;
	private static final int IMAGE_HEIGHT = 70;
	private static final int VERTICAL_VELOCITY = 12;
	private static final int HEALTH = 50;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 100;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 5;
	private static final int Y_POSITION_UPPER_BOUND = 20;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int MAX_FRAMES_WITH_SHIELD = 50;
	private static final int MIN_FRAMES_WITHOUT_SHIELD = 50;
	private static final int HEALTH_BAR_WIDTH = 200;
	private static final String HEALTH_BAR_COLOUR = "RED";

	private int consecutiveMovesInSameDirection = 0;
	private int indexOfCurrentMove = 0;
	private int framesWithShieldActivated = 0;
	private int framesWithShieldDeactivated = 0;

	private final List<Integer> movePattern;
	private final ShieldImage shieldImage;
	private final ProgressBar healthBar;
	private final ExplosionImage explosionImage;
	private Runnable removeHealthBar;

	private boolean isShielded = false;

	/**
	 * Constructor to initialize the Plane_Boss object.
	 */
	public Plane_Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		shieldImage = new ShieldImage();
		healthBar = createHealthBar();
		explosionImage = new ExplosionImage();
		initializeMovePattern();
	}

	/**
	 * Updates the position of the boss plane.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_POSITION_UPPER_BOUND || currentPositionY > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the state of the boss plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		updateHealthBar();
		updateExplosion();
	}

	/**
	 * Fires a projectile from the boss plane.
	 *
	 * @return an ActiveActorDestructible representing the fired projectile, or null if not firing
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new Projectile_Boss(getProjectileInitialPosition()) : null;
	}

	/**
	 * Reduces the health of the boss plane and triggers explosion if health reaches zero.
	 * Boss should only take damage if it's not shielded.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
		if (getHealth() <= 0) {
			removeHealthBar.run();
			triggerExplosion();
		}
	}

	/**
	 * Initializes the movement pattern for the boss plane.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield state and position.
	 */
	private void updateShield() {
		shieldImage.updateShieldPosition(Boss_XCoordinate() - IMAGE_HEIGHT * 2, Boss_YCoordinate() - (double) IMAGE_HEIGHT / 2);

		if (isShielded) {
			framesWithShieldActivated++;
		} else {
			framesWithShieldDeactivated++;
		}

		if (shieldShouldBeActivated()) {
			activateShield();
		}

		if (shieldExhausted()) {
			deactivateShield();
		}
	}

	/**
	 * Updates the explosion's image based on boss' current position.
	 */
	private void updateExplosion() {
		//has to updated every frame because it will be (0,0) when boss is destroyed
		explosionImage.setExplostionPosition(Boss_XCoordinate(), Boss_YCoordinate() - IMAGE_HEIGHT*2);
	}

	/**
	 * Gets the next move for the boss plane.
	 *
	 * @return the next move value
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection >= MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Creates the health bar for the boss plane.
	 *
	 * @return the created ProgressBar object
	 */
	private ProgressBar createHealthBar() {
		ProgressBar healthBar = new ProgressBar();
		healthBar.setPrefWidth(HEALTH_BAR_WIDTH);
		healthBar.setStyle("-fx-accent: " + HEALTH_BAR_COLOUR + ";");
		return healthBar;
	}

	/**
	 * Updates the health bar position and progress.
	 */
	private void updateHealthBar() {
		healthBar.setLayoutX(Boss_XCoordinate() + 50);
		healthBar.setLayoutY(Boss_YCoordinate() - 50);
		healthBar.setProgress((double) getHealth() / HEALTH); // expects a value between 0.0 and 1.0 only
	}

	/**
	 * Activates the shield for the boss plane.
	 */
	private void activateShield() {
		isShielded = true;
		framesWithShieldDeactivated = 0;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the shield for the boss plane.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();
	}

	/**
	 * Sets the action to remove the health bar from root.
	 *
	 * @param action the action to remove the health bar
	 */
	public void setRemoveHealthBar(Runnable action) {
		removeHealthBar = action;
	}

	/**
	 * Triggers the explosion effect for the boss plane.
	 */
	private void triggerExplosion() {
		explosionImage.showExplosion();
	}

	/**
	 * Checks if the shield should be activated.
	 *
	 * @return true if the shield should be activated, false otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return (Math.random() < BOSS_SHIELD_PROBABILITY) && (framesWithShieldDeactivated >= MIN_FRAMES_WITHOUT_SHIELD);
	}

	/**
	 * Checks if the shield is exhausted.
	 *
	 * @return true if the shield is exhausted, false otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Gets the X coordinate of the boss plane.
	 *
	 * @return the X coordinate
	 */
	private double Boss_XCoordinate() {
		return getLayoutX() + getTranslateX();
	}

	/**
	 * Gets the Y coordinate of the boss plane.
	 *
	 * @return the Y coordinate
	 */
	private double Boss_YCoordinate() {
		return getLayoutY() + getTranslateY();
	}

	/**
	 * Checks if the boss plane should fire in the current frame.
	 *
	 * @return true if the boss plane should fire, false otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial position for the projectile.
	 *
	 * @return the initial Y position for the projectile
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Gets the shield image for the boss plane.
	 *
	 * @return the ShieldImage object
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	/**
	 * Gets the health bar for the boss plane.
	 *
	 * @return the ProgressBar object
	 */
	public ProgressBar getHealthBar() {
		return healthBar;
	}

	/**
	 * Gets the explosion image for the boss plane.
	 *
	 * @return the ExplosionImage object
	 */
	public ExplosionImage getExplosionImage() {
		return explosionImage;
	}

	/**
	 * Sets the shielded state for the boss plane.
	 *
	 * @param shielded the shielded state to set
	 */
	public void setShielded(boolean shielded) {
		isShielded = shielded;
	}

}