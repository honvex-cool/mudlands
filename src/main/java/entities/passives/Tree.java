package entities.passives;

import actions.ActionType;
import entities.EntityTag;
import entities.Mob;
import utils.SaveStruct;

import java.util.HashMap;

public class Tree extends Passive {
    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType,actor);
        if(hp<=0){
            generated = false;
            successor = new SaveStruct(EntityTag.NONE,2, positionComponent.getX(),positionComponent.getY(),new HashMap<>());
        }
    }
}
