package brickGame;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;

public class SliderManagerBgMusic {
    public static SliderManagerBgMusic instance;
    private Slider bgMusicController;
    private BgMusicManager bgMusicManager;

    private SliderManagerBgMusic() {
        bgMusicController = new Slider(0, 1, 0.5);
        bgMusicController.setOrientation(Orientation.HORIZONTAL);
        bgMusicController.setPrefSize(250, 20);
        bgMusicController.setTranslateX(150);
        bgMusicController.setTranslateY(280);
        bgMusicController.getStyleClass().add("custom-volume-slider");
        bgMusicController.setVisible(true);

        bgMusicManager = BgMusicManager.getInstance();
        bgMusicManager.setBgMusicSlider(bgMusicController);

        bgMusicController.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue();
            if (bgMusicManager != null) {
                bgMusicManager.setVolume(volume);
            }
        });

    }

        public static SliderManagerBgMusic getInstance() {
            if (instance == null) {
                instance = new SliderManagerBgMusic();
            }
            return instance;
        }

    public Slider getBgMusicController() {
        return bgMusicController;

    }

    public void setBgMusicController(Slider bgMusicController) {
        this.bgMusicController = bgMusicController;

        if (bgMusicManager != null) {
            bgMusicManager.setVolume(bgMusicController.getValue());
        }
    }
    
}
