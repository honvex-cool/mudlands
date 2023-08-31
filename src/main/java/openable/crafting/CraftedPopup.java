package openable.crafting;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CraftedPopup extends Dialog {
    public CraftedPopup(String title, Skin skin) {
        super(title, skin);
    }

    @Override
    protected void result(Object object) {
        System.out.println("Crafted " + object.toString());
    }
}
