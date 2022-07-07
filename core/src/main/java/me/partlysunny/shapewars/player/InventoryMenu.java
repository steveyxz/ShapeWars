package me.partlysunny.shapewars.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import me.partlysunny.shapewars.player.equipment.PlayerEquipment;

public abstract class InventoryMenu {

    protected final PlayerEquipment equipment;
    protected final Stage stage;
    public boolean shown = false;

    public InventoryMenu(PlayerEquipment equipment, Stage stage) {
        this.equipment = equipment;
        this.stage = stage;
        buildUi();
    }

    protected abstract void buildUi();

}
