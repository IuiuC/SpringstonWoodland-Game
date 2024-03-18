package gamestates;

import BazaDeDate.DataBase;
import Exceptions.LevelIndexOutOfBoundsException;
import Exceptions.PlayerTooCloseToBorderException;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOver;
import ui.LevelCompletedOverlay;
import utiliz.LoadSave;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static utiliz.Constants.Environment.*;

import static main.Game.SCALE;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;

    private ObjectManager objectManager;

    private GameOver GameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private int xLvlOffset;
    private int leftBorder=(int)(0.2*Game.GAME_WIDTH);
    private int rightBorder=(int)(0.8*Game.GAME_WIDTH);
    private int maxLvlOffsetX;
    private BufferedImage backgroundImg, bigCloud, smallCloud, decor6;

    private boolean gameOver;
    private boolean lvlCompleted=true;
    private DataBase datab;
    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.BG_IMG);
        bigCloud=LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud=LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        calcLvlOffset();
        loadStartLevel();
    }
    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

    }

    public void loadbackground(){
        if(levelManager.getLvlIndex()==1)
        {
            backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.BG_IMG1);
            bigCloud=LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS1);
            smallCloud=LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS1);
        }
        if(levelManager.getLvlIndex()==2) {
            backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BG_IMG2);
            bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS2);
            smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS2);
        }
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX=levelManager.getCurrentLevel().getMaxLvlOffset();
    }

    private void initClasses() {
        datab= DataBase.getInstance();
        levelManager = new LevelManager(game);
        enemyManager=new EnemyManager(this);
        objectManager=new ObjectManager(this);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this, datab);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        GameOverOverlay=new GameOver(this);
        levelCompletedOverlay=new LevelCompletedOverlay(this, datab);

    }
    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer()
    {
        return player;

    }
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }

    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX=lvlOffset;
    }

    public ObjectManager getObjectManager(){
        return objectManager;
    }

    @Override
    public void update() {
        if(lvlCompleted)
            levelCompletedOverlay.update();
        else if(gameOver)
            GameOverOverlay.update();
        else{
        levelManager.update();
        objectManager.update();
        player.update();
        enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
        checkCloseToBorder();

        }

    }

    private void checkCloseToBorder() {
        //playerX=pozitia jucatorului la acel moment
        int playerX=(int)player.getHitbox().x;
        //calculam diferenta dintre pozitie si offset
        int diff=playerX-xLvlOffset;
        //daca diff> 0.8*gamewidth
        if(diff>rightBorder)
            xLvlOffset+=diff-rightBorder;
        //daca diff< 0.2*gamewidth
        else if(diff<leftBorder)
            xLvlOffset+=diff-leftBorder;
        if(xLvlOffset>maxLvlOffsetX)
            xLvlOffset=maxLvlOffsetX;
        else if(xLvlOffset<0)
            xLvlOffset=0;

        diff=playerX-xLvlOffset;
        try{
            PlayerTooCloseToBorderException.TestCustomException.validate(rightBorder, diff);
        }
        catch(Exception m){
            System.out.println("Exception occured: "+m);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg,0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);
        levelManager.draw(g,xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        if(gameOver)
            GameOverOverlay.draw(g);
        else if(lvlCompleted)
            levelCompletedOverlay.draw(g);

    }

    private void drawClouds(Graphics g) {
        for(int i=0;i<9;++i)
        {
//            g.drawImage(bigCloud, 0+i*BIG_CLOUDS_WIDTH-(int)(xLvlOffset*0.3),(int)(210* SCALE), BIG_CLOUDS_WIDTH,BIG_CLOUDS_HEIGHT, null);
//            g.drawImage(smallCloud, 0+i*SMALL_CLOUDS_WIDTH-(int)(xLvlOffset*0.7),(int)(210* SCALE) , SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
            g.drawImage(bigCloud,0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
            g.drawImage(smallCloud,0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        }
        //g.drawImage(decor6, 300, 300, DEC6_WIDTH, DEC6_HEIGHT, null);

    }

    public void setLevelCompleted(boolean levelCompleted)
    {
        this.lvlCompleted=levelCompleted;
    }

    public void resetAll(){
        gameOver=false;
        lvlCompleted=false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }
    //metode ce se afla in object manager, respectiv enemy manager
    public void checkEnemyHit(Rectangle2D.Float attackBox){

        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkSpikesTouched(Player p){
        objectManager.checkSpikesTouched(p);
    }

    public void checkBerriesTouched(Rectangle2D.Float hitbox){
        objectManager.checkObjectTouched(hitbox);
    }

    public void setGameOver(boolean gameOver){
        this.gameOver=gameOver;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //if (e.getButton() == MouseEvent.BUTTON1)
           // player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }else {
            GameOverOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if (lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }else {
            GameOverOverlay.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver) {
            if (lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }else {
            GameOverOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(gameOver)
           GameOverOverlay.keyPressed(e);
       else
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
               player.setRight(true);
                break;
            case KeyEvent.VK_W:
                player.setJump(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setAttacking(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                Gamestate.state=Gamestate.MENU;
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch(e.getKeyCode())
        {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_W:
                player.setJump(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setAttacking(false);
                break;

        }
    }

}
