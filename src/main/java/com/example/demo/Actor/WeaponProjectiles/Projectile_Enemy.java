package com.example.demo.Actor.WeaponProjectiles;

/**
 * Class representing an enemy projectile in the game.
 */
public class Projectile_Enemy extends Projectile {

	/**
	 * The image name for the enemy projectile.
	 */
	private static final String IMAGE_NAME = "/images/weapons/enemyFire.png";

	/**
	 * The height of the enemy projectile image.
	 */
	private static final int IMAGE_HEIGHT = 25;

	/**
	 * The horizontal velocity of the enemy projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * The health of the enemy projectile.
	 */
	private static final int HEALTH = 1;

	/**
	 * Constructor to initialize a Projectile_Enemy object.
	 *
	 * @param initialXPos the initial X position of the enemy projectile
	 * @param initialYPos the initial Y position of the enemy projectile
	 */
	public Projectile_Enemy(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HEALTH, HORIZONTAL_VELOCITY);
	}

}