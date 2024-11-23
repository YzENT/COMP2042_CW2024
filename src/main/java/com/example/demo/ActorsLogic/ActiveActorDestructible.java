package com.example.demo.ActorsLogic;

/**
 * Abstract class representing a destructible active actor in the game.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * Constructor to initialize an ActiveActorDestructible object.
	 *
	 * @param imageName the name of the image representing the actor
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the actor
	 * @param initialYPos the initial Y position of the actor
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor.
	 */
	public abstract void updateActor();

	/**
	 * Reduces the health of the actor or performs other damage-related logic.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor by setting its destroyed state to true.
	 */
	@Override
	public void destroy() {
		this.isDestroyed = true;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

}