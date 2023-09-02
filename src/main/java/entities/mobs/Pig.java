package entities.mobs;

import actions.ActionType;
import actions.Cooldown;
import components.PositionComponent;
import entities.materials.Composition;
import entities.materials.Mix;
import utils.Pair;
import utils.VectorMath;

import java.util.random.RandomGenerator;

public class Pig extends Mob {
    private static final float ROAMING_SPEED = 1f;
    private static final float ESCAPE_SPEED = 3f;
    private boolean escaping = false;
    private transient final RandomGenerator generator;
    private Cooldown untilChange;

    public Pig(RandomGenerator generator) {
        this.composition = new Composition(new Mix(0,0,0,100));
        this.generator = generator;
        change();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(untilChange.use(deltaTime))
            change();
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actionType == ActionType.HIT)
            triggerEscape(actor.mutablePositionComponent);
    }

    private void change() {
        if(escaping)
            escaping = false;
        untilChange = Cooldown.notReadyToUse(generator.nextFloat(5, 10));
        if(generator.nextBoolean()) {
            setSpeed(0);
            return;
        }
        setSpeed(ROAMING_SPEED);
        setDirection(generator.nextFloat(360));
    }

    private void triggerEscape(PositionComponent danger) {
        escaping = true;
        Pair<Float, Float> difference = new Pair<>(
            mutablePositionComponent.getX() - danger.getX(),
            mutablePositionComponent.getY() - danger.getY()
        );
        setDirection(VectorMath.getRotationFromVector(difference));
        setSpeed(ESCAPE_SPEED);
        untilChange = Cooldown.notReadyToUse(0.5f);
    }
}
