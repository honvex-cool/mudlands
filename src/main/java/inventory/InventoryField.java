package inventory;

public class InventoryField {
    private InventoryFieldType fieldType;
    private int number;
    InventoryField(){
        fieldType = InventoryFieldType.NONE;
        number = 0;
    }

    public int getNumber() {
        return number;
    }

    public InventoryFieldType getObjectType() {
        return fieldType;
    }
}
