package components;

public class MutableVital implements Vital {
    private int currentPoints;
    private final int maxPoints;

    MutableVital(int maxPoints) {
        if(maxPoints <= 0)
            throw new IllegalArgumentException("`maxHp` must be positive");
        this.maxPoints = maxPoints;
        currentPoints = this.maxPoints;
    }

    @Override
    public final int getCurrentPoints() {
        return currentPoints;
    }

    @Override
    public final int getMaxPoints() {
        return maxPoints;
    }

    public final void damage(int amount) {
        currentPoints = Math.max(currentPoints - amount, 0);
    }

    public final void fix(int amount) {
        currentPoints = Math.min(currentPoints + amount, getMaxPoints());
    }
}
