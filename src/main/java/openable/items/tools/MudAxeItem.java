package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.items.materials.GhostEssenceItem;
import openable.items.materials.MudEssenceItem;
import openable.items.materials.ZombieBloodItem;
import utils.Pair;

public class MudAxeItem extends AxeItem {
    private final MudEssenceItem mud = new MudEssenceItem();
    private final GhostEssenceItem ghost = new GhostEssenceItem();
    private final ZombieBloodItem zombie = new ZombieBloodItem();
    private final int requiredZombie = 3;
    private final int requiredMud = 3;
    private final int requiredGhost = 3;

    public MudAxeItem() {
        super();
        name = "MudAxe";
        damage = new Damage(35, 1, 20, 25);
        durability = 250;
        max_durability = 250;
        recipe.add(new Pair<>(mud, requiredMud));
        recipe.add(new Pair<>(zombie, requiredZombie));
        recipe.add(new Pair<>(ghost, requiredGhost));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudAxeItem());
    }
}
