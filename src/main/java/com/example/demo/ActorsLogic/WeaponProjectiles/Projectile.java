package com.example.demo.ActorsLogic.WeaponProjectiles;

import com.example.demo.ActorsLogic.ActiveActorDestructible;

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

	private boolean healthAtZero() {
		return health == 0;
	}

	public int getHealth() {
		return health;
	}

}
