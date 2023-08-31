package openable.items.materials;

import openable.items.Item;

public class StoneItem extends Item {
    String name = "Stone";

    public StoneItem() {
        stackable = true;
        craftable = false;
        edible = false;
        equipable = false;
        usable = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
