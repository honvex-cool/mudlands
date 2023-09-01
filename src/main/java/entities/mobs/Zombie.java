package entities.mobs;

import actions.ActionType;
import entities.controllers.HuntingMovementController;
import utils.Debug;

public class Zombie extends Mob {
    private transient final HuntingMovementController controller;

    public Zombie(HuntingMovementController controller) {
        this.controller = controller;
    }

    @Override
    public void updateVelocity() {
        //this.velocityComponent = controller.getVelocity(this.mutablePositionComponent);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        System.err.println(actionType);
    }
}
