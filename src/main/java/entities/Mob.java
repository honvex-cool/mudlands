package entities;

import com.badlogic.gdx.graphics.Texture;
import components.PositionComponent;
import components.VelocityComponent;
import utils.Config;
import utils.Pair;

public class Mob extends Entity implements Hitbox {
    public VelocityComponent velocityComponent;
    private float moveSpeed;

    public Mob() {
        this(0,0);
    }

    public Mob(float x, float y){
        positionComponent = new PositionComponent(x, y);
        velocityComponent = new VelocityComponent();
    }

    public void updateVelocity() {
    }
}
