package com.example.demo.Actor.Plane;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.WeaponProjectiles.Projectile_User;

/**
 * Class representing the user-controlled plane in the game.
 */
public class Plane_User extends Plane {

	private static final String IMAGE_NAME = "/com/example/demo/images/actors/userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 660;
	private static final double X_UPPER_BOUND = 0;
	private static final double X_LOWER_BOUND = 1100;
	private static final double INITIAL_X_POSITION = 5;
	private static final double INITIAL_Y_POSITION = 300;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION_OFFSET = 140;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -15;

	private int verticalVelocityMultiplier = 0;
	private int horizontalVelocityMultiplier = 0;
	private int numberOfKills = 0;

	/**
	 * Constructor to initialize the Plane_User object with initial health.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public Plane_User(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
	}

	/**
	 * Updates the position of the user plane based on its velocity multipliers.
	 */
	@Override
	public void updatePosition() {
		if (!isMoving()) {
			return;
		}

		double initialTranslateY = getTranslateY();
		double initialTranslateX = getTranslateX();

		moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
		moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);

		double newPositionY = getLayoutY() + getTranslateY();
		double newPositionX = getLayoutX() + getTranslateX();

		if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		if (newPositionX < X_UPPER_BOUND || newPositionX > X_LOWER_BOUND) {
			setTranslateX(initialTranslateX);
		}
	}

	/**
	 * Updates the state of the user plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 *
	 * @return an ActiveActorDestructible representing the fired projectile
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new Projectile_User(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the user plane is currently moving.
	 *
	 * @return true if the plane is moving, false otherwise
	 */
	private boolean isMoving() {
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	/**
	 * Sets the vertical velocity multiplier to move the plane up.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Sets the vertical velocity multiplier to move the plane down.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Sets the horizontal velocity multiplier to move the plane forward.
	 */
	public void moveForward() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Sets the horizontal velocity multiplier to move the plane backward.
	 */
	public void moveBackward() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Stops the vertical movement of the plane.
	 */
	public void stopVerticalMovement() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Stops the horizontal movement of the plane.
	 */
	public void stopHorizontalMovement() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Gets the number of kills made by the user plane.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the user plane by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Gets the vertical velocity multiplier of the user plane.
	 *
	 * @return the vertical velocity multiplier
	 */
	public int getVerticalVelocityMultiplier() {
		return verticalVelocityMultiplier;
	}

	/**
	 * Gets the horizontal velocity multiplier of the user plane.
	 *
	 * @return the horizontal velocity multiplier
	 */
	public int getHorizontalVelocityMultiplier() {
		return horizontalVelocityMultiplier;
	}

}