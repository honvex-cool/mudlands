package systems;

import entities.mobs.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;

import java.util.Collection;

public class UpdateSystem {
    private final Player player;
    private final Collection<Ground> ground;
    private final Collection<Passive> passives;
    private final Collection<Mob> mobs;

    public UpdateSystem(Player player, Collection<Ground> ground, Collection<Passive> passives, Collection<Mob> mobs) {
        this.player = player;
        this.ground = ground;
        this.passives = passives;
        this.mobs = mobs;
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
        for(Ground tile : ground)
            tile.update(deltaTime);
        for(Passive passive : passives)
            passive.update(deltaTime);
        for(Mob mob : mobs)
            mob.update(deltaTime);
    }
}
