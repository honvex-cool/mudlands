package openable.items.structures;

import entities.passives.Passive;
import entities.passives.WoodenGate;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StickItem;
import utils.Pair;

public class WoodenGateItem extends Item implements Placable {
    public WoodenGateItem() {
        name = "WoodenGate";
        edible = false;
        craftable = true;
        stackable = true;
        type = ItemType.LEFT_HAND;
        recipe.add(new Pair<>(new StickItem(),6));
    }
    @Override
    public boolean craft(Inventory inventory) {
        if(super.craft(inventory)) {
            return tryCrafting(inventory, new WoodenGateItem());
        }
        return false;
    }
    @Override
    public Passive afterConstruction() {
        return new WoodenGate();
    }
}
