package com.example.demo.Levels;

import java.util.*;

import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.Plane;
import com.example.demo.Actor.Plane_User;
import com.example.demo.ActorsLogic.UserControls;
import com.example.demo.Initialize.Main;
import com.example.demo.Screens.Screen_PauseMenu;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final Plane_User user;
	private final Scene scene;
	private final ImageView background;
	private final UserControls userControls;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

    private final LevelView levelView;

	public enum GameStatus{
		VICTORY,
		DEFEAT
	}

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new Plane_User(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.userControls = new UserControls(user, root, userProjectiles);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
        initializeTimeline();
		friendlyUnits.add(user);
	}

	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		sendPauseMenuRunbacks();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
		timeline.stop();
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleProjectileCollisions();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
//		displayHitboxes();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(this::handleKeyPressed);
		background.setOnKeyReleased(this::handleKeyReleased);
		root.getChildren().add(background);
	}

	private void handleKeyPressed(KeyEvent e) {
		userControls.handleKeyPressed(e);
	}

	private void pauseGame() {
		timeline.pause();
		Screen_PauseMenu pauseMenu = new Screen_PauseMenu(Main.getStage(), Main.getScreenWidth(), Main.getScreenHeight());
		pauseMenu.show();
	}

	private void resumeGame() {
		timeline.play();
	}

	private void handleKeyReleased(KeyEvent e) {
		userControls.handleKeyReleased(e);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> {
			Plane fighter = (Plane) enemy;
			ActiveActorDestructible projectile = fighter.fireProjectile();

			if (projectile != null) {
				root.getChildren().add(projectile);
				enemyProjectiles.add(projectile);
			}
		});
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2, Runnable specialCondition) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();

					// special condition only executed if object destroyed
					if (actor.isDestroyed() || otherActor.isDestroyed()) {
						specialCondition.run();
					}
				}
			}
		}
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits, () -> {});
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits, () -> {
			// kill count should only be incremented if missile hits enemy plane (special condition)
			for (ActiveActorDestructible enemy : enemyUnits) {
				if (enemy.isDestroyed()) {
					user.incrementKillCount();
				}
			}
		});
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits, () -> {});
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void handleProjectileCollisions() {
		handleCollisions(enemyProjectiles, userProjectiles, () -> {});
	}

	private void updateLevelView() {
		levelView.displayHeartRemaining(user.getHealth());
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void gameStatus(GameStatus results) {
		timeline.stop();

		switch (results) {
			case VICTORY:
				levelView.showWinImage();
				break;
			case DEFEAT:
				levelView.showGameOverImage();
				break;
		}
	}

	protected Plane_User getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void displayHitboxes() {
		displayHitboxesForActors(friendlyUnits);
		displayHitboxesForActors(enemyUnits);
		displayHitboxesForActors(userProjectiles);
		displayHitboxesForActors(enemyProjectiles);
	}

	private void displayHitboxesForActors(List<ActiveActorDestructible> actors) {
		for (ActiveActorDestructible actor : actors) {
			Rectangle hitbox = new Rectangle(
					actor.getBoundsInParent().getMinX(),
					actor.getBoundsInParent().getMinY(),
					actor.getBoundsInParent().getWidth(),
					actor.getBoundsInParent().getHeight()
			);
			hitbox.setStroke(Color.RED);
			hitbox.setFill(Color.TRANSPARENT);
			root.getChildren().add(hitbox);
		}
	}

	private void sendPauseMenuRunbacks() {
		Screen_PauseMenu.receiveScene(scene); //so pause menu can set stage back to this scene
		Screen_PauseMenu.receiveRunback(this::resumeGame); //so pause menu can resume game
		UserControls.receivePauseMenu(this::pauseGame); //so pause menu can pause game
	}

}
