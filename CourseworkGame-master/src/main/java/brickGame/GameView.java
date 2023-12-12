package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;
import javafx.scene.Parent;
import javafx.animation.TranslateTransition;
import javafx.geometry.Orientation;
import javafx.util.Duration;

public class GameView {
    private Stage            primaryStage;

    private Circle ball;
    private int       ballRadius = 10;
    private Rectangle rect;

    private Button loadButton;
    private Button newGameButton;
    private Button settingsButton;
    private Button quitButton;

    private Button homeButton;
    private Button restartButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button resumeButton2;
    private Button restartFromGameOver;

    private ImageView heartImageView;

    private Label pauseLabel;
    private Label settingLabel;
    private Label scoreLabel;
    private Label levelLabel;
    private Label heartLabel;
    private Label winLabel;
    private Label gameOverLabel;
    private Label highScoreLabel;
    private Label score1;
    private Label score2;
    private Label countDownLabel;

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

    private int breakWidth     = 130;
    private int breakHeight    = 30;

    private double xBreak = 0.0f;
    private double yBreak = 640.0f;

    private Rectangle pauseMenu;
    private Rectangle highScoreRect;
    private Rectangle scoreRect;

    public GameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeUI();
        
        root.getChildren().addAll(ball, rect, newGameButton, settingsButton, quitButton, loadButton, heartImageView, scoreLabel, heartLabel, levelLabel);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void initializeUI() {
        initRoots();
        initScene();
        initButtons();
        initRectangles();
        initLabels();
        initImageView();
        initBall();
        initBreak();
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
        restartFromGameOver = createCustomButton(65, 55, 210, 420, restartButtonImage);
    }

    private void initRoots() {
        root = new Pane();
        pauseRoot = new Pane();
        settingRoot = new Pane();
        gameOverRoot = new Pane(); 
    }

    public void initBall() {
        this.ball = new Circle();
        this.ball.setRadius(ballRadius);
        this.ball.setFill(new ImagePattern(new Image("ball1.png")));
    }

    public void initBreak() {
        this.rect = new Rectangle();
        this.rect.setWidth(breakWidth);
        this.rect.setHeight(breakHeight);
        this.rect.setX(xBreak);
        this.rect.setY(yBreak);
        ImagePattern pattern = new ImagePattern(new Image("ground.png"));
        this.rect.setFill(pattern);
    }

    public void showGameScreen() {
        root.getChildren().add(countDownLabel);
        root.getChildren().removeAll(loadButton, newGameButton, settingsButton, quitButton);
    }

    public void showPauseScreen(){
        primaryStage.setScene(pauseScene);
    }

    public void showMainMenu() {
        initializeUI();
        root.getChildren().addAll(ball, rect, newGameButton, settingsButton, quitButton, loadButton, heartImageView, scoreLabel, heartLabel, levelLabel);
        primaryStage.setScene(scene);
    }



    public void showSettingScreen() {
        settingRoot.getChildren().addAll(pauseMenu, resumeButton2, settingLabel);
        primaryStage.setScene(settingScene);
    }

    public void showGameOverScreen() {
        gameOverRoot.getChildren().addAll(scoreRect, highScoreRect, gameOverLabel, highScoreLabel, score1, score2, restartFromGameOver);
        primaryStage.setScene(gameOverScene);
    }

    public void resumeToMainMenu() {
        settingRoot.getChildren().removeAll(pauseMenu, resumeButton2, settingLabel);
        primaryStage.setScene(scene);
    }

    public void resumeGame(){
        primaryStage.setScene(scene);
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

    private void initRectangles() {
        Image pauseMenuImage = new Image("pauseMenu.png");
        pauseMenu = createRectangle(345, 130, 75, 250, pauseMenuImage);
        Image highScoreImage = new Image("highscore.png");
        highScoreRect = createRectangle(200, 130, 40, 260, highScoreImage);
        Image scoreImage = new Image("score.png");
        scoreRect = createRectangle(200, 130, 248, 260, scoreImage);
    }

    private void initLabels() {
        pauseLabel = createLabel("PAUSE", "bigText", "-fx-font-size: 24px", 180, 200);
        settingLabel = createLabel("SETTINGS", "bigText", "-fx-font-size: 24px", 145, 200);
        scoreLabel = createLabel("SCORE: 0", "text", "-fx-font-size: 12px", 0, 0);
        levelLabel = createLabel("LEVEL: 1", "text", "-fx-font-size: 12px", 0, 30);
        heartLabel = createLabel("X3", "text", "-fx-font-size: 12px" , 445, 15);
        winLabel = createLabel("YOU WIN", "bigText", "-fx-font-size: 24px", 200, 250);
        gameOverLabel = createLabel("GAMEOVER", "bigText", "-fx-font-size: 24px", 150, 210);
        highScoreLabel = createLabel("SCORE          HIGH SCORE", "text", "fx-font-size: 16px", 100, 260);
        score1 = createLabel(" ", "bigText", "-fx-font-size: 36px", 100, 310);
        score2 = createLabel(" ", "bigText", "-fx-font-size: 36px", 300, 310);
        countDownLabel = createLabel(" ", "bigText", "-fx-font-size: 36px", sceneWidth / 2 - 20, sceneHeigt/2);
    }

    private void initImageView() {
        Image heartImage = new Image("lives.png");
        heartImageView = createImageView(400, 10, 40, 40, heartImage);
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

    public void slideUpAnimation(Parent root) {
        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.5), root);
        slideUp.setFromY(sceneHeigt);
        slideUp.setToY(0);
        slideUp.play();
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
            case "restartGameover":
                return restartFromGameOver;
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
            case "gameOverLabel":
                return gameOverLabel;
            case "highScoreLabel":
                return highScoreLabel;
            case "score1":
                return score1;
            case "score2":
                return score2;
            case "countDownLabel":
                return countDownLabel;
            default:
                return null;
        }
    }

    public Rectangle getRectangle(String rectangleId) {
        switch (rectangleId) {
            case "pauseMenu":
                return pauseMenu;
            case "scoreRect":
                return scoreRect;
            case "highScoreRect":
                return highScoreRect;
            case "rect":
                return rect;
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

    public Circle getBall(){
        return ball;
    }

    public void updateGameOverScore(int score, int highestScore) {
        this.score1.setText("" + score);
        this.score2.setText("" + highestScore);
    }

}