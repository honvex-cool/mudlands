package inventory;

import java.util.ArrayList;

import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

public class Inventory {
    private Grid fields;
    public Inventory(){
        fields = new Grid(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        printInventory();
    }

    public void printInventory(){
        for(int i = 0; i < fields.getHeight(); i++){
            for(int j = 0; j < fields.getWidth(); j++){
                System.out.print(fields.get(i).get(j).getObjectType() + " " + fields.get(i).get(j).getNumber() + " | ");
            }
            System.out.println();
        }
    }
}
