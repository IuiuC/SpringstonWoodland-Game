package ui;

import BazaDeDate.DataBase;
import gamestates.Gamestate;
import gamestates.Playing;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utiliz.LoadSave;

import javax.xml.crypto.Data;

import static utiliz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButtons menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    private DataBase datab;

    public LevelCompletedOverlay(Playing playing, DataBase datab) {
        this.playing = playing;
        this.datab=datab;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (600 * Game.SCALE);
        int nextX = (int) (530 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButtons(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_SCREEN);
        bgW = (int) (img.getWidth() * Game.SCALE/3);
        bgH = (int) (img.getHeight() * Game.SCALE/3);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(img, bgX, bgY, bgW, bgH, null);
       //afisare scor
        Font font=new Font("Helvetica", Font.BOLD, 40);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(Integer.toString(datab.getScore()),Game.GAME_WIDTH / 3, 340);
        //afisare butoane
        next.draw(g);
        menu.draw(g);

//        g.setColor(Color.white);
//        g.drawString("Game completed", Game.GAME_WIDTH / 2, 150);
//        g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);
    }

    public void update() {
        next.update();
        menu.update();

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }//
    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(next, e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(next, e))
            if (next.isMousePressed())
                playing.loadNextLevel();

        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(next, e))
            next.setMousePressed(true);
    }

}