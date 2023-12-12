package brickGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Label;
//import sun.plugin2.message.Message;

public class Score {
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
            
            //FIX
            // main.root.getChildren().add(label);

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


    public void showGameOver(final GameView gameView, final GameModel gameModel, int score) {
        Platform.runLater(() -> {

            int highestScore = readHighestScore();
            if(score > highestScore) {
                highestScore = score;
                saveHighestScore(highestScore);
            }

            gameView.updateGameOverScore(score, highestScore);
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
                // main.root.getChildren().addAll(label);

            }
        });
    }
}

