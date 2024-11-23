package com.example.demo.ActorsLogic.WeaponProjectiles;

/**
 * Class representing a user projectile in the game.
 */
public class Projectile_User extends Projectile {

	private static final String IMAGE_NAME = "/com/example/demo/images/weapons/userfire.png";
	private static final int IMAGE_HEIGHT = 100;
	private static final int HORIZONTAL_VELOCITY = 15;
	private static final int HEALTH = 1;

	/**
	 * Constructor to initialize a Projectile_User object.
	 *
	 * @param initialXPos the initial X position of the user projectile
	 * @param initialYPos the initial Y position of the user projectile
	 */
	public Projectile_User(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HEALTH, HORIZONTAL_VELOCITY);
	}

}