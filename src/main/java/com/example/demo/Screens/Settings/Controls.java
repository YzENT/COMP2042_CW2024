package com.example.demo.Screens.Settings;

import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.Initialize.Main;
import com.example.demo.Screens.Screen_Settings;

public class Controls extends Screen_Settings {

    private static final String TITLE_TEXT = "Control Settings";
    private static final double TITLE_SIZE = 50;
    private static String prevScreen;
    private EventHandler<KeyEvent> activeKeyListener = null;
    private final Map<String, KeyCode> tempKeyBindings = new HashMap<>(Main.getKeyBindings());

    //keys that need to go through modifier (Shift + 1 = !), are ignored.
    private static final Map<KeyCode, String> SYMBOL_MAP = Map.ofEntries(
            Map.entry(KeyCode.BACK_QUOTE, ""),
            Map.entry(KeyCode.MINUS, "-"),
            Map.entry(KeyCode.EQUALS, "="),
            Map.entry(KeyCode.OPEN_BRACKET, "["),
            Map.entry(KeyCode.CLOSE_BRACKET, "]"),
            Map.entry(KeyCode.BACK_SLASH, "\\"),
            Map.entry(KeyCode.SEMICOLON, ";"),
            Map.entry(KeyCode.QUOTE, "'"),
            Map.entry(KeyCode.COMMA, ","),
            Map.entry(KeyCode.PERIOD, "."),
            Map.entry(KeyCode.SLASH, "/"),
            Map.entry(KeyCode.RIGHT, "→"),
            Map.entry(KeyCode.LEFT, "←"),
            Map.entry(KeyCode.UP, "↑"),
            Map.entry(KeyCode.DOWN, "↓")
    );

    public Controls(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        super(stage, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        Text title = initializeTitle(TITLE_TEXT, TITLE_SIZE);
        Button[] buttons = initializeButtons();
        VBox bindingBoxes = initializeBindingSection();
        Text instructions = initializeInstructions();

        HBox bottomButtons = new HBox(20, buttons);
        bottomButtons.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(50);
        vbox.getChildren().addAll(title, bindingBoxes, bottomButtons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");

        StackPane root = new StackPane();
        StackPane.setAlignment(instructions, Pos.BOTTOM_LEFT);

        root.getChildren().addAll(vbox, instructions);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.getScene().getRoot().requestFocus();
        disableMouseInput(scene);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (activeKeyListener != null) return;
            if (event.getCode() == KeyCode.R) {
                resetKeybinds();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                goScreen_PreviousScreen();
            }
        });
    }

    @Override
    protected Button[] initializeButtons() {
        Button saveButton = createButton("Save", this::saveKeyBindings);
        Button backButton = createButton("Back", this::goScreen_PreviousScreen);
        return new Button[]{saveButton, backButton};
    }

