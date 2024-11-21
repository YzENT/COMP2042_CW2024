package com.example.demo.Initialize;

import com.example.demo.JavaFXBaseTesting;
import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class ControllerTest {

    private Controller controller;
    private Stage stage;
    private static final int MILLISECOND_DELAY = 50; //this needs a delay as mediaPlayer can't execute immediately, natural delay

    @BeforeEach
    void setUp() {
        controller = new Controller(stage);
    }

    @Test
    void playBGM() throws InterruptedException {
        controller.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        assertNotNull(Controller.getMediaPlayer(), "MediaPlayer should be initialized after playBGM is called");

        //need to pause for a while because it doesn't start playing immediately
        Thread.sleep(MILLISECOND_DELAY);
        assertEquals(MediaPlayer.Status.PLAYING, Controller.getMediaPlayer().getStatus(),"MediaPlayer should be playing BGM");
    }

    @Test
    void stopBGM() throws InterruptedException {
        controller.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        Thread.sleep(MILLISECOND_DELAY);
        controller.stopBGM();
        assertNull(Controller.getMediaPlayer(), "MediaPlayer should be null after stopBGM is called");
    }

    @Test
    void pauseBGM() throws InterruptedException {
        controller.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        Thread.sleep(MILLISECOND_DELAY);
        controller.pauseBGM();
        Thread.sleep(MILLISECOND_DELAY);
        assertEquals(MediaPlayer.Status.PAUSED, Controller.getMediaPlayer().getStatus(), "MediaPlayer should be paused after pauseBGM is called");
    }

    @Test
    void resumeBGM() throws InterruptedException {
        controller.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        Thread.sleep(MILLISECOND_DELAY);
        controller.pauseBGM();
        Thread.sleep(MILLISECOND_DELAY);
        controller.resumeBGM();
        Thread.sleep(MILLISECOND_DELAY);
        assertEquals(MediaPlayer.Status.PLAYING, Controller.getMediaPlayer().getStatus(), "MediaPlayer should be playing after resumeBGM is called");
    }

    @Test
    void playSFX() {
        controller.playSFX("/com/example/demo/audio/sfx/metal pipe falling.mp3");
        assertNotNull(controller.getAudioClip(), "AudioClip should be initialized after playSFX is called");
    }

    @Test
    void stopSFX() {
        controller.playSFX("/com/example/demo/audio/sfx/metal pipe falling.mp3");
        controller.stopSFX();
        assertNull(controller.getAudioClip(), "AudioClip should be null after stopSFX is called");
    }

    @Test
    void setVolumes() {
        Controller.setMusicVolume(0.5);
        assertEquals(0.5, Controller.getMusicVolume(), "Music volume should be set to 0.5");

        Controller.setSfxVolume(0.5);
        assertEquals(0.5, Controller.getSfxVolume(), "SFX volume should be set to 0.5");
    }

}