package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.Game;

public class MouseInputs implements MouseListener, MouseMotionListener, MouseWheelListener {

    private Game game;

    public MouseInputs(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        game.getPlayScene().mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        game.getPlayScene().mousePressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        game.getPlayScene().mouseDragged(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        game.getPlayScene().mouseReleased(e);
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        game.getPlayScene().mouseWheelMoved(e);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        game.getPlayScene().mouseMoved(e);
    }


}
