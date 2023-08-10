package entities.passives;

import actions.ActionType;
import entities.EntityTag;
import entities.Mob;
import utils.SaveStruct;

import java.util.HashMap;

public class Rock extends Passive {
    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType,actor);
        if(hp<=0){
            generated = false;
            successor = new SaveStruct(EntityTag.PASSIVE,1, positionComponent.getX(),positionComponent.getY(),new HashMap<>());
        }
    }
}
