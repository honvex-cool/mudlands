package systems;

import actions.ActionType;
import entities.Player;
import entities.mobs.Mob;
import entities.mobs.Zombie;
import entities.passives.Passive;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ActionManagerSystemTest {
    @Test
    public void testNextActionIsNullAfterAction(){
        ActionManagerSystem actionManagerSystem = new ActionManagerSystem();
        Player player = new Player();
        player.nextAction = ActionType.HIT;
        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();
        Zombie zombie = new Zombie();
        zombie.nextAction = ActionType.HIT;
        Collection<Mob> mobs = new ArrayList<>(List.of(zombie));

        actionManagerSystem.update(player,passives,mobs);

        assertNull(player.nextAction);
        assertNull(zombie.nextAction);
    }

    @Test
    public void testDestroyedMobsDontMakeActions(){
        ActionManagerSystem actionManagerSystem = new ActionManagerSystem();
        Player player = new Player();
        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();
        Mob mob = new Mob() {
            @Override
            public boolean isDestroyed() {
                return true;
            }
        };
        mob.nextAction = ActionType.HIT;

        Collection<Mob> mobs = new ArrayList<>(List.of(mob));

        actionManagerSystem.update(player,passives,mobs);

        assertEquals(ActionType.HIT,mob.nextAction);
    }

    @Test
    public void testPlayerHitAction(){
        ActionManagerSystem actionManagerSystem = new ActionManagerSystem();
        Player player = new Player();
        player.mutablePositionComponent.setX(2f);
        player.mutablePositionComponent.setY(2f);

        Mob mob = new Mob() {
            private boolean hit = false;
            @Override
            public void react(ActionType actionType, Mob actor) {
                if(actionType == ActionType.HIT && actor == player){
                    hit = true;
                }
            }

            @Override
            public boolean isDestroyed() {
                return hit;
            }
        };
        mob.mutablePositionComponent.setX(2.5f);
        mob.mutablePositionComponent.setY(2f);

        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();


        Collection<Mob> mobs = new ArrayList<>(List.of(mob));

        player.nextAction = ActionType.HIT;
        player.rotationComponent.setRotationFromVector(new Pair<>(-1f,0f));
        actionManagerSystem.update(player,passives,mobs);

        assertFalse(mob.isDestroyed());


        player.nextAction = ActionType.HIT;
        player.rotationComponent.setRotationFromVector(new Pair<>(1f,0f));
        actionManagerSystem.update(player,passives,mobs);

        assertTrue(mob.isDestroyed());
    }
}