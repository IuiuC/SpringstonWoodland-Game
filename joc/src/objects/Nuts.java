package objects;

import main.Game;

public class Nuts extends GameObject {
    public Nuts(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(15, 15);
        xDrawOffset = (int) (3 * Game.SCALE);
        yDrawOffset = (int) (2 * Game.SCALE);

    }

    public void update() {
        updateAnimationTick();
    }
}
