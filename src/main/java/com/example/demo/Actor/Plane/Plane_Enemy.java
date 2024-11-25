package com.example.demo.Actor.Plane;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.WeaponProjectiles.Projectile_Enemy;

/**
 * Class representing an enemy plane in the game.
 */
public class Plane_Enemy extends Plane {

	/**
	 * The image name for the enemy plane.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/actors/enemyplane.png";

	/**
	 * The height of the enemy plane image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The horizontal velocity of the enemy plane.
	 */
	private static final int HORIZONTAL_VELOCITY = -6;

	/**
	 * The X position offset for the projectile.
	 */
	private static final double PROJECTILE_X_POSITION_OFFSET = -50.0;

	/**
	 * The Y position offset for the projectile.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;

	/**
	 * The initial health of the enemy plane.
	 */
	private static final int INITIAL_HEALTH = 1;

	/**
	 * The fire rate of the enemy plane.
	 */
	private static double FIRE_RATE;

	/**
	 * Constructor to initialize the Plane_Enemy object.
	 *
	 * @param initialXPos the initial X position of the enemy plane
	 * @param initialYPos the initial Y position of the enemy plane
	 * @param fireRate the rate at which the enemy plane fires projectiles
	 */
	public Plane_Enemy(double initialXPos, double initialYPos, double fireRate) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		FIRE_RATE = fireRate;
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane if the random condition based on fire rate is met.
	 *
	 * @return an ActiveActorDestructible representing the fired projectile, or null if not firing
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			return new Projectile_Enemy(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}