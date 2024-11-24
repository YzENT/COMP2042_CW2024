package com.example.demo.ImageEntities;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Class representing a display of heart images in the game.
 */
public class HeartDisplay {

	/**
	 * The name of the heart image file.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/misc/heart.png";

	/**
	 * The heart image object.
	 */
	private static final Image HEART_IMAGE = new Image(Objects.requireNonNull(HeartDisplay.class.getResource(HEART_IMAGE_NAME)).toExternalForm());

	/**
	 * The height of the heart image.
	 */
	private static final int HEART_HEIGHT = 50;

	/**
	 * The container for the heart images.
	 */
	private HBox container;

	/**
	 * The X position of the heart display container.
	 */
	private final double containerXPosition;

	/**
	 * The Y position of the heart display container.
	 */
	private final double containerYPosition;

	/**
	 * The number of hearts to display.
	 */
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructor to initialize a HeartDisplay object.
	 *
	 * @param xPosition the X position of the heart display container
	 * @param yPosition the Y position of the heart display container
	 * @param heartsToDisplay the number of hearts to display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for the heart display.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the hearts to be displayed in the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(HEART_IMAGE);
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes a heart from the display.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().removeFirst();
	}

	/**
	 * Gets the container holding the heart images.
	 *
	 * @return the container holding the heart images
	 */
	public HBox getContainer() {
		return container;
	}

}