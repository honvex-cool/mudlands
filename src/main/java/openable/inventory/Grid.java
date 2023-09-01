package openable.inventory;

import java.io.Serializable;
import java.util.ArrayList;

import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

public class Grid implements Serializable {
    private final int width;
    private final int height;
    ArrayList<ArrayList<InventoryField>> inventoryFields;

    Grid(int width, int height) {
        this.width = width;
        this.height = height;
        inventoryFields = new ArrayList<>();
        for(int i = 0; i < this.height; i++) {
            ArrayList<InventoryField> arrayList = new ArrayList<>();
            for(int j = 0; j < this.width; j++) {
                arrayList.add(new InventoryField());
            }
            inventoryFields.add(arrayList);
        }
    }

    Grid() {
        this(INVENTORY_WIDTH, INVENTORY_HEIGHT);
    }

    public ArrayList<InventoryField> get(int i) {
        return inventoryFields.get(i);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
