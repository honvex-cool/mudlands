package entities.mobs;

import entities.controllers.HuntingMovementController;

public class Zombie extends Mob {
    private final HuntingMovementController controller;

    public Zombie(HuntingMovementController controller) {
        this.controller = controller;
    }

    @Override
    public void updateVelocity() {
        this.velocityComponent = controller.getVelocity(this.mutablePositionComponent);
    }
}
