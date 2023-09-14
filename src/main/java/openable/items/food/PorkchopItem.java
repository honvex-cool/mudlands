package openable.items.food;

import entities.Player;
import openable.items.Item;

public class PorkchopItem extends Item {
    private final int hp = 50;
    public PorkchopItem() {
        name = "Porkchop";
        stackable = true;
        craftable = false;
        edible = true;
    }

    @Override
    public void use(Player player) {
        player.feed(hp);
    }
}
