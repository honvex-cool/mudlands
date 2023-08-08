package entities.passives;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.Entity;
import entities.Hitbox;
import utils.Config;
import utils.SaveStruct;

import java.util.Map;

public class Passive extends Entity implements Hitbox {
    private boolean generated;

    @Override
    public boolean isGenerated() {
        return generated;
    }
}
