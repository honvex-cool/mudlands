package systems;

import components.PositionComponent;
import entities.Entity;
import entities.mobs.Mob;
import entities.Player;
import entities.passives.Passive;
import utils.Pair;
import utils.VectorMath;

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
        for(float mo=1f;mo>0f;mo-=0.2f){
            Pair<Float,Float> end = VectorMath.getVectorFromRotation(mob.rotationComponent.getRotation(),mo);
            float x = mob.mutablePositionComponent.getX();
            float y = mob.mutablePositionComponent.getY();
            Pair<Integer,Integer> endField = new Pair<>((int) Math.floor(x+end.getFirst()), (int) Math.floor(y+end.getSecond()));
            Pair<Float,Float> endPoint = new Pair<>(x+end.getFirst(),y+end.getSecond());
            if(passives.containsKey(endField)){
                set.add(passives.get(endField));
            }
            for(Mob m:mobs){
                if(VectorMath.distance(endPoint,m.mutablePositionComponent.getPosition()) < m.getRadius()) {
                    set.add(m);
                }
            }
        }
        return set;
    }
}
