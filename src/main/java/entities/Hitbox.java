package entities;

public interface Hitbox {
    default boolean isActive() {
        return true;
    }
}
