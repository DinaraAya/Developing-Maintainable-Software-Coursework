package brickGame;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Observable {
    private final DoubleProperty xBreakProperty = new SimpleDoubleProperty();
    private final DoubleProperty yBreakProperty = new SimpleDoubleProperty();


    private final DoubleProperty xBallProperty = new SimpleDoubleProperty();
    private final DoubleProperty yBallProperty = new SimpleDoubleProperty();

    private final IntegerProperty scoreProperty = new SimpleIntegerProperty();
    private final IntegerProperty levelProperty = new SimpleIntegerProperty();
    private final IntegerProperty heartProperty = new SimpleIntegerProperty();


    //score
    public IntegerProperty scoreProperty() {
        return scoreProperty;
    }

    public int getScoreProperty() {
        return scoreProperty.get();
    }

    public void setScoreProperty(int score) {
        scoreProperty.set(score);
    }

    //level
    public IntegerProperty levelProperty() {
        return levelProperty;
    }

    public int getLevelProperty() {
        return levelProperty.get();
    }

    public void setLevelProperty(int level) {
        levelProperty.set(level);
    }

    //heart
    public IntegerProperty heartProperty() {
        return heartProperty;
    }

    public int getHeartProperty() {
        return heartProperty.get();
    }

    public void setHeartProperty(int heart) {
        heartProperty.set(heart);
    }

    //xBreak
    public DoubleProperty xBreakProperty() {
        return xBreakProperty;
    }

    public double getXBreak() {
        return xBreakProperty.get();
    }

    public void setXBreak(double xBreak) {
        xBreakProperty.set(xBreak);
    }

    public DoubleProperty yBreakProperty() {
        return yBreakProperty;
    }

    public double getYBreak() {
        return yBreakProperty.get();
    }

    public void setYBreak(double yBreak) {
        yBreakProperty.set(yBreak);
    }

    //xBall and yBall

    public DoubleProperty xBallProperty() {
        return xBallProperty;
    }

    public double getXBall() {
        return xBallProperty.get();
    }

    public void setXBall(double xBall) {
        xBallProperty.set(xBall);
    }

    public DoubleProperty yBallProperty() {
        return yBallProperty;
    }

    public double getYBall() {
        return yBallProperty.get();
    }

    public void setYBall(double yBall) {
        yBallProperty.set(yBall);
    }

}
