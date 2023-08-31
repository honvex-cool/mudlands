package openable.items;

import java.util.ArrayList;

public class NoneItem extends Item {
    String name = "None";
    public NoneItem() {
        stackable = false;
        craftable = false;
        edible = false;
        equipable = false;
        usable = false;
        recipe = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }
}
