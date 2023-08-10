package entities.passives;

import actions.ActionType;
import entities.EntityTag;
import entities.Mob;
import utils.SaveStruct;

import java.util.HashMap;
import java.util.Map;

public class Tree extends Passive {
    @Override
    public void construct(Map<Integer, Integer> struct, boolean generated) {
        super.construct(struct, generated);
        this.defaultSuccessor = new SaveStruct(EntityTag.PASSIVE,101,0,0,new HashMap<>());
    }
}
