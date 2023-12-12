package brickGame;

import javafx.scene.control.Slider;
import javafx.scene.media.*;

public class BgMusicManager {
    public static BgMusicManager instance;
    private MediaPlayer mediaPlayer;
    private double volume = 0.5;
    private Slider bgMusicSlider;

    public static BgMusicManager getInstance(){
        if(instance == null){
            instance = new BgMusicManager();
        }
        return instance;
    }

    public void initialize(String bgMusicFile) {
        Media bgMusicMedia = new Media(getClass().getResource(bgMusicFile).toString());
        mediaPlayer = new MediaPlayer(bgMusicMedia);
        mediaPlayer.setVolume(volume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

     public void setBgMusicSlider(Slider bgMusicSlider) {
        this.bgMusicSlider = bgMusicSlider;
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setVolume(double volume) {
        this.volume = volume; 
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

}
