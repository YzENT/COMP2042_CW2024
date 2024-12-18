package com.example.demo.Levels;

import java.util.*;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.demo.ActorsLogic.ActiveActorDestructible;
import com.example.demo.Actor.Plane.Plane;
import com.example.demo.Actor.Plane.Plane_User;
import com.example.demo.ActorsLogic.UserControls;
import com.example.demo.Controller.AudioController;
import com.example.demo.Controller.Main;
import com.example.demo.Screens.Screen_GameEnded;
import com.example.demo.Screens.Screen_LoadingAnimation;
import com.example.demo.Screens.Screen_PauseMenu;

/**
 * Abstract class representing a parent level in the game.
 */
public abstract class LevelParent {

	/**
	 * The screen height adjustment value.
	 */
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	/**
	 * The delay in milliseconds for the game loop.
	 */
	private static final int MILLISECOND_DELAY = 50;

	/**
	 * The path to the background music file.
	 * Source: <a href="https://pixabay.com/music/main-title-cinematic-epic-237173/">Link to epic music</a>
	 */
	private static final String BGM_PATH = "/audio/bgm/cinematic-epic-237173.mp3";

	/**
     * The path to the metal pipe sound effect file.
     * Source: <a href="https://www.youtube.com/watch?v=f8mL0_4GeV0">Link to metal pipe falling sfx</a>
     */
	private static final String METAL_PIPE = "/audio/sfx/metal pipe falling.mp3";

	/**
	 * The height of the screen.
	 */
	private final double screenHeight;

	/**
	 * The width of the screen.
	 */
	private final double screenWidth;

	/**
	 * The maximum Y position for enemies.
	 */
	private final double enemyMaximumYPosition;

	/**
	 * The number of kills required to advance to the next level.
	 */
	private final int KILLS_TO_ADVANCE;

	/**
	 * The string message that appears on screen when user first enters the level.
	 */
	private final String MESSAGE_ON_SCREEN;

	/**
	 * The root group for the scene.
	 */
	private final Group root;

	/**
	 * The timeline for the game loop.
	 */
	private final Timeline timeline;

	/**
	 * The user plane.
	 */
	private final Plane_User user;

	/**
	 * The scene for the level.
	 */
	private final Scene scene;

	/**
	 * The background image view.
	 */
	private final ImageView background;

	/**
	 * The user controls for the game.
	 */
	private final UserControls userControls;

	/**
	 * The audio controller for the game.
	 */
	private final AudioController audioController;

	/**
	 * The level view.
	 */
	private final LevelView levelView;

	/**
	 * The list of friendly units.
	 */
	private final List<ActiveActorDestructible> friendlyUnits;

	/**
	 * The list of enemy units.
	 */
	private final List<ActiveActorDestructible> enemyUnits;

	/**
	 * The list of user projectiles.
	 */
	private final List<ActiveActorDestructible> userProjectiles;

	/**
	 * The list of enemy projectiles.
	 */
	private final List<ActiveActorDestructible> enemyProjectiles;

	/**
	 * The cooldown period for spawning enemies.
	 */
	private double enemySpawnCooldown = 30;

