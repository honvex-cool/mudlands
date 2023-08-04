package systems;

import entities.Entity;

public abstract class RepetitiveSystem extends RequirementBasedSystem {
    protected void begin() {
    }

    protected abstract void updateOne(Entity entity, float deltaTime);

    protected void end() {
    }

    @Override
    public void update(float deltaTime) {
        begin();
        for(Entity entity : entities)
            updateOne(entity, deltaTime);
        end();
    }
}
