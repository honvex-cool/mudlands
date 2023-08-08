package entities;

import com.badlogic.gdx.graphics.Texture;
import utils.Pair;

public class Player extends Mob{
    public Player(float x, float y, Texture texture){
        super(x,y,texture,0);
    }

    @Override
    public void updateVelocity() {
        super.updateVelocity();
    }
}
