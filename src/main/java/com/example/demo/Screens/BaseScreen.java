package com.example.demo.Screens;

import javafx.stage.Stage;

public abstract class BaseScreen {

    protected final Stage stage;
    protected final int SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT;

    public BaseScreen(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.stage = stage;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public abstract void show();
}
