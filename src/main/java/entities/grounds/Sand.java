package entities.grounds;

public class Sand extends Ground {
    @Override
    public float getSpeedModifier() {
        return 0.75f;
    }
}
