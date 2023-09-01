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
    /*private final EntityLoader<Ground> groundLoader;
    private final EntityLoader<Passive> passiveLoader;
    private final EntityLoader<Mob> mobLoader;
    private final EntityLoader<Player> playerLoader;*/

    Map<GroundType, Class<? extends Ground>> groundMap;
    Map<ObjectType, Class<? extends Passive>> passiveMap;

    public UniversalFactory(
        Map<GroundType, Class<? extends Ground>> groundMap,
        Map<ObjectType, Class<? extends Passive>> passiveMap
        //Map<Integer, Class<? extends Mob>> mobMap
    ) {
        /*groundLoader = new EntityLoader<>(groundMap);
        passiveLoader = new EntityLoader<>(passiveMap);
        mobLoader = new EntityLoader<>(mobMap);
        playerLoader = new EntityLoader<>(Map.of(0, Player.class));*/
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

    /*public SaveStruct saveGround(Ground ground) {
        SaveStruct struct = groundLoader.structFromEntity(ground);
        struct.entityTag = EntityTag.GROUND;
        return struct;
    }*/

    public Passive createPassive(FieldStruct struct, Pair<Integer,Integer> key) {
        //return passiveLoader.entityFromStruct(struct);
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
