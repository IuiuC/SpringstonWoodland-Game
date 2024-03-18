package entities;
import BazaDeDate.DataBase;
import gamestates.Playing;
import main.Game;
import utiliz.LoadSave;

import javax.xml.crypto.Data;

import static BazaDeDate.DataBase.getInstance;
import static main.Game.SCALE;
import static utiliz.Constants.GRAVITY;
import static utiliz.Constants.PlayerConstants.*;
import static utiliz.HelpMethods.*;
import static utiliz.HelpMethods.GetEntityXPosNextToWall;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.SQLException;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;;
    private boolean left, right, up, down, jump;
    private float playerSpeed = 2.0f;
    private int[][]lvlData;

    private float xDrawOffset=21* SCALE;
    private float yDrawOfsset=4* SCALE;

    private float airSpeed=0f;
    private float jumpSpeed=-2.25f* SCALE;
    private float fallSpeedAfterCollision=0.5f* SCALE;
    private boolean inAir=false;
    //variabila pt gravitatie
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth=10000;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    private DataBase datab;

    private Rectangle2D.Float attackBox;

    //changeDirectionForSquirrelSprites
    private int flipX=0;
    private int flipW=1;
    private boolean attackChecked;
    private Playing playing;
    private static int powerkey=0;
    private static int score=0;



    public Player(float x, float y, int width, int height, Playing playing, DataBase datab) {
        super(x, y, width, height);
        this.datab=datab;
        this.playing=playing;
        loadAnimations();
        initHitbox(x, y, 20*Game.SCALE, 28*Game.SCALE);
        initAttackBox();
    }

    public void setSpawn(Point spawn){
        this.x=spawn.x;
        this.y=spawn.y;
        hitBox.x=x;
        hitBox.y=y;

    }

    private void initAttackBox(){

        attackBox=new Rectangle2D.Float(x, y,(int)(48* Game.SCALE), (int)(30* Game.SCALE));
    }//in functie de dimensiunile sprite-ului

    public void update() {
        udateHealthBar();
        if(currentHealth<=0) {
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
        updatePos();
        if(moving) {
            checkBerriesTouched();
            checkSpikesTouched();
        }//verificare obiecte
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }//verificare trapa

    private void checkBerriesTouched() {
        playing.checkBerriesTouched(hitBox);
    }

    private void checkAttack() {
        if(attackChecked || aniIndex!=0)
            return;
        attackChecked=true;
        playing.checkEnemyHit(attackBox);

    }
    public void kill(){

        currentHealth=-1;
    }//-1 pentru ca verific cu<

    private void updateAttackBox() {
        if(right){
            attackBox.x=hitBox.x+hitBox.width+(int)(Game.SCALE*10);
        }else if(left)
            attackBox.x=hitBox.x-hitBox.width-(int)(Game.SCALE*10);
        attackBox.y= hitBox.y+(Game.SCALE*10);
    }//pentru deplasare

    private void udateHealthBar() {
        healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x-xDrawOffset)-lvlOffset+flipX, (int) (hitBox.y-yDrawOfsset), width*flipW, height,null);
        //drawHitbox(g, lvlOffset);
        //drawAttackBox(g, lvlOffset);
        drawUI(g);
    }//

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x-lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);

    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart+statusBarX,healthBarYStart+statusBarY, healthWidth, healthBarHeight);
    }//DESENAREA HEALTH?STATUS BAR

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction))
                aniIndex = 0;
                attacking = false;
                attackChecked=false;
        }


    }


    private void setAnimation() {
        int startAni = playerAction;
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if (inAir) {
           // if (airSpeed < 0)
              //  playerAction = JUMP;

        }

        if (attacking)
            playerAction = ATTACK_1;


        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {

        moving = false;
        if(jump)
            jump();
//        if(!left && !right && !inAir)
//            return;

        if(!inAir)
            if((!left && !right)||(right && left))
                return;
        float xSpeed =0;
        //pentru intoarcerea sprite ului
        if (left) {
            xSpeed -= playerSpeed;
            flipX=width;
            flipW=-1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX=0;
            flipW=1;
        }
        //daca nu e in aer activat si nici on floor=>activare inAir
        if(!inAir){
            if(!IsEntityOnFloor(hitBox, lvlData))
            {
                inAir=true;
            }

        }

        if(inAir) {
            if(CanMoveHere(hitBox.x, hitBox.y+airSpeed, hitBox.width, hitBox.height, lvlData))
            {

                hitBox.y+=airSpeed;
                airSpeed+=GRAVITY;
                updateXPos(xSpeed);
            }else{
                hitBox.y=GetEntityPosUnderRooforAboveFloor(hitBox, airSpeed);
                if(airSpeed>0)
                    resetInAir();
                else
                    airSpeed=fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        }else{
            updateXPos(xSpeed);
        }
        moving=true;

    }

    private void jump() {
        if(inAir)
            return;
        inAir=true;
        airSpeed=jumpSpeed;

    }

    private void resetInAir() {
        inAir =false;
        airSpeed=0;

    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitBox.x+xSpeed, hitBox.y,  hitBox.width, hitBox.height, lvlData)){
            hitBox.x+=xSpeed;
        }else{
            hitBox.x=GetEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    public void changeHealth(int value){
        currentHealth+=value;
        if(currentHealth<=0){
            currentHealth=0;
            //gameOver();
        }else if(currentHealth>=maxHealth)
            currentHealth=maxHealth;
    }



    public void changePower(int blueberryValue){
        System.out.println("POWER++");
        score+=blueberryValue;
        String sql = "INSERT INTO DB (ID, SCORE) " + "VALUES ("+ powerkey+ ","+ score+")";
        datab.setScoreInDatabase(blueberryValue);
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/squirrel.png");


        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[5][8];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        statusBarImg=LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int [][]lvlData){
        this.lvlData=lvlData;
        if(!IsEntityOnFloor(hitBox, lvlData)){
            inAir=true;
        }

    }
    public void resetDirBooleans(){
        left=false;
        right=false;
        down=false;
        up=false;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump){
        this.jump=jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir=false;
        attacking=false;
        moving=false;
        playerAction=IDLE;
        currentHealth=maxHealth;
        hitBox.x=x;
        hitBox.y=y;
        if(!IsEntityOnFloor(hitBox, lvlData)) {
            inAir = true;

        }
    }


}
