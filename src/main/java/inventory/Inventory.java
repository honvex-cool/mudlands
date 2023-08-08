package inventory;

import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

public class Inventory {
    public Grid getFields() {
        return fields;
    }

    private Grid fields;

    public Inventory() {
        fields = new Grid(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        printInventory();
    }

    public void printInventory() {
        for(int i = 0; i < fields.getHeight(); i++) {
            for(int j = 0; j < fields.getWidth(); j++) {
                System.out.print(fields.get(i).get(j).getItemType() + " " + fields.get(i).get(j).getNumber() + " | ");
            }
            System.out.println();
        }
    }

    public InventoryField get(int i, int j) {
        return fields.get(i).get(j);
    }

    public void addItem(InventoryField field, int i, int j) {
        fields.get(i).get(j).setField(field);
    }

    public void removeItem(int i, int j) {
        InventoryField field = fields.get(i).get(j);
        field.clearField();
    }
}
