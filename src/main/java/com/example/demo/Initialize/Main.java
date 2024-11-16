package com.example.demo.Initialize;

import com.example.demo.Screens.Screen_MainMenu;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private static Stage stage;

	//user controls
	private static Map<String, KeyCode> keyBindings;
	private static final String config_Path = "/com/example/demo/keyConfigs.properties";

	@Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException{
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		stage.show(); //this is to show the application window
		Main.stage = stage;
		setKeyBindings();

		Screen_MainMenu screenMainMenu = new Screen_MainMenu(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
		screenMainMenu.show(); //this is to show the UI
	}

	private void setKeyBindings() {
		Properties properties = new Properties();

		try (InputStream inputStream = getClass().getResourceAsStream(config_Path)) {
			if (inputStream == null) {
				throw new FileNotFoundException("Key configuration file not found.");
			}
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
