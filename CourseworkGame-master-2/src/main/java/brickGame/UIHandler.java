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

public class UIHandler {
    private Stage primaryStage;
    private Scene scene;

    private Button load;
    private Button newGame;
    private Button settings;
    private Button quit;

    private Button homeButton;
    private Button restartButton;
    private Button pause;
    private Button resumeButton;
    private Button resumeButton2;

    private ImageView heartImageView;

//     private Rectangle pauseMenu;

    private Label pauseLabel;
    private Label scoreLabel;
//     private Label levelLabel;
//     private Label heartLabel;
    private Label settingLabel;

    private Rectangle pauseMenu;

public UIHandler(Stage primaryStage) {
    this.primaryStage = primaryStage;
    initializeUI();
}

private void initializeUI() {
    initButtons();
    initLabels();
    initRectangles();
    initImageView();
}

// public void updateScoreUI(int newScore) {
//     Platform.runLater(() -> {
//         scoreLabel.setText("SCORE: " + newScore);
//     });
// }


private void initButtons() {
    newGame = createButton("NEW GAME", 200, 50, 145, 250);
    load = createButton("LOAD GAME", 200, 50, 145, 310);
    settings = createButton("SETTINGS", 200, 50, 145, 370);
    quit = createButton("QUIT", 200, 50, 145, 430);

    Image homeButtonImage = new Image("homeButton.png");
    homeButton = createCustomButton(65, 55, 320, 405, homeButtonImage);
    Image restartButtonImage = new Image("restart.png");
    restartButton = createCustomButton(65, 55, 110, 405, restartButtonImage);
    Image pauseButtonImage = new Image("pauseButton.png");
    pause = createCustomButton(55, 45, 220, 5, pauseButtonImage);
    Image resumeButtonImage = new Image("resume.png");
    resumeButton = createCustomButton(75, 65, 210, 400, resumeButtonImage);
    resumeButton2 = createCustomButton(75, 65, 210, 400, resumeButtonImage);
}

private void initLabels(){
    pauseLabel = createLabel("PAUSE", "bigText", "-fx-font-size: 24px", 180, 200);
    settingLabel = createLabel("SETTINGS", "bigText", "-fx-font-size: 24px", 145, 200);

    // scoreLabel = new Label("SCORE: 0");
    // scoreLabel.getStyleClass().add("text");
    
    // levelLabel = new Label("LEVEL: " + level);
    // levelLabel.setTranslateY(30);
    // levelLabel.getStyleClass().add("text");
    
    // heartLabel = new Label("X" + heart);
    // heartLabel.setTranslateY(15);
    // heartLabel.setTranslateX(445);
    // heartLabel.getStyleClass().add("text");
}

private void initImageView(){
    Image heartImage = new Image("lives.png");
    heartImageView = createImageView(400, 10, 40, 40, heartImage);
}

private void initRectangles(){
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

private ImageView createImageView(double translateX, double translateY, double width, double height, Image image){
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
            return load;
        case "newGame":
            return newGame;
        case "settings":
            return settings;
        case "pause":
            return pause;
        case "quit":
            return quit;
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
    switch(labelId) {
        case "pauseLabel":
            return pauseLabel; 
        case "settingLabel":
            return settingLabel;
        // case "scoreLabel":
        //     return scoreLabel;
        default:
            return null;
    }
}

public Rectangle getRectangle(String rectangleId) {
    switch(rectangleId) {
        case "pauseMenu":
           return pauseMenu;
        default:
           return null;
    }
}

public ImageView getImageView(String imageId) {
    switch(imageId) {
        case "heartImageView":
           return heartImageView;
        default:
           return null;
    }
}
}