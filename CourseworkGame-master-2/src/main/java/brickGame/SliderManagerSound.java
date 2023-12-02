package brickGame;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;

public class SliderManagerSound {
    public static SliderManagerSound instance;
    private Slider soundController;
    private SoundManager soundManager;


private SliderManagerSound() {
    soundController = new Slider(0, 1, 0.5);
    soundController.setOrientation(Orientation.HORIZONTAL);
    soundController.setPrefSize(250, 20);
    soundController.setTranslateX(150);
    soundController.setTranslateY(325);
    soundController.getStyleClass().add("custom-volume-slider");
    soundController.setVisible(true);

    soundManager = SoundManager.getInstance();
    soundManager.setSoundSlider(soundController);

    soundController.valueProperty().addListener((observable, oldValue, newValue) -> {
        double volume = newValue.doubleValue();
        if(soundManager != null) {
            soundManager.setVolume(volume);
        }
    });
}

public static SliderManagerSound getInstance() {
    if(instance == null) {
        instance = new SliderManagerSound();
    }
    return instance;
}

public Slider getSoundController() {
    return soundController;
}

public void setSoundController(Slider soundController){
    this.soundController = soundController;

    if(soundManager != null) {
        soundManager.setVolume(soundController.getValue());
    }
}

}
