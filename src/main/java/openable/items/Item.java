package openable.items;

import entities.Player;
import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;


public class Item implements Serializable {

    protected String name = "";
    protected boolean stackable;
    protected boolean edible;
    protected boolean craftable;
    protected Damage damage = new Damage(5, 5, 5, 5);
    protected int durability = 100;

    protected int damageReceived = 1;
    protected ItemType type = ItemType.NONE;

    public ItemType getType() {
        return type;
    }

    protected ArrayList<Pair<Item, Integer>> recipe = new ArrayList<>();

    public String getRecipe() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Pair<Item, Integer> item : recipe) {
            stringBuilder.append(item.getFirst()).append(": ").append(item.getSecond()).append(", ");
        }
        return stringBuilder.toString();
    }


    public Damage getAttackStrength() {
        return damage;
    }

    public boolean craft(Inventory inventory) {
        return isCraftable();
    }

    public boolean tryCrafting(Inventory inventory, Item item) {
        for(Pair<Item, Integer> pair : recipe) {
            if(!inventory.checkInventory(pair.getFirst(), pair.getSecond())) {
                return false;
            }
        }
        inventory.addItem(item, 1);
        for(Pair<Item, Integer> pair : recipe) {
            inventory.removeItem(pair.getFirst(), pair.getSecond());
        }
        return true;
    }

    public void use(Player player) {

    }

    public void damageItem() {
        durability = Math.max(durability - damageReceived, 0);
    }

    public int getDurability() {
        return durability;
    }

    public String getStats() {
        return "Durability " + durability;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public boolean isEdible() {
        return edible;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public boolean isCraftable() {
        return craftable;
    }

    public void setCraftable(boolean craftable) {
        this.craftable = craftable;
    }

    @Override
    public String toString() {
        return name;
    }
}