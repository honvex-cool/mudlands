package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import utils.Config;
import utils.SaveStruct;

public class Passive extends Entity implements Hitbox {
    public Passive(float x, float y, Texture texture, int type) {
        super(x, y, Config.TILE_SIZE, texture, type);
    }

    public Passive(SaveStruct saveStruct) {
        super(saveStruct.x+0.25f, saveStruct.y+0.25f,Config.TILE_SIZE/2,new Texture(Gdx.files.internal("assets/textures/DIRT.png")), saveStruct.type);
        String name = switch(saveStruct.type) {
            case 0 -> "DIRT";
            case 1 -> "DIRT";
            default -> "";
        };
        if(!name.equals("")) {
            this.renderComponent.getSprite().setTexture(new Texture(Gdx.files.internal("assets/textures/" + name + ".png")));
        }
    }

    public boolean isDefault() {
        return true;
    }
}
