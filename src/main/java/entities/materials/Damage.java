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

    public Damage withResistance(Damage resistance) {
        if(resistance == null)
            return this;
        return new Damage(
            Math.max(wood - 1, 1),
            Math.max(mineral - 1, 1),
            Math.max(mud - 1, 1),
            Math.max(flesh - 1, 1)
        );
    }

    public static Damage combined(Damage... damages) {
        int wood = 0, mineral = 0, mud = 0, flesh = 0;
        for(Damage damage : damages) {
            wood += damage.wood;
            mineral += damage.mineral;
            mud += damage.mud;
            flesh += damage.flesh;
        }
        return new Damage(wood, mineral, mud, flesh);
    }
}
