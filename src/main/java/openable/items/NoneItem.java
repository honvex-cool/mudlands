package openable.items;

public class NoneItem extends Item {
    String name = "None";
    public NoneItem() {
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
