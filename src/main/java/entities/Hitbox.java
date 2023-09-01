package entities;

public interface Hitbox {
    default boolean isActive() {
        return true;
    }
    default float getRadius(){
        return 0.5f;
    }
}
