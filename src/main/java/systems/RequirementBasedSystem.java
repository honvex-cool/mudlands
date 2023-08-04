package systems;

import components.Component;
import entities.Entity;

import java.util.Set;

public abstract class RequirementBasedSystem extends StoringSystem {
    protected abstract Set<Class<? extends Component>> requirements();

    private boolean meetsRequirements(Entity entity) {
        for(Class<? extends Component> componentType : requirements())
            if(!entity.has(componentType))
                return false;
        return true;
    }

    @Override
    public final void reactToUpgrade(Entity entity) {
        if(meetsRequirements(entity))
            entities.add(entity);
    }

    @Override
    public final void reactToDowngrade(Entity entity) {
        if(!meetsRequirements(entity))
            removeEntity(entity);
    }
}
