package entities.mobs;

import entities.controllers.HuntingController;

public class Zombie extends Mob {
    private final HuntingController controller;

    public Zombie(HuntingController controller) {
        this.controller = controller;
    }

    @Override
    public void updateVelocity() {
        this.velocityComponent = controller.getVelocity(this.mutablePositionComponent);
    }
}