    private VBox initializeBindingSection() {
        VBox keyBindingArea = new VBox();
        keyBindingArea.setAlignment(Pos.CENTER);
        keyBindingArea.setMaxWidth(400);
        keyBindingArea.setMaxHeight(400);

        //reads from main
        Main.getKeyBindings().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            String action = entry.getKey();
            KeyCode key = entry.getValue();

            HBox keyBindingRow = new HBox(20);
            keyBindingRow.setAlignment(Pos.CENTER);

            //moveUp, moveDown, etc..
            Label actionLabel = new Label(action);
            actionLabel.setStyle("-fx-font-family: '" + fontName + "'; " +
                    "-fx-font-size: 20px; " +
                    "-fx-text-fill: yellow;");
            actionLabel.setAlignment(Pos.CENTER_LEFT);

            //value of keys
            TextField keyField = new TextField(SYMBOL_MAP.getOrDefault(key, key.getName()).toUpperCase());
            keyField.setPrefWidth(100);
            keyField.setEditable(false); //user cannot manually edit
            styleKeys_Normal(keyField);
            keyField.setFocusTraversable(true);
            keyField.setAlignment(Pos.CENTER_RIGHT);

            HBox.setHgrow(actionLabel, Priority.NEVER);
            HBox.setHgrow(keyField, Priority.ALWAYS);
            setupFocusListener(keyField);

            keyField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    startKeyCapture(keyField, action);
                }
                if (event.getCode() == KeyCode.R) {
                    resetKeybinds();
                }
            });

            keyBindingRow.getChildren().addAll(actionLabel, keyField);
            keyBindingArea.getChildren().addAll(keyBindingRow);
        });
        return keyBindingArea;
    }

    private Text initializeInstructions() {
        Text instructionsText = new Text("TAB to Navigate\nENTER to Edit\nR to Restore Defaults");
        instructionsText.setFill(Color.WHITE);
        instructionsText.setFont(arcadeFont);
        instructionsText.setStyle("-fx-font-size: 15px; ");

        return instructionsText;
    }

    protected void startKeyCapture(TextField keyField, String action) {
        Scene scene = stage.getScene();
        stage.getScene().getRoot().requestFocus();
        styleKeys_Editing(keyField);

        //if current one is active then remove to prevent double capture
        if (activeKeyListener != null) {
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, activeKeyListener);
        }

        activeKeyListener = new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {

                KeyCode newKey = event.getCode();

                //ignore enter
                if (newKey == KeyCode.ENTER) {
                    return;
                }

                //keycode: 255(only found 1 atm)
                if (newKey == KeyCode.UNDEFINED) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid key: Please choose another key.");
                    alert.show();
                    return;
                }

                //handle certain special symbols
                String displayText = SYMBOL_MAP.getOrDefault(newKey, newKey.getName().toUpperCase());
                keyField.setText(displayText);
                tempKeyBindings.put(action, newKey);

                //reset status
                styleKeys_Normal(keyField);
                removeEffect(keyField);
                scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                activeKeyListener = null;
                keyField.requestFocus();
            }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED, activeKeyListener);
    }

    private void saveKeyBindings() {
        //only put to main if saved is clicked
        Main.getKeyBindings().clear();
        Main.getKeyBindings().putAll(tempKeyBindings);

        try (PrintWriter writer = new PrintWriter(new FileWriter(Main.getConfigPath()))) {
            tempKeyBindings.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> writer.println(entry.getKey() + "=" + entry.getValue().toString()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Key bindings saved successfully.");
            alert.show();
            alert.setOnCloseRequest(event -> goScreen_PreviousScreen());

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error saving key bindings: " + e.getMessage());
            alert.show();
        }
    }

    private void goScreen_PreviousScreen() {
        //revert content if it's not saved
        tempKeyBindings.clear();
        tempKeyBindings.putAll(Main.getKeyBindings());
        goScreenPrevious(prevScreen);
    }

    public static void setPrevScreen(String previousClassSimplifiedName) {
        prevScreen = previousClassSimplifiedName;
    }

    private void styleKeys_Normal(Node node) {
        node.setStyle("-fx-font-family: '" + fontName + "'; " +
                "-fx-font-size: 20px; " +
                "-fx-text-fill: red; " +
                "-fx-background-color: transparent; " +
                "-fx-border-width: 0;");
    }

    private void styleKeys_Editing(Node node) {
        node.setStyle("-fx-font-family: '" + fontName + "'; " +
                "-fx-font-size: 20px; " +
                "-fx-text-fill: #90EE90; " +
                "-fx-background-color: transparent; " +
                "-fx-border-width: 0;");
        addEffect(node);
    }

    protected void resetKeybinds() {
        tempKeyBindings.clear();
        tempKeyBindings.put("Fire", KeyCode.SPACE);
        tempKeyBindings.put("Move_DOWN", KeyCode.S);
        tempKeyBindings.put("Move_LEFT", KeyCode.A);
        tempKeyBindings.put("Move_RIGHT", KeyCode.D);
        tempKeyBindings.put("Move_UP", KeyCode.W);
        tempKeyBindings.put("Pause", KeyCode.ESCAPE);
        saveKeyBindings();
    }

}
