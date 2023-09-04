package graphics;

public interface GraphicsContextInventory {
    default void begin() {

    }

    default void end(boolean inv, boolean craft, boolean status) {

    }

    void dispose();
}
