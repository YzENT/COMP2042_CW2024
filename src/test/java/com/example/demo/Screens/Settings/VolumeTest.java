package com.example.demo.Screens.Settings;

import com.example.demo.Initialize.Controller;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class VolumeTest {

    private Volume volume;
    private Stage stage;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage = new Stage();
            volume = new Volume(stage, 800, 600);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testCreateSlider_UpdatesVolumeOnChange() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            HBox sliderBox = volume.createSlider("Music", 0.5, Controller::setMusicVolume);

            Slider slider = (Slider) sliderBox.getChildren().get(1);
            slider.setValue(0.8);

            assertEquals(0.8, slider.getValue(), 0.01, "Slider value should be 0.8");
            assertEquals(0.8, Controller.getMusicVolume(), 0.01, "Music volume should be 0.8");
            latch.countDown();
        });
        latch.await();
    }

}