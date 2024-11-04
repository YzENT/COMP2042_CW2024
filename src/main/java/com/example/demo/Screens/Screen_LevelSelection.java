package com.example.demo.Screens;

import com.example.demo.Initialize.ResourceStarter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

public class Screen_LevelSelection extends BaseScreen{

    private final ResourceStarter resourceStarter;

    public Screen_LevelSelection(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.resourceStarter = new ResourceStarter(this.stage);
    }

    @Override
    public void show() {

        Text title = new Text("Select Level");


        Button level1Button = new Button("Level 1");
        Button level2Button = new Button("Level 2");

        level1Button.setOnAction(e -> startLevel("com.example.demo.Levels.Level_1"));
        level2Button.setOnAction(e -> startLevel("com.example.demo.Levels.Level_2"));

        VBox vbox = new VBox(50, title, level1Button, level2Button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
    }

    private void startLevel(String levelClassName) {
        try {
            resourceStarter.goToLevel(levelClassName);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }
}
