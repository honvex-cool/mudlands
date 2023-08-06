package generator;

import utils.Config;
import utils.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Generator {
    private int seed;
    private Perlin height_noise, height_noise2, humidity_noise, humidity_noise2;

    Generator(Integer seed) {
        this.seed = seed;
        this.height_noise = new Perlin(seed, Config.DEFAULT_GRID_SIZE);
        this.height_noise2 = new Perlin(seed + 1, Config.DEFAULT_GRID_SIZE);
        this.humidity_noise = new Perlin(seed + 2, Config.DEFAULT_GRID_SIZE);
        this.humidity_noise2 = new Perlin(seed + 3, Config.DEFAULT_GRID_SIZE);
    }

    Generator() {
        this(123456789);
    }

    public int getSeed() {
        return seed;
    }

    public Map<Pair<Integer, Integer>, FieldStruct> generateChunk(int chunk_x, int chunk_y) { //object is temporary and will be replaced be appropriate class
        var map = new HashMap<Pair<Integer, Integer>, FieldStruct>();
        for(int y = 0; y < Config.CHUNK_SIZE; y++) {
            for(int x = 0; x < Config.CHUNK_SIZE; x++) {
                int rx = x + chunk_x * Config.CHUNK_SIZE;
                int ry = y + chunk_y * Config.CHUNK_SIZE;
                double height = height_noise.getNoise(rx, ry) + 0.2 * Math.abs(height_noise2.getNoise(rx, ry));
                double humidity = humidity_noise.getNoise(rx, ry) + 0.2 * Math.abs(humidity_noise2.getNoise(rx, ry));
                GroundType ground = getGroundType(height, humidity);
                ObjectType object = getObjectType(height, humidity, ground, x, y);

                map.put(new Pair(rx, ry), new FieldStruct(ground, object));
            }
        }
        return map;
    }

    private GroundType getGroundType(double height, double humidity) {
        GroundType ground;
        if(height < -0.3)
            ground = GroundType.WATER;
        else if(height <= 0) {
            ground = GroundType.SAND;
        } else if(height < 0.5)
            ground = GroundType.GRASS;
        else
            ground = GroundType.STONE;

        if(height >= -0.3 && height < 0.1 && humidity > 0.1)
            ground = GroundType.MUD;
        return ground;
    }

    private ObjectType getObjectType(double height, double humidity, GroundType ground, int x, int y) {
        ObjectType object = ObjectType.NONE;
        Random random = new Random(x * this.seed + y);
        int rand_val = random.nextInt();
        switch(ground) {
            case GRASS -> {
                if((humidity > 0 && rand_val % 4 == 0) || rand_val % 30 == 0) {
                    object = ObjectType.TREE;
                }
                if(height > 0.4 && rand_val % 10 == 0) {
                    object = ObjectType.STONE;
                }
            }
            case STONE -> {
                if(rand_val % 5 == 0) {
                    object = ObjectType.STONE;
                }
            }
        }
        return object;
    }

    @Deprecated
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
                    case WATER -> ' ';
                    case SAND -> '.';
                    case GRASS -> '/';
                    case STONE -> '#';
                    case MUD -> '~';
                    default -> '?';
                };
                System.err.print(symbol);
                System.err.print(' ');
            }
            System.err.println();
        }
    }

    void saveMapToFile(Map<Pair<Integer, Integer>, FieldStruct> map, String filename) throws IOException {
        File file = new File(filename + ".map");
        file.createNewFile();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

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
                char symbol = switch(map.get(new Pair(x + minx, y + miny)).groundType) {
                    case WATER -> ' ';
                    case SAND -> '.';
                    case GRASS -> '/';
                    case STONE -> '#';
                    case MUD -> '~';
                    default -> '?';
                };
                char symbol2 = switch(map.get(new Pair(x + minx, y + miny)).objectType) {
                    case TREE -> 't';
                    case STONE -> 's';
                    default -> '?';
                };

                if(symbol2 != '?')
                    symbol = symbol2;

                fileWriter.write(symbol);
                fileWriter.write(' ');
            }
            fileWriter.write('\n');
        }
        fileWriter.close();
    }

}
