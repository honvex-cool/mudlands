package openable.items;

import entities.Player;
import openable.inventory.Inventory;

public class Item {

    boolean stackable;
    boolean usable;
    boolean edible;
    boolean equipable;
    boolean craftable;

    public void craft(Inventory inventory) {

    }

    void use() {

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
