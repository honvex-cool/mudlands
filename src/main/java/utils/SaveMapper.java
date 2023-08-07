package utils;

import components.Component;
import components.RenderComponent;
import world.EntityTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

/*public final class SaveMapper {
    private SaveMapper(){
    }
    public static final Map<Integer, EntityTag> intToEntity = Map.of(
        0, EntityTag.NONE,
        1, EntityTag.WATER,
        2, EntityTag.GRASS,
        3, EntityTag.SAND,
        4, EntityTag.STONE,
        5, EntityTag.MUD,
        6, EntityTag.DIRT,
        100, EntityTag.TREE,
        101, EntityTag.ROCK
    );
    public static final Map<EntityTag,Integer> entityToInt = new HashMap<>();
    public static final Map<Function<SaveStruct,List<Component>>,List<Integer>> funcToEntities = Map.of(
        new Function<SaveStruct, List<Component>>() {
            @Override
            public List<Component> apply(SaveStruct saveStruct) {
                return List.of(new RenderComponent());
            }
        }, IntStream.range(1,2000)
    )

    public static final Map<Integer,Function<SaveStruct,List<Component>>> entityToFunction = new HashMap<>();

    static {
        for(var key:intToEntity.keySet()){
            entityToInt.put(intToEntity.get(key),key);
        }
    }
}*/
