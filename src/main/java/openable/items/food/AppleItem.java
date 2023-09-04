package openable.items.food;

import entities.Player;
import openable.items.Item;

public class AppleItem extends Item {
    int hp = 20;
    public AppleItem() {
        name = "Apple";
        stackable = true;
        craftable = false;
        edible = true;
        usable = true;
    }

    @Override
    public void use(Player player) {
        player.getHp().fix(hp);
    }
}
