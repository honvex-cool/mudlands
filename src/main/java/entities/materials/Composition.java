package entities.materials;

import components.HealthComponent;

public class Composition implements HealthComponent {
    private final Materials full;
    private Materials current;

    public Composition(Materials full) {
        current = this.full = full;
    }

    public void damage(Damage damage) {
        current = current.damaged(damage);
    }

    @Override
    public int getCurrentPoints() {
        return current.total();
    }

    @Override
    public int getMaxPoints() {
        return full.total();
    }
}
