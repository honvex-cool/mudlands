package openable.items;

public class StoneItem extends Item {
    String name = "Stone";

    public StoneItem() {
        stackable = true;
        craftable = false;
        edible = false;
        equipable = true;
        usable = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
