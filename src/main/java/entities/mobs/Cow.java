package entities.mobs;

import actions.ActionType;
import actions.Cooldown;
import components.PositionComponent;
import entities.materials.Composition;
import entities.materials.Mix;
import utils.Pair;
import utils.VectorMath;

import java.util.random.RandomGenerator;

public class Cow extends RoamingMob {
    private static final float ROAMING_SPEED = 1.2f;
    private static final float CHARGE_SPEED = 3f;
    private final int attackOdds;
    private boolean charging = false;

    public Cow(RandomGenerator generator, int attackOdds) {
        super(generator, ROAMING_SPEED);
        this.composition = new Composition(new Mix(0, 0, 30, 100));
        this.attackOdds = attackOdds;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(charging)
            this.nextAction = ActionType.HIT;
    }

    @Override
    protected void change() {
        charging = false;
        super.change();
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actionType == ActionType.HIT && generator.nextInt() % attackOdds == 0)
            triggerCharge(actor.mutablePositionComponent);
    }

    private void triggerCharge(PositionComponent target) {
        Pair<Float, Float> difference = new Pair<>(
            target.getX() - mutablePositionComponent.getX(),
            target.getY() - mutablePositionComponent.getY()
        );
        float direction = VectorMath.getRotationFromVector(difference) + generator.nextFloat(-15, 15);
        setVelocity(CHARGE_SPEED, direction);
        untilChange = Cooldown.notReadyToUse(0.7f);
        charging = true;
    }
}
