package brickGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;



//import sun.plugin2.message.Message;

public class Score {
    private Rectangle highScoreRect;
    private Rectangle scoreRect;

    private Stage primaryStage;
    

    public void show(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        // Platform.runLater(new Runnable() {
        //     @Override
        //     public void run() {
        //         main.root.getChildren().add(label);
        //     }
        // });

        
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         for (int i = 0; i < 21; i++) {
        //             try {
        //                 label.setScaleX(i);
        //                 label.setScaleY(i);
        //                 label.setOpacity((20 - i) / 20.0);
        //                 Thread.sleep(15);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //         }
        //         main.root.getChildren().remove(label);
        //     }
        // }).start();
    }

    public void showMessage(String message, final Main main) {
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setTranslateX(220);
            label.setTranslateY(340);
            
            main.root.getChildren().add(label);

            new Thread(() -> {
                for (int i = 0; i < 21; i++) {
                    try {
                        final int finalI = i;
                        Platform.runLater(() -> {
                            label.setScaleX(Math.abs(finalI - 10));
                            label.setScaleY(Math.abs(finalI - 10));
                            label.setOpacity((20 - finalI) / 20.0);
                        });
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }

    private int readHighestScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getHighestScore() {
        return readHighestScore();
    }


    public void showGameOver(final Main main, int score) {
        Platform.runLater(() -> {

            int highestScore = readHighestScore();
            if(score > highestScore) {
                highestScore = score;
                saveHighestScore(highestScore);
            }

            Label label = new Label("GAME OVER");
            label.getStyleClass().add("bigText");
            label.setTranslateX(180);
            label.setTranslateY(210);
            label.setScaleX(2);
            label.setScaleY(2);

            Image highScoreImage = new Image("highscore.png");
            ImagePattern highScorePattern = new ImagePattern(highScoreImage);
            highScoreRect = new Rectangle(200, 130);
            highScoreRect.setTranslateY(260);
            highScoreRect.setTranslateX(40);
            highScoreRect.setFill(highScorePattern);

            Image scoreImage = new Image("score.png");
            ImagePattern scoreImagePattern = new ImagePattern(scoreImage);
            scoreRect = new Rectangle(200, 130);
            scoreRect.setTranslateY(260);
            scoreRect.setTranslateX(248);
            scoreRect.setFill(scoreImagePattern);

            Label highScoreLabel = new Label("SCORE");
            highScoreLabel.getStyleClass().add("text");
            highScoreLabel.setStyle("-fx-font-size: 16px");
            highScoreLabel.setTranslateX(90);
            highScoreLabel.setTranslateY(260);

            Label score1 = new Label(String.valueOf(score));
            score1.getStyleClass().add("bigText");
            score1.setStyle("-fx-font-size: 36px");
            score1.setTranslateX(105);
            score1.setTranslateY(310);

            Label score2 = new Label(String.valueOf(highestScore));
            score2.getStyleClass().add("bigText");
            score2.setStyle("-fx-font-size: 36px");
            score2.setTranslateX(310);
            score2.setTranslateY(310);

            Label ScoreLabel = new Label("BEST");
            ScoreLabel.getStyleClass().add("text");
            ScoreLabel.setStyle("-fx-font-size: 16px");
            ScoreLabel.setTranslateX(310);
            ScoreLabel.setTranslateY(260);
            
            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.goHome());

            Image restartImage = new Image("restart.png");
            ImageView restartView = new ImageView(restartImage);
            restart = new Button("");
            restart.setId("pause-button");
            restart.setPrefSize(65, 55);
            restart.setTranslateX(210);
            restart.setTranslateY(420);
            restart.setGraphic(restartView);

            restart.setOnAction(event -> {
                main.goHome();
            });

            main.gameOverRoot.getChildren().addAll(label, highScoreRect, scoreRect, highScoreLabel, ScoreLabel, score1, score2, restart);
        });
    }

    private void saveHighestScore(int highestScore) {
        try(FileWriter writer = new FileWriter("highscore.txt")) {
            writer.write(String.valueOf(highestScore));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void showWin(final Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label("You Win :)");
                label.setTranslateX(200);
                label.setTranslateY(250);
                label.setScaleX(2);
                label.setScaleY(2);
                main.root.getChildren().addAll(label);

            }
        });
    }
}

