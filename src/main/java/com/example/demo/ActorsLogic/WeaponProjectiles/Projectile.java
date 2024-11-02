package com.example.demo.ActorsLogic.WeaponProjectiles;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Initialize.Main;

public abstract class Projectile extends ActiveActorDestructible {

	private int health;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	@Override
	public abstract void updatePosition();

	public void updateActor() {
		updatePosition();
		if (outOfBounds()) {
			this.destroy();
		}
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	public boolean outOfBounds() {
		return getTranslateX() > Main.getScreenWidth();
	}

}
