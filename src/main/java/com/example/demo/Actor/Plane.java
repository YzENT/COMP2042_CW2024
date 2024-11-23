package com.example.demo.Actor;

import com.example.demo.ActorsLogic.ActiveActorDestructible;

/**
 * Abstract class representing a Plane, which is a type of ActiveActorDestructible.
 */
public abstract class Plane extends ActiveActorDestructible {

	private int health;

	/**
	 * Constructor to initialize a Plane object.
	 *
	 * @param imageName the name of the image representing the plane
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the plane
	 * @param initialYPos the initial Y position of the plane
	 * @param health the initial health of the plane
	 */
	public Plane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method to fire a projectile from the plane.
	 *
	 * @return an ActiveActorDestructible representing the fired projectile
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the plane by one and destroys it if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position for a projectile based on an offset.
	 *
	 * @param xPositionOffset the offset to be added to the current X position
	 * @return the calculated X position for the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position for a projectile based on an offset.
	 *
	 * @param yPositionOffset the offset to be added to the current Y position
	 * @return the calculated Y position for the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the plane is zero.
	 *
	 * @return true if health is zero, false otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the plane.
	 *
	 * @return the current health
	 */
	public int getHealth() {
		return health;
	}

}