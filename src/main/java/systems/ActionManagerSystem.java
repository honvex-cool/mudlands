package systems;

import components.PositionComponent;
import entities.Entity;
import entities.Mob;
import entities.Player;
import entities.passives.Passive;
import utils.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActionManagerSystem {
    public void update(Player player,Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs){
        if(player.nextAction != null){
            Set<Entity> recipients = getActionRecipients(player,passives,mobs);
            for(Entity entity:recipients) {
                entity.react(player.nextAction,player);
            }
            player.nextAction = null;
        }
    }
    private Set<Entity> getActionRecipients(Mob mob, Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs){
        Set<Entity> set = new HashSet<>();
        Pair<Float,Float> end = angleToVector(mob.rotationComponent.getRotation(),1);
        float x = mob.mutablePositionComponent.getX();
        float y = mob.mutablePositionComponent.getY();
        Pair<Integer,Integer> endField = new Pair<>((int) Math.floor(x+end.getFirst()), (int) Math.floor(y+end.getSecond()));
        if(passives.containsKey(endField)){
            set.add(passives.get(endField));
        }
        for(Mob m:mobs){
            if(PositionComponent.getFieldAsPair(m.mutablePositionComponent).equals(endField) && mob != m) {
                set.add(m);
            }
        }
        return set;
    }

    protected Pair<Float,Float> angleToVector(float angle,float scale){
        float dx = (float) Math.cos(Math.toRadians(angle))*scale;
        float dy = (float) Math.sin(Math.toRadians(angle))*scale;
        return new Pair<>(dx,dy);
    }
}
