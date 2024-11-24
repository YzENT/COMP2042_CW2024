package com.example.demo.Levels;

import java.util.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.Plane.Plane;
import com.example.demo.Actor.Plane.Plane_User;
import com.example.demo.ActorsLogic.UserControls;
import com.example.demo.Initialize.Controller;
import com.example.demo.Initialize.Main;
import com.example.demo.Screens.Screen_GameEnded;
import com.example.demo.Screens.Screen_LoadingAnimation;
import com.example.demo.Screens.Screen_PauseMenu;

/**
 * Abstract class representing a parent level in the game.
 */
public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private final int KILLS_TO_ADVANCE;

	//https://pixabay.com/music/main-title-cinematic-epic-237173/
	private static final String BGM_PATH = "/com/example/demo/audio/bgm/cinematic-epic-237173.mp3";

	//https://www.youtube.com/watch?v=f8mL0_4GeV0
	private static final String METAL_PIPE = "/com/example/demo/audio/sfx/metal pipe falling.mp3";

	private final Group root;
	private final Timeline timeline;
	private final Plane_User user;
	private final Scene scene;
	private final ImageView background;
	private final UserControls userControls;
	private final Controller controller;
	private final LevelView levelView;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	/**
	 * Enum representing the game status.
	 */
	public enum GameStatus {
		VICTORY,
		DEFEAT
	}

	/**
	 * Constructor to initialize a LevelParent object.
	 *
	 * @param backgroundImageName the name of the background image
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
	 * @param playerInitialHealth the initial health of the player
	 * @param killsToAdvance the number of kills required to advance to the next level
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, int killsToAdvance) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new Plane_User(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.userControls = new UserControls(user, root, userProjectiles, (Stage) scene.getWindow());
		this.controller = new Controller(Main.getStage());
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		initializeTimeline();
		friendlyUnits.add(user);
		this.KILLS_TO_ADVANCE = killsToAdvance;
	}

	/**
	 * Initializes the scene for the level.
	 *
	 * @return the initialized scene
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.initializeKillCounter();
		sendPauseMenuRunbacks();
		controller.playBGM(BGM_PATH);
		return scene;
	}

	/**
	 * Initializes the background for the level.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(this::handleKeyPressed);
		background.setOnKeyReleased(this::handleKeyReleased);
		root.getChildren().add(background);
	}

	/**
	 * Handles key press events.
	 *
	 * @param e the key event
	 */
	private void handleKeyPressed(KeyEvent e) {
		userControls.handleKeyPressed(e);
	}

	/**
	 * Handles key release events.
	 *
	 * @param e the key event
	 */
	private void handleKeyReleased(KeyEvent e) {
		userControls.handleKeyReleased(e);
	}

	/**
	 * Sends runbacks to the pause menu.
	 */
	private void sendPauseMenuRunbacks() {
		Screen_PauseMenu.setScene(scene); // so pause menu can set stage back to this scene
		Screen_PauseMenu.setRunback(this::resumeGame); // so pause menu can resume game
		UserControls.setPauseMenuRunback(this::pauseGame); // so pause menu can pause game
	}

	/**
	 * Initializes friendly units for the level.
	 */
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Abstract method to check if the game is over.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method to spawn enemy units.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method to instantiate the level view.
	 *
	 * @return the instantiated level view
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Starts the game.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Initializes the timeline for the game loop.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Updates the scene for each frame.
	 */
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
	}

	/**
	 * Updates all actors in the scene.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Generates enemy fire.
	 */
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

	/**
	 * Adds an enemy unit to the level.
	 *
	 * @param enemy the enemy unit to add
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Handles collisions between two lists of actors.
	 *
	 * @param actors1 the first list of actors
	 * @param actors2 the second list of actors
	 * @param specialCondition the special condition to execute if an actor is destroyed
	 */
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

	/**
	 * Handles collisions between friendly units and enemy units.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits, this::shakeScreen);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 */
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

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits, this::shakeScreen);
	}

	/**
	 * Handles enemy penetration of defenses.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * Handles collisions between enemy projectiles and user projectiles.
	 */
	private void handleProjectileCollisions() {
		handleCollisions(enemyProjectiles, userProjectiles, () -> {});
	}

	/**
	 * Removes all destroyed actors from the scene.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from a list.
	 *
	 * @param actors the list of actors
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Updates the level view every frame.
	 */
	private void updateLevelView() {
		levelView.displayHeartRemaining(user.getHealth());
		levelView.updateKillCounter(user.getNumberOfKills(), KILLS_TO_ADVANCE);
	}

	/**
	 * Advances to the next level.
	 *
	 * @param levelName the name of the next level
	 */
	protected void goToNextLevel(String levelName) {
		timeline.stop();
		levelView.screenFade(2, () -> {
			Screen_LoadingAnimation.setGameLevel(levelName);
			Screen_LoadingAnimation loadingAnimation = new Screen_LoadingAnimation(Main.getStage(), Main.getScreenWidth(), Main.getScreenHeight());
			loadingAnimation.show();
		});
	}

	/**
	 * Sets the game status.
	 *
	 * @param result the game status
	 */
	protected void gameStatus(GameStatus result) {
		timeline.stop();
		levelView.screenFade(5, () -> {
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), root);
			Rectangle background = new Rectangle(Main.getScreenWidth(), Main.getScreenHeight(), Color.BLACK);
			root.getChildren().addFirst(background);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1.0);
			fadeTransition.play();

			Timeline volumeFade = new Timeline(
					new KeyFrame(Duration.ZERO, new KeyValue(Controller.getMediaPlayer().volumeProperty(), Controller.getMusicVolume())),
					new KeyFrame(Duration.seconds(2), new KeyValue(Controller.getMediaPlayer().volumeProperty(), 0))
			);
			volumeFade.play();

			fadeTransition.setOnFinished(event -> {
				controller.stopBGM();
				Screen_GameEnded end = new Screen_GameEnded(Main.getStage(), Main.getScreenWidth(), Main.getScreenHeight());
				end.setResults(result);
				end.show();
			});
		});
	}

	/**
	 * Pauses the game.
	 */
	private void pauseGame() {
		timeline.pause();
		Screen_PauseMenu pauseMenu = new Screen_PauseMenu(Main.getStage(), Main.getScreenWidth(), Main.getScreenHeight());
		pauseMenu.show();
	}

	/**
	 * Resumes the game.
	 */
	private void resumeGame() {
		timeline.play();
	}

	/**
	 * Shakes the screen and plays collision SFX.
	 */
	private void shakeScreen() {
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(50), root);
		translateTransition.setFromX(-5);
		translateTransition.setToX(5);
		translateTransition.setCycleCount(4);
		translateTransition.setAutoReverse(true);
		translateTransition.play();
		controller.playSFX(METAL_PIPE);
	}

	/**
	 * Checks if an enemy has penetrated defenses.
	 *
	 * @param enemy the enemy unit
	 * @return true if the enemy has penetrated defenses, false otherwise
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Gets the user plane.
	 *
	 * @return the user plane
	 */
	protected Plane_User getUser() {
		return user;
	}

	/**
	 * Gets the root group.
	 *
	 * @return the root group
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Gets the current number of enemies.
	 *
	 * @return the current number of enemies
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Gets the maximum Y position for enemies.
	 *
	 * @return the maximum Y position for enemies
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Gets the screen width.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user is destroyed.
	 *
	 * @return true if the user is destroyed, false otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Checks if the user has reached the kill target.
	 *
	 * @return true if the user has reached the kill target, false otherwise
	 */
	protected boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Gets the MILLISECOND_DELAY value for this class.
	 *
	 * @return the MILLISECOND_DELAY value for this class.
	 */
	public static double getProgramDelay() {
		return MILLISECOND_DELAY;
	}
}