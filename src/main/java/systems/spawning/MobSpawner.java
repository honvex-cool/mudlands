package systems.spawning;

import components.PositionComponent;
import entities.mobs.*;

import java.util.Random;
import java.util.random.RandomGenerator;

public class MobSpawner {
    private final RandomGenerator generator;
    private final PlacementRules rules;
    private final int patience;

    public MobSpawner(PlacementRules rules, RandomGenerator generator, int patience) {
        this.rules = rules;
        this.generator = generator;
        this.patience = patience;
    }

    public Mob spawnPigAround(PositionComponent positionComponent) {
        return placeOrGiveUp(new Pig(new Random(generator.nextLong())), positionComponent);
    }

    public Mob spawnCowAround(PositionComponent positionComponent) {
        int tameness = generator.nextInt(2, 10);
        return placeOrGiveUp(
            new Cow(new Random(generator.nextLong()), tameness),
            positionComponent
        );
    }

    public Mob spawnZombieAround(PositionComponent positionComponent) {
        return placeOrGiveUp(new Zombie(), positionComponent);
    }

    public Mob spawnGhostAround(PositionComponent positionComponent) {
        return placeOrGiveUp(new Ghost(), positionComponent);
    }

    private Mob placeOrGiveUp(Mob mob, PositionComponent positionComponent) {
        if(placeRandomlyAround(mob, positionComponent))
            return mob;
        return null;
    }

    private boolean placeRandomlyAround(Mob mob, PositionComponent positionComponent) {
        for(int i = 0; i < patience; i++)
            if(tryPlaceRandomlyAround(mob, positionComponent))
                return true;
        return false;
    }

    private boolean tryPlaceRandomlyAround(Mob mob, PositionComponent positionComponent) {
        float x = generator.nextFloat(positionComponent.getX() - 16, positionComponent.getX() + 16);
        float y = generator.nextFloat(positionComponent.getY() - 16, positionComponent.getY() + 16);
        mob.mutablePositionComponent.setX(x);
        mob.mutablePositionComponent.setY(y);
        return rules.canMobBePlaced(mob, mob.mutablePositionComponent);
    }
}
