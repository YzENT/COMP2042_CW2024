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
- Users now can only navigate the application using keyboard, <b>ALL</b> mouse events are disabled.

### Transition and Animations
- Transition and animations have been added to the game to aid the user's visual experience. Such transitions are used in changing game screen, finishing the game, etc..
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
- Theoratically, this gives us 1000/50 `MILLISECOND_DELAY` ≈ 20FPS
- By decreasing the value of `MILLISECOND_DELAY`, we should be able to boost the FPS
- However, when decreasing this value, the entire game speeds up as other logic variables are set/modified based on this value
- This means that the variable values are all hardcoded and the frame rate cannot be changed as is, a lot of the values have to be modified which just makes it improper to maintain if done
- V-sync was thought of to slow down the game while maintaining the speed of the game as is, unfortunately JavaFX doesn't seem to even support V-sync, and the idea was later dropped.

# New Java Classes