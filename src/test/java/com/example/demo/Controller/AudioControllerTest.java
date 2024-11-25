package com.example.demo.Controller;

import com.example.demo.JavaFXBaseTesting;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXBaseTesting.class)
class AudioControllerTest {

    private AudioController audioController;
    private static final int MILLISECOND_DELAY = 50; //this needs a delay as mediaPlayer can't execute immediately, natural delay

    @BeforeEach
    void setUp() {
        audioController = new AudioController();
    }

    @Test
    void playBGM() throws InterruptedException {
        audioController.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        assertNotNull(AudioController.getMediaPlayer(), "MediaPlayer should be initialized after playBGM is called");

        //need to pause for a while because it doesn't start playing immediately
        Thread.sleep(MILLISECOND_DELAY);
        assertEquals(MediaPlayer.Status.PLAYING, AudioController.getMediaPlayer().getStatus(),"MediaPlayer should be playing BGM");
    }

    @Test
    void stopBGM() throws InterruptedException {
        audioController.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        Thread.sleep(MILLISECOND_DELAY);
        audioController.stopBGM();
        assertNull(AudioController.getMediaPlayer(), "MediaPlayer should be null after stopBGM is called");
    }

    @Test
    void pauseBGM() throws InterruptedException {
        audioController.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        Thread.sleep(MILLISECOND_DELAY);
        audioController.pauseBGM();
        Thread.sleep(MILLISECOND_DELAY);
        assertEquals(MediaPlayer.Status.PAUSED, AudioController.getMediaPlayer().getStatus(), "MediaPlayer should be paused after pauseBGM is called");
    }

    @Test
    void resumeBGM() throws InterruptedException {
        audioController.playBGM("/com/example/demo/audio/bgm/cinematic-epic-237173.mp3");
        Thread.sleep(MILLISECOND_DELAY);
        audioController.pauseBGM();
        Thread.sleep(MILLISECOND_DELAY);
        audioController.resumeBGM();
        Thread.sleep(MILLISECOND_DELAY);
        assertEquals(MediaPlayer.Status.PLAYING, AudioController.getMediaPlayer().getStatus(), "MediaPlayer should be playing after resumeBGM is called");
    }

    @Test
    void playSFX() {
        audioController.playSFX("/com/example/demo/audio/sfx/metal pipe falling.mp3");
        assertNotNull(audioController.getAudioClip(), "AudioClip should be initialized after playSFX is called");
    }

    @Test
    void stopSFX() {
        audioController.playSFX("/com/example/demo/audio/sfx/metal pipe falling.mp3");
        audioController.stopSFX();
        assertNull(audioController.getAudioClip(), "AudioClip should be null after stopSFX is called");
    }

    @Test
    void setVolumes() {
        AudioController.setMusicVolume(0.5);
        assertEquals(0.5, AudioController.getMusicVolume(), "Music volume should be set to 0.5");

        AudioController.setSfxVolume(0.5);
        assertEquals(0.5, AudioController.getSfxVolume(), "SFX volume should be set to 0.5");
    }

}