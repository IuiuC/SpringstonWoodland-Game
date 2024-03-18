package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import main.Game;
import utiliz.HelpMethods;
import utiliz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utiliz.Constants.ObjectConstants.*;

public class ObjectManager {

    public BufferedImage[][] nutsImgs, berryImgs;

    private ArrayList<Nuts> nuts;
    private ArrayList<Berries> berries;
    private ArrayList<Spike> spikes;

    private Playing playing;
    private BufferedImage spikeImg;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }
    public void loadObjects(Level newLevel) {
        berries=newLevel.getBerries();
        nuts=newLevel.getNuts();
        spikes=newLevel.getSpikes();
    }

    public void checkSpikesTouched(Player player){
        for(Spike s: spikes)
            if(s.getHitbox().intersects(player.getHitbox()))
                player.kill();
    }//daca atinge spikeurile moare
    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Berries b : berries)
            if (b.isActive()) {
                if (hitbox.intersects(b.getHitbox())) {
                    b.setActive(false);
                    applyEffectToPlayer(b);
                }
            }
    }//daca obiectul inca e activ si il ating=>dezactivare

    public void applyEffectToPlayer(Berries p) {
        if (p.getObjType() == BLUEBERRY)
            playing.getPlayer().changePower(BLUEBERRY_VALUE);
    }//adaugare putere pentru obietele colectate


    private void loadImgs() {
        BufferedImage berrySprite = LoadSave.GetSpriteAtlas(LoadSave.BERRY);
        berryImgs = new BufferedImage[1][2];
        for (int j = 0; j < berryImgs.length; j++)
            for (int i = 0; i < berryImgs[j].length; i++)
                 berryImgs[j][i] = berrySprite.getSubimage(32*i, 32 * j, 32, 32);

        BufferedImage nutSprite = LoadSave.GetSpriteAtlas(LoadSave.ACORN);
        nutsImgs = new BufferedImage[1][2];
        for (int j = 0; j < nutsImgs.length; j++)
            for (int i = 0; i < nutsImgs[j].length; i++)
                nutsImgs[j][i] = nutSprite.getSubimage(32*j, 32 * j, 32, 32);
        spikeImg=LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

    }

    public void update() {
        for (Nuts n : nuts)
            if (n.isActive())
                n.update();

        for (Berries b : berries)
            if (b.isActive())
                b.update();
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawNuts(g, xLvlOffset);
        drawBerries(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int lvlOffset) {
        for(Spike s:spikes)
            g.drawImage(spikeImg, (int)(s.getHitbox().x-lvlOffset),(int)(s.getHitbox().y-s.getyDrawOffset()),SPIKE_WIDTH,SPIKE_HEIGHT, null);
    }

    private void drawNuts(Graphics g, int xLvlOffset) {
        for (Nuts n : nuts)
            if (n.isActive()) {
                g.drawImage(nutsImgs[0][n.getAniIndex()], (int) (n.getHitbox().x - n.getxDrawOffset() - xLvlOffset), (int) (n.getHitbox().y - n.getyDrawOffset()), NUT_WIDTH,
                        NUT_HEIGHT, null);
            }
    }

    private void drawBerries(Graphics g, int xLvlOffset) {
        for (Berries b : berries)
            if (b.isActive()) {
                g.drawImage(berryImgs[0][b.getAniIndex()], (int) (b.getHitbox().x - b.getxDrawOffset() - xLvlOffset), (int) (b.getHitbox().y - b.getyDrawOffset()), BLUEBERRY_WIDTH,
                        BLUEBERRY_HEIGHT, null);
            }
    }

    public void resetAllObjects(){
        for (Berries b : berries)
            b.reset();
        for (Nuts n : nuts)
            n.reset();
    }



}