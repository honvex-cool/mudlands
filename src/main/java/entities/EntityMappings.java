package entities;

import entities.grounds.*;
import entities.passives.*;
import generator.GroundType;
import generator.ObjectType;

import java.util.Map;

public final class EntityMappings {
    private EntityMappings() {
    }

    public static final Map<GroundType, Class<? extends Ground>> GROUND_MAP = Map.of(
        GroundType.WATER, Water.class,
        GroundType.SAND, Sand.class,
        GroundType.GRASS, Grass.class,
        GroundType.MUD, Mud.class,
        GroundType.STONE, Stone.class,
        GroundType.DIRT, Dirt.class
    );

    public static final Map<ObjectType, Class<? extends Passive>> PASSIVE_MAP = Map.of(
        ObjectType.TREE, Tree.class,
        ObjectType.STONE, Rock.class,
        ObjectType.MUDPUDDLE, MudPuddle.class
    );
}
