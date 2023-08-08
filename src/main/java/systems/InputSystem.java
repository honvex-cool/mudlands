package systems;

import components.Component;
import components.PlayerComponent;
import components.PositionComponent;
import components.VelocityComponent;
import entities.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entities.Player;

import java.util.Set;

public class InputSystem {

    public void update(Player player, float deltaTime) {
        VelocityComponent velocity = player.velocityComponent;

        float speed = 2f;

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
}
