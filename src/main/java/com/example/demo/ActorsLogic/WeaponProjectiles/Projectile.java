package com.example.demo.ActorsLogic.WeaponProjectiles;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Initialize.Main;

public abstract class Projectile extends ActiveActorDestructible {

	private int health;
	private final int horizontalVelocity;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int horizontalVelocity) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
		this.horizontalVelocity = horizontalVelocity;
	}

	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
	}

	public void updateActor() {
		updatePosition();
		if (outOfBounds()) {
			this.destroy();
		}
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	private boolean outOfBounds() {
		return getTranslateX() > Main.getScreenWidth();
	}

}
