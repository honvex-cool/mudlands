package entities;

import components.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {
    private int nextEntityId = 0;
    private final Set<Entity> entities = new HashSet<>();

    public Entity createEntity() {
        return createEntity(null);
    }

    public Entity createEntity(String name) {
        Entity entity = new Entity(this, nextEntityId++, name);
        entities.add(entity);
        return entity;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntitiesWithComponents(Class<? extends Component>... componentClasses) {
        List<Entity> matchingEntities = new ArrayList<>();

        for(Entity entity : entities) {
            boolean allComponentsFound = true;

            for(Class<? extends Component> componentClass : componentClasses) {
                if(!entity.has(componentClass)) {
                    allComponentsFound = false;
                    break;
                }
            }

            if(allComponentsFound) {
                matchingEntities.add(entity);
            }
        }

        return matchingEntities;
    }
}
