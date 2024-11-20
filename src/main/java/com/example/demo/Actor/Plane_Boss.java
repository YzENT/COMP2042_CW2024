package com.example.demo.Actor;

import java.util.*;
import javafx.scene.control.ProgressBar;
import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.ActorsLogic.WeaponProjectiles.Projectile_Boss;
import com.example.demo.ImageEntities.ExplosionImage;
import com.example.demo.ImageEntities.ShieldImage;

public class Plane_Boss extends Plane {

	private static final String IMAGE_NAME = "/com/example/demo/images/actors/bossplane.png";

	private static final double INITIAL_X_POSITION = 1000;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50;
	private static final double BOSS_FIRE_RATE = .3;
	private static final double BOSS_SHIELD_PROBABILITY = .05;
	private static final int IMAGE_HEIGHT = 70;
	private static final int VERTICAL_VELOCITY = 12;
	private static final int HEALTH = 50;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 100;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 5;
	private static final int Y_POSITION_UPPER_BOUND = 20;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int MAX_FRAMES_WITH_SHIELD = 50;
	private static final int MIN_FRAMES_WITHOUT_SHIELD = 50;
	private static final int HEALTH_BAR_WIDTH = 200;
	private static final String HEALTH_BAR_COLOUR = "RED";

	private int consecutiveMovesInSameDirection = 0;
	private int indexOfCurrentMove = 0;
	private int framesWithShieldActivated = 0;
	private int framesWithShieldDeactivated = 0;

	private final List<Integer> movePattern;
	private final ShieldImage shieldImage;
	private final ProgressBar healthBar;
	private final ExplosionImage explosionImage;
	private Runnable removeHealthBar;

	private boolean isShielded = false;

	public Plane_Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		shieldImage = new ShieldImage();
		healthBar = createHealthBar();
		explosionImage = new ExplosionImage();
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_POSITION_UPPER_BOUND || currentPositionY > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		updateHealthBar();
		updateExplosion();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new Projectile_Boss(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
		if (getHealth() <= 0) {
			removeHealthBar.run();
			triggerExplosion();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		shieldImage.updateShieldPosition(Boss_XCoordinate() - IMAGE_HEIGHT*2, Boss_YCoordinate() - (double) IMAGE_HEIGHT /2);

		if (isShielded) {
			framesWithShieldActivated++;
		} else {
			framesWithShieldDeactivated++;
		}

		if (shieldShouldBeActivated()) {
			activateShield();
		}

		if (shieldExhausted()) {
			deactivateShield();
		}

	}

	private void updateExplosion() {
		//has to updated every frame because it will be (0,0) when boss is destroyed
		explosionImage.setExplostionPosition(Boss_XCoordinate(), Boss_YCoordinate() - IMAGE_HEIGHT*2);
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection >= MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private ProgressBar createHealthBar() {
		ProgressBar healthBar = new ProgressBar();
		healthBar.setPrefWidth(HEALTH_BAR_WIDTH);
		healthBar.setStyle("-fx-accent: " + HEALTH_BAR_COLOUR + ";");
		return healthBar;
	}

	private void updateHealthBar() {
		healthBar.setLayoutX(Boss_XCoordinate() + 50);
		healthBar.setLayoutY(Boss_YCoordinate() - 50);
		healthBar.setProgress((double) getHealth() / HEALTH); //expects a value between 0.0 and 1.0 only
	}

	private void activateShield() {
		isShielded = true;
		framesWithShieldDeactivated = 0;
		shieldImage.showShield();
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();
	}

	public void setRemoveHealthBar(Runnable action) {
		removeHealthBar = action;
	}

	private void triggerExplosion() {
		explosionImage.showExplosion();
	}

	private boolean shieldShouldBeActivated() {
		return (Math.random() < BOSS_SHIELD_PROBABILITY) && (framesWithShieldDeactivated >= MIN_FRAMES_WITHOUT_SHIELD);
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private double Boss_XCoordinate() {
		return getLayoutX() + getTranslateX();
	}

	private double Boss_YCoordinate() {
		return getLayoutY() + getTranslateY();
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	public ProgressBar getHealthBar() {
		return healthBar;
	}

	public ExplosionImage getExplosionImage() {
		return explosionImage;
	}

}
