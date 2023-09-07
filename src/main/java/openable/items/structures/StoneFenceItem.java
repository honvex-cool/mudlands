package openable.items.structures;

import entities.passives.Passive;
import entities.passives.StoneFence;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StoneItem;
import utils.Pair;

public class StoneFenceItem extends Item implements Placable {
    public StoneFenceItem() {
        name = "StoneFence";
        edible = false;
        craftable = true;
        usable = false;
        stackable = true;
        type = ItemType.HANDS;
        recipe.add(new Pair<>(new StoneItem(),4));
    }
    @Override
    public boolean craft(Inventory inventory) {
        if(super.craft(inventory)) {
            return tryCrafting(inventory, new StoneFenceItem());
        }
        return false;
    }
    @Override
    public Passive afterConstruction() {
        return new StoneFence();
    }
}
