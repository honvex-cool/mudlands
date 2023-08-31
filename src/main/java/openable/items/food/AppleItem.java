package openable.items.food;

import entities.Player;
import openable.items.Item;

public class AppleItem extends Item {
    String name = "Apple";

    int hp = 20;

    public AppleItem() {
        stackable = true;
        craftable = false;
        edible = true;
        equipable = false;
        usable = true;
    }

    @Override
    public void use(Player player) {
        player.getHp().fix(hp);
    }

    @Override
    public String toString() {
        return name;
    }
}
