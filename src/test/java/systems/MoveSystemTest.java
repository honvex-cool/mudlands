package systems;

import actions.Movement;
import entities.Player;
import entities.grounds.Ground;
import entities.mobs.Mob;
import org.junit.jupiter.api.Test;
import systems.spawning.PlacementRules;
import utils.Debug;
import utils.Pair;
import utils.VectorMath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoveSystemTest {
    @Test
    void testTryMoveMovesPlayerAndMobs(){
        Player player = new Player();
        Mob mob = new Mob() {};

        PlacementRules placementRulesMock = mock(PlacementRules.class);
        when(placementRulesMock.canMobBePlaced(any(),any())).thenReturn(true);
        Ground groundMock = mock(Ground.class);
        when(groundMock.getSpeedModifier()).thenReturn(1f);

        player.mutablePositionComponent.setX(10);
        player.mutablePositionComponent.setY(20);
        player.velocityComponent.setX(2.5f);
        player.velocityComponent.setY(-1);

        mob.mutablePositionComponent.setX(-20);
        mob.mutablePositionComponent.setY(10);
        mob.velocityComponent.setX(0);
        mob.velocityComponent.setY(0.75f);


        Map<Pair<Integer,Integer>,Ground> grounds = new HashMap<>();
        grounds.put(new Pair<>(10,20),groundMock);
        grounds.put(new Pair<>(-20,10),groundMock);

        Collection<Mob> mobs = new ArrayList<>();
        mobs.add(mob);
        mobs.add(player);

        MoveSystem moveSystem = new MoveSystem(placementRulesMock,grounds,mobs);

        moveSystem.tryMove(player.getMovement(),player,player.velocityComponent.getAsPair(),1f);

        assertEquals(12.5f,player.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(19,player.mutablePositionComponent.getY(), Debug.TEST_DELTA);

        moveSystem.tryMove(mob.getMovement(),mob,mob.velocityComponent.getAsPair(),1f);

        assertEquals(-20,mob.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(10.75f,mob.mutablePositionComponent.getY(), Debug.TEST_DELTA);
    }

    @Test
    void testMoveMovesPlayerAndMobs(){
        Player player = new Player();
        Mob mob = new Mob() {};

        PlacementRules placementRulesMock = mock(PlacementRules.class);
        when(placementRulesMock.canMobBePlaced(any(),any())).thenReturn(true);
        Ground groundMock = mock(Ground.class);
        when(groundMock.getSpeedModifier()).thenReturn(1f);

        player.mutablePositionComponent.setX(10);
        player.mutablePositionComponent.setY(20);
        player.velocityComponent.setX(2.5f);
        player.velocityComponent.setY(-1);

        mob.mutablePositionComponent.setX(-20);
        mob.mutablePositionComponent.setY(10);
        mob.velocityComponent.setX(0);
        mob.velocityComponent.setY(0.75f);


        Map<Pair<Integer,Integer>,Ground> grounds = new HashMap<>();
        grounds.put(new Pair<>(10,20),groundMock);
        grounds.put(new Pair<>(-20,10),groundMock);

        Collection<Mob> mobs = new ArrayList<>();
        mobs.add(mob);
        mobs.add(player);

        MoveSystem moveSystem = new MoveSystem(placementRulesMock,grounds,mobs);

        moveSystem.move(1f);

        assertEquals(12.5f,player.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(19,player.mutablePositionComponent.getY(), Debug.TEST_DELTA);
        assertEquals(-20,mob.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(10.75f,mob.mutablePositionComponent.getY(), Debug.TEST_DELTA);
    }

    @Test
    void testMobsDontCollideAndAlternativeMoveIsFound(){
        Player player = new Player();
        Mob mob1 = new Mob() {
            @Override
            public float getRadius() {
                return 0.25f;
            }
        };
        Mob mob2 = new Mob() {
            @Override
            public float getRadius() {
                return 0.25f;
            }
        };

        Ground groundMock = mock(Ground.class);
        when(groundMock.getSpeedModifier()).thenReturn(1f);

        mob1.mutablePositionComponent.setX(10);
        mob1.mutablePositionComponent.setY(20);
        mob1.velocityComponent.setX(0.4f);
        mob1.velocityComponent.setY(0);

        mob2.mutablePositionComponent.setX(11);
        mob2.mutablePositionComponent.setY(20);
        mob2.velocityComponent.setX(-0.2f);
        mob2.velocityComponent.setY(0);


        Map<Pair<Integer,Integer>,Ground> grounds = new HashMap<>();
        grounds.put(new Pair<>(10,20),groundMock);
        grounds.put(new Pair<>(11,20),groundMock);

        Collection<Mob> mobs = new ArrayList<>();
        mobs.add(mob1);
        mobs.add(mob2);

        PlacementRules placementRulesMock = new PlacementRules(new HashMap<>(),grounds,mobs);

        MoveSystem moveSystem = new MoveSystem(placementRulesMock,grounds,mobs);

        moveSystem.move(1f);

        assertEquals(10.4f,mob1.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(20,mob1.mutablePositionComponent.getY(), Debug.TEST_DELTA);
        assertNotEquals(10.8f,mob2.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertNotEquals(11,mob2.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertNotEquals(20,mob2.mutablePositionComponent.getY(), Debug.TEST_DELTA);

        assertTrue(VectorMath.distance(mob1.mutablePositionComponent.getPosition(),mob2.mutablePositionComponent.getPosition()) > 0.5f);
    }

    @Test
    void testNullMovementDoesNotWork(){
        Mob mob = new Mob() {
            @Override
            public Movement getMovement() {
                return null;
            }
        };

        PlacementRules placementRulesMock = mock(PlacementRules.class);
        when(placementRulesMock.canMobBePlaced(any(),any())).thenReturn(true);
        Ground groundMock = mock(Ground.class);
        when(groundMock.getSpeedModifier()).thenReturn(1f);

        mob.mutablePositionComponent.setX(-20);
        mob.mutablePositionComponent.setY(10);
        mob.velocityComponent.setX(0);
        mob.velocityComponent.setY(0.75f);


        Map<Pair<Integer,Integer>,Ground> grounds = new HashMap<>();
        grounds.put(new Pair<>(-20,10),groundMock);

        Collection<Mob> mobs = new ArrayList<>();
        mobs.add(mob);

        MoveSystem moveSystem = new MoveSystem(placementRulesMock,grounds,mobs);

        moveSystem.move(1f);

        assertEquals(-20,mob.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(10,mob.mutablePositionComponent.getY(), Debug.TEST_DELTA);
    }

    @Test
    void testGroundModifier(){
        Mob mob = new Mob() {};

        PlacementRules placementRulesMock = mock(PlacementRules.class);
        when(placementRulesMock.canMobBePlaced(any(),any())).thenReturn(true);
        Ground groundMock = mock(Ground.class);
        when(groundMock.getSpeedModifier()).thenReturn(2f);

        mob.mutablePositionComponent.setX(-20);
        mob.mutablePositionComponent.setY(10);
        mob.velocityComponent.setX(2.5f);
        mob.velocityComponent.setY(0.75f);


        Map<Pair<Integer,Integer>,Ground> grounds = new HashMap<>();
        grounds.put(new Pair<>(-20,10),groundMock);

        Collection<Mob> mobs = new ArrayList<>();
        mobs.add(mob);

        MoveSystem moveSystem = new MoveSystem(placementRulesMock,grounds,mobs);

        moveSystem.move(1f);

        assertEquals(-15,mob.mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(11.5f,mob.mutablePositionComponent.getY(), Debug.TEST_DELTA);
    }
}