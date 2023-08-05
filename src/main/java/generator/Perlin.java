package generator;

import com.badlogic.gdx.math.Vector2;
import utils.Config;
import utils.Pair;

import java.io.File;
import java.util.*;

public class Perlin {
    private int seed;
    private float tile_len;
    private float tile_offset;

    public Perlin(int seed) {
        this.seed = seed;
        tile_len = (float) 1 / (float) Config.CHUNK_SIZE;
        tile_offset = tile_len / 2;
    }

    public float getNoise(int x, int y) {
        return 0;
    }

    public Map<Pair<Integer, Integer>, Float> getChunkNoiseMap(int chunkX, int chunkY) {
        List<Vector2> corner_vectors = getCornerVectors(chunkX, chunkY);

        Map<Pair<Integer, Integer>, Float> noise_map = new HashMap<>();

        for(int i = 0; i < Config.CHUNK_SIZE; i++) {
            for(int j = 0; j < Config.CHUNK_SIZE; j++) {
                noise_map.put(new Pair(i + chunkX * Config.CHUNK_SIZE, j + chunkY * Config.CHUNK_SIZE), generateValue(i, j, corner_vectors));
            }
        }
        return noise_map;
    }

    private float generateValue(int x, int y, List<Vector2> corner_vectors) {
        //ugly written dot products
        float x_offset = x * tile_len + tile_offset;
        float y_offset = y * tile_len + tile_offset;

        float dotLL = corner_vectors.get(0).dot(new Vector2(x_offset, y_offset));
        float dotUL = corner_vectors.get(1).dot(new Vector2(x_offset - 1, y_offset));
        float dotUR = corner_vectors.get(2).dot(new Vector2(x_offset - 1, y_offset - 1));
        float dotLR = corner_vectors.get(3).dot(new Vector2(x_offset, y_offset - 1));
        //linear interpolation

        //System.err.println(dotLL);

        float U = dotUL + y_offset * (dotUR - dotUL);
        float L = dotLL + y_offset * (dotLR - dotLL);

        float value = L + x_offset * (U - L);
        return (float) (6 * Math.pow(value, 5) - 15 * Math.pow(value, 4) + 10 * Math.pow(value, 3));
    }

    private List<Vector2> getCornerVectors(int chunkX, int chunkY) { //returns a list of four corner vectors {LL,UL,UR,LR}
        List<Vector2> list = new ArrayList<>();

        Random rand = new Random(seed + (long) seed * chunkX + chunkY);
        //Vector2 v = new Vector2((rand.nextBoolean()) ? 1 : -1, (rand.nextBoolean()) ? 1 : -1);
        Vector2 v = new Vector2(rand.nextFloat(), rand.nextFloat());
        //System.err.println(v.x);
        //System.err.println(v.y);
        list.add(v);

        rand = new Random(seed + (long) seed * (chunkX + 1) + chunkY);
        //v = new Vector2((rand.nextBoolean()) ? 1 : -1, (rand.nextBoolean()) ? 1 : -1);
        v = new Vector2(rand.nextFloat(), rand.nextFloat());
        //System.err.println(v.x);
        //System.err.println(v.y);
        list.add(v);

        rand = new Random(seed + (long) seed * (chunkX + 1) + (chunkY + 1));
        //v = new Vector2((rand.nextBoolean()) ? 1 : -1, (rand.nextBoolean()) ? 1 : -1);
        v = new Vector2(rand.nextFloat(), rand.nextFloat());
        //System.err.println(v.x);
        //System.err.println(v.y);
        list.add(v);

        rand = new Random(seed + (long) seed * chunkX + (chunkY + 1));
        //v = new Vector2((rand.nextBoolean()) ? 1 : -1, (rand.nextBoolean()) ? 1 : -1);
        v = new Vector2(rand.nextFloat(), rand.nextFloat());
        //System.err.println(v.x);
        //System.err.println(v.y);
        list.add(v);

        System.err.println("----------------");
        return list;
    }

    Map<Pair<Integer, Integer>, Float> createBigMap(int x, int y, int s) {
        for(int i = 0; i < s; i++) {
            for(int j = 0; j < s; j++) {

            }
        }
        return null;
    }

    void printNoiseMap(Map<Pair<Integer, Integer>, Float> map, int beg, int num) {
        for(int i = 0; i < Config.CHUNK_SIZE * num; i++) {
            for(int j = 0; j < Config.CHUNK_SIZE * num; j++) {
                System.err.format("%f ", map.get(new Pair(beg * Config.CHUNK_SIZE + i, beg * Config.CHUNK_SIZE + j)));
                /*if(map.get(new Pair(i,j)) < -0.5)
                    System.err.print(' ');
                else if(map.get(new Pair(i,j)) < 0)
                    System.err.print('.');
                else if(map.get(new Pair(i,j)) == 0)
                    System.err.print('o');
                else if(map.get(new Pair(i,j)) < 0.5)
                    System.err.print('*');
                else
                    System.err.print('#');*/
            }
            System.err.println();
        }
    }
}
