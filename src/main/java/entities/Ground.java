package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import utils.Config;
import utils.SaveStruct;

public class Ground extends Entity{

    private float speedModifier = 1f;
    public Ground(float x, float y, Texture texture,int type){
        super(x,y,Config.TILE_SIZE,texture,type);
    }
    public Ground(SaveStruct saveStruct){
        super(saveStruct.x, saveStruct.y,Config.TILE_SIZE,new Texture(Gdx.files.internal("assets/textures/DIRT.png")), saveStruct.type);
        String name = switch(saveStruct.type){
            case 0 -> "WATER";
            case 1 -> "SAND";
            case 2 -> "GRASS";
            case 3 -> "MUD";
            case 4 -> "STONE";
            case 5 -> "DIRT";
            default -> "";
        };
        if(saveStruct.type == 0){
            speedModifier = 3f;
        }
        this.renderComponent.getSprite().setTexture(new Texture(Gdx.files.internal("assets/textures/" + name + ".png")));
    }

    public float getSpeedModifier() {
        return speedModifier;
    }
}
