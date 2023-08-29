package components;

public class MutableVital implements Vital {
    private int currentHp;
    private final int maxHp;

    MutableVital(int maxHp) {
        if(maxHp <= 0)
            throw new IllegalArgumentException("`maxHp` must be positive");
        this.maxHp = maxHp;
        currentHp = this.maxHp;
    }

    @Override
    public final int getCurrentPoints() {
        return currentHp;
    }

    @Override
    public final int getMaxPoints() {
        return maxHp;
    }

    public final void damage(int amount) {
        currentHp = Math.max(currentHp - amount, 0);
    }

    public final void fix(int amount) {
        currentHp = Math.min(currentHp + amount, getMaxPoints());
    }
}
