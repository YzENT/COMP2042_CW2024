package com.example.demo.Actor;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.ActorsLogic.WeaponProjectiles.Projectile_User;

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

	public Plane_User(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
	}

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
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new Projectile_User(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void moveForward() {
		horizontalVelocityMultiplier = 1;
	}

	public void moveBackward() {
		horizontalVelocityMultiplier = -1;
	}

	public void stopVerticalMovement() {
		verticalVelocityMultiplier = 0;
	}

	public void stopHorizontalMovement() {
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	public int getVerticalVelocityMultiplier() {
		return verticalVelocityMultiplier;
	}

	public int getHorizontalVelocityMultiplier() {
		return horizontalVelocityMultiplier;
	}

}
