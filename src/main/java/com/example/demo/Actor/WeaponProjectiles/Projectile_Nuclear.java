package com.example.demo.Actor.WeaponProjectiles;

/**
 * Class representing a nuclear projectile in the game.
 */
public class Projectile_Nuclear extends Projectile{

    /**
     * The image name for the nuclear projectile.
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/weapons/nuclear.png";

    /**
     * The height of the nuclear projectile image.
     */
    private static final int IMAGE_HEIGHT = 40;

    /**
     * The horizontal velocity of the nuclear projectile.
     */
    private static final int HORIZONTAL_VELOCITY = -20;

    /**
     * The health of the nuclear projectile.
     */
    private static final int HEALTH = 10;

    /**
     * Constructor to initialize a Projectile_Enemy object.
     *
     * @param initialXPos the initial X position of the enemy projectile
     * @param initialYPos the initial Y position of the enemy projectile
     */
    public Projectile_Nuclear(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HEALTH, HORIZONTAL_VELOCITY);
    }

}