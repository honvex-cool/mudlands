package generator;

import com.badlogic.gdx.math.Vector2;
import utils.Config;
import utils.Pair;

import java.io.File;
import java.util.*;

public class Perlin {
    private int seed;
    private int gridsize;

    public Perlin(int seed, int gridsize) {
        this.seed = seed;
        this.gridsize = gridsize;
    }

    public double getNoise(int x, int y) {
        double gx = (double) x / gridsize;
        double gy = (double) y / gridsize;

        int xi = (int) Math.floor(gx);
        int yi = (int) Math.floor(gy);

        double xf = gx - xi;
        double yf = gy - yi;

        double d1 = grad(xi, yi, xf, yf);
        double d2 = grad(xi + 1, yi, xf - 1, yf);
        double d3 = grad(xi, yi + 1, xf, yf - 1);
        double d4 = grad(xi + 1, yi + 1, xf - 1, yf - 1);

        double u = fade(xf);
        double v = fade(yf);

        return fade(lerp(v, lerp(u, d1, d2), lerp(u, d3, d4)));
    }

    private double lerp(double amount, double left, double right) {
        return ((1 - amount) * left + amount * right);
    }

    private double grad(int x, int y, double xf, double yf) {
        Random random = new Random(x * this.seed + y);
        return switch(random.nextInt() & 3) {
            case 0 -> xf + yf;
            case 1 -> -xf + yf;
            case 2 -> xf - yf;
            case 3 -> -xf - yf;
            default -> 0;
        };
    }

    private double fade(double val) {
        return 6 * Math.pow(val, 5) - 15 * Math.pow(val, 4) + 10 * Math.pow(val, 3);
    }
}
