package entities;

import components.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private int nextEntityId;
    private Map<Integer, Entity> entities;

    public EntityManager() {
        nextEntityId = 1;
        entities = new HashMap<>();
    }

    public Entity createEntity() {
        int entityId = nextEntityId++;
        Entity entity = new Entity();
        entity.setId(entityId);
        entities.put(entityId, entity);
        return entity;
    }

    public Entity getEntity(int entityId) {
        return entities.get(entityId);
    }

    public void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public List<Entity> getEntitiesWithComponents(Class<? extends Component>... componentClasses) {
        List<Entity> matchingEntities = new ArrayList<>();

        for (Entity entity : entities.values()) {
            boolean allComponentsFound = true;

            for (Class<? extends Component> componentClass : componentClasses) {
                if (!entity.has(componentClass)) {
                    allComponentsFound = false;
                    break;
                }
            }

            if (allComponentsFound) {
                matchingEntities.add(entity);
            }
        }

        return matchingEntities;
    }
}
