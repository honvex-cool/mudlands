package generator;

import org.junit.jupiter.api.Test;
import utils.Config;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PerlinTest {
    @Test
    void testOutput() {
        Perlin perlin = new Perlin(123, 32);
        Perlin perlin1 = new Perlin(123, 32);
        int startx = 0;
        int starty = 0;
        int size = 64;

        for(int y = starty; y < starty + size; y++) {
            for(int x = startx; x < startx + size; x++) {
                double noise = perlin.getNoise(x, y) + 0.5 * perlin1.getNoise(4 * x, 4 * y);
                if(noise < -0.5)
                    System.err.print(' ');
                else if(noise < 0)
                    System.err.print('.');
                else if(noise == 0)
                    System.err.print(',');
                else if(noise < 0.5)
                    System.err.print('/');
                else
                    System.err.print('#');
                System.err.print(' ');
            }
            System.err.println();
        }
    }
}