package systems.spawning;

import entities.grounds.Grass;
import entities.grounds.Ground;
import entities.grounds.Sand;
import entities.mobs.Mob;
import entities.mobs.Zombie;
import entities.passives.Passive;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlacementRulesTest {
    @Test
    void testForbiddenAtCertainTypeOfGround(){
        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();
        Map<Pair<Integer,Integer>, Ground> grounds = new HashMap<>();
        Collection<Mob> mobs = new ArrayList<>();

        PlacementRules placementRules = new PlacementRules(passives,grounds,mobs);

        Zombie zombie = new Zombie();
        zombie.mutablePositionComponent.setX(2);
        zombie.mutablePositionComponent.setY(3);
        mobs.add(zombie);
        Sand sand = new Sand();
        grounds.put(new Pair<>(2,3),sand);

        assertTrue(placementRules.canMobBePlaced(zombie, zombie.mutablePositionComponent));
        assertFalse(placementRules.isForbiddenAt(Zombie.class, zombie.mutablePositionComponent));

        placementRules.forbidOn(Zombie.class,Sand.class);

        assertFalse(placementRules.canMobBePlaced(zombie, zombie.mutablePositionComponent));
        assertTrue(placementRules.isForbiddenAt(Zombie.class, zombie.mutablePositionComponent));
    }

    @Test
    void testForbiddenAtActivePassivesAndAllowedAtOther(){
        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();
        Map<Pair<Integer,Integer>, Ground> grounds = new HashMap<>();
        Collection<Mob> mobs = new ArrayList<>();

        PlacementRules placementRules = new PlacementRules(passives,grounds,mobs);

        Zombie zombie = new Zombie();
        zombie.mutablePositionComponent.setX(2);
        zombie.mutablePositionComponent.setY(3);
        mobs.add(zombie);
        Passive passiveMock = mock(Passive.class);
        passives.put(new Pair<>(2,3),passiveMock);
        Ground groundMock = mock(Ground.class);
        grounds.put(new Pair<>(2,3),groundMock);

        when(passiveMock.isActive()).thenReturn(false);
        assertTrue(placementRules.canMobBePlaced(zombie, zombie.mutablePositionComponent));

        when(passiveMock.isActive()).thenReturn(true);
        assertFalse(placementRules.canMobBePlaced(zombie, zombie.mutablePositionComponent));
    }
}