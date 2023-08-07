package entities;

public interface Hitbox {
    float getRadius();
    default boolean isActive() {
        return true;
    }
}
