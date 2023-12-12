package brickGame;


import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class GameController implements EventHandler <KeyEvent>{
    
    private GameModel gameModel;
    private GameView gameView;
    private int LEFT = 1;
    private int RIGHT = 2;
    private int countDown = 3;
    private Slider           bgMusicSlider;
    private Slider           soundSlider;
    private BgMusicManager bgMusicManager;
    private SliderManagerBgMusic sliderManager;
    private SliderManagerSound sliderManagerSound;
    private Timeline        timeline;

    private Observable breakObservable = new Observable();

    public GameController(GameModel model, GameView view) {
        gameModel = model;
        gameView = view;
        bgMusicManager = BgMusicManager.getInstance();
        if (!bgMusicManager.isPlaying()) {
            bgMusicManager.initialize("/bgMusic.mp3");
            bgMusicManager.play();
        }

        if (sliderManager == null) {
            sliderManager = SliderManagerBgMusic.getInstance();
            bgMusicSlider = sliderManager.getBgMusicController();
        }

        if(sliderManagerSound == null) {
            sliderManagerSound = SliderManagerSound.getInstance();
            soundSlider = sliderManagerSound.getSoundController();
        }

        gameView.getScene("scene").setOnKeyPressed(this);
        initButtonActions();

        gameModel.observable.xBreakProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getRectangle("rect").setX(newValue.doubleValue());
        });

        gameModel.observable.yBreakProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getRectangle("rect").setY(newValue.doubleValue());
        });

        gameModel.observable.xBallProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getBall().setCenterX(newValue.doubleValue());
        });
        gameModel.observable.yBallProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getBall().setCenterY(newValue.doubleValue());
        });

        gameModel.observable.scoreProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getLabel("scoreLabel").setText("SCORE: " + newValue);
        });

        gameModel.observable.heartProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getLabel("heartLabel").setText("X"+newValue);
        });

        gameModel.observable.levelProperty().addListener((obs, oldValue, newValue) -> {
            gameView.getLabel("levelLabel").setText("LEVEL: "+newValue);
        }); 
    }

    public Observable getBreakObservable() {
        return breakObservable;
    }

    @Override
    public void handle(KeyEvent event) {
        if(event!=null) {
            switch (event.getCode()) {
                case LEFT:
                    gameModel.move(LEFT);
                    break;
                case RIGHT:
                    gameModel.move(RIGHT);
                    break;
                case S:
                    gameModel.saveGame();
                    break;
                default:
            }
        }
    }


    public void initButtonActions() {
        gameView.getButton("newGame").setOnAction(event-> {
            SoundManager.playButtonSound();
            gameView.showGameScreen();
            gameModel.addBlocksToRoot();
            gameView.getRoots("root").getChildren().addAll(gameView.getButton("pause"));
            gameView.getButton("pause").setFocusTraversable(false);
            countDownDisplay();
        });

        gameView.getButton("load").setOnAction(event-> {
            SoundManager.playButtonSound();
            gameView.showGameScreen();
            gameView.getRoots("root").getChildren().addAll(gameView.getButton("pause"));
            gameModel.loadGame();
        });

        gameView.getButton("settings").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameView.slideUpAnimation(gameView.getRoots("settingRoot"));
            gameView.showSettingScreen();
            gameView.getRoots("settingRoot").getChildren().addAll(bgMusicSlider, soundSlider);
        });

        gameView.getButton("pause").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameModel.stopEngine();
            gameView.getRoots("pauseRoot").getChildren().addAll(gameView.getRectangle("pauseMenu"), gameView.getLabel("pauseLabel"), gameView.getButton("restart"), gameView.getButton("home"), gameView.getButton("resume"), bgMusicSlider, soundSlider);
            gameView.slideUpAnimation(gameView.getRoots("pauseRoot"));
            gameView.showPauseScreen();
        });

        gameView.getButton("restart").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameView.getRoots("pauseRoot").getChildren().removeAll(gameView.getRectangle("pauseMenu"), gameView.getLabel("pauseLabel"), gameView.getButton("restart"), gameView.getButton("home"), gameView.getButton("resume"), bgMusicSlider, soundSlider);
            gameModel.restartGame();
            gameView.resumeGame();
            initButtonActions();
            
        });

        gameView.getButton("restartGameover").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameModel.goHome();
            gameView.showMainMenu();
            gameModel.initBoard();
            gameView.getScene("scene").setOnKeyPressed(this);
            initButtonActions();
        });

        gameView.getButton("home").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameModel.goHome();
            gameView.showMainMenu();
            gameModel.initBoard();
            gameView.getScene("scene").setOnKeyPressed(this);
            initButtonActions();
        });

        gameView.getButton("resume2").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameView.getRoots("settingRoot").getChildren().removeAll(bgMusicSlider, soundSlider);
            gameView.resumeToMainMenu();
        });
        
        gameView.getButton("resume").setOnAction(event -> {
            SoundManager.playButtonSound();
            gameView.getRoots("pauseRoot").getChildren().removeAll(gameView.getRectangle("pauseMenu"), gameView.getLabel("pauseLabel"), gameView.getButton("restart"), gameView.getButton("home"), gameView.getButton("resume"), bgMusicSlider, soundSlider);
            gameView.resumeGame();
            gameModel.startEngine();
        });

        gameView.getButton("quit").setOnAction(event-> {
            Platform.exit();
        });
    }

    private void countDownDisplay() {
        countDown = 3;
        gameView.getLabel("countDownLabel").setVisible(true);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gameView.getLabel("countDownLabel").setText(Integer.toString(countDown));
                    countDown--;

                    if (countDown < 0) {
                        gameView.getLabel("countDownLabel").setVisible(false);
                        timeline.stop();
                        gameModel.startEngine();
                    }
                }
            })
    );
    timeline.setCycleCount(4); 
    timeline.play();
    }

}
