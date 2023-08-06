package world;

import generator.FieldStruct;
import generator.GroundType;
import generator.WorldLoader;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final Map<Pair<Integer, Integer>, GroundType> loadedSquares = new HashMap<>();

    public WorldMap(WorldLoader loader) {
        var map = new HashMap<Pair<Integer, Integer>, FieldStruct>();
        for(int i = -8; i <= 8; i++)
            for(int j = -8; j <= 8; j++)
                map.putAll(loader.loadChunk(i, j));
        for(int i = -100; i <= 100; i++)
            for(int j = -100; j <= 100; j++)
                loadedSquares.put(new Pair<>(i, j), map.get(new Pair<>(i, j)).groundType);
                //loader.loadChunk(i, j).forEach((coords, field) -> loadedSquares.put(coords, field.groundType));
    }

    public GroundType query(int i, int j) {
        return loadedSquares.get(new Pair<>(i, j));
    }
}
