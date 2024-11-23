package com.example.demo.Actor.WeaponProjectiles;

/**
 * Class representing an enemy projectile in the game.
 */
public class Projectile_Enemy extends Projectile {

	private static final String IMAGE_NAME = "/com/example/demo/images/weapons/enemyFire.png";
	private static final int IMAGE_HEIGHT = 25;
	private static final int HORIZONTAL_VELOCITY = -10;
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