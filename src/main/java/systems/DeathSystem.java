package systems;

import components.Component;
import components.PlayerComponent;
import entities.Entity;

import java.util.Set;

public class DeathSystem extends RepetitiveSystem {
    private float time = 0.0f;

    @Override
    protected void updateOne(Entity entity, float deltaTime) {
        time += deltaTime;
        if(time >= 5.0f)
            entity.discard();
    }

    @Override
    protected Set<Class<? extends Component>> requirements() {
        return Set.of(PlayerComponent.class);
    }
}
