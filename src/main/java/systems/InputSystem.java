package systems;

import actions.ActionType;
import components.VelocityComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entities.Player;
import utils.Config;
import utils.Debug;
import utils.Pair;

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

        Pair<Float,Float> pointerPosition = new Pair<>((float)Gdx.input.getX() - Config.NATIVE_WIDTH/2,(float)Config.NATIVE_HEIGHT/2 - Gdx.input.getY());
        player.rotationComponent.setRotationFromVector(pointerPosition);


        if(Gdx.input.isButtonPressed((Input.Buttons.LEFT)))
            player.requestAction(ActionType.HIT);
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
            player.requestAction(ActionType.INTERACT);
    }
}
