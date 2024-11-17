package com.example.demo.Screens.Settings;

import com.example.demo.Initialize.Main;
import com.example.demo.Screens.Screen_Settings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Controls extends Screen_Settings {

    private static final String TITLE_TEXT = "Control Settings";
    private static final double TITLE_SIZE = 100;
    private static String prevScreen;
    private EventHandler<KeyEvent> activeKeyListener = null;

    public Controls(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();
        VBox bindingBox = initializeBindingSection();

        HBox buttonBox = new HBox(20, buttons);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(50);
        vbox.getChildren().addAll(title, bindingBox, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vbox, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);

//        scene.removeEventFilter(MouseEvent.ANY, MouseEvent::consume);
    }

    @Override
    protected Button[] initializeButtons() {
        Button saveButton = createButton("Save", this::saveKeyBindings);
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{saveButton, backButton};
    }

    private VBox initializeBindingSection() {
        VBox keyBindingsBox = new VBox(20);
        keyBindingsBox.setAlignment(Pos.CENTER);
        keyBindingsBox.setPadding(new Insets(20));

        Main.getKeyBindings().forEach((action, key) -> {
            HBox keyBindingRow = new HBox(10);
            keyBindingRow.setAlignment(Pos.CENTER);

            Label actionLabel = new Label(action);
            actionLabel.setTextFill(Color.WHITE);
            actionLabel.setFont(Font.font("ArcadeClassic", 20));

            TextField keyField = new TextField(key.getName());
            keyField.setPrefWidth(100);
            keyField.setEditable(false); //no manual edit
            keyField.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
            keyField.setFocusTraversable(false);
            keyField.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);

            //temp, use enter to edit later
            Button captureButton = new Button("Capture");
            captureButton.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: darkgray;");
            captureButton.setOnAction(e -> startKeyCapture(keyField, action));

            keyBindingRow.getChildren().addAll(actionLabel, keyField, captureButton);
            keyBindingsBox.getChildren().add(keyBindingRow);
        });
        return keyBindingsBox;
    }

    private void startKeyCapture(TextField keyField, String action) {
        Scene scene = stage.getScene();
        stage.getScene().getRoot().requestFocus(); //if not then spacebar and enter will get consumed

        //if current one is active then remove to prevent double capture
        if (activeKeyListener != null) {
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, activeKeyListener);
        }

        activeKeyListener = new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode newKey = event.getCode();

                if (newKey == KeyCode.UNDEFINED) { //FN key(only found 1 atm)
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid key: Please choose another key.");
                    alert.show();
                    return;
                }

                keyField.setText(newKey.getName());
                Main.getKeyBindings().put(action, newKey);

                scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                activeKeyListener = null;
            }
        };

        scene.addEventHandler(KeyEvent.KEY_PRESSED, activeKeyListener);
    }

    private void saveKeyBindings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Main.getConfigPath()))) {
            Main.getKeyBindings().forEach((action, key) -> writer.println(action + "=" + key.toString()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Key bindings saved successfully.");
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error saving key bindings: " + e.getMessage());
            alert.show();
        }
    }

    private void goScreen_PreviousScreen() {
        goScreenPrevious(prevScreen);
    }

    public static void setPrevScreen(String previousClassSimplifiedName) {
        prevScreen = previousClassSimplifiedName;
    }
}
