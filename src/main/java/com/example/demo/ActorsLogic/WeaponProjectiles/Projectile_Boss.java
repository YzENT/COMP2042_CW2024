package com.example.demo.ActorsLogic.WeaponProjectiles;

/**
 * Class representing a boss projectile in the game.
 */
public class Projectile_Boss extends Projectile {

	private static final String IMAGE_NAME = "/com/example/demo/images/weapons/fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;
	private static final int HEALTH = 1;

	/**
	 * Constructor to initialize a Projectile_Boss object.
	 *
	 * @param initialYPos the initial Y position of the boss projectile
	 */
	public Projectile_Boss(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos, HEALTH, HORIZONTAL_VELOCITY);
	}

}