package brickGame;

import javafx.scene.media.Media;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    // private static MediaPlayer bonusPlayer;
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
        // // Load sound files
        // Media bonusSound = new Media(SoundManager.class.getResource("/bonus_sound.wav").toString());
        // bonusPlayer = new MediaPlayer(bonusSound);

        Media buttonSound = new Media(SoundManager.class.getResource("/button.mp3").toString());
        buttonPlayer = new MediaPlayer(buttonSound);

        Media gameOverSound = new Media(SoundManager.class.getResource("/gameover.wav").toString());
        gameOverPlayer = new MediaPlayer(gameOverSound);

        // Media levelUpSound = new Media(SoundManager.class.getResource("/levelup.wav").toString());
        // levelUpPlayer = new MediaPlayer(levelUpSound);

        Media hitBlockSound = new Media(SoundManager.class.getResource("/kick.wav").toString());
        hitBlockPlayer = new MediaPlayer(hitBlockSound);
    }

    // public static void playBonusSound() {
    //     bonusPlayer.stop();
    //     bonusPlayer.play();
    // }

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

    public static void playHitBlockSound() {
        hitBlockPlayer.stop();
        hitBlockPlayer.play();
    }

    public void setVolume(double volume) {
        this.volume = volume;
        buttonPlayer.setVolume(volume);
        gameOverPlayer.setVolume(volume);
        hitBlockPlayer.setVolume(volume);
    }
}

