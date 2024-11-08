package com.example.demo.Actor;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.ActorsLogic.WeaponProjectiles.Projectile_Enemy;

public class Plane_Enemy extends Plane {

	private static final String IMAGE_NAME = "/com/example/demo/images/actors/enemyplane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -50.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public Plane_Enemy(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new Projectile_Enemy(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
