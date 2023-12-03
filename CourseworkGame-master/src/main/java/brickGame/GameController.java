package brickGame;

import javafx.stage.Stage;

public class GameController {
    private GameModel gameModel;
    private GameView gameView;
    private Stage primaryStage;

    public GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gameModel = new GameModel();
        this.gameView = new GameView(primaryStage, 0, 1, 3);
    }

    private void initButtonActions() {
        gameView.getButton("load").setOnAction(event -> {
            
        });
    }



//     private void initButtonActions() {
//         gameView.getButton("newGame").setOnAction(event -> {

//         });

//         gameView.getButton("load").setOnAction(event -> {

//         });

//         gameView.getButton("settings").setOnAction(event -> {

//         });

//         gameView.getButton("quit").setOnAction(event -> {

//         });

//         gameView.getButton("pause").setOnAction(event -> {

//         });

//         gameView.getButton("restart").setOnAction(event -> {

//         });

//         gameView.getButton("home").setOnAction(event -> {

//         });

//         gameView.getButton("resume").setOnAction(event-> {

//         });

//         gameView.getButton("resume2").setOnAction(event -> {

//         });


//     }

//     // public void restartGame() {
//     //     // model.restartGame();
//     // }
}
