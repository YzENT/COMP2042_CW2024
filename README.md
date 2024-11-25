# COMP2042 Coursework
This is a coursework for students of University of Nottingham Malaysia (2023-2026) batch for the module COMP2042 Developing Maintainable Software.

## Details
- Written by: Chea Yong Zen (20617094)
- GitHub Repository for COMP2042_Coursework: https://github.com/YzENT/COMP2042_CW2024

## Pre-requisites
- Java JDK (21 and above)
- JavaFX SDK (23 and above)
- Environmental Variables `JAVA_HOME` and `PATH_TO_FX`
- Machine equipped with Windows 10 and above

To verify whether the environmental variables above are set correctly, launch `cmd` and enter the following commands.

```
echo %JAVA_HOME%
```

```
echo %PATH_TO_FX%
```

If set up correctly, similar outputs should show:

````
> echo %JAVA_HOME%
C:\Program Files\Java\jdk-21

> echo %PATH_TO_FX%
C:\Program Files\Java\javafx-sdk-23.0.1\lib
````

## Compilation instructions
Clone the repository to local device
```
git clone https://github.com/YzENT/COMP2042_CW2024.git
```
### Automatic
An automated script `start.bat` can be found in the root directory. Launch the script and it will automatically install and compile everything. The application will be launched after everything has been completed.

### Manual
1. After cloning the repository to local device, launch `cmd` and navigate to root directory.
2. Enter the following command in `cmd`:
```
mvnw install -DskipTests
```
3. Navigate to the output folder:
```
cd target
```
4. Launch the .jar file created
```
"%JAVA_HOME%\bin\java" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml,javafx.media -jar CW2024-1.0-SNAPSHOT.jar
```

### Utilizing Maven (mvnw)
``mvnw clean`` - used to clean output files in `..\target` folder

``mvnw test`` - used to run the test scripts only

``mvnw install`` - used to compile the jar and run the tests units

To only compile the jar file and not run the tests,
``mvnw clean install -DskipTests``

# Features

## Implemented and Working Properly

### Keyboard Events Only
- Users now can only navigate the application using keyboard, <b>ALL</b> mouse events are disabled. WASD keys mimics arrow keys to navigate through buttons.

### Transition and Animations
- Transition and animations have been added to the game to aid the user's visual experience. Such transitions are used in changing game screen, finishing the game, etc…
- Boss now has a visual effect when defeated.

### Information in-game
- A health bar has been added for `boss` <b>only</b> for user to easier understand the status of the boss.
- Kill count on top right has been added for user to easily understand the target they need to reach in that specific level.

### Destroyable Projectiles
- Projectile/Weapons now have been made destroyable in-game.

### Horizontal Movement
- User's can now move horizontally in-game, with proper boundaries set to not exceed the dimension of the application.

### Non-hardcoded controls
- User's controls are now non-hardcoded, and it's stored in a `.properties` file under `<user>\Documents\SkyBattle_20617094`.

### New Levels
- One new level has been added to the game.

### Menu/Screens
- New screens have been introduced to the game to improve the user's experience.
- Examples are: `Main Menu, Pause Menu, Settings`, etc..

### Visual/Sound effects
- Elements above have been introduced to the game to improve user's experience.

## Implemented but Not Working Properly

### Dynamic Screen Size
- An attempt to make the screen size resizable was made, but there were a few bugs, which I think was significant enough to not be included in the final game.
- Majority of the elements are correctly resized, such as background size, health container, etc., are all working properly

