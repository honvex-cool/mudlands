package entities.materials;

import java.io.Serializable;

public record Mix(int wood, int mineral, int mud, int flesh) implements Serializable {
    public Mix {
        if(wood < 0 || mineral < 0 || mud < 0 || flesh < 0)
            throw new IllegalArgumentException("material values must not be negative");
        if(wood + mineral + mud + flesh == 0)
            throw new IllegalArgumentException("at least one material value must be positive");
    }

    public int total() {
        return wood + mineral + mud + flesh;
    }
}
