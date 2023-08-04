package systems;

import entities.World;

public abstract class System {
    protected World world;

    public System(World world) {
        this.world = world;
    }

    public abstract void update(float deltaTime);
}
