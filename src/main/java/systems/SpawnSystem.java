package systems;

import actions.Cooldown;
import entities.controllers.HuntingController;
import entities.mobs.Mob;
import entities.mobs.Zombie;

import java.util.Collection;

public class SpawnSystem {
    private final HuntingController controller;
    private final Cooldown cooldown = new Cooldown(4f);
    private final Collection<Mob> mobs;

    public SpawnSystem(Collection<Mob> mobs, HuntingController controller) {
        this.mobs = mobs;
        this.controller = controller;
    }

    public void update(float deltaTime) {
        controller.update();
        cooldown.advance(deltaTime);
        if(cooldown.use()) {
            Mob zombie = new Zombie(controller);
            zombie.mutablePositionComponent.setX(-2);
            zombie.mutablePositionComponent.setY(-2);
            mobs.add(zombie);
        }
    }
}
