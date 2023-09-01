package entities.grounds;

public class Water extends Ground {
    @Override
    public float getSpeedModifier() {
        return 0.25f;
    }
}
