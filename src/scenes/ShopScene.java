package scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import helpers.Constants;
import objects.Button;
import structures.Belt;

import static helpers.Constants.GameConstants.*;
import static helpers.Constants.Colors.*;

public class ShopScene implements SceneMethods {

    private PlayScene playScene;
    int width = 200;
    int height = (int) (width * 1.5);
    int x = GAME_WIDTH - 10 - width;
    int y = GAME_HEIGHT - 10 - height - 40;
    private boolean isOpen;
    private Button beltButton;

    public ShopScene(PlayScene playScene) {
        this.playScene = playScene;
        init();
    }

    private void init() {
        beltButton = new Button(x + 10, y + 10, 75, 75, playScene.getStructureManager().getBeltSprite(0),
                Constants.BeltTypes.BELT_STRAIGHT);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(BACKGROUND_COLOR);
        g.fillRoundRect(x, y, width, height, 20, 20);
        g2d.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, 20, 20);

        beltButton.render(g);

    }

    public boolean contains(int x, int y) {
        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height && isOpen) {
            return true;
        }
        return false;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (beltButton.contains(e.getX(), e.getY())) {
            playScene.setSelectStructure(new Belt(0, 0, 0, beltButton.getType(), 0));
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        beltButton.reset();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (beltButton.contains(e.getX(), e.getY())) {
            beltButton.setHovered(true);
        } else {
            beltButton.setHovered(false);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub

    }

    public boolean isOpen() {
        return isOpen;
    }

}
