package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utiliz.Constants.Directions.RIGHT;
import static utiliz.Constants.EnemyConstants.*;

public class Gator extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    public Gator(float x, float y) {
        super(x, y, GATOR_WIDTH, GATOR_HEIGHT, ENEMY2);
        initHitbox(x, y-35, (int)(40* Game.SCALE),(int)(29*Game.SCALE));
        initAttackBox();

    }

    private void initAttackBox() {
        attackBox=new Rectangle2D.Float(x,y, (int)(66*Game.SCALE), (int)(30*Game.SCALE));
        attackBoxOffsetX=(int)(Game.SCALE*30);
    }

    public void update(int[][] lvlData, Player player){
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x=hitBox.x-attackBoxOffsetX;
        attackBox.y=hitBox.y;
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    attackChecked = false;
                    if (!attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case DEAD:
                    break;
                //case HIT:
                //  break;

            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x-xLvlOffset), (int)attackBox.y, (int)attackBox.width,(int) attackBox.height);
    }
    public int flipX(){
        if(walkDir==RIGHT)
            return width;
        else
            return 0;
    }
    public int flipW(){
        if(walkDir==RIGHT)
            return -1;
        else
            return 1;
    }


}
