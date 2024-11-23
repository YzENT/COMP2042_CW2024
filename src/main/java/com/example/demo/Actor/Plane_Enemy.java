package com.example.demo.Actor;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.ActorsLogic.WeaponProjectiles.Projectile_Enemy;

/**
 * Class representing an enemy plane in the game.
 */
public class Plane_Enemy extends Plane {

	private static final String IMAGE_NAME = "/com/example/demo/images/actors/enemyplane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -50.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
	private static final int INITIAL_HEALTH = 1;
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
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new Projectile_Enemy(projectileXPosition, projectileYPosition);
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