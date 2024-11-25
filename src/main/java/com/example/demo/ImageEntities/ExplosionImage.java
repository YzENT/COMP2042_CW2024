package com.example.demo.ImageEntities;

import java.util.Objects;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Class representing an explosion image in the game.
 */
public class ExplosionImage extends ImageView {

    /**
     * The name of the explosion image file.
     * Source: <a href="https://www.rawpixel.com/search/explosion%20png/">Link to Explosion Image</a>
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/misc/explosion.png";

    /**
     * The size of the explosion image.
     */
    private static final int IMAGE_SIZE = 300;

    /**
     * The explosion image object.
     */
    private static final Image EXPLOSION_IMAGE = new Image(Objects.requireNonNull(ShieldImage.class.getResource(IMAGE_NAME)).toExternalForm());

    /**
     * The duration of the transition effect for the explosion.
     */
    private static final int TRANSITION_DURATION = 5;

    /**
     * Constructor to initialize an ExplosionImage object.
     */
    public ExplosionImage() {
        this.setImage(EXPLOSION_IMAGE);
        this.setVisible(false);
        this.setFitHeight(IMAGE_SIZE);
        this.setFitWidth(IMAGE_SIZE);
    }

    /**
     * Displays the explosion effect with a scaling transition.
     */
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

    /**
     * Sets the position of the explosion image.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public void setExplosionCoordinates(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

}