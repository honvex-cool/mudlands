package entities;

import entities.grounds.*;
import entities.mobs.Mob;
import entities.mobs.Zombie;
import entities.passives.*;

import java.util.Map;

public final class EntityMappings {
    private EntityMappings() {
    }

    public static final Map<Integer, Class<? extends Ground>> GROUND_MAP = Map.of(
        1, Water.class,
        2, Sand.class,
        3, Grass.class,
        4, Mud.class,
        5, Stone.class,
        6, Dirt.class
    );

    public static final Map<Integer, Class<? extends Passive>> PASSIVE_MAP = Map.of(
        1, Tree.class,
        2, Rock.class,
        101, Sticks.class,
        102, Stones.class
    );

    public static final Map<Integer, Class<? extends Mob>> MOB_MAP = Map.of(
        201, Zombie.class
    );
}
