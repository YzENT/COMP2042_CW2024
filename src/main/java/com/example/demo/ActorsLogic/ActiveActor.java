package com.example.demo.ActorsLogic;

import java.util.Objects;
import javafx.scene.image.*;

/**
 * Abstract class representing an active actor in the game.
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * Constructor to initialize an ActiveActor object.
	 *
	 * @param imageName the name of the image representing the actor
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the actor
	 * @param initialYPos the initial Y position of the actor
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified amount.
	 *
	 * @param horizontalMove the amount to move horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified amount.
	 *
	 * @param verticalMove the amount to move vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}