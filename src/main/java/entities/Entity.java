package entities;

import actions.ActionType;
import components.PositionComponent;
import components.RenderComponent;
import utils.*;

import java.util.HashMap;
import java.util.Map;

public class Entity implements Savable, AssetUser {
    public PositionComponent positionComponent = new PositionComponent(0, 0);
    public RenderComponent renderComponent;
    public int type;
    protected int hp;
    protected SaveStruct successor=null;
    public boolean isGenerated() {
        return false;
    }

    public Map<Integer,Integer> getSaveMap(){
        return new HashMap<>();
    }

    @Override
    public Map<Integer, Integer> saveData() {
        return Map.of(0,hp);
    }

    @Override
    public void construct(Map<Integer, Integer> struct, boolean generated) {
        hp = 100;
        if(!struct.isEmpty()){
            hp = struct.get(0);
        }
    }

    @Override
    public void loadAssets(AssetManager assetManager) {
        renderComponent = new RenderComponent(Config.TILE_SIZE, assetManager.getSprite(spriteName()));
    }
    protected String spriteName() {
        return getClass().getSimpleName().toLowerCase();
    }

    public void react(ActionType actionType, Mob actor){
        if(actionType == ActionType.HIT){
            hp -= actor.getAttackStrength();
            if(hp <=0 ){
                successor = new SaveStruct(EntityTag.NONE,0, positionComponent.getX(), positionComponent.getY(), new HashMap<>());
            }
        }
    }

    public boolean isDestroyed(){
        return (successor != null);
    }
    public SaveStruct getSuccessor(){
        return successor;
    }
}
