package brickGame;

public class GameModel {
    private int level;
    private int heart;
    private int score;
    private double vX;
    private int destroyedBlockCount;
    private boolean goDownBall;
    private boolean isGamePaused;
    private boolean isGoldStauts;
    private boolean isExistHeartBlock;
    private long hitTime;
    private long time;
    private long goldTime;

    // public void restartGame() {
    //     try {
    //         level = 0;
    //         heart = 3;
    //         score = 0;
    //         vX = 1.000;
    //         destroyedBlockCount = 0;
    //         resetColideFlags();
    //         goDownBall = true;
    //         isGamePaused = false;

    //         isGoldStauts = false;
    //         isExistHeartBlock = false;
    //         hitTime = 0;
    //         time = 0;
    //         goldTime = 0;

    //         blocks.clear();
    //         chocos.clear();

            
    //         start(primaryStage);
    //         addBlocksToRoot();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
