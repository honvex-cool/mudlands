package entities;

import entities.grounds.Ground;
import entities.passives.EmptyPassive;
import entities.passives.Passive;
import generator.FieldStruct;
import generator.GroundType;
import generator.ObjectType;
import utils.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class UniversalFactory {
    Map<GroundType, Class<? extends Ground>> groundMap;
    Map<ObjectType, Class<? extends Passive>> passiveMap;

    public UniversalFactory(
        Map<GroundType, Class<? extends Ground>> groundMap,
        Map<ObjectType, Class<? extends Passive>> passiveMap
    ) {
        this.groundMap = groundMap;
        this.passiveMap = passiveMap;
    }

    public Ground createGround(FieldStruct struct, Pair<Integer,Integer> key) {
        Ground ground;
        try {
            ground = groundMap.get(struct.groundType).getConstructor().newInstance();
            ground.mutablePositionComponent.setX(key.getFirst());
            ground.mutablePositionComponent.setY(key.getSecond());
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return ground;
    }
    public Passive createPassive(FieldStruct struct, Pair<Integer,Integer> key) {
        if(struct.objectType == ObjectType.NONE)
            return new EmptyPassive();
        Passive passive;
        try {
            passive = passiveMap.get(struct.objectType).getConstructor().newInstance();
            passive.mutablePositionComponent.setX(key.getFirst());
            passive.mutablePositionComponent.setY(key.getSecond());
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return passive;
    }
    public Passive createEmptyPassive(Pair<Integer,Integer> key){
        Passive passive = new EmptyPassive();
        passive.mutablePositionComponent.setX(key.getFirst());
        passive.mutablePositionComponent.setY(key.getSecond());
        return passive;
    }
}