	/**
	 * Enum representing the game status.
	 */
	public enum GameStatus {
		/**
		 * User wins the game.
		 */
		VICTORY,
		/**
		 * User loses the game.
		 */
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
	 * @param messageOnScreen the custom message each level has to tell what the user's goal is
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, int killsToAdvance, String messageOnScreen) {
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
		this.audioController = new AudioController();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		initializeTimeline();
		friendlyUnits.add(user);
		this.KILLS_TO_ADVANCE = killsToAdvance;
		this.MESSAGE_ON_SCREEN = messageOnScreen;
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
		levelView.entryMessage(MESSAGE_ON_SCREEN);
		sendPauseMenuRunbacks();
		audioController.playBGM(BGM_PATH);
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
		background.toBack();
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
		if (enemySpawnCooldown > 0) {
			enemySpawnCooldown--;
		} else {
			spawnEnemyUnits();
		}
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
				addEnemyProjectile(projectile);
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
	 * Adds an enemy projectile to the root.
	 *
	 * @param enemyProjectile the enemy projectile to add
	 */
	protected void addEnemyProjectile(ActiveActorDestructible enemyProjectile) {
		root.getChildren().add(enemyProjectile);
		enemyProjectiles.add(enemyProjectile);
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
		handleCollisions(friendlyUnits, enemyUnits, () -> {
			shakeScreen(METAL_PIPE);
		});
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
		handleCollisions(enemyProjectiles, friendlyUnits, () -> {
			shakeScreen(METAL_PIPE);
		});
	}

	/**
	 * Handles enemy penetration of defenses.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
				shakeScreen(METAL_PIPE);
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
		levelView.fadeObjectOnScreen(2, root, () -> {
			root.getChildren().clear();
			Screen_LoadingAnimation.setGameLevel(levelName);
			Screen_LoadingAnimation loadingAnimation = new Screen_LoadingAnimation((Stage) scene.getWindow(), Main.getScreenWidth(), Main.getScreenHeight());
			loadingAnimation.show();
		});
	}

	/**
	 * Sets the game status and shows the results.
	 *
	 * @param result the game status
	 */
	protected void gameStatus(GameStatus result) {
		timeline.stop();
		levelView.fadeObjectOnScreen(5, root, () -> {
			root.getChildren().clear();
			FadeTransition fadeToBlack = new FadeTransition(Duration.seconds(2), root);
			Rectangle blackBackground = new Rectangle(Main.getScreenWidth(), Main.getScreenHeight(), Color.BLACK);
			root.getChildren().addFirst(blackBackground);
			fadeToBlack.setFromValue(0);
			fadeToBlack.setToValue(1.0);
			fadeToBlack.play();

			Timeline volumeFade = new Timeline(
					new KeyFrame(Duration.ZERO, new KeyValue(AudioController.getMediaPlayer().volumeProperty(), AudioController.getMusicVolume())),
					new KeyFrame(Duration.seconds(2), new KeyValue(AudioController.getMediaPlayer().volumeProperty(), 0))
			);
			volumeFade.play();

			fadeToBlack.setOnFinished(event -> {
				audioController.stopBGM();
				Screen_GameEnded end = new Screen_GameEnded((Stage) scene.getWindow(), Main.getScreenWidth(), Main.getScreenHeight());
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
		Screen_PauseMenu pauseMenu = new Screen_PauseMenu((Stage) scene.getWindow(), Main.getScreenWidth(), Main.getScreenHeight());
		pauseMenu.show();
	}

	/**
	 * Resumes the game.
	 */
	private void resumeGame() {
		timeline.play();
	}

	/**
	 * Shakes the screen and plays specified SFX.
	 */
	protected void shakeScreen(String sfxToPlay) {
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(50), root);
		translateTransition.setFromX(-5);
		translateTransition.setToX(5);
		translateTransition.setCycleCount(4);
		translateTransition.setAutoReverse(true);
		translateTransition.play();
		audioController.playSFX(sfxToPlay);
	}

	/**
	 * Resets the spawn timer to a random value (0.0 - 30.0).
	 */
	protected void resetEnemyCooldown() {
		enemySpawnCooldown = Math.random() * 30;
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
	 * Gets the timeline.
	 *
	 * @return the timeline of program.
	 */
	public Timeline getTimeline() {
		return timeline;
	}

	/**
	 * Gets the level view.
	 *
	 * @return level's view of current level.
	 */
	public LevelView getLevelView() {
		return levelView;
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
	 * @return the MILLISECOND_DELAY value for this class
	 */
	public static double getProgramDelay() {
		return MILLISECOND_DELAY;
	}

	/**
	 * Gets the enemy spawn cooldown value.
	 *
	 * @return the cooldown for enemy spawning.
	 */
	protected double getEnemySpawnCooldown() {
		return enemySpawnCooldown;
	}
}