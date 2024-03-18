package levels;

import entities.Ant;
import entities.Enemy2;
import entities.Gator;
import main.Game;
import objects.Berries;
import objects.Nuts;
import objects.Spike;
import utiliz.HelpMethods;
import utiliz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static utiliz.HelpMethods.*;

public class Level {
    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Ant>ants;
    private ArrayList<Enemy2>enemy2;
    private ArrayList<Gator>gator;

    private ArrayList<Nuts> nuts;
    private ArrayList<Berries> berries;
    private ArrayList<Spike> spikes;

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img){
       this.img=img;
       createLevelData();
       createEnemies();
       createBerries();
       createNuts();
       createSpikes();
       calcLvlOffsets();
       calcPlayerSpawn();
    }

    private void createSpikes() {
        spikes=HelpMethods.GetSpikes(img);
    }

    private void createNuts() {
        nuts=HelpMethods.GetNuts(img);
    }

    private void createBerries() {
        berries=HelpMethods.GetBerries(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide=img.getWidth();
        maxTilesOffset=lvlTilesWide-Game.TILES_IN_WIDTH;
        maxLvlOffsetX=Game.TILES_SIZE*maxTilesOffset;
    }

    private void calcPlayerSpawn(){
        playerSpawn=GetPlayerSpawn(img);
    }

    private void createEnemies() {
        ants=GetAnts(img);
        enemy2=GetEnemy2(img);
        gator=GetGator(img);

    }

    private void createLevelData() {
        lvlData=GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    public int [][] getLevelData(){

        return lvlData;
    }
    public int getMaxLvlOffset(){
        return maxLvlOffsetX;
    }
    public Point getPlayerSpawn() {
        return playerSpawn;
    }
    public ArrayList<Ant> getAnts() {
        return ants;
    }
    public ArrayList<Enemy2> getEnemy2() {
        return enemy2;
    }
    public ArrayList<Gator> getGator() {
        return gator;
    }


    public ArrayList<Nuts>getNuts(){
        return nuts;
    }
    public ArrayList<Berries>getBerries(){
        return berries;
    }
    public ArrayList<Spike>getSpikes(){
        return spikes;
    }


}
