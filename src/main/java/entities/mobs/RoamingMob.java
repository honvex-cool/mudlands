package entities.mobs;

import actions.GameTimer;

import java.util.random.RandomGenerator;

public abstract class RoamingMob extends Mob {
    protected RandomGenerator generator;
    protected GameTimer untilChange;
    private final float roamingSpeed;

    protected RoamingMob(RandomGenerator generator, float roamingSpeed) {
        this.generator = generator;
        this.roamingSpeed = roamingSpeed;
        change();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        untilChange.advance(deltaTime);
        if(untilChange.isFinished())
            change();
    }

    protected void change() {
        triggerRoaming();
    }

    protected final void triggerRoaming() {
        untilChange = GameTimer.started(generator.nextFloat(5, 10));
        if(generator.nextBoolean()) {
            halt();
            return;
        }
        setVelocity(roamingSpeed, generator.nextFloat(360));
    }
}
