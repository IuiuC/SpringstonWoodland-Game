package entities;

import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import utiliz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static utiliz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private LevelManager levelManager;
    private BufferedImage[][] antArray;
    private ArrayList<Ant>ants=new ArrayList<>();
    private BufferedImage[][] enemy2Array;
    private ArrayList<Enemy2>enemy2=new ArrayList<>();
    private BufferedImage[][] gatorArray;
    private ArrayList<Gator>gator=new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        enemy2=level.getEnemy2();
        ants=level.getAnts();
        gator=level.getGator();
    }


    public void update(int[][] lvlData, Player player){
        //int numberlevel = levelManager.getCurrentLevel().getMaxLvlOffset();
        boolean isAnyActive1=false;
        boolean isAnyActive2=false;
        boolean isAnyActive3=false;



        for(Ant a: ants)
            if(a.isActive()) {
                a.update(lvlData, player);
                isAnyActive1=true;
            }


        for(Enemy2 e: enemy2)
            if(e.isActive()) {
                e.update(lvlData, player);
                isAnyActive2=true;
            }
        for(Gator g: gator)
            if(g.isActive()) {
                g.update(lvlData, player);
                isAnyActive3=true;
            }
        if(!isAnyActive2 && !isAnyActive1 && !isAnyActive3)
            playing.setLevelCompleted(true);

    }
    public void draw(Graphics g, int xLvlOffset){

        drawAnts(g, xLvlOffset);
        drawEnemy2(g, xLvlOffset);
        drawGator(g, xLvlOffset);

    }

    private void drawAnts(Graphics g, int xLvlOffset) {
        for(Ant a:ants)
             if(a.isActive())
        {
            g.drawImage(antArray[a.getEnemyState()][a.getAniIndex()],
                    (int) a.getHitbox().x - xLvlOffset - ANT_DRAWOFFSET_X + a.flipX(),
                    (int) a.getHitbox().y, (int) (0.9 * ANT_WIDTH * a.flipW()),
                    (int) (0.9 * ANT_HEIGHT), null);
            //a.drawHitbox(g, xLvlOffset);
            //a.drawAttackBox(g, xLvlOffset);
        }
    }
    private void drawEnemy2(Graphics g, int xLvlOffset) {
        for(Enemy2 a:enemy2)
            if(a.isActive())
            {
                g.drawImage(enemy2Array[a.getEnemyState()][a.getAniIndex()],
                        (int) a.getHitbox().x - xLvlOffset - ANT_DRAWOFFSET_X + a.flipX(),
                        (int) a.getHitbox().y, (int) (1.2 * ENEMY2_WIDTH * a.flipW()),
                        (int) (1.2 * ENEMY2_HEIGHT), null);
                //a.drawHitbox(g, xLvlOffset);
                //a.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawGator(Graphics g, int xLvlOffset) {
        for(Gator ga: gator)
            if(ga.isActive())
            {
                g.drawImage(gatorArray[ga.getEnemyState()][ga.getAniIndex()],
                        (int) ga.getHitbox().x - xLvlOffset - ANT_DRAWOFFSET_X + ga.flipX(),
                        (int) ga.getHitbox().y, (int) (1.5 * GATOR_WIDTH * ga.flipW()),
                        (int) (1.5 * GATOR_HEIGHT), null);
                //ga.drawHitbox(g, xLvlOffset);
                //ga.drawAttackBox(g, xLvlOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Ant a : ants)
            if (a.isActive())
                if (attackBox.intersects(a.getHitbox())) {
                    a.hurt(10);
                    return;
                }
        for (Enemy2 e : enemy2)
            if (e.isActive())
                if (attackBox.intersects(e.getHitbox())) {
                    e.hurt(20);
                    return;
                }
        for (Gator ga : gator)
            if (ga.isActive())
                if (attackBox.intersects(ga.getHitbox())) {
                    ga.hurt(5);
                    return;
                }
    }

    private void loadEnemyImages() {
        antArray=new BufferedImage[4][4];
        BufferedImage temp= LoadSave.GetSpriteAtlas(LoadSave.ANTSPRITE);
        for(int j=0;j<antArray.length;j++)
            for(int i=0;i<antArray[j].length;i++)
                antArray[j][i]=temp.getSubimage(i*ANT_WIDTH_DEFAULT, j*ANT_HEIGHT_DEFAULT,ANT_WIDTH_DEFAULT, ANT_HEIGHT_DEFAULT);

        enemy2Array=new BufferedImage[4][6];
        BufferedImage temp1= LoadSave.GetSpriteAtlas(LoadSave.ESPRITE);
        for(int j=0;j<enemy2Array.length;j++)
            for(int i=0;i<enemy2Array[j].length;i++)
                enemy2Array[j][i]=temp1.getSubimage(i*ENEMY2_WIDTH_DEFAULT, j*ENEMY2_HEIGHT_DEFAULT,ENEMY2_WIDTH_DEFAULT, ENEMY2_HEIGHT_DEFAULT);

        gatorArray=new BufferedImage[4][4];
        BufferedImage temp2= LoadSave.GetSpriteAtlas(LoadSave.GATORSPRITE);
        for(int j=0;j<gatorArray.length;j++)
            for(int i=0;i<gatorArray[j].length;i++)
                gatorArray[j][i]=temp2.getSubimage(i*GATOR_WIDTH_DEFAULT, j*GATOR_HEIGHT_DEFAULT,GATOR_WIDTH_DEFAULT, GATOR_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies(){
        for(Ant a: ants)
            a.resetEnemy();
        for(Enemy2 e: enemy2)
            e.resetEnemy();
        for(Gator ga: gator)
            ga.resetEnemy();
    }
}
