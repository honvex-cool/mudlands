package entities.materials;

public record Materials(int wood, int mineral, int mud, int flesh) {
    public Materials {
        if(wood < 0 || mineral < 0 || mud < 0 || flesh < 0)
            throw new IllegalArgumentException("material values must not be negative");
    }

    public int total() {
        return wood + mineral + mud + flesh;
    }

    public Materials damaged(Damage damage) {
        return new Materials(
            Math.max(wood - damage.wood(), 0),
            Math.max(mineral - damage.mineral(), 0),
            Math.max(mud - damage.mud(), 0),
            Math.max(flesh - damage.flesh(), 0)
        );
    }
}
