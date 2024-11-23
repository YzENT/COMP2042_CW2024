package com.example.demo.ActorsLogic;

/**
 * Interface representing a destructible entity in the game.
 */
public interface Destructible {

	/**
	 * Method to handle the logic when the entity takes damage.
	 */
	void takeDamage();

	/**
	 * Method to handle the logic when the entity is destroyed.
	 */
	void destroy();

}