package utiliz;

import entities.Ant;
import entities.Enemy2;
import entities.Gator;
import main.Game;
import objects.Berries;
import objects.Nuts;
import objects.Spike;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utiliz.Constants.EnemyConstants.*;
import static utiliz.Constants.ObjectConstants.SPIKE;
import static utiliz.LoadSave.ACORN;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }//verificam daca entitatea se poate muta in zona urmatoare

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);

    }//sa nu depaseasca marginile jocului

    private static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];
        if (value >= 48 || value < 0 || value != 11)//cause 11 is the empty tile
            return true;
        return false;

    }


    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        //daca se misca pe axa x =>calculam pozitia fata de wall
        if (xSpeed > 0) {
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }

    }

    public static float GetEntityPosUnderRooforAboveFloor(Rectangle2D.Float hitBox, float airSpeeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (airSpeeed > 0) {
            //Falling=>calculam pozitia pe axa 0y
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData))
            if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
                return false;

        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }//verificam daca hitboxul se afla pe tilesuri

    public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y+1, lvlData))
                return false;
        }
        return true;
    }//verificam daca ne aflam pe o pozitie ok si daca pozitia urm va fi ok

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {

        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);
        if (firstXTile > secondXTile)
            return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);

        else
            return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
    }//verificam daca hitboxul meu si cel al inamicului sunt pe aceeasi axa x pentru ca inamicul sa ma vada

    public static int[][] GetLevelData(BufferedImage img){
        int[][] lvlData=new int[img.getHeight()][img.getWidth()];
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getRed();
                if(value>=48)
                    value=0;
                lvlData[j][i]=value;
            }
        return lvlData;

    }//folosim culoarea rosu pentru a detecta tilesurile!!

    public static ArrayList<Ant>GetAnts(BufferedImage img){
        ArrayList<Ant>list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value==ANT)
                    list.add(new Ant(i* Game.TILES_SIZE, j*Game.TILES_SIZE));
            }
        return list;

    }//parcurgem mapa si cu verde identificam inamicii

    public static ArrayList<Enemy2>GetEnemy2(BufferedImage img){
        ArrayList<Enemy2>list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value==ENEMY2)
                    list.add(new Enemy2(i* Game.TILES_SIZE, j*Game.TILES_SIZE));
            }
        return list;

    }

    public static ArrayList<Gator>GetGator(BufferedImage img){
        ArrayList<Gator>list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value==GATOR)
                    list.add(new Gator(i* Game.TILES_SIZE, j*Game.TILES_SIZE));
            }
        return list;

    }

    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }//??????????????????????????????????????????????????????????

    public static ArrayList<Nuts> GetNuts(BufferedImage img) {
        ArrayList<Nuts> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                double value = color.getBlue();
                if(value==1.2)
                list.add(new Nuts(i * Game.TILES_SIZE, j * Game.TILES_SIZE,1));
            }
        return list;
    }

    public static ArrayList<Berries> GetBerries(BufferedImage img) {
        ArrayList<Berries> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if(value==0)
                list.add(new Berries(i * Game.TILES_SIZE, j * Game.TILES_SIZE,0));
            }
        return list;
    }
    public static ArrayList<Spike> GetSpikes(BufferedImage img){
        ArrayList<Spike> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if(value==SPIKE)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE,SPIKE));
            }
        return list;
    }//cu albastru identificam obiectele


}