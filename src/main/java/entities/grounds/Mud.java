package entities.grounds;

public class Mud extends Ground {
    @Override
    public float getSpeedModifier() {
        return 0.5f;
    }
}
