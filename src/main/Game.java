package main;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import helpers.Constants.GameConstants;
import helpers.LoadSave;
import scenes.PlayScene;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread gameThread;
    private PlayScene playScene;
    private Cursor mainCursor;
    private Cursor moveCursor;

    public Game() {
        init();
        // request focus for inputs
        gamePanel.requestFocus();
        System.out.println("Game started");
    }

    private void init() {
        playScene = new PlayScene(this);
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gameThread = new Thread(this);
        mainCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(LoadSave.getStdCursor(), new Point(0, 0), "std cursor");
        moveCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(LoadSave.getMoveCursor(), new Point(0, 0), "move cursor");
        setMainCursor();

        // listen for when the window closes
        gameWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
                System.exit(0);
            }
        });
    }

    public void start() {
        gameThread.start();
    }

    private void update() {
        playScene.update();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / GameConstants.GAME_FPS;
        long now = System.nanoTime();
        long lastTime = now;
        long lastCheck = System.currentTimeMillis();
        int frames = 0;

        // game loop
        while (true) {
            now = System.nanoTime();
            if (now - lastTime >= timePerFrame) {
                // render
                gamePanel.repaint();
                // update
                update();

                lastTime = now;
                frames++;
            }

            // fps counter
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    private void onClose() {
        playScene.onClose();
    }

    public PlayScene getPlayScene() {
        return playScene;
    }

    public void setMoveCursor() {
        gameWindow.setCursor(moveCursor);
    }

    public void setMainCursor() {
        gameWindow.setCursor(mainCursor);
    }
}
