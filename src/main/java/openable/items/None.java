package openable.items;

public class None extends Item {
    String name = "None";
    public None() {
        stackable = false;
        craftable = false;
        edible = false;
        equipable = false;
        usable = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
