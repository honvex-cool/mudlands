package entities.materials;

public record Damage(int wood, int mineral, int mud, int flesh) {
    public Damage {
        if(wood <= 0 || mineral <= 0 || mud <= 0 || flesh <= 0)
            throw new IllegalArgumentException("damage values must be positive");
    }
}
