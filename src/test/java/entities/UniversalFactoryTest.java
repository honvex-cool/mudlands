package entities;

import entities.grounds.Ground;
import entities.grounds.Water;
import entities.passives.EmptyPassive;
import entities.passives.Passive;
import entities.passives.Tree;
import generator.FieldStruct;
import generator.GroundType;
import generator.ObjectType;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UniversalFactoryTest {
    @Test
    void testCreateGround(){
        Map<GroundType,Class<? extends Ground>> groundMap = Map.of(GroundType.WATER, Water.class);
        Map<ObjectType,Class<? extends Passive>> passiveMap = new HashMap<>();
        UniversalFactory universalFactory = new UniversalFactory(groundMap,passiveMap);

        var test = universalFactory.createGround(new FieldStruct(GroundType.WATER,ObjectType.TREE),new Pair<>(2,3));

        assertNotNull(test);
        assertEquals(Water.class,test.getClass());
        assertEquals(new Pair<>(2f,3f),test.mutablePositionComponent.getPosition());

        try {
            var test2 = universalFactory.createGround(new FieldStruct(GroundType.STONE, ObjectType.TREE), new Pair<>(2, 3));
            fail();
        }
        catch(Exception e){
            //
        }
    }

    @Test
    void testCreatePassive(){
        Map<GroundType,Class<? extends Ground>> groundMap = new HashMap<>();
        Map<ObjectType,Class<? extends Passive>> passiveMap = Map.of(ObjectType.TREE, Tree.class);
        UniversalFactory universalFactory = new UniversalFactory(groundMap,passiveMap);

        var test = universalFactory.createPassive(new FieldStruct(GroundType.WATER,ObjectType.TREE),new Pair<>(2,3));

        assertNotNull(test);
        assertEquals(Tree.class,test.getClass());
        assertEquals(new Pair<>(2f,3f),test.mutablePositionComponent.getPosition());

        try {
            var test2 = universalFactory.createPassive(new FieldStruct(GroundType.STONE, ObjectType.STONE), new Pair<>(2, 3));
            fail();
        }
        catch(Exception e){
            //
        }
    }

    @Test
    void testCreateEmptyPassive(){
        Map<GroundType,Class<? extends Ground>> groundMap = Map.of(GroundType.WATER, Water.class);
        Map<ObjectType,Class<? extends Passive>> passiveMap = new HashMap<>();
        UniversalFactory universalFactory = new UniversalFactory(groundMap,passiveMap);

        var test = universalFactory.createEmptyPassive(new Pair<>(2,3));

        assertNotNull(test);
        assertEquals(EmptyPassive.class,test.getClass());
        assertEquals(new Pair<>(2f,3f),test.mutablePositionComponent.getPosition());
    }

}