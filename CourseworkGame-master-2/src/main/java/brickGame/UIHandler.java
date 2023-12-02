package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UIHandler {
    private Stage primaryStage;
    private Pane root;
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

    private Rectangle pauseMenu;

    private Label pauseLabel;
    private Label scoreLabel;
    private Label levelLabel;
    private Label heartLabel;
    private Label settingLabel;

    private int Heart;
    private int Level;
    private int Score;

public UIHandler(Stage primaryStage, int Heart, int Level, int Score) {
    this.primaryStage = primaryStage;
    initializeUI();
}

private void initializeUI() {
    initButtons();
    initLabels();
}

private void initButtons() {
    load = createButton("LOADGAME", 200, 50, 145, 370);
    newGame = createButton("NEW GAME", 200, 50, 145, 250);
    settings = createButton("SETTINGS", 200, 50, 145, 310);
    quit = createButton("QUIT", 200, 50, 145, 430);

    Image homeButtonImage = new Image("homeButton.png");
    homeButton = createCustomButton(65, 55, 320, 405, homeButtonImage);
    Image restartButtonImage = new Image("restart.png");
    restartButton = createCustomButton(65, 55, 110, 405, restartButtonImage);
    Image pauseButtonImage = new Image("pauseButton.png");
    pause = createCustomButton(55, 45, 220, 5, pauseButtonImage);
    Image resumeButtonImage = new Image("resume.png");
    resumeButton = createCustomButton(75, 65, 210, 400, resumeButtonImage);
    Image resumeButtonImage2 = new Image("resume.png");
    resumeButton2 = createCustomButton(75, 65, 210, 400, resumeButtonImage);
}

public Button getButton(Button button){
    return button;
}

public Label getLabel(Label label) {
    return label;
}

private void initLabels(){
    pauseLabel = createLabel("PAUSE", "bigText", "-fx-font-size: 24px", 180, 200);
    settingLabel = createLabel("SETTINGS", "bigText", "-fx-font-size: 24px", 158, 200);

    scoreLabel = new Label("SCORE: " + Score);
    scoreLabel.getStyleClass().add("text");
    
    levelLabel = new Label("LEVEL: " + Level);
    levelLabel.setTranslateY(30);
    levelLabel.getStyleClass().add("text");
    
    heartLabel = new Label("X" + Heart);
    heartLabel.setTranslateY(15);
    heartLabel.setTranslateX(445);
    heartLabel.getStyleClass().add("text");
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

private Label createLabel(String text, String styleclass, String style, double translateX, double translateY) {
    Label label = new Label(text);
    label.getStyleClass().add(styleclass);
    label.setStyle(style);
    label.setTranslateX(translateX);
    label.setTranslateY(translateY);
    return label;
}

}