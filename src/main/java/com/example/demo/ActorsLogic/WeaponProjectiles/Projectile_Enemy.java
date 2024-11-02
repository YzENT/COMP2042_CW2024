package com.example.demo.ActorsLogic.WeaponProjectiles;

public class Projectile_Enemy extends Projectile {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/weapons/enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -10;
	private static final int HEALTH = 1;

	public Projectile_Enemy(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

}
