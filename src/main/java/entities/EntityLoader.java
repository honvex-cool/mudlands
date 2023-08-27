package entities;

import utils.Savable;
import utils.SaveStruct;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityLoader<T extends Entity & Savable> {
    private final Map<Integer, Class<? extends T>> typeToClass;
    private final Map<Class<? extends T>, Integer> classToType = new HashMap<>();

    public EntityLoader(Map<Integer, Class<? extends T>> typeToClass) {
        this.typeToClass = typeToClass;
        for(Map.Entry<Integer, Class<? extends T>> entry : this.typeToClass.entrySet())
            classToType.put(entry.getValue(), entry.getKey());
    }

    T entityFromStruct(SaveStruct struct) {
        T entity;
        try {
            entity = typeToClass.get(Math.abs(struct.type)).getConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        entity.construct(struct.data, struct.type < 0);
        entity.mutablePositionComponent.setX(struct.x);
        entity.mutablePositionComponent.setY(struct.y);
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
            entity.mutablePositionComponent.getX(),
            entity.mutablePositionComponent.getY(),
            data
        );
    }
}
