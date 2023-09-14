package graphics;

import entities.Player;
import openable.inventory.Inventory;

public interface GraphicsContextInventory {
    default void begin() {

    }

    default void end(boolean inv, boolean craft, boolean status) {

    }

    void setPlayer(Player player);
    void dispose();
}
