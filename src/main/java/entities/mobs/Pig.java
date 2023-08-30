package entities.mobs;

import actions.Cooldown;
import components.VelocityComponent;

import java.util.random.RandomGenerator;

public class Pig extends Mob {
    private final RandomGenerator randomGenerator;
    private Cooldown changeCooldown;

    public Pig(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        change();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        changeCooldown.advance(deltaTime);
        if(changeCooldown.use())
            change();
    }

    private void change() {
        float duration = randomGenerator.nextFloat(2, 5);
        changeCooldown = Cooldown.notReadyToUse(duration);
        changeCooldown.reset();
        float x, y;
        if(randomGenerator.nextBoolean())
            x = y = 0;
        else {
            x = randomGenerator.nextFloat(-1, 1);
            y = randomGenerator.nextFloat(-1, 1);
            float length = (float)Math.sqrt(x * x + y * y);
            if(length > 0) {
                x /= length;
                y /= length;
            }
        }
        velocityComponent = new VelocityComponent(x, y);
    }
}
