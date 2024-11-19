package com.example.demo.ImageEntities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/misc/shield.png";
	private static final int SHIELD_SIZE = 200;
	private static final Image SHIELD_IMAGE = new Image(Objects.requireNonNull(ShieldImage.class.getResource(IMAGE_NAME)).toExternalForm());
	
	public ShieldImage() {
		this.setImage(SHIELD_IMAGE);
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		this.setVisible(true);
	}
	
	public void hideShield() {
		this.setVisible(false);
	}

	public void updateShieldPosition(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
	}

}
