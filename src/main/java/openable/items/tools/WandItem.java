package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.*;
import utils.Pair;

public class WandItem extends Item {

    private final MudEssenceItem mud = new MudEssenceItem();
    private final GhostEssenceItem ghost = new GhostEssenceItem();
    private final ZombieBloodItem zombie = new ZombieBloodItem();
    private final LeatherItem leather = new LeatherItem();
    private final StickItem stick = new StickItem();

    public WandItem() {
        name = "Wand";
        stackable = false;
        craftable = true;
        edible = false;
        damage = new Damage(1, 1, 50, 50);
        durability = 1000;
        max_durability = 1000;
        type = ItemType.RIGHT_HAND;
        createRecipe();
    }

    private void createRecipe() {
        int requiredMud = 3;
        recipe.add(new Pair<>(mud, requiredMud));
        int requiredStick = 2;
        recipe.add(new Pair<>(stick, requiredStick));
        int requiredGhost = 1;
        recipe.add(new Pair<>(ghost, requiredGhost));
        int requiredZombie = 1;
        recipe.add(new Pair<>(zombie, requiredZombie));
        int requiredLeather = 1;
        recipe.add(new Pair<>(leather, requiredLeather));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new WandItem());
    }
}
