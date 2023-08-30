package systems;

import actions.Cooldown;
import entities.controllers.HuntingMovementController;
import entities.mobs.Mob;
import entities.mobs.Pig;
import entities.mobs.Zombie;

import java.util.Collection;
import java.util.Random;

public class SpawnSystem {
    private final HuntingMovementController controller;
    private final Cooldown zombieCooldown = Cooldown.notReadyToUse(10f);
    private final Cooldown pigCooldown = Cooldown.notReadyToUse(10f);
    private final Collection<Mob> mobs;
    private final Random random = new Random(42);

    public SpawnSystem(Collection<Mob> mobs, HuntingMovementController controller) {
        this.mobs = mobs;
        this.controller = controller;
    }

    public void update(float deltaTime) {
        controller.update();
        zombieCooldown.advance(deltaTime);
        if(zombieCooldown.use()) {
            Mob zombie = new Zombie(controller);
            zombie.mutablePositionComponent.setX(-2);
            zombie.mutablePositionComponent.setY(-2);
            mobs.add(zombie);
        }
        pigCooldown.advance(deltaTime);
        if(pigCooldown.use()) {
            Mob pig = new Pig(random);
            pig.mutablePositionComponent.setX(-4);
            pig.mutablePositionComponent.setY(-2);
            mobs.add(pig);
        }
    }
}
