package entities.materials;

import components.HealthComponent;

import java.util.Objects;

public class Composition implements HealthComponent {
    private final Mix mix;
    private int currentHp;

    private final int maxHp;

    public Composition(Mix full) {
        this(full,full.total());
    }

    public Composition(Mix full,int points) {
        this.mix = full;
        currentHp = points;
        maxHp = points;
    }

    public void damage(Damage complexDamage) {
        damage(complexDamage.against(mix));
    }

    public void damage(int amount) {
        currentHp = Math.max(currentHp - amount, 0);
    }

    public void fix(int amount) {
        currentHp = Math.min(currentHp + amount, maxHp);
    }

    @Override
    public int getCurrentPoints() {
        return currentHp;
    }

    @Override
    public int getMaxPoints() {
        return maxHp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Composition other)
            return Objects.equals(mix, other.mix)
                && Objects.equals(currentHp, other.currentHp)
                && Objects.equals(maxHp, other.maxHp);
        return false;
    }
}
