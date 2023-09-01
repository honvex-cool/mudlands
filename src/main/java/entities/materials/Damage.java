package entities.materials;

import java.io.Serializable;

public record Damage(int wood, int mineral, int mud, int flesh) implements Serializable {
    public Damage {
        if(wood <= 0 || mineral <= 0 || mud <= 0 || flesh <= 0)
            throw new IllegalArgumentException("damage values must be positive");
    }

    public int against(Mix mix) {
        long woodDamage = (long)wood * mix.wood();
        long mineralDamage = (long)mineral * mix.mineral();
        long mudDamage = (long)mud * mix.mud();
        long fleshDamage = (long)flesh * mix.flesh();
        double total = (woodDamage + mineralDamage + mudDamage + fleshDamage) / (double)mix.total();
        return (int)Math.ceil(total);
    }
}
