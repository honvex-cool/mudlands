package entities;

import utils.AssetManager;
import utils.SaveStruct;

import java.util.Map;

public class UniversalLoader {
    private final EntityLoader<Ground> groundLoader;
    private final EntityLoader<Passive> passiveLoader;
    private final EntityLoader<Mob> mobLoader;

    private UniversalLoader(
        Map<Integer, Class<? extends Ground>> groundMap,
        Map<Integer, Class<? extends Passive>> passiveMap,
        Map<Integer, Class<? extends Mob>> mobMap,
        AssetManager assetManager
    ) {
        groundLoader = new EntityLoader<>(groundMap, assetManager);
        passiveLoader = new EntityLoader<>(passiveMap, assetManager);
        mobLoader = new EntityLoader<>(mobMap, assetManager);
    }

    public Ground loadGround(SaveStruct struct) {
        return groundLoader.entityFromStruct(struct);
    }

    public SaveStruct saveGround(Ground ground) {
        return groundLoader.structFromEntity(ground);
    }

    public Passive loadPassive(SaveStruct struct) {
        return passiveLoader.entityFromStruct(struct);
    }

    public SaveStruct savePassive(Passive passive) {
        return passiveLoader.structFromEntity(passive);
    }

    public Mob loadMob(SaveStruct struct) {
        return mobLoader.entityFromStruct(struct);
    }

    public SaveStruct saveMob(Mob mob) {
        return mobLoader.structFromEntity(mob);
    }
}
