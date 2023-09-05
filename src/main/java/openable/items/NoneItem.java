package openable.items;

import java.util.ArrayList;

public class NoneItem extends Item {
    public NoneItem() {
        name = "None";
        stackable = false;
        craftable = false;
        edible = false;
        usable = false;
        damageReceived = 0;
        recipe = new ArrayList<>();
    }
}
