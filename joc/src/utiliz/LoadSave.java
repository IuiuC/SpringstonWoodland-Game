package utiliz;

import entities.Ant;
import main.Game;
import objects.Berries;
import objects.Nuts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static utiliz.Constants.EnemyConstants.ANT;

public class LoadSave {
    public static final String PLAYER_ATLAS="squirrel.png";
    public static final String LEVEL_ATLAS="Tilesetul.png";
    public static final String MENU_BUTTONS="button_atlas.png";

    public static final String BG_IMG="DECC1.png";
    public static final String BIG_CLOUDS="DECC3.png";
    public static final String SMALL_CLOUDS="DECC2.png";

    public static final String BG_IMG1="DECCC1.png";
    public static final String BIG_CLOUDS1="DECCC2.png";
    public static final String SMALL_CLOUDS1="DECCC3.png";

    public static final String SMALL_CLOUDS2="DEC2.png";
    public static final String BG_IMG2="DEC1.png";
    public static final String BIG_CLOUDS2="DEC3.png";


    public static final String DECO6= "deco.png";
    public static final String ANTSPRITE= "gandacuu.png";
    public static final String ESPRITE= "enemy2good.png";
    public static final String GATORSPRITE= "gator-1.png";


    public static final String STATUS_BAR= "health_power_bar.png";
    public static final String ACORN= "acorn.png";
    public static final String BERRY= "berry.png";
    //public static final BufferedImage img=GetSpriteAtlas(LEVEL_ONE_DATA);
    public static final String TRAP_ATLAS= "trap_atlas.png";
    public static final String MENU_BCG= "menuback.png";
    public static final String DEATH_SCREEN= "gameover.png";
    public static final String URM_BUTTONS= "urm_buttons.png";
    public static final String COMPLETED_SCREEN= "levelcompleted.png";



    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img=null;
        InputStream is = LoadSave.class.getResourceAsStream("/"+fileName);

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }

    public static BufferedImage[] GetAllLevels(){
        URL url=LoadSave.class.getResource("/lvls");
        File file=null;
        try{
        file=new File(url.toURI());
        }catch(URISyntaxException e){
            e.printStackTrace();
        }
        File[] files=file.listFiles();

        File[] filesSorted=new File[files.length];
        for(int i=0; i<filesSorted.length;i++)
            for(int j=0;j<files.length;j++) {
                 if(files[j].getName().equals((i+1) + ".png"))
                     filesSorted[i]=files[j];

            }

        BufferedImage[] imgs=new BufferedImage[filesSorted.length];
        for(int i=0;i<imgs.length;i++) {
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    return imgs;
    }



}
