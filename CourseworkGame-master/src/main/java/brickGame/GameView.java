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

public class GameView {
    private Stage primaryStage;
    private Scene scene;

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

    private Rectangle pauseMenu;

    public GameView(Stage primaryStage, int Score, int Level, int Heart) {
        this.primaryStage = primaryStage;
        initializeUI(Score, Level, Heart);
    }

    private void initializeUI(int Score, int Level, int Heart) {
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

    private void initLabels(int Score, int Level, int Heart) {
        pauseLabel = createLabel("PAUSE", "bigText", "-fx-font-size: 24px", 180, 200);
        settingLabel = createLabel("SETTINGS", "bigText", "-fx-font-size: 24px", 145, 200);
        scoreLabel = createLabel("SCORE: " + Score, "text", "-fx-font-size: 12px", 0, 0);
        levelLabel = createLabel("LEVEL: "+Level, "text", "-fx-font-size: 12px", 0, 30);
        heartLabel = createLabel("X"+Heart, "text", "-fx-font-size: 12px" , 445, 15);
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