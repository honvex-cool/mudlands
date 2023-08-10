package entities;

import com.badlogic.gdx.graphics.Texture;
import components.PositionComponent;
import components.RotationComponent;
import components.VelocityComponent;
import utils.Config;
import utils.Pair;

public class Mob extends Entity implements Hitbox {
    public VelocityComponent velocityComponent;
    public RotationComponent rotationComponent;
    private float moveSpeed;

    public int attackStrength = 6;

    public Mob() {
        this(0,0);
    }

    public Mob(float x, float y){
        positionComponent = new PositionComponent(x, y);
        velocityComponent = new VelocityComponent();
        rotationComponent = new RotationComponent();
    }

    public void updateVelocity() {
    }

    public int getAttackStrength(){
        return attackStrength;
    }
}
