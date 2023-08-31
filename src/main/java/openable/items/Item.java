package openable.items;

import entities.Player;
import entities.materials.Damage;
import openable.inventory.Inventory;

public class Item {

    protected boolean stackable;
    protected boolean usable;
    protected boolean edible;
    protected boolean equipable;
    protected boolean craftable;
    protected Damage damage;

    public Damage getAttackStrength() {
        return damage;
    }

    public void craft(Inventory inventory) {

    }

    public void use(Player player) {

    }

    void equip() {

    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public boolean isEdible() {
        return edible;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public boolean isEquipable() {
        return equipable;
    }

    public void setEquipable(boolean equipable) {
        this.equipable = equipable;
    }

    public boolean isCraftable() {
        return craftable;
    }

    public void setCraftable(boolean craftable) {
        this.craftable = craftable;
    }

    @Override
    public String toString() {
        return "item";
    }
}
