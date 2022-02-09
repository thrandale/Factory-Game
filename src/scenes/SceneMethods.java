package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface SceneMethods {

    public void render(Graphics g);

    public void update();

    public void keyPressed(KeyEvent e);

    public void mouseClicked(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseMoved(MouseEvent e);

    public void mouseDragged(MouseEvent e);

    public void mouseWheelMoved(MouseWheelEvent e);
}
