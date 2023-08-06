package generator;

import utils.Config;
import utils.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Generator {
    private int seed;
    private Perlin height_noise, height_noise2;

    Generator(Integer seed) {
        this.seed = seed;
        this.height_noise = new Perlin(seed, Config.DEFAULT_GRID_SIZE);
        this.height_noise2 = new Perlin(seed + 1, Config.DEFAULT_GRID_SIZE);
    }

    Generator() {
        this(123456789);
    }

    public int getSeed() {
        return seed;
    }

    public Map<Pair<Integer, Integer>, Pair<GroundType, ObjectType>> generateChunk(int chunk_x, int chunk_y) { //object is temporary and will be replaced be appropriate class
        var map = new HashMap<Pair<Integer, Integer>, Pair<GroundType, ObjectType>>();
        for(int y = 0; y < Config.CHUNK_SIZE; y++) {
            for(int x = 0; x < Config.CHUNK_SIZE; x++) {
                int rx = x + chunk_x * Config.CHUNK_SIZE;
                int ry = y + chunk_y * Config.CHUNK_SIZE;
                double height = height_noise.getNoise(rx, ry) + 0.2 * Math.abs(height_noise2.getNoise(rx, ry));
                GroundType ground;
                ObjectType object = ObjectType.NONE;
                if(height < -0.3)
                    ground = GroundType.WATER;
                    //else if(height < 0)
                    //ground = GroundType.SAND;
                else if(height <= 0)
                    ground = GroundType.SAND;
                else if(height < 0.5)
                    ground = GroundType.GRASS;
                else
                    ground = GroundType.STONE;
                map.put(new Pair(rx, ry), new Pair(ground, object));
            }
        }
        return map;
    }

    void printMap(Map<Pair<Integer, Integer>, Pair<GroundType, ObjectType>> map) {
        int minx = Integer.MAX_VALUE;
        int maxx = Integer.MIN_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxy = Integer.MIN_VALUE;
        for(var p : map.keySet()) {
            minx = Math.min(minx, p.getFirst());
            miny = Math.min(miny, p.getSecond());
            maxx = Math.max(maxx, p.getFirst());
            maxy = Math.max(maxy, p.getSecond());
        }
        for(int y = 0; y < maxy - miny; y++) {
            for(int x = 0; x < maxx - minx; x++) {
                char symbol = switch(map.get(new Pair(x + minx, y + miny)).getFirst()) {
                    case WATER -> '~';
                    case SAND -> '.';
                    case GRASS -> '/';
                    case STONE -> '#';
                    default -> '?';
                };
                System.err.print(symbol);
                System.err.print(' ');
            }
            System.err.println();
        }
    }
}
