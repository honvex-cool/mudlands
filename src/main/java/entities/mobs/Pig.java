package entities.mobs;

import actions.ActionType;
import actions.Cooldown;
import components.PositionComponent;
import entities.materials.Composition;
import entities.materials.Mix;
import utils.Pair;
import utils.VectorMath;

import java.util.random.RandomGenerator;

public class Pig extends RoamingMob {
    private static final float ROAMING_SPEED = 1f;
    private static final float ESCAPE_SPEED = 3f;

    public Pig(RandomGenerator generator) {
        super(generator, ROAMING_SPEED);
        this.composition = new Composition(new Mix(0,0,0,100));
        this.generator = generator;
        change();
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actionType == ActionType.HIT)
            triggerEscape(actor.mutablePositionComponent);
    }

    private void triggerEscape(PositionComponent danger) {
        Pair<Float, Float> difference = new Pair<>(
            mutablePositionComponent.getX() - danger.getX(),
            mutablePositionComponent.getY() - danger.getY()
        );
        setVelocity(ESCAPE_SPEED, VectorMath.getRotationFromVector(difference));
        untilChange = Cooldown.notReadyToUse(0.5f);
    }
}
