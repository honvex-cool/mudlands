package openable.items.materials;

import openable.items.Item;

public class MudEssenceItem extends Item {
    String name = "MudEssence";

    public MudEssenceItem() {
        stackable = true;
        craftable = false;
        edible = true;
        equipable = false;
        usable = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