#### Bugs
- When the screen is big (e.g: 1920x1080) and resized smaller (e.g: 1500x800), the actors that exceed the window boundary remains outside, and are not properly destroyed.
- This can lead to user dealing damage from enemies, projectiles not being properly freed in memory, and boss not being able to appear in the frame again.
- An attempt was made such that, when the case above happens, the current active actors (except user) that are beyond the dimension of the window, destroyed and respawned, but unfortunately no solution was found, and it was left unpatched.
- The attempt can be seen [here](https://github.com/YzENT/COMP2042_CW2024/commit/164bbeae69ce2bad5ab308b3067fbd5f3b3e82e7).

## Features Not Implemented

### Variable Frame Rate
- The frame rate of the game is determined by [LevelParent.java's](src/main/java/com/example/demo/Levels/LevelParent.java)
```
KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
```
- Theoretically, this gives us 1000/50 `MILLISECOND_DELAY` ≈ 20FPS
- By decreasing the value of `MILLISECOND_DELAY`, we should be able to boost the FPS
- However, when decreasing this value, the entire game speeds up as other logic variables are set/modified based on this value
- This means that the variable values are all hardcoded and the frame rate cannot be changed as is, a lot of the values have to be modified which just makes it improper to maintain if done
- V-sync was thought of to slow down the game while maintaining the speed of the game as is, unfortunately JavaFX doesn't seem to even support V-sync, and the idea was later dropped.

# New Java Classes

| New Classes                    | Description                                                                                      | Package                                                                                            |
|--------------------------------|--------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `UserControls.java`            | This class controls the user's action, mapping's are obtained from `Main.java`.                  | [com.example.demo.ActorsLogic](src/main/java/com/example/demo/ActorsLogic/UserControls.java)       |
| `ExplosionImage.java`          | This class initializes the explosion effect used for Plane_Boss.java when it's defeated.         | [com.example.demo.ImageEntities](src/main/java/com/example/demo/ImageEntities/ExplosionImage.java) |
| `Level_3.java`                 | The third level of the game. It is still the boss enemy as original, because level 2 was remade. | [com.example.demo.Levels](src/main/java/com/example/demo/Levels/Level_3.java)                      |
| `BaseScreen.java`              | Abstract class for other screens used in the application.                                        | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/BaseScreen.java)                 |
| `Screen_GameEnded.java`        | Screen used to display results of the game (VICTORY, DEFEAT).                                    | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/Screen_GameEnded.java)           |
| `Screen_LevelSelection.java`   | Screen used to select the level user wants to navigate to.                                       | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/Screen_LevelSelection.java)      |
| `Screen_LoadingAnimation.java` | Screen used for loading animation between levels.                                                | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/Screen_LoadingAnimation.java)    |
| `Screen_MainMenu.java`         | Screen used to display the Main Menu for user to navigate to different screens.                  | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/Screen_MainMenu.java)            |
| `Screen_PauseMenu.java`        | Screen displayed when user pauses the game in middle of game.                                    | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/Screen_PauseMenu.java)           |
| `Screen_Settings.java`         | Screen used to display the settings user can navigate to (Controls, Volume).                     | [com.example.demo.Screens](src/main/java/com/example/demo/Screens/Screen_Settings.java)            |
| `Controls.java`                | Screen used to display and modify the user's controls.                                           | [com.example.demo.Screens.Settings](src/main/java/com/example/demo/Screens/Settings/Controls.java) |
| `Volume.java`                  | Screen used to display and modify the game's volume (SFX, Music)                                 | [com.example.demo.Screens.Settings](src/main/java/com/example/demo/Screens/Settings/Volume.java)   |


# Renamed Java Classes

### com.example.demo.Actor.Plane
`FighterPlane.java` -> `Plane.java`

`Boss.java` -> `Plane_Boss.java`

`EnemyPlane.java` -> `Plane_Enemy.java`

`UserPlane.java` -> `Plane_User.java`

### com.example.demo.Actor.WeaponProjectiles
`Projectile.java` -> `Projectile.java`

`BossProjectile.java` -> `Projectile_Boss.java`

`EnemyProjectile.java` -> `Projectile_Enemy.java`

`UserProjectile.java` -> `Projectile_User.java`

# Deleted Java Classes

`LevelViewLevelTwo.java` -> merged into `LevelView.java` due to similar logic

`GameOverImage.java` & `WinImage.java` -> assets not used

# Modified Java Classes

## `com.example.demo.Actor`
- All images that belongs to this package has been resized and modified to improve hitbox dimensions.

