package com.example.demo.ImageEntities;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class ExplosionImage extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/misc/explosion.png";
    private static final int IMAGE_SIZE = 300;
    private static final Image EXPLOSION_IMAGE =new Image(Objects.requireNonNull(ShieldImage.class.getResource(IMAGE_NAME)).toExternalForm());
    private static final int TRANSITION_DURATION = 5;

    public ExplosionImage() {
        this.setImage(EXPLOSION_IMAGE);
        this.setVisible(false);
        this.setFitHeight(IMAGE_SIZE);
        this.setFitWidth(IMAGE_SIZE);
    }

    public void showExplosion() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(TRANSITION_DURATION), this);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(2.0);
        scaleTransition.setToY(2.0);

        ParallelTransition explosionEffect = new ParallelTransition(scaleTransition);

        this.setVisible(true);
        explosionEffect.play();
    }

    public void setExplostionPosition(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

}
