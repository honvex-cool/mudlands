package openable.items.food;

import entities.Player;
import openable.items.Item;

public class PorkchopItem extends Item {
    public PorkchopItem() {
        name = "Porkchop";
        stackable = true;
        craftable = false;
        edible = true;
    }

    @Override
    public void use(Player player) {
        int hp = 50;
        player.feed(hp);
    }
}
