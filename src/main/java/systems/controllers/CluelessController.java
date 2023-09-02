package systems.controllers;

import entities.mobs.Mob;
import utils.Pair;
import utils.VectorMath;

import java.util.random.RandomGenerator;

public class CluelessController implements Controller {
    private final RandomGenerator generator;
    private final int changeOdds;

    public CluelessController(RandomGenerator generator, int changeOdds) {
        this.generator = generator;
        if(changeOdds <= 0)
            throw new IllegalArgumentException("`changeOdds` must be positive");
        this.changeOdds = changeOdds;
    }

    @Override
    public void control(Mob mob) {
        if(generator.nextInt() % changeOdds == 0)
            change(mob);
    }

    @Override
    public boolean canControl(Class<? extends Mob> mobClass) {
        return true;
    }

    private void change(Mob mob) {
        if(generator.nextBoolean()) {
            mob.velocityComponent.setX(0);
            mob.velocityComponent.setY(0);
            return;
        }
        float rotation = generator.nextFloat(360);
        Pair<Float, Float> direction = VectorMath.getVectorFromRotation(rotation, 1);
        mob.velocityComponent.setX(direction.getFirst());
        mob.velocityComponent.setY(direction.getSecond());
        mob.rotationComponent.setRotation(rotation);
    }
}
