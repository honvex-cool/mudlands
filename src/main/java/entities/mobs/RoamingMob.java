package entities.mobs;

import actions.Cooldown;

import java.util.random.RandomGenerator;

public abstract class RoamingMob extends Mob {
    protected RandomGenerator generator;
    protected Cooldown untilChange;
    private final float roamingSpeed;

    protected RoamingMob(RandomGenerator generator, float roamingSpeed) {
        this.generator = generator;
        this.roamingSpeed = roamingSpeed;
        change();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(untilChange.use(deltaTime))
            change();
    }

    protected void change() {
        triggerRoaming();
    }

    protected final void triggerRoaming() {
        untilChange = Cooldown.notReadyToUse(generator.nextFloat(5, 10));
        if(generator.nextBoolean()) {
            halt();
            return;
        }
        setVelocity(roamingSpeed, generator.nextFloat(360));
    }
}
