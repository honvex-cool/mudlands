package openable.items.armor;

import openable.inventory.Inventory;
import openable.items.materials.MudEssenceItem;
import utils.Pair;

public class MudLeggingsItem extends LeatherLeggingsItem {
    public MudLeggingsItem(){
        super();
        name = "MudLeggings";
        recipe.add(new Pair<>(new MudEssenceItem(), 7));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudLeggingsItem());
    }
}
