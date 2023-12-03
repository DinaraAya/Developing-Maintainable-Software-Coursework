package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.control.Slider;


public class GameView {
    private Stage primaryStage;

    private Slider           bgMusicSlider;
    private Slider           soundSlider;

    private Button loadButton;
    private Button newGameButton;
    private Button settingsButton;
    private Button quitButton;

    private Button homeButton;
    private Button restartButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button resumeButton2;

    private ImageView heartImageView;

    private Label pauseLabel;
    private Label settingLabel;
    private Label scoreLabel;
    private Label levelLabel;
    private Label heartLabel;
    private Label winLabel;

    public Pane root;
    public Pane settingRoot;
    public Pane pauseRoot;
    public Pane gameOverRoot;

    private Scene settingScene;
    private Scene pauseScene;
    private Scene gameOverScene;
    private Scene scene;

    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private Rectangle pauseMenu;

    public GameView(Stage primaryStage, int Score, int Level, int Heart) {
        this.primaryStage = primaryStage;
        initializeUI(Score, Level, Heart);
    }

    private void initializeUI(int Score, int Level, int Heart) {
        initRoots();
        initScene();
        initButtons();
        initLabels(Score, Level, Heart);
        initRectangles();
        initImageView();
    }

    private void initButtons() {
        newGameButton = createButton("NEW GAME", 200, 50, 145, 250);
        loadButton = createButton("LOAD GAME", 200, 50, 145, 310);
        settingsButton = createButton("SETTINGS", 200, 50, 145, 370);
        quitButton = createButton("QUIT", 200, 50, 145, 430);

        Image homeButtonImage = new Image("homeButton.png");
        homeButton = createCustomButton(65, 55, 320, 405, homeButtonImage);
        Image restartButtonImage = new Image("restart.png");
        restartButton = createCustomButton(65, 55, 110, 405, restartButtonImage);
        Image pauseButtonImage = new Image("pauseButton.png");
        pauseButton = createCustomButton(55, 45, 220, 5, pauseButtonImage);
        Image resumeButtonImage = new Image("resume.png");
        resumeButton = createCustomButton(75, 65, 210, 400, resumeButtonImage);
        resumeButton2 = createCustomButton(75, 65, 210, 400, resumeButtonImage);
    }

    private void initRoots() {
        root = new Pane();
        
        pauseRoot = new Pane();

        settingRoot = new Pane();

        gameOverRoot = new Pane();
        
    }

    private void initScene() {
        scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");

        pauseScene = new Scene(pauseRoot, sceneWidth, sceneHeigt);
        pauseScene.getStylesheets().addAll("style.css");

        settingScene = new Scene(settingRoot, sceneWidth, sceneHeigt);
        settingScene.getStylesheets().add("style.css");

        gameOverScene = new Scene(gameOverRoot, sceneWidth, sceneHeigt);
        gameOverScene.getStylesheets().add("style.css");
    }

    private void initLabels(int Score, int Level, int Heart) {
        pauseLabel = createLabel("PAUSE", "bigText", "-fx-font-size: 24px", 180, 200);
        settingLabel = createLabel("SETTINGS", "bigText", "-fx-font-size: 24px", 145, 200);
        scoreLabel = createLabel("SCORE: " + Score, "text", "-fx-font-size: 12px", 0, 0);
        levelLabel = createLabel("LEVEL: "+Level, "text", "-fx-font-size: 12px", 0, 30);
        heartLabel = createLabel("X"+Heart, "text", "-fx-font-size: 12px" , 445, 15);
        winLabel = createLabel("YOU WIN", "bigText", "-fx-font-size: 24px", 200, 250);
    }

    private void initImageView() {
        Image heartImage = new Image("lives.png");
        heartImageView = createImageView(400, 10, 40, 40, heartImage);
    }

    private void initRectangles() {
        Image pauseMenuImage = new Image("pauseMenu.png");
        pauseMenu = createRectangle(345, 130, 75, 250, pauseMenuImage);
    }

    private Button createButton(String text, double width, double height, double translateX, double translateY) {
        Button button = new Button(text);
        button.setId("button");
        button.setPrefSize(width, height);
        button.setTranslateX(translateX);
        button.setTranslateY(translateY);
        return button;
    }

    private Button createCustomButton(double width, double height, double translateX, double translateY, Image image) {
        Button button = new Button("");
        ImageView imageView = new ImageView(image);
        button.setId("pause-button");
        button.setPrefSize(width, height);
        button.setTranslateX(translateX);
        button.setTranslateY(translateY);
        button.setGraphic(imageView);
        return button;
    }

    private Rectangle createRectangle(double width, double height, double translateX, double translateY, Image image) {
        Rectangle rectangle = new Rectangle(width, height);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setTranslateX(translateX);
        rectangle.setTranslateY(translateY);
        rectangle.setFill(imagePattern);
        return rectangle;
    }

    private Label createLabel(String text, String styleclass, String style, double translateX, double translateY) {
        Label label = new Label(text);
        label.getStyleClass().add(styleclass);
        label.setStyle(style);
        label.setTranslateX(translateX);
        label.setTranslateY(translateY);
        return label;
    }

    private ImageView createImageView(double translateX, double translateY, double width, double height, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(translateX);
        imageView.setTranslateY(translateY);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public Pane getRoots (String rootId) {
        switch (rootId) {
            case "root":
                return root;
            case "pauseRoot":
                return pauseRoot;
            case "settingRoot":
                return settingRoot;
            case "gameOverRoot":
                return gameOverRoot;
            default :
                return null;
        }
    }

    public Scene getScene (String sceneId) {
        switch(sceneId) {
            case "scene":
                return scene;
            case "pauseScene":
                return pauseScene;
            case "settingScene":
                return settingScene;
            case "gameOverScene":
                return gameOverScene;
            default:
                return null;
        }
    }

    public Button getButton(String buttonId) {
        switch (buttonId) {
            case "load":
                return loadButton;
            case "newGame":
                return newGameButton;
            case "settings":
                return settingsButton;
            case "pause":
                return pauseButton;
            case "quit":
                return quitButton;
            case "home":
                return homeButton;
            case "restart":
                return restartButton;
            case "resume":
                return resumeButton;
            case "resume2":
                return resumeButton2;
            default:
                return null;
        }
    }

    public Label getLabel(String labelId) {
        switch (labelId) {
            case "pauseLabel":
                return pauseLabel;
            case "settingLabel":
                return settingLabel;
            case "scoreLabel":
                return scoreLabel;
            case "levelLabel":
                return levelLabel;
            case "heartLabel":
                return heartLabel;
            case "winLabel":
                return winLabel;
            default:
                return null;
        }
    }

    public Rectangle getRectangle(String rectangleId) {
        switch (rectangleId) {
            case "pauseMenu":
                return pauseMenu;
            default:
                return null;
        }
    }

    public ImageView getImageView(String imageId) {
        switch (imageId) {
            case "heartImageView":
                return heartImageView;
            default:
                return null;
        }
    }

    public void updateLabel(int score){
        this.scoreLabel.setText("SCORE: "+ score);
    }

    public void updateLevel(int level){
        this.levelLabel.setText("LEVEL: "+level);
    }

    public void updateHeart(int heart){
        this.heartLabel.setText("X"+heart);
    }

}