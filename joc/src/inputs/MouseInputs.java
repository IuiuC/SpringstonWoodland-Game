package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener , MouseMotionListener {
    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(Gamestate.state)
        {
            case MENU:
                gamePanel.getGame().GetMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().GetPlaying().mouseMoved(e);
                break;
            default:
                break;

        }

    }



    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.getGame().GetPlaying().mouseClicked(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().GetMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().GetPlaying().mousePressed(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().GetMenu().mouseReleased(e);
            case PLAYING:
                gamePanel.getGame().GetPlaying().mouseReleased(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}