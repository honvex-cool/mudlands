package entities.grounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import components.RenderComponent;
import entities.Entity;
import utils.AssetManager;
import utils.Config;
import utils.SaveStruct;

public abstract class Ground extends Entity {
    private float speedModifier = 1f;

    public float getSpeedModifier() {
        return speedModifier;
    }
}
