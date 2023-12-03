package brickGame;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileNotFoundException;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class GameModel {
    private GameView gameView;
    private GameEngine engine;

    private boolean loadFromSave;
    private Stage primaryStage;

    private int sceneHeigt = 700;
    private int sceneWidth = 500;

    private int level;
    private int heart;
    private int score;
    private int destroyedBlockCount;
    private boolean goDownBall;
    private boolean isGamePaused;
    private boolean isGoldStauts;
    private boolean isExistHeartBlock;
    private long hitTime;
    private long time;
    private long goldTime;


    private Circle ball;

    private double xBall;
    private double yBall;
    private double xBreak = 0.0f;
    private double centerBreakX;
    private double yBreak = 640.0f;

    private int breakHeight    = 30;
    private int breakWidth     = 130;

    private double vX = 1.000;
    private double vY = 1.000;

    private int       ballRadius = 10;

    private Main main;

    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();

    public boolean          colideToBreak;
    public boolean          colideToBreakAndMoveToRight;
    public boolean          colideToRightWall;
    public boolean          colideToLeftWall;
    public boolean          colideToRightBlock;
    public boolean          colideToBottomBlock;
    public boolean          colideToLeftBlock;
    public boolean          colideToTopBlock;

    private boolean goRightBall                 = true;


    // @Override
    // public void onPhysicsUpdate() {
    //         checkDestroyedCount();
    //         setPhysicsToBall();

    //     if (time - goldTime > 5000) {
    //         ball.setFill(new ImagePattern(new Image("ball1.png")));
    //         main.root.getStyleClass().remove("goldRoot");
    //         isGoldStauts = false;
    //     }

    //     for (Bonus choco : chocos) {
    //         if (choco.y > sceneHeigt || choco.taken) {
    //             continue;
    //         }
    //         if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
    //             System.out.println("You Got it and +3 score for you");
    //             choco.taken = true;
    //             choco.choco.setVisible(false);
    //             score += 3;
    //             new Score().show(choco.x, choco.y, 3, this);
    //         }
    //         choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
    //     }
    //     //System.out.println("time is:" + time + " goldTime is " + goldTime);
    // }

    // private void setPhysicsToBall() {
    //     //v = ((time - hitTime) / 1000.000) + 1.000;

    //     if (goDownBall) {
    //         yBall += vY;
    //     } else {
    //         yBall -= vY;
    //     }

    //     if (goRightBall) {
    //         xBall += vX;
    //     } else {
    //         xBall -= vX;
    //     }

    //     if (yBall <= 0) {
    //         //vX = 1.000;
    //         resetColideFlags();
    //         goDownBall = true;
    //         return;
    //     }
    //     if (yBall >= sceneHeigt) {
    //         goDownBall = false;
    //         if (!isGoldStauts) {
    //             //TODO gameover
    //             heart--;
    //             new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);

    //             if (heart == 0) {
    //                 new Score().showGameOver(this, score);
    //                 SoundManager.playGameOverSound();
    //                 primaryStage.setScene(gameOverScene);
    //                 engine.stop();
    //             }

    //         }
    //         //return;
    //     }

    //     if (yBall >= yBreak - ballRadius) {
    //         //System.out.println("Colide1");
    //         if (xBall >= xBreak && xBall <= xBreak + breakWidth) {
    //             hitTime = time;
    //             resetColideFlags();
    //             colideToBreak = true;
    //             goDownBall = false;

    //             double relation = (xBall - centerBreakX) / (breakWidth / 2);

    //             if (Math.abs(relation) <= 0.3) {
    //                 //vX = 0;
    //                 vX = Math.abs(relation);
    //             } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
    //                 vX = (Math.abs(relation) * 1.5) + (level / 3.500);
    //                 //System.out.println("vX " + vX);
    //             } else {
    //                 vX = (Math.abs(relation) * 2) + (level / 3.500);
    //                 //System.out.println("vX " + vX);
    //             }

    //             if (xBall - centerBreakX > 0) {
    //                 colideToBreakAndMoveToRight = true;
    //             } else {
    //                 colideToBreakAndMoveToRight = false;
    //             }
    //             //System.out.println("Colide2");
    //         }
    //     }

    //     if (xBall >= sceneWidth) {
    //         resetColideFlags();
    //         //vX = 1.000;
    //         colideToRightWall = true;
    //     }

    //     if (xBall <= 0) {
    //         resetColideFlags();
    //         //vX = 1.000;
    //         colideToLeftWall = true;
    //     }

    //     if (colideToBreak) {
    //         if (colideToBreakAndMoveToRight) {
    //             goRightBall = true;
    //         } else {
    //             goRightBall = false;
    //         }
    //     }

    //     //Wall Colide

    //     if (colideToRightWall) {
    //         goRightBall = false;
    //     }

    //     if (colideToLeftWall) {
    //         goRightBall = true;
    //     }

    //     //Block Colide

    //     if (colideToRightBlock) {
    //         goRightBall = true;
    //     }

    //     if (colideToLeftBlock) {
    //         goRightBall = false;
    //     }

    //     if (colideToTopBlock) {
    //         goDownBall = false;
    //     }

    //     if (colideToBottomBlock) {
    //         goDownBall = true;
    //     }


    // }



    // private void checkDestroyedCount() {
    //     if (destroyedBlockCount == blocks.size()) {
    //         nextLevel();
    //     }
    // }


    // public void resetColideFlags() {
    //     colideToBreak = false;
    //     colideToBreakAndMoveToRight = false;
    //     colideToRightWall = false;
    //     colideToLeftWall = false;

    //     colideToRightBlock = false;
    //     colideToBottomBlock = false;
    //     colideToLeftBlock = false;
    //     colideToTopBlock = false;
    // }


    // private void initBoard() {
    //     for (int i = 0; i < 4; i++) {
    //         for (int j = 0; j < level + 1; j++) {
    //             int r = new Random().nextInt(500);
    //             if (r % 5 == 0) {
    //                 continue;
    //             }
    //             int type;
    //             if (r % 10 == 1) {
    //                 type = Block.BLOCK_CHOCO;
    //             } else if (r % 10 == 2) {
    //                 if (!isExistHeartBlock) {
    //                     type = Block.BLOCK_HEART;
    //                     isExistHeartBlock = true;
    //                 } else {
    //                     type = Block.BLOCK_NORMAL;
    //                 }
    //             } else if (r % 10 == 3) {
    //                 type = Block.BLOCK_STAR;
    //             } else {
    //                 type = Block.BLOCK_NORMAL;
    //             }
    //             blocks.add(new Block(j, i, type));
    //             //System.out.println("colors " + r % (colors.length));
    //         }
    //     }
    // }


    // private void nextLevel() {
    //     Platform.runLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             try {
    //                 vX = 1.000;

    //                 engine.stop();
    //                 resetColideFlags();
    //                 goDownBall = true;

    //                 isGoldStauts = false;
    //                 isExistHeartBlock = false;


    //                 hitTime = 0;
    //                 time = 0;
    //                 goldTime = 0;
                    
    //                 for (Block block : blocks) {
    //                     main.root.getChildren().remove(block.rect);
    //                 }

    //                 engine.stop();
    //                 blocks.clear();
    //                 chocos.clear();
    //                 destroyedBlockCount = 0;
    //                 initBoard();

    //                 for (Block block : blocks) {
    //                     main.root.getChildren().add(block.rect);
    //                 }

    //                 level++;

    //                 xBall = sceneWidth/2.0;
    //                 yBall = sceneHeigt - (sceneHeigt/4.0);

    //                 if (level>1 && level<=18) {
    //                     engine.start();
    //                 }else if (level>18) {
    //                     main.root.getChildren().add(gameView.getLabel("winLabel"));
    //                     engine.stop();
    //                 }
    //                 // start(primaryStage);
    //                 // updateScore(score);

    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     });
    // }   
}


