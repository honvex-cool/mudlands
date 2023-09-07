package systems;

import actions.ActionType;
import components.MutablePositionComponent;
import components.PositionComponent;
import entities.Entity;
import entities.grounds.Ground;
import entities.mobs.Mob;
import entities.Player;
import entities.passives.Passive;
import openable.items.Item;
import openable.items.structures.Placable;
import utils.Pair;
import utils.VectorMath;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActionManagerSystem {
    public void update(Player player,Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs){
        if(player.nextAction != null){
            if(player.nextAction == ActionType.BUILD){
                handleBuilding(player,passives,mobs);
            }
            else {
                Set<Entity> recipients = getActionRecipients(player, passives, mobs);
                for(Entity entity : recipients) {
                    entity.react(player.nextAction, player);
                }
            }
            player.nextAction = null;
        }

        mobs.add(player);
        for(Mob mob:mobs){
            if(mob.nextAction != null && !mob.isDestroyed()){
                Set<Entity> recipients = getActionRecipients(mob,passives,mobs);
                for(Entity entity:recipients) {
                    entity.react(mob.nextAction,mob);
                }
                mob.nextAction = null;
            }
        }
        mobs.remove(player);
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
                if(VectorMath.distance(endPoint,m.mutablePositionComponent.getPosition()) < m.getRadius() && m != mob) {
                    set.add(m);
                }
            }
        }
        return set;
    }

    private void handleBuilding(Player player,Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs){
        Item item = player.getInventory().getLeftHand();
        if(item == null)
            return;
        if(!(item instanceof Placable)) //which also means item != null
            return;

        for(float mo=0.2f;mo<=1f;mo+=0.2f) {
            Pair<Float, Float> endDelta = VectorMath.getVectorFromRotation(player.rotationComponent.getRotation(), mo);
            Pair<Float,Float> end = new Pair<>(player.mutablePositionComponent.getX() + endDelta.getFirst(),player.mutablePositionComponent.getY() + endDelta.getSecond());
            Pair<Integer,Integer> endPoint = new Pair<>((int)Math.floor(end.getFirst()),(int)Math.floor(end.getSecond()));
            boolean possible = true;
            if(passives.containsKey(endPoint) || PositionComponent.getFieldAsPair(player.mutablePositionComponent).equals(endPoint)){
                continue;
            }
            for(Mob mob:mobs){
                if(PositionComponent.getFieldAsPair(mob.mutablePositionComponent).equals(endPoint)){
                    possible = false;
                    break;
                }
            }
            if(possible){
                Passive constructed = ((Placable) item).afterConstruction();
                constructed.mutablePositionComponent.setX(endPoint.getFirst());
                constructed.mutablePositionComponent.setY(endPoint.getSecond());
                passives.put(endPoint,constructed);
                player.getInventory().removeItem(item,1);
                return;
            }
        }
    }
}
