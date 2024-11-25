package com.example.demo.Controller;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import com.example.demo.Screens.Screen_MainMenu;

/**
 * Main class to initialize and start the Sky Battle application.
 */
public class Main extends Application {

	/**
	 * The width of the screen.
	 */
	private static final int SCREEN_WIDTH = 1300;

	/**
	 * The height of the screen.
	 */
	private static final int SCREEN_HEIGHT = 750;

	/**
	 * The title of the application window.
	 */
	private static final String TITLE = "Sky Battle";

	/**
	 * The primary stage for this application.
	 */
	private static Stage stage;

	/**
	 * The key bindings for the application.
	 */
	private static Map<String, KeyCode> keyBindings;

	/**
	 * The path to the configuration file for key bindings.
	 */
	private static final String CONFIG_PATH = System.getProperty("user.home") + "/Documents/SkyBattle_20617094/keyConfigs.properties";

	/**
	 * Starts the application and sets up the main stage.
	 *
	 * @param stage the primary stage for this application
	 * @throws SecurityException if a security manager exists and its checkPermission method doesn't allow the operation
	 * @throws IllegalArgumentException if the specified stage is null
	 */
	@Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException {
		Main.stage = stage;
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		stage.show(); // this is to show the application window

		ensureConfigFileExists();
		setKeyBindings();

		Screen_MainMenu screenMainMenu = new Screen_MainMenu(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
		screenMainMenu.show(); // this is to show the UI
	}

	/**
	 * The main method to launch the application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Ensures that the configuration file for key bindings exists.
	 * If it does not exist, creates a default configuration file.
	 */
	public void ensureConfigFileExists() {
		File configFile = new File(CONFIG_PATH);
		if (configFile.exists() && configFile.length() > 0) return;

		try {
			createParentDirectory(configFile);
			writeDefaultConfig(configFile);
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error creating default key configuration file: " + e.getMessage());
			alert.show();
			alert.setOnCloseRequest(event -> System.exit(-1));
		}
	}

	/**
	 * Creates the parent directory for the configuration file if it does not exist.
	 *
	 * @param configFile the configuration file
	 * @throws IOException if an I/O error occurs
	 */
	private void createParentDirectory(File configFile) throws IOException {
		File parentDir = configFile.getParentFile();
		if (!parentDir.exists() && !parentDir.mkdirs()) {
			throw new IOException("Failed to create directories: " + parentDir.getAbsolutePath());
		}
	}

	/**
	 * Writes the default key bindings to the configuration file.
	 *
	 * @param configFile the configuration file
	 * @throws IOException if an I/O error occurs
	 */
	private void writeDefaultConfig(File configFile) throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
			writer.println("Fire=SPACE");
			writer.println("Move_DOWN=S");
			writer.println("Move_LEFT=A");
			writer.println("Move_RIGHT=D");
			writer.println("Move_UP=W");
			writer.println("Pause=ESCAPE");
		}
	}

	/**
	 * Sets the key bindings by loading them from the configuration file.
	 */
	public void setKeyBindings() {
		Properties properties = new Properties();

		try (InputStream inputStream = new FileInputStream(CONFIG_PATH)) {
			properties.load(inputStream);

			keyBindings = properties.entrySet().stream()
					.collect(Collectors.toMap(
							e -> (String) e.getKey(),
							e -> KeyCode.valueOf(((String) e.getValue()).toUpperCase())
					));

		} catch (IOException | IllegalArgumentException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error loading key bindings: " + e.getMessage());
			alert.show();
			alert.setOnCloseRequest(event -> System.exit(-1));
		}
	}

	/**
	 * Gets the screen width.
	 *
	 * @return the screen width
	 */
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	/**
	 * Gets the screen height.
	 *
	 * @return the screen height
	 */
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	/**
	 * Gets the primary stage.
	 *
	 * @return the primary stage
	 */
	public static Stage getStage() {
		return stage;
	}

	/**
	 * Gets the key bindings.
	 *
	 * @return the key bindings map
	 */
	public static Map<String, KeyCode> getKeyBindings() {
		return keyBindings;
	}

	/**
	 * Gets the configuration file path.
	 *
	 * @return the configuration file path
	 */
	public static String getConfigPath() {
		return CONFIG_PATH;
	}
}