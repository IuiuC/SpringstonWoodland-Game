package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utiliz.LoadSave;

import static utiliz.Constants.UI.URMButtons.URM_SIZE;

public class GameOver {

    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private UrmButtons menu,play;


    public GameOver(Playing playing) {
        
        this.playing = playing;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (240 * Game.SCALE);
        int playX = (int) (160 * Game.SCALE);
        int y = (int) (300 * Game.SCALE);
        play = new UrmButtons(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void createImg() {
        img= LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW=(int)(img.getWidth()*Game.SCALE/3);
        imgH=(int)(img.getHeight()*Game.SCALE/3);
        imgX=Game.GAME_WIDTH/2-imgW/2;
        imgY=(int)(100*Game.SCALE);

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        menu.draw(g);
        play.draw(g);


//        g.setColor(Color.white);
//        g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
//        g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);

    }

    public void update(){
        menu.update();
        play.update();
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }//verificam daca mouseul se afla in limitele pe care le am initiat

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(play, e))
           play.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(play, e))
            if (play.isMousePressed()){
              playing.resetAll();
            }

        menu.resetBools();
        play.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(play, e))
            play.setMousePressed(true);
    }
}