package systems;

import actions.ActionType;
import components.VelocityComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entities.Player;
import entities.materials.Damage;
import entities.mobs.Mob;
import utils.Config;
import utils.Debug;
import utils.Pair;

public class InputSystem {

    private boolean inventoryClicked;
    private boolean craftingClicked;
    private boolean statusClicked;

    private boolean open;

    public InputSystem() {
        inventoryClicked = false;
        craftingClicked = false;
        statusClicked = false;
        open = false;
    }

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

        Pair<Float, Float> pointerPosition = new Pair<>((float)Gdx.input.getX() - Config.NATIVE_WIDTH / 2, (float)Config.NATIVE_HEIGHT / 2 - Gdx.input.getY());
        player.rotationComponent.setRotationFromVector(pointerPosition);

        if(Debug.INSTANT_DEATH && Gdx.input.isKeyPressed(Input.Keys.O)) {
            player.react(ActionType.HIT, new Mob() {
                @Override
                public Damage getAttackDamage() {
                    return new Damage(1000, 1000, 1000, 1000);
                }
            });
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            setBooleans(true, false, false);
            open = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            setBooleans(false, true, false);
            open = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            setBooleans(false, false, true);
            open = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setBooleans(false, false, false);
            open = false;
        } else {
            setBooleans(false, false, false);
        }

        if(Gdx.input.isButtonPressed((Input.Buttons.LEFT)) && !open)
            player.requestAction(ActionType.HIT);
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && !open)
            player.requestAction(ActionType.INTERACT);
        else if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            player.requestAction(ActionType.BUILD);
    }


    private void setBooleans(boolean inv, boolean craft, boolean status) {
        inventoryClicked = inv;
        craftingClicked = craft;
        statusClicked = status;
    }

    public boolean isInventoryClicked() {
        return inventoryClicked;
    }

    public boolean isCraftingClicked() {
        return craftingClicked;
    }

    public boolean isStatusClicked() {
        return statusClicked;
    }

}
