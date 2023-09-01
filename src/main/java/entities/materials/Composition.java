package entities.materials;

import components.HealthComponent;

public class Composition implements HealthComponent {
    private final Mix mix;
    private int currentHp;

    public Composition(Mix full) {
        this.mix = full;
        currentHp = this.mix.total();
    }

    public Composition(Mix full,int points) {
        this.mix = full;
        currentHp = points;
    }

    public void damage(Damage damage) {
        currentHp = Math.max(currentHp - damage.against(mix), 0);
    }

    @Override
    public int getCurrentPoints() {
        return currentHp;
    }

    @Override
    public int getMaxPoints() {
        return mix.total();
    }
}
