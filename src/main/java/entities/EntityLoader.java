package entities;

import utils.AssetManager;
import utils.AssetUser;
import utils.Savable;
import utils.SaveStruct;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityLoader<T extends Entity & Savable & AssetUser> {
    private final Map<Integer, Class<? extends T>> typeToClass;
    private final Map<Class<? extends T>, Integer> classToType = new HashMap<>();
    private final AssetManager assetManager;

    public EntityLoader(Map<Integer, Class<? extends T>> typeToClass, AssetManager assetManager) {
        this.typeToClass = typeToClass;
        for(Map.Entry<Integer, Class<? extends T>> entry : this.typeToClass.entrySet())
            classToType.put(entry.getValue(), entry.getKey());
        this.assetManager = assetManager;
    }

    T entityFromStruct(SaveStruct struct) {
        T entity;
        try {
            entity = typeToClass.get(Math.abs(struct.type)).getConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        entity.construct(struct.data, struct.type < 0);
        entity.positionComponent.setX(struct.x);
        entity.positionComponent.setY(struct.y);
        entity.loadAssets(assetManager);
        return entity;
    }

    SaveStruct structFromEntity(T entity) {
        Map<Integer, Integer> data = entity.saveData();
        int type = classToType.get(entity.getClass());
        if(entity.isGenerated())
            type = -type;
        return new SaveStruct(
            null,
            type,
            entity.positionComponent.getX(),
            entity.positionComponent.getY(),
            data
        );
    }
}
