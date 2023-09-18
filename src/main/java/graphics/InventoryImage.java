package graphics;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class InventoryImage extends ImageButton{

    public void set(int i ,int j) {
        this.i = i;
        this.j = j;
    }

    public int i, j;
    public InventoryImage(ImageButtonStyle style) {
        super(style);
    }
    public InventoryImage(ImageButtonStyle style, int i, int j) {
        super(style);
        this.i = i;
        this.j = j;
    }
}
