package brickGame;

import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.animation.TranslateTransition;  
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.media.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;



public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    private BgMusicManager bgMusicManager;
    private SliderManager sliderManager;
    private SliderManagerSound sliderManagerSound;
    private UIHandler uiHandler;

    private boolean isGamePaused = false;

    private int level = 0;

    private double xBreak = 0.0f;
    private double centerBreakX;
    private double yBreak = 640.0f;

    private int breakWidth     = 130;
    private int breakHeight    = 30;
    private int halfBreakWidth = breakWidth / 2;

    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private static int LEFT  = 1;
    private static int RIGHT = 2;

    private Circle ball;
    private double xBall;
    private double yBall;

    private boolean isGoldStauts      = false;
    private boolean isExistHeartBlock = false;

    private Rectangle rect;
    public Rectangle background;
    private Rectangle saveMenu;
    private int       ballRadius = 10;

    private int destroyedBlockCount = 0;

    private double v = 1.000;

    private int  heart    = 3;
    private int  score    = 0;
    private long time     = 0;
    private long hitTime  = 0;
    private long goldTime = 0;

    private int countDown = 3;

    private double bgVol = 0.5;

    private GameEngine engine;
    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";


    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    // private Color[]          colors = new Color[]{
    //         Color.MAGENTA,
    //         Color.RED,
    //         Color.GOLD,
    //         Color.CORAL,
    //         Color.AQUA,
    //         Color.VIOLET,
    //         Color.GREENYELLOW,
    //         Color.ORANGE,
    //         Color.PINK,
    //         Color.SLATEGREY,
    //         Color.YELLOW,
    //         Color.TOMATO,
    //         Color.TAN,
    // };
    
    public  Pane             root;
    public  Pane             pauseRoot;
    public  Pane             settingRoot;
    public  Pane             gameOverRoot;
    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;
    private Label            countDownLabel;
    private String           bgMusicFile;
    private Media            bgMusicMedia;
    private Slider           bgMusicSlider;
    private Slider           soundSlider;

    private Scene settingScene;
    private Scene pauseScene;
    private Scene gameOverScene;
    private Scene scene;

    private Timeline        timeline;

    private boolean loadFromSave = false;
    private boolean gameInitialized = false;

    Stage  primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        bgMusicManager = BgMusicManager.getInstance();
        if (!bgMusicManager.isPlaying()) {
            bgMusicManager.initialize("/bgMusic.mp3");
            bgMusicManager.play();
        }

        if (sliderManager == null) {
            sliderManager = SliderManager.getInstance();
            bgMusicSlider = sliderManager.getBgMusicController();
        }

        if(sliderManagerSound == null) {
            sliderManagerSound = SliderManagerSound.getInstance();
            soundSlider = sliderManagerSound.getSoundController();
        }


        this.primaryStage = primaryStage;

        if (loadFromSave == false) {
            System.out.println("Starting new");
           level++;
            if (level > 1){
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }

            initBall();
            initBreak();
            initBoard();

        }
        
        uiHandler = new UIHandler(primaryStage);
        initLabels();
        initRoots();

        scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        initButtonActions();

        if (loadFromSave == false) {
            if (level > 1 && level < 18) {
                root.getChildren().removeAll(uiHandler.getButton("load"), uiHandler.getButton("newGame"), uiHandler.getButton("settings"), uiHandler.getButton("quit"));
                root.getChildren().add(uiHandler.getButton("pause"));
                uiHandler.getButton("pause").setFocusTraversable(false); 
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(240);
                addBlocksToRoot(); 
                engine.start();
            }
 
            uiHandler.getButton("newGame").setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SoundManager.playButtonSound();
                addBlocksToRoot();
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(240);
                root.getChildren().removeAll(uiHandler.getButton("pause"), uiHandler.getButton("load"), uiHandler.getButton("newGame"), uiHandler.getButton("settings"), uiHandler.getButton("quit"));
                uiHandler.getButton("pause").setFocusTraversable(false);
                countDownDisplay();
            }
        });
        } else {
            root.getChildren().removeAll(uiHandler.getButton("pause"), uiHandler.getButton("load"), uiHandler.getButton("newGame"), uiHandler.getButton("settings"), uiHandler.getButton("quit"));
            uiHandler.getButton("pause").setFocusTraversable(false);
            root.getChildren().add(uiHandler.getButton("pause"));
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(240);
            addBlocksToRoot();
            engine.start();
            loadFromSave = false;
        }
    }
    private void addBlocksToRoot() {
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
    }

    private void countDownDisplay() {
        countDown = 3;
        countDownLabel.setVisible(true);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    countDownLabel.setText(Integer.toString(countDown));
                    countDown--;

                    if (countDown < 0) {
                        countDownLabel.setVisible(false);
                        timeline.stop();
                        engine.start();
                        root.getChildren().add((uiHandler.getButton("pause")));
                    }
                }
            })
    );
    timeline.setCycleCount(4); 
    timeline.play();
    }
    

    private void initBoard() {
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
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }

    private void slideUpAnimation(Parent root) {
        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.5), root);
        slideUp.setFromY(sceneHeigt);
        slideUp.setToY(0);
        slideUp.play();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        if(event!=null) {
            switch (event.getCode()) {
                case LEFT:
                    move(LEFT);
                    break;
                case RIGHT:
    
                    move(RIGHT);
                    break;
                case DOWN:
                    //setPhysicsToBall();
                    break;
                case S:
                    saveGame();
                    break;
                default:
            }
        }
        }

    float oldXBreak;

    private void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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

    private void initBall() {
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + ((level + 1) * Block.getHeight()) + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball1.png")));
    }

    private void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("ground.png"));

        rect.setFill(pattern);
    }

    private void initLabels(){
        
        //countdown label(3,2,1)
        countDownLabel = new Label("");
        countDownLabel.getStyleClass().add("bigText");
        countDownLabel.setStyle("-fx-font-size: 36px");
        countDownLabel.setTranslateX(sceneWidth / 2 - 20);
        countDownLabel.setTranslateY(sceneHeigt / 2);

        scoreLabel = new Label("SCORE: " + score);
        scoreLabel.getStyleClass().add("text");

        levelLabel = new Label("LEVEL: " + level);
        levelLabel.setTranslateY(30);
        levelLabel.getStyleClass().add("text");
        
        heartLabel = new Label("X" + heart);
        heartLabel.setTranslateY(15);
        heartLabel.setTranslateX(sceneWidth - 55);
        heartLabel.getStyleClass().add("text");
    }
    private void initRoots(){
        pauseRoot = new Pane();
        pauseScene = new Scene(pauseRoot, sceneWidth, sceneHeigt);
        pauseScene.getStylesheets().add("style.css");

        settingRoot = new Pane();
        settingScene = new Scene(settingRoot, sceneWidth, sceneHeigt);
        settingScene.getStylesheets().add("style.css");
        settingRoot.getChildren().addAll(uiHandler.getRectangle("pauseMenu"), bgMusicSlider, uiHandler.getButton("resume2"), uiHandler.getLabel("settingLabel"), soundSlider);

        gameOverRoot = new Pane();
        gameOverScene = new Scene(gameOverRoot, sceneWidth, sceneHeigt);
        gameOverScene.getStylesheets().add("style.css");

        root = new Pane();
        if (loadFromSave == false) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, uiHandler.getButton("newGame"), uiHandler.getImageView("heartImageView"), uiHandler.getButton("settings"), countDownLabel, uiHandler.getButton("quit"), uiHandler.getButton("load"));
        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, uiHandler.getButton("newGame"), uiHandler.getImageView("heartImageView"), uiHandler.getButton("settings"), countDownLabel, uiHandler.getButton("quit"), uiHandler.getButton("load"));
        }
    }

    private void initButtonActions(){
        uiHandler.getButton("settings").setOnAction(event -> {
            SoundManager.playButtonSound();
            slideUpAnimation(settingRoot);
            primaryStage.setScene(settingScene);
            settingRoot.getChildren().addAll(uiHandler.getRectangle("pauseMenu"), bgMusicSlider, uiHandler.getButton("resume2"), uiHandler.getLabel("settingLabel"), soundSlider);
        });

        uiHandler.getButton("load").setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadGame();
                root.getChildren().removeAll(uiHandler.getButton("load"), uiHandler.getButton("newGame"), uiHandler.getButton("settings"), uiHandler.getButton("quit"));
                root.getChildren().add(uiHandler.getButton("pause"));
            }
        });


        uiHandler.getButton("quit").setOnAction(event-> {
            Platform.exit();
        });


        uiHandler.getButton("restart").setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SoundManager.playButtonSound();
                engine.stop();
                restartGame();
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(240);
                root.getChildren().removeAll(uiHandler.getButton("pause"), uiHandler.getButton("load"), uiHandler.getButton("newGame"), uiHandler.getButton("settings"), uiHandler.getButton("quit"));
                uiHandler.getButton("pause").setFocusTraversable(false);
                countDownDisplay();
            }
        });

        uiHandler.getButton("home").setOnAction(event -> {
            SoundManager.playButtonSound();
            primaryStage.setScene(scene);
            goHome();
        });

        uiHandler.getButton("resume2").setOnAction(event -> {
            SoundManager.playButtonSound();
            primaryStage.setScene(scene);
        }); 

        uiHandler.getButton("resume").setOnAction(event -> {
            SoundManager.playButtonSound();
            isGamePaused = false;
            pauseRoot.getChildren().removeAll(uiHandler.getRectangle("pauseMenu"), uiHandler.getLabel("pauseLabel"), uiHandler.getButton("restart"), uiHandler.getButton("home"), uiHandler.getButton("resume"), bgMusicSlider, soundSlider);
            primaryStage.setScene(scene);
            engine.start();
        });

        uiHandler.getButton("pause").setOnAction(event -> {
            if(!isGamePaused) {
                SoundManager.playButtonSound();
                isGamePaused = true;
                engine.stop();
                pauseRoot.getChildren().addAll(uiHandler.getRectangle("pauseMenu"), uiHandler.getLabel("pauseLabel"), uiHandler.getButton("restart"), uiHandler.getButton("home"), uiHandler.getButton("resume"), bgMusicSlider, soundSlider);
                slideUpAnimation(pauseRoot);
                primaryStage.setScene(pauseScene);
                System.out.println("Game paused");
            }
            // else{
            //     isGamePaused = false;
            //     primaryStage.setScene(scene);
            //     root.getChildren().add(uiHandler.getButton("pause"));
            //     engine.start();
            //     System.out.println("Game resumed");
            // }
        });
    }

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

    private double vX = 1.000;
    private double vY = 1.000;


    private void resetColideFlags() {
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
        //v = ((time - hitTime) / 1000.000) + 1.000;

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
            //vX = 1.000;
            resetColideFlags();
            goDownBall = true;
            return;
        }
        if (yBall >= sceneHeigt) {
            goDownBall = false;
            if (!isGoldStauts) {
                //TODO gameover
                heart--;
                new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this, score);
                    SoundManager.playGameOverSound();
                    primaryStage.setScene(gameOverScene);
                    engine.stop();
                }

            }
            //return;
        }

        if (yBall >= yBreak - ballRadius) {
            //System.out.println("Colide1");
            if (xBall >= xBreak && xBall <= xBreak + breakWidth) {
                hitTime = time;
                resetColideFlags();
                colideToBreak = true;
                goDownBall = false;

                double relation = (xBall - centerBreakX) / (breakWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                if (xBall - centerBreakX > 0) {
                    colideToBreakAndMoveToRight = true;
                } else {
                    colideToBreakAndMoveToRight = false;
                }
                //System.out.println("Colide2");
            }
        }

        if (xBall >= sceneWidth) {
            resetColideFlags();
            //vX = 1.000;
            colideToRightWall = true;
        }

        if (xBall <= 0) {
            resetColideFlags();
            //vX = 1.000;
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            if (colideToBreakAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
        }

        //Wall Colide

        if (colideToRightWall) {
            goRightBall = false;
        }

        if (colideToLeftWall) {
            goRightBall = true;
        }

        //Block Colide

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


    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");
            nextLevel();
        }
    }


    private void saveGame() {
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

    private void loadGame() {

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
        destroyedBlockCount = loadSave.destroyedBlockCount;
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

        uiHandler.getButton("pause").setFocusTraversable(false);

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, ser.type));
        }


        try {
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


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

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    start(primaryStage);
                    // updateScore(score);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void restartGame() {

        try {
            //engine.stop();
            level = 0;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;
            isGamePaused = false;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            
            start(primaryStage);
            addBlocksToRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goHome() {
        try {
            //engine.stop();
            level = 0;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;
            isGamePaused = false;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("SCORE: " + score);
                heartLabel.setText("X" + heart);

                rect.setX(xBreak);
                rect.setY(yBreak);
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });

        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT && !block.isDestroyed) {

                    new Score().show(block.x, block.y, 1, this);

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
                                root.getChildren().add(choco.choco);
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
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

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }
    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
            checkDestroyedCount();
            setPhysicsToBall();

        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("ball1.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStauts = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
        //System.out.println("time is:" + time + " goldTime is " + goldTime);
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }  
}