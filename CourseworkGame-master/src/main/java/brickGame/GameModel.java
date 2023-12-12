package brickGame;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public class GameModel implements GameEngine.OnAction{

    public Observable observable;

    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private boolean colideToBreak               = false;
    private boolean colideToBreakAndMoveToRight = true;
    private boolean colideToRightWall           = false;
    private boolean colideToLeftWall            = false;
    private boolean colideToRightBlock          = false;
    private boolean colideToBottomBlock         = false;
    private boolean colideToLeftBlock           = false;
    private boolean colideToTopBlock            = false;

    private int  score    = 0;
    private long time     = 0;
    private long hitTime  = 0;
    private long goldTime = 0;

    private int       ballRadius = 10;

    private double vX = 1.000;
    private double vY = 1.000;

    private GameView gameView;
    private GameController gameController;

    private boolean isExistHeartBlock = false;
    private boolean isGoldStauts      = false;

    private int  heart    = 3;

    private static int LEFT  = 1;
    private static int RIGHT = 2;

    private double xBreak = 0.0f;
    private double centerBreakX;
    private double yBreak = 640.0f;

    private int breakWidth     = 130;
    private int breakHeight    = 30;
    private int halfBreakWidth = breakWidth / 2;

    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private int level = 1;

    private double xBall;
    private double yBall;

    private int destroyedBlockCount = 0;

    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();

    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private GameEngine engine;

    public GameModel(GameView gameView){
        this.gameView = gameView;
        this.gameController = gameController;
        engine = new GameEngine();
        engine.setOnAction(this);
        engine.setFps(240);
        observable = new Observable();
        initBoard();
    }


    public void startEngine() {
        engine.start();
    }

    public void stopEngine() {
        engine.stop();
    }

    public int getScore(){
        return score;
    }

        public void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("xBreak is: " +xBreak);
                System.out.println("yBreak is: " +yBreak);
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                        return;
                    }
                    if (xBreak == 0 && direction == LEFT) {
                        return;
                    }
                    if (direction == RIGHT) {
                        xBreak++;
                    } else {
                        xBreak--;
                    }
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }


       private void nextLevel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    vX = 1.000;

                    engine.stop();
                    resetColideFlags();
                    goDownBall = true;

                    isGoldStauts = false;
                    isExistHeartBlock = false;


                    hitTime = 0;
                    time = 0;
                    goldTime = 0;
                    
                    removeBlocksFromRoot();

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    initBoard();

                    addBlocksToRoot();

                    level++;

                    xBall = sceneWidth/2.0;
                    yBall = sceneHeigt - (sceneHeigt/4.0);

                    if (level>1 && level<=18) {
                        engine.start();
                    }else if (level>18) {
                        gameView.getRoots("root").getChildren().add(gameView.getLabel("winLabel"));
                        engine.stop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goHome() {
        try {
            level = 1;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, type));
            }
        }
    }

    public void addBlocksToRoot() {
        for (Block block : blocks) {
            gameView.getRoots("root").getChildren().add(block.rect);
        }
    }

    public void removeBlocksFromRoot() {
        for (Block block : blocks) {
            gameView.getRoots("root").getChildren().remove(block.rect);
        }
    }

    public void resetColideFlags() {
        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;

        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }
    
    private void setPhysicsToBall() {

        if (goDownBall) {
            yBall += vY;
        } else {
            yBall -= vY;
        }

        if (goRightBall) {
            xBall += vX;
        } else {
            xBall -= vX;
        }

        if (yBall <= 0) {
            resetColideFlags();
            goDownBall = true;
            return;
        }
        if (yBall >= sceneHeigt) {
            goDownBall = false;
            if (!isGoldStauts) {
                heart--;

                if (heart == 0) {
                    new Score().showGameOver(gameView, this, score);
                    SoundManager.playGameOverSound();
                    gameView.showGameOverScreen();
                    engine.stop();
                }

            }
        }

        if (yBall >= yBreak - ballRadius) {
            if (xBall >= xBreak && xBall <= xBreak + breakWidth) {
                hitTime = time;
                resetColideFlags();
                colideToBreak = true;
                goDownBall = false;

                double relation = (xBall - centerBreakX) / (breakWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                }

                if (xBall - centerBreakX > 0) {
                    colideToBreakAndMoveToRight = true;
                } else {
                    colideToBreakAndMoveToRight = false;
                }
            }
        }

        if (xBall >= sceneWidth) {
            resetColideFlags();
            colideToRightWall = true;
        }

        if (xBall <= 0) {
            resetColideFlags();
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            if (colideToBreakAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
        }

        if (colideToRightWall) {
            goRightBall = false;
        }

        if (colideToLeftWall) {
            goRightBall = true;
        }

        if (colideToRightBlock) {
            goRightBall = true;
        }

        if (colideToLeftBlock) {
            goRightBall = false;
        }

        if (colideToTopBlock) {
            goDownBall = false;
        }

        if (colideToBottomBlock) {
            goDownBall = true;
        }


    }

    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // scoreLabel.setText("SCORE: " + score);
                // gameView.updateLabel(score);
                // gameView.updateLevel(level);
                // gameView.updateHeart(heart);
                // gameView.getBreakObservable().setScoreProperty(score);
                // gameView.getBreakObservable().setLevelProperty(level);
                // gameView.getBreakObservable().setHeartProperty(heart);

                // heartLabel.setText("X" + heart);

                // gameView.getBreakObservable().setXBall(xBall);
                observable.setHeartProperty(heart);
                observable.setLevelProperty(level);
                observable.setScoreProperty(score);

                observable.setXBall(xBall);
                observable.setYBall(yBall);

                observable.setXBreak(xBreak);
                observable.setYBreak(yBreak);
                // gameView.getBreakObservable().setYBall(yBall);
                // rect.setX(xBreak);
                // rect.setY(yBreak);

                // ball.setCenterX(xBall);
                // ball.setCenterY(yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });

        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT && !block.isDestroyed) {

                    // new Score().show(block.x, block.y, 1, this);

                    block.onHit();

                    // block.rect.setVisible(false);
                    // block.isDestroyed = true;
                
                    if(block.isDestroyed) {
                        score += 1;
                        destroyedBlockCount++;
                        // updateScore(score);
                    }

                    //System.out.println("size is " + blocks.size());
                    resetColideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                gameView.getRoots("root").getChildren().add(choco.choco);
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        gameView.getBall().setFill(new ImagePattern(new Image("goldball1.png")));
                        // ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        isGoldStauts = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        colideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        colideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        colideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        colideToTopBlock = true;
                    }

                }
            }
        }
    }

    @Override
    public void onPhysicsUpdate() {
            checkDestroyedCount();
            setPhysicsToBall();

        if (time - goldTime > 5000) {
            gameView.getBall().setFill(new ImagePattern(new Image("ball1.png")));
            // ball.setFill(new ImagePattern(new Image("ball1.png")));
            // root.getStyleClass().remove("goldRoot");
            isGoldStauts = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                SoundManager.playBonusSound();
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                // new Score().show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
        //System.out.println("time is:" + time + " goldTime is " + goldTime);
    }


    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");
            nextLevel();
        }
    }


    @Override
    public void onInit() {

    }

    @Override
    public void onTime(long time) {
        this.time = time;
    }  

    public void restartGame() {

        try {
            //engine.stop();
            level = 1;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;
            // isGamePaused = false;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            removeBlocksFromRoot();

            blocks.clear();
            chocos.clear();

            initBoard();

            addBlocksToRoot();

            engine.start();
            // start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(heart);
                    outputStream.writeInt(destroyedBlockCount);


                    outputStream.writeDouble(xBall);
                    outputStream.writeDouble(yBall);
                    outputStream.writeDouble(xBreak);
                    outputStream.writeDouble(yBreak);
                    outputStream.writeDouble(centerBreakX);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(vX);


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStauts);
                    outputStream.writeBoolean(goDownBall);
                    outputStream.writeBoolean(goRightBall);
                    outputStream.writeBoolean(colideToBreak);
                    outputStream.writeBoolean(colideToBreakAndMoveToRight);
                    outputStream.writeBoolean(colideToRightWall);
                    outputStream.writeBoolean(colideToLeftWall);
                    outputStream.writeBoolean(colideToRightBlock);
                    outputStream.writeBoolean(colideToBottomBlock);
                    outputStream.writeBoolean(colideToLeftBlock);
                    outputStream.writeBoolean(colideToTopBlock);

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);


                    //new Score().showMessage("Game Saved", Main.this);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initializeFromSave(){
        observable.setScoreProperty(score);
        observable.setHeartProperty(heart);
        observable.setLevelProperty(level);
        observable.setXBall(xBall);
        observable.setYBreak(xBreak);
        observable.setYBall(yBall);
        observable.setYBreak(yBreak);
        for (Block block : blocks) {
            gameView.getRoots("root").getChildren().add(block.rect);
        }
        if (isGoldStauts == true) {
            gameView.getBall().setFill(new ImagePattern(new Image("goldball1.png")));
        }
    }

      public void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();


        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStauts = loadSave.isGoldStauts;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        colideToBreak = loadSave.colideToBreak;
        colideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
        colideToRightWall = loadSave.colideToRightWall;
        colideToLeftWall = loadSave.colideToLeftWall;
        colideToRightBlock = loadSave.colideToRightBlock;
        colideToBottomBlock = loadSave.colideToBottomBlock;
        colideToLeftBlock = loadSave.colideToLeftBlock;
        colideToTopBlock = loadSave.colideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = 0;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xBreak = loadSave.xBreak;
        yBreak = loadSave.yBreak;
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

        blocks.clear();
        chocos.clear();

        gameView.getButton("pause").setFocusTraversable(false);

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, ser.type));
        }


        try {
            initializeFromSave();
            engine.start();
            // loadFromSave = true;
            // start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
