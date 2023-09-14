package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.items.materials.GhostEssenceItem;
import openable.items.materials.MudEssenceItem;
import openable.items.materials.ZombieBloodItem;
import utils.Pair;

public class MudSwordItem extends SwordItem{
    private final MudEssenceItem mud = new MudEssenceItem();
    private final GhostEssenceItem ghost = new GhostEssenceItem();

    private final ZombieBloodItem zombie = new ZombieBloodItem();
    private final int requiredZombie = 3;
    private final int requiredMud = 3;
    private final int requiredGhost = 3;
    public MudSwordItem(){
        super();
        name = "MudSword";
        damage = new Damage(1, 1, 1, 30);
        durability = 250;
        max_durability = 250;
        recipe.add(new Pair<>(mud, requiredMud));
        recipe.add(new Pair<>(ghost, requiredGhost));
        recipe.add(new Pair<>(zombie, requiredZombie));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudSwordItem());
    }
}
