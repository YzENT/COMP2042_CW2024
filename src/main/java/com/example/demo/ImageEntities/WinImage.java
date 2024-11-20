package com.example.demo.ImageEntities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/misc/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;
	private static final Image WIN_IMAGE = new Image(Objects.requireNonNull(WinImage.class.getResource(IMAGE_NAME)).toExternalForm());
	
	public WinImage(double xPosition, double yPosition) {
		this.setImage(WIN_IMAGE);
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
	
	public void showWinImage() {
		this.setVisible(true);
	}

}
