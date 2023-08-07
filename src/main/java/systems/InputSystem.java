package systems;

import components.Component;
import components.PlayerComponent;
import components.VelocityComponent;
import entities.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Set;

import static java.lang.System.out;

public class InputSystem extends RepetitiveSystem {
    private static final Set<Class<? extends Component>> REQUIRED_COMPONENTS = Set.of(
        PlayerComponent.class,
        VelocityComponent.class
    );

    @Override
    public void updateOne(Entity player, float deltaTime) {
        VelocityComponent velocity = player.get(VelocityComponent.class);

        float speed = 200f;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.setX(-speed);
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.setX(speed);
        } else {
            velocity.setX(0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.setY(speed);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.setY(-speed);
        } else {
            velocity.setY(0);
        }
    }

    @Override
    protected Set<Class<? extends Component>> requirements() {
        return REQUIRED_COMPONENTS;
    }
}
