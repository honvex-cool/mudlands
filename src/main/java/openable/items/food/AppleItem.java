package openable.items.food;

import entities.Player;
import openable.items.Item;

public class AppleItem extends Item {
    public AppleItem() {
        name = "Apple";
        stackable = true;
        craftable = false;
        edible = true;
    }

    @Override
    public void use(Player player) {
        int hp = 20;
        player.feed(hp);
    }
}
