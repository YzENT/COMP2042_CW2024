package com.example.demo.Initialize;

import com.example.demo.Screens.Screen_MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static int width = 1300;
	private static int height = 750;
	private static final String TITLE = "Sky Battle";
	private static Stage primaryStage;

	@Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException {
		primaryStage = stage;
		stage.setTitle(TITLE);
		stage.setResizable(true);
		stage.setHeight(height);
		stage.setWidth(width);
		stage.setMinHeight(height);
		stage.setMinWidth(width);

		//listeners
		stage.widthProperty().addListener((obs, oldVal, newVal) -> setWidth(newVal.intValue()));
		stage.heightProperty().addListener((obs, oldVal, newVal) -> setHeight(newVal.intValue()));

		Screen_MainMenu screenMainMenu = new Screen_MainMenu(stage, width, height);
		screenMainMenu.show();
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int newWidth) {
		width = newWidth;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int newHeight) {
		height = newHeight;
	}

	public static Stage getStage() {
		return primaryStage;
	}

	public static int originalWidth() {
		return 1300;
	}

	public static int originalHeight() {
		return 750;
	}

}