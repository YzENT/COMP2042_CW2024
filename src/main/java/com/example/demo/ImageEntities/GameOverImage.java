package com.example.demo.ImageEntities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/misc/gameover.png";
	private static final Image GAME_OVER_IMAGE = new Image(Objects.requireNonNull(GameOverImage.class.getResource(IMAGE_NAME)).toExternalForm());

	public GameOverImage(double xPosition, double yPosition) {
		this.setImage(GAME_OVER_IMAGE);
		this.setVisible(false);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	public void showGameOverImage() {
		this.setVisible(true);
	}

}
