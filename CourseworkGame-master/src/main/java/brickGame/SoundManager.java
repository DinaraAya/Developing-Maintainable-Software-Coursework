package brickGame;

import javafx.scene.media.Media;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    public  static SoundManager instance;
    private static MediaPlayer levelUpPlayer;
    private static MediaPlayer hitBlockPlayer;
    private static MediaPlayer buttonPlayer;
    private static MediaPlayer gameOverPlayer;
    private double volume = 0.5;
    private Slider soundSlider;

    public static SoundManager getInstance() {
        if(instance == null) {
           instance = new SoundManager();
        }
        return instance;
    }

    static {
        Media buttonSound = new Media(SoundManager.class.getResource("/button.mp3").toString());
        buttonPlayer = new MediaPlayer(buttonSound);

        Media gameOverSound = new Media(SoundManager.class.getResource("/gameover.wav").toString());
        gameOverPlayer = new MediaPlayer(gameOverSound);

        Media bonusSound = new Media(SoundManager.class.getResource("/bonus.wav").toString());
        hitBlockPlayer = new MediaPlayer(bonusSound);
    }

    public void setSoundSlider(Slider soundSlider) {
        this.soundSlider = soundSlider;
    }

    public static void playGameOverSound() {
        gameOverPlayer.stop();
        gameOverPlayer.play();
    }

    public static void playButtonSound() {
        buttonPlayer.stop();
        buttonPlayer.play();
    }

    public static void playLevelUpSound() {
        levelUpPlayer.stop();
        levelUpPlayer.play();
    }

    public static void playBonusSound() {
        hitBlockPlayer.play();
    }

    public void setVolume(double volume) {
        this.volume = volume;
        buttonPlayer.setVolume(volume);
        gameOverPlayer.setVolume(volume);
        hitBlockPlayer.setVolume(volume);
    }
}

