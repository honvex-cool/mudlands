package entities;

import com.badlogic.gdx.graphics.Texture;
import components.VelocityComponent;
import utils.Config;
import utils.Pair;

public class Mob extends Entity implements Hitbox {
    public VelocityComponent velocityComponent;
    private final float radius;
    public Mob(float x, float y, float radius, Texture texture,int type){
        super(x,y, Config.TILE_SIZE/2,texture,type);
        velocityComponent = new VelocityComponent();
        this.radius = radius;
    }

    public void updateVelocity() {
    }

    @Override
    public float getRadius() {
        return radius;
    }
}
