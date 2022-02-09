package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import helpers.Constants.GameConstants;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;
    private Dimension size;

    public GamePanel(Game game) {
        this.game = game;
        init();
        setPanelSize();
    }

    private void init() {
        size = new Dimension(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        mouseInputs = new MouseInputs(game);

        // add input listeners
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        addMouseWheelListener(mouseInputs);
        addKeyListener(new KeyboardInputs(game));
    }

    private void setPanelSize() {
        // set the size of the panel
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics g) {

        // set the rendering hints
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        qualityHints.put(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(qualityHints);
        game.getPlayScene().render(g);
    }
}
