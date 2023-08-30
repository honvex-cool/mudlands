package entities;

import entities.grounds.Ground;
import entities.mobs.Mob;
import entities.passives.Passive;
import utils.SaveStruct;

import java.util.Map;

public class UniversalLoader {
    private final EntityLoader<Ground> groundLoader;
    private final EntityLoader<Passive> passiveLoader;
    private final EntityLoader<Mob> mobLoader;
    private final EntityLoader<Player> playerLoader;

    public UniversalLoader(
        Map<Integer, Class<? extends Ground>> groundMap,
        Map<Integer, Class<? extends Passive>> passiveMap,
        Map<Integer, Class<? extends Mob>> mobMap
    ) {
        groundLoader = new EntityLoader<>(groundMap);
        passiveLoader = new EntityLoader<>(passiveMap);
        mobLoader = new EntityLoader<>(mobMap);
        playerLoader = new EntityLoader<>(Map.of(0, Player.class));
    }

    public Ground loadGround(SaveStruct struct) {
        return groundLoader.entityFromStruct(struct);
    }

    public SaveStruct saveGround(Ground ground) {
        SaveStruct struct = groundLoader.structFromEntity(ground);
        struct.entityTag = EntityTag.GROUND;
        return struct;
    }

    public Passive loadPassive(SaveStruct struct) {
        return passiveLoader.entityFromStruct(struct);
    }

    public SaveStruct savePassive(Passive passive) {
        SaveStruct struct = passiveLoader.structFromEntity(passive);
        struct.entityTag = EntityTag.PASSIVE;
        return struct;
    }

    public Mob loadMob(SaveStruct struct) {
        return mobLoader.entityFromStruct(struct);
    }

    public SaveStruct saveMob(Mob mob) {
        SaveStruct struct = mobLoader.structFromEntity(mob);
        struct.entityTag = EntityTag.MOB;
        return struct;
    }

    public Player loadPlayer(SaveStruct struct) {
        return playerLoader.entityFromStruct(struct);
    }

    public SaveStruct savePlayer(Player player) {
        SaveStruct struct = playerLoader.structFromEntity(player);
        struct.entityTag = EntityTag.PLAYER;
        return struct;
    }
}
