package entities;

import com.badlogic.gdx.graphics.Texture;
import components.VelocityComponent;
import utils.Config;

public class Mob extends Entity{
    public VelocityComponent velocityComponent;
    public Mob(float x, float y, Texture texture,int type){
        super(x,y, Config.TILE_SIZE/2,texture,type);
        velocityComponent = new VelocityComponent();
    }
}
