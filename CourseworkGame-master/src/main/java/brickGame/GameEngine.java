package brickGame;

import javafx.application.Platform;


public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    private volatile boolean isStopped = true;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    private synchronized void Update() {
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStopped) {
                    try {
                        onAction.onUpdate();
                        Thread.sleep(fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();
    }

    private void Initialize() {
        onAction.onInit();
    }

    private synchronized void PhysicsCalculation() {
        physicsThread = new Thread(() -> {
            while (!isStopped) {
                try {
                    Platform.runLater(() -> {
                        onAction.onPhysicsUpdate();
                    });
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    
        physicsThread.start();
    }

    public void start() {
        time = 0;
        Initialize();
        isStopped = false;
        Update();
        PhysicsCalculation();
        TimeStart();
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
        }
    }

    private long time = 0;

    private Thread timeThread;

    

    private void TimeStart() {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isStopped) {
                        time++;
                        onAction.onTime(time);
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }


    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }

}
