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
        for(int i = -20; i <= 20; i++)
            for(int j = -20; j <= 20; j++)
                loadedSquares.put(new Pair<>(i, j), GroundType.GRASS);
                //loader.loadChunk(i, j).forEach((coords, field) -> loadedSquares.put(coords, field.groundType));
    }

    public GroundType query(int i, int j) {
        return loadedSquares.get(new Pair<>(i, j));
    }
}
