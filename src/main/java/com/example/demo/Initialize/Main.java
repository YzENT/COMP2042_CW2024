package com.example.demo.Initialize;

import com.example.demo.Screens.Screen_MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private static Stage stage;

	@Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException{
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		Screen_MainMenu screenMainMenu = new Screen_MainMenu(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
		screenMainMenu.show();
		stage.show();
		Main.stage = stage;
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
}
