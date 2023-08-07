package systems;

import entities.Mob;
import entities.Passive;
import utils.Pair;

import java.util.Collection;
import java.util.stream.Stream;

public class MoveSystem {
    public MoveSystem() {
    }

    public void move(Collection<Mob> mobs, Collection<Passive> passives, float deltaTime) {
        for(var mob : mobs) {
            Pair<Float, Float> velocity = mob.velocityComponent.getAsPair();
            if(velocity == null)
                continue;
            float x = mob.positionComponent.getX();
            float y = mob.positionComponent.getY();
            float newX = x + velocity.getFirst() * deltaTime;
            float newY = y + velocity.getSecond() * deltaTime;
            var all = Stream.concat(mobs.stream(), passives.stream());
            boolean collision = all.anyMatch(
                entity -> entity != mob && entity.isActive() && colliding(
                    newX,
                    newY,
                    mob.getRadius(),
                    entity.positionComponent.getX(),
                    entity.positionComponent.getY(),
                    entity.getRadius()
                )
            );
            if(collision)
                continue;
            mob.positionComponent.setX(newX);
            mob.positionComponent.setY(newY);
        }
    }

    private static boolean colliding(float firstX, float firstY, float firstRadius, float secondX, float secondY, float secondRadius) {
        float xDiff = firstX - secondX;
        float yDiff = firstY - secondY;
        float squaredDistance = xDiff * xDiff + yDiff * yDiff;
        float radiusSum = firstRadius + secondRadius;
        return squaredDistance <= radiusSum * radiusSum;
    }
}
