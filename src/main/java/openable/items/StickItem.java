package openable.items;

public class StickItem extends Item{
    String name = "Stick";

    public StickItem() {
        stackable = true;
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
