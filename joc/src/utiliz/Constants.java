package utiliz;

import main.Game;

public class Constants {
    public static final float GRAVITY=0.04f* Game.SCALE;

    public static class ObjectConstants{

        public static final int BLUEBERRY=0;
        public static final int NUT=1;
        public static final int BLUEBERRY_WIDTH_DEFAULT=32;
        public static final int BLUEBERRY_HEIGHT_DEFAULT=32;
        public static final int SPIKE=10;

        public static final int BLUEBERRY_HEIGHT=(int)(Game.SCALE*BLUEBERRY_HEIGHT_DEFAULT);
        public static final int BLUEBERRY_WIDTH=(int)(Game.SCALE*BLUEBERRY_WIDTH_DEFAULT);
        public static final int NUT_WIDTH_DEFAULT=32;
        public static final int NUT_HEIGHT_DEFAULT=32;
        public static final int NUT_HEIGHT=(int)(Game.SCALE*NUT_HEIGHT_DEFAULT);
        public static final int NUT_WIDTH=(int)(Game.SCALE*NUT_WIDTH_DEFAULT);
        public static final int SPIKE_WIDTH_DEFAULT=32;
        public static final int SPIKE_HEIGHT_DEFAULT=32;
        public static final int SPIKE_HEIGHT=(int)(Game.SCALE*SPIKE_HEIGHT_DEFAULT);
        public static final int SPIKE_WIDTH=(int)(Game.SCALE*SPIKE_WIDTH_DEFAULT);

        public static final int BLUEBERRY_VALUE=15;
        public static final int NUT_VALUE=10;

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case BLUEBERRY:
                case NUT:
                    return 2;
            }
            return 1;
        }
    }
    public static class EnemyConstants{
        public static final int ANT=0;
        public static final int ENEMY2=127;
        public static final int GATOR=92;

        public static final int IDLE=1;
        public static final int RUNNING=0;
        public static final int ATTACK=2;
        public static final int DEAD=3;
        public static final int ANT_WIDTH_DEFAULT=52;
        public static final int ANT_HEIGHT_DEFAULT=45;
        public static final int GATOR_WIDTH_DEFAULT=46;
        public static final int GATOR_HEIGHT_DEFAULT=49;
        public static final int ENEMY2_WIDTH_DEFAULT=37;
        public static final int ENEMY2_HEIGHT_DEFAULT=31;
        public static final int ANT_WIDTH=(int)(ANT_WIDTH_DEFAULT*Game.SCALE);
        public static final int ANT_HEIGHT=(int)(ANT_HEIGHT_DEFAULT*Game.SCALE);
        public static final int ENEMY2_WIDTH=(int)(ENEMY2_WIDTH_DEFAULT*Game.SCALE);
        public static final int ENEMY2_HEIGHT=(int)(ENEMY2_HEIGHT_DEFAULT*Game.SCALE);
        public static final int GATOR_WIDTH=(int)(GATOR_WIDTH_DEFAULT*Game.SCALE);
        public static final int GATOR_HEIGHT=(int)(GATOR_HEIGHT_DEFAULT*Game.SCALE);

        public static final int ANT_DRAWOFFSET_X=(int)(26*Game.SCALE);
        public static final int ANT_DRAWOFFSET_Y=(int)(9*Game.SCALE);
        //returneaza numarul de imagini
        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch(enemy_type){
                case ANT:
                    switch(enemy_state){
                        case RUNNING:
                        case ATTACK:
                            return 2;
                        case IDLE:
                        case DEAD:
                            return 4;
                    }
                case ENEMY2:
                    switch(enemy_state){
                        case DEAD:
                            return 4;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                        case IDLE:
                            return 2;
                    }
                case GATOR:
                    switch(enemy_state){
                       case DEAD:
                        case RUNNING:
                        case ATTACK:
                        case IDLE:
                            return 4;

                    }
            }
            return 0;
        }
        public static int GetMAxHealth(int enemyType){
            switch(enemyType) {
                case ANT:
                    return 10;
                case ENEMY2:
                    return 10;
                case GATOR:
                    return 20;
                default:return 0;

            }

        }
        public static int GetEnemyDmg(int enemyType){
            switch(enemyType) {
                case ANT:
                    return 30;
                case ENEMY2:
                    return 20;
                case GATOR:
                    return 50;
                default:
                    return 0;
            }
        }

    }
    public static class Environment{
        public static final int BIG_CLOUDS_WIDTH_DEFAULT=448;
        public static final int BIG_CLOUDS_HEIGHT_DEFAULT=300;
        public static final int SMALL_CLOUDS_WIDTH_DEFAULT=448;
        public static final int SMALL_CLOUDS_HEIGHT_DEFAULT=112;
        public static final int DEC6_WIDTH_DEFAULT=74;
        public static final int DEC6_HEIGHT_DEFAULT=24;
        public static final int BIG_CLOUDS_WIDTH=(int)(BIG_CLOUDS_WIDTH_DEFAULT *Game.SCALE);
        public static final int BIG_CLOUDS_HEIGHT=(int)(BIG_CLOUDS_HEIGHT_DEFAULT *Game.SCALE);
        public static final int SMALL_CLOUDS_WIDTH=(int)(SMALL_CLOUDS_WIDTH_DEFAULT *Game.SCALE);
        public static final int SMALL_CLOUDS_HEIGHT=(int)(SMALL_CLOUDS_HEIGHT_DEFAULT *Game.SCALE);

        public static final int DEC6_WIDTH=(int)(DEC6_WIDTH_DEFAULT *Game.SCALE);
        public static final int DEC6_HEIGHT=(int)(DEC6_HEIGHT_DEFAULT *Game.SCALE);


    }

        public static class UI{
            public static class Buttons{
                public static final int B_WIDTH_DEFAULT=140;
                public static final int B_HEIGHT_DEFAULT=56;
                public static final int B_WIDTH=(int)(B_WIDTH_DEFAULT* Game.SCALE);
                public static final int B_HEIGHT=(int)(B_HEIGHT_DEFAULT* Game.SCALE);

            }
            public static class URMButtons {
                public static final int URM_DEFAULT_SIZE = 56;
                public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

            }

    }
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 2;
        //public static final int JUMP=3;
        public static final int HURT = 4;
        public static final int ATTACK_1 = 1;
        public static final int DEAD = 3;
        //public static final int CROUCH = 5;

        //pentru fiecare actiune returnez numarul de imagini ce alcatuiesc miscarea respectiva
        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case IDLE:
                    return 8;
                case RUNNING:
                    return 6;
                case DEAD:
                case ATTACK_1:
                    return 4;
                case HURT:
                     return 2;
                default:
                    return 1;

            }
        }
    }
}
