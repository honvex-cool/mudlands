package entities;

import com.badlogic.gdx.graphics.Texture;
import components.Component;
import components.PositionComponent;
import components.RenderComponent;
import utils.SaveStruct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Entity {
    public PositionComponent positionComponent;
    public RenderComponent renderComponent;
    public int type;
    Entity(float x, float y, float size, Texture texture,int type){
        positionComponent = new PositionComponent(x,y);
        renderComponent = new RenderComponent(size,texture);
        this.type = type;
    }

    public Map<Integer,Integer> getSaveMap(){
        return new HashMap<>();
    }
}
