package brickGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private GameView gameView;
    private GameModel gameModel;
    private GameController gameController;

    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    Stage  primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameView = new GameView(primaryStage);
        gameModel = new GameModel(gameView);
        gameController = new GameController(gameModel, gameView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
