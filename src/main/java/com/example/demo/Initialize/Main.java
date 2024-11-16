package com.example.demo.Initialize;

import com.example.demo.Screens.Screen_MainMenu;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private static Stage stage;

	private static Map<String, KeyCode> keyBindings;
	private static final String CONFIG_PATH = System.getProperty("user.home") + "/Documents/SkyBattle_20617094/keyConfigs.properties";

	@Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException{
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		stage.show(); //this is to show the application window
		Main.stage = stage;

		ensureConfigFileExists();
		setKeyBindings();

		Screen_MainMenu screenMainMenu = new Screen_MainMenu(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
		screenMainMenu.show(); //this is to show the UI
	}

	private void ensureConfigFileExists() {
		File configFile = new File(CONFIG_PATH);

		if (!configFile.exists() || configFile.length() == 0) {
			try {
				File parentDir = configFile.getParentFile();
				if (!parentDir.exists() && !parentDir.mkdirs()) {
					throw new IOException("Failed to create directories: " + parentDir.getAbsolutePath());
				}

				try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
					writer.println("moveUp=W");
					writer.println("moveDown=S");
					writer.println("moveLeft=A");
					writer.println("moveRight=D");
					writer.println("fire=SPACE");
					writer.println("pause=ESCAPE");
				}
			} catch (IOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Error creating default key configuration file: " + e.getMessage());
				alert.show();
				alert.setOnCloseRequest(event -> System.exit(-1));
			}
		}
	}

	private void setKeyBindings() {
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

	public static void main(String[] args) {
		launch();
	}

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Map<String, KeyCode> getKeyBindings() {
		return keyBindings;
	}
}