### `Plane_Boss.java`
- Increased fire rate and movement rate to increase difficulty.
- Introduced cooldown before spawning when user first enters to level (user is exiting transition).
- Introduced minimum frames boss should have shield deactivated.
- Modified `takeDamage()` to show explosion and remove health bar when `health == 0`. 
- Modified `showShield()` to show shieldImage when it is created in `Plane_Boss.java`.
- Modified `hideShield()` to hide shieldImage.
- Modified `updateShield()` to sync position of shield with boss.
- Modified `shieldShouldBeActivated()` to check if minimum frames without shield is achieved too.
- Removed `getProjectileInitialPosition()` as code duplication from superclass.
- Created `updateExplosionCoordinates()` to sync position of explosion with boss.
  -  If not done so, the position of boss will be (0,0) when it is destroyed (null).
- Created `createHealthBar()` to create health bar showing boss' health.
- Created `updateHealthBar()` to update boss' health when it takes damage. Also updates position of health bar based on coordinates of boss.
- Created `triggerExplosion()` to show explosion when boss is defeated.
- Created `getBossXCoordinate()` and `getBossYCoordinate()` to obtain coordinate of the boss.

### `Plane_User.java`
- Modified `updatePosition()` to introduce bounds on X-coordinate.
- Modified `isMoving()` to also check for horizontalVelocity.
- Created `moveForward()` and `moveBackward()` for user to move horizontally.
- Created `stopHorizontalMovement()` to stop user's horizontal movement.

### `Projectile.java`
- Modified default constructor to accept arguments `(int) health, (int) horizontalVelocity`
  - `health` is passed here, so it can check and destroy if it's 0.
  - `horizontalVelocity` is passed here so abstract method `updatePosition()` can be undeclared and refactored.
- Modified `takeDamage()` to check if health is at 0. If it is then `destroy`.
- Undeclared `updatePosition()` from abstract method as all subclasses share the same code.
- Created `updateActor()` so it gets called every frame.
  - It checks if `projectile` has travelled too far, if true then destroy.
  - Also calls `updatePosition()` in this method.

### `Projectile_Boss.java`, `Projectile_Enemy.java`, `Projectile_User.java`
- Now passes health and horizontalVelocity to superclass `Projectile.java`.
- Moved both `updatePosition()` and `updateActor()` to superclass.

### `HeartDisplay.java`
- Created variable `Image HEART_IMAGE` to read from `String HEART_IMAGE_NAME`
- Modified `initializeHearts()` so that it doesn't have to initialize a new `HEART_IMAGE` everytime when it's called, just use static variable.

### `ShieldImage.java`
- Created `updateShieldPosition()` to update the shield's position based on boss' coordinates.

### `Controller.java`
- Removed `observer`.
- Most modifications made here are for elements that are behind the scenes.
- Modified `goToLevel()` to now have a transition when going to next level.
- Created `playBGM()`, `stopBGM()`, `pauseBGM()`, `resumeBGM()`, which are for `MediaPlayer` to play music.
- Created `playSFX()`, `stopSFX()`, which are for `AudioClip` to play short audios.
- Created getter and setter for `sfxVolume` and `musicVolume`.

### `Main.java`
- Introduced `keyBindings` variable to store key binds for user controls.
- Modified `start()` to now show `Screen_MainMenu` instead of going through `controller` and going to level directly.
- Created `ensureConfigFileExists()`, `createParentDirectory()`, `writeDefaultConfig()`, `setKeyBindings()` to store and write configs.

### `Level_1.java`
- `(int)KILLS_TO_ADVANCE` is now passed to superclass for logic check.
- `initializeFriendlyUnits()` is now moved to superclass due to code duplication.
- `userHasReachedKillTarget()` is now moved to superclass due to code duplication.
- Introduced spawn cooldown to since user will be exiting transition when stage is set.
  - Enemies are only spawned when the cooldown has reached 0.

### `Level_2.java`
- Same changes as `Level_1.java`, just increased difficulty.

### `Level_3.java` (the old LevelTwo.java)
- Similar changes to previous levels.
- Created `createBoss()` for easier readability
  - shieldImage, HealthBar, explosionImage are added to root through this method (boss was initiated at default constructor of this class back then)

### `LevelParent.java`
- Relationship with `UserControls.java` to pass keys/user logic in this class to that class.
  - Moved `fireProjectile()`
  - Moved key press/release actions
- Modified default constructor to accept `(int)KILLS_TO_ADVANCE`.
  - `userHasReachedKillTarget()` in subclasses has been moved to here.
- Undeclared `initializeFriendlyUnits()` from abstract due to code duplication.
- Modified `initializeScene()` to send `Runnable` actions to `Screen_PauseMenu`, as well as initializing other elements (`song, kill count`).
- Modified `initializeBackground()` to handle key press/release in another function.
- Modified `updateLevelView()` to update kill count too.
- Removed `updateKillCount()` and placed it under `handleUserProjectileCollisions()` so kill count only increases if user's projectile hit enemy, provided enemy is destroyed.
- Merged `winGame()` and `loseGame()` to `gameStatus()` to handle game's status.
- Modified `goToNextLevel()` as it's now not an observer under `controller` anymore.
- Modified `handleCollisions()` to accept `Runnable` as an argument, so code that meets the requirements can be executed afterward. (Shake Screen, Play SFX, etc...).
- Created `pauseGame()` and `resumeGame()` to handle pause logic.
- Created `shakeScreen()` when user collides with enemy projectile.
- Created `handleKeyPressed()` and `handleKeyReleased()` to handle key actions.

### `LevelView.java`
- Removed logics/creations related to WinImage and GameOverImage.
- Created `initializeKillCounter()` and `updateKillCounter()` to display kill count of user at top right of screen.
- Created `screenFade()` for transition visual's.

# Unexpected Problems
1. When first forking and running the project, the game crashes when it tries to go to the next level. After further debugging, the issues were:
 - Incorrect shield image extension. It was "../shield.jpg" in the source code, while the actual file extension was "shield.png".
 - The timeline of the level wasn't being stopped properly, as advancing to the next level triggered neither `winGame()` or `loseGame()` to execute `timeline.stop()`. This code was then added to `checkIfGameOver()` afterward and switching levels got smooth.

2. Weird kill count logic
 - While refactoring the code, it was discovered that the kill count was increasing based on how many enemy units were disappearing.
 - This meant that if enemy unit penetrates the boundary, the kill count will still increase, because the enemy has disappeared.
 - It was then modified so that the kill count would only be increased if user's projectile collide with an enemy unit.

3. Shield Image not showing
 - After connecting `ShieldImage.java` and `Plane_Boss.java` together, the shield image was not showing still.
 - Messages at the console indicated that the shield was activated, but nothing appeared on screen.
 - The background was then hidden to test if the shield was being rendered behind, and it indeed was.
 - The shield image was then only initialized if when it's toggled, provided it's not found at the root.

4. Weird Hitbox dimensions
 - The images originally had a lot of whitespace, which caused this weird hitbox issue. The images may not seem to be overlapping, but it was then detected as a collision.
 - Bounding boxes were drawn around the actors, and it was found that it had a lot of whitespace.
 - The whitespaces were then removed and proper scaling was applied to the code to accept these changes.

5. Pause Menu closes the entire application when using `stage.hide()` or `stage.close()`
 - Due to the way `Screen_PauseMenu` is set-up, originally it was supposed to be closed with `stage.hide()` or `stage.close()`, but that led to closing the entire application.
 - After further research, it was due to the menu being the main stage of the screen, so if that closes then it closes the entire application.
 - To further solidify the issue, a new stage was created when pause menu was initialized, and it opened a new window. When using `stage.hide()`, there were no problems at all and the game kept running.
 - The solution to this was to pass the entire gameScene to pauseMenu, then after the user clicks on the resume button, pauseMenu has to set the scene back to gameScene.

6. Script (start.bat) not able to accept .json properly
 - `.json` file was supposed to be the default format to store the keyBindings for the game, and `jackson-core` was used.
 - It worked properly launching through the IDE, but when launching through `start.bat`, the application wouldn't work properly as it couldn't detect one of the dependencies, nor could it install it.
 - It was then decided to use `.properties` file instead to store the key bindings.