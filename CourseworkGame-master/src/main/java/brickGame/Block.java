package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, 99);

    private boolean isHit;

    public int row;
    public int column;


    public boolean isDestroyed = false;

    public int type;

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;
    public Rectangle leftBorder;
    public Rectangle rightBorder;


    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;

    private static final long COOLDOWN_DURATION = 200; 
    private int hitCount = 0; 
    private long lastHitTime = 0;

    public void onHit() {
        long currentTime = System.currentTimeMillis();

        if(currentTime - lastHitTime < COOLDOWN_DURATION){
            return;
        }

        lastHitTime = currentTime;
        hitCount++;

        if(type == BLOCK_CHOCO || type == BLOCK_HEART || type == BLOCK_STAR) {
            isDestroyed = true;
            rect.setVisible(false);
        }else{
        if (hitCount == 1) {
            Image crackedImage = new Image("cracked_bloc.png");
            ImagePattern crackedPattern = new ImagePattern(crackedImage);
            rect.setFill(crackedPattern);
        }else if(hitCount >= 2) {
            isDestroyed = true;
            rect.setVisible(false);
        }
    }
    }


    public Block(int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco_block.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart_block.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("gold_block.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            Image image = new Image("game_block.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }
    }


    public int checkHitToBlock(double xBall, double yBall) {

        if (isDestroyed) {
            return NO_HIT;
        }

        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return HIT_BOTTOM;
        }

        if (xBall >= x && xBall <= x + width && yBall == y) {
            return HIT_TOP;
        }

        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return HIT_RIGHT;
        }

        if (yBall >= y && yBall <= y + height && xBall == x) {
            return HIT_LEFT;
        }

        return NO_HIT;
    }
    

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}
