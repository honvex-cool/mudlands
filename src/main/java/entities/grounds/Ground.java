package entities.grounds;

import actions.ActionType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import components.RenderComponent;
import entities.Entity;
import entities.Mob;
import utils.AssetManager;
import utils.Config;
import utils.SaveStruct;

public abstract class Ground extends Entity {
    private float speedModifier = 1f;
    public float getSpeedModifier() {
        return speedModifier;
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        return;
    }
}
