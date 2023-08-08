package entities;

import com.badlogic.gdx.graphics.Texture;
import components.Component;
import components.PositionComponent;
import components.RenderComponent;
import utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Entity implements Savable, AssetUser {
    public PositionComponent positionComponent = new PositionComponent(0, 0);
    public RenderComponent renderComponent;
    public int type;

    public boolean isGenerated() {
        return false;
    }

    public Map<Integer,Integer> getSaveMap(){
        return new HashMap<>();
    }

    @Override
    public Map<Integer, Integer> saveData() {
        return null;
    }

    @Override
    public void construct(Map<Integer, Integer> struct, boolean generated) {
    }

    @Override
    public void loadAssets(AssetManager assetManager) {
        renderComponent = new RenderComponent(Config.TILE_SIZE, assetManager.getSprite(spriteName()));
    }

    protected String spriteName() {
        return getClass().getSimpleName().toUpperCase();
    }
}
