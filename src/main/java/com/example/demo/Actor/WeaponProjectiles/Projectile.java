package com.example.demo.Actor.WeaponProjectiles;

import com.example.demo.Controller.Main;
import com.example.demo.ActorsLogic.ActiveActorDestructible;

/**
 * Abstract class representing a projectile in the game.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * The health of the projectile.
	 */
	private int health;

	/**
	 * The horizontal velocity of the projectile.
	 */
	private final int horizontalVelocity;

	/**
	 * Constructor to initialize a Projectile object.
	 *
	 * @param imageName the name of the image representing the projectile
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the projectile
	 * @param initialYPos the initial Y position of the projectile
	 * @param health the initial health of the projectile
	 * @param horizontalVelocity the horizontal velocity of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int horizontalVelocity) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
		this.horizontalVelocity = horizontalVelocity;
	}

	/**
	 * Reduces the health of the projectile by one and destroys it if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
	}

	/**
	 * Updates the state of the projectile.
	 * Destroys the projectile if it has travelled the maximum distance.
	 */
	public void updateActor() {
		updatePosition();
		if (maximumTravelDistanceAllowed()) {
			this.destroy();
		}
	}

	/**
	 * Checks if the health of the projectile is zero.
	 *
	 * @return true if health is zero, false otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Checks if the projectile has travelled too far.
	 *
	 * @return true if the projectile has travelled too far, false otherwise
	 */
	private boolean maximumTravelDistanceAllowed() {
		// Divide by 2 so projectile doesn't fly too far
		return getTranslateX() > (double) Main.getScreenWidth() / 2;
	}

}