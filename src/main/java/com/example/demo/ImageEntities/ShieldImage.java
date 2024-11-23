package com.example.demo.ImageEntities;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class representing a shield image in the game.
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/misc/shield.png";
	private static final Image SHIELD_IMAGE = new Image(Objects.requireNonNull(ShieldImage.class.getResource(IMAGE_NAME)).toExternalForm());
	private static final int SHIELD_SIZE = 200;

	/**
	 * Constructor to initialize a ShieldImage object.
	 */
	public ShieldImage() {
		this.setImage(SHIELD_IMAGE);
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Shows the shield image.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield image.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

	/**
	 * Updates the position of the shield image.
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public void updateShieldPosition(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
	}

}