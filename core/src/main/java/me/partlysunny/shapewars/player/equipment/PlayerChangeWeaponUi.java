package me.partlysunny.shapewars.player.equipment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.Tooltip;
import me.partlysunny.shapewars.item.items.ItemManager;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.player.InventoryMenu;
import me.partlysunny.shapewars.player.InventoryMenuManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

import java.util.List;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class PlayerChangeWeaponUi extends InventoryMenu {

    private final PlayerEquipment equipment;
    private final Stage stage;
    private final Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK);
    private int slotToChange = 0;
    private int rememberedSize = -1;


    public PlayerChangeWeaponUi(PlayerEquipment equipment, Stage stage) {
        this.equipment = equipment;
        this.stage = stage;
        buildUi();
    }

    private void buildUi() {
        Table table = new Table();
        table.setPosition(FRUSTUM_WIDTH / 4f, FRUSTUM_HEIGHT / 4f);
        table.setSize(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f);
        table.setBackground(TextureRegionDrawableCache.get("equipmentSwapBackground"));

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (shown) {
                    InventoryMenuManager.close();
                }
                return false;
            }
        });
        InGameScreen.guiManager.registerGui("weaponUI", table, e -> {
            if (equipment.unlockedWeapons().size() > rememberedSize) {
                updateTable((Table) e);
                rememberedSize = equipment.unlockedWeapons().size();
            }
            ((Label) table.findActor("title")).setText("Change Weapon " + (slotToChange + 1));
            table.setVisible(shown);
        });
    }

    private void updateTable(Table table) {
        table.setVisible(shown);

        table.clear();

        table.row().width(40).padBottom(4);
        Label actor = new Label("Change Weapon " + (slotToChange + 1), labelStyle);
        actor.setAlignment(Alignment.CENTER.getAlignment());
        actor.setName("title");
        table.add(actor);

        Table inventory = new Table();
        inventory.setSize(table.getWidth(), table.getHeight() - 4);

        List<String> unlockedWeapon = equipment.unlockedWeapons();

        int rowMax = 5;
        int rows = (int) Math.ceil(unlockedWeapon.size() / (float) rowMax);

        for (int i = 0; i < rows; i++) {
            inventory.row().pad(0.5f);
            for (int j = 0; j < rowMax; j++) {
                int index = i * rowMax + j;
                if (index >= unlockedWeapon.size()) {
                    break;
                }
                String weapon = unlockedWeapon.get(index);
                WeaponItem item = (WeaponItem) ItemManager.getItem(weapon);
                Container<Image> weaponContainer = new Container<>(new Image(TextureManager.getTexture(item.texture())));
                weaponContainer.setBackground(equipment.regular(), true);
                weaponContainer.getActor().setScaling(Scaling.fit);
                weaponContainer.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (shown) {
                            if (slotToChange == 0) {
                                equipment.setWeaponOne(item);
                            } else {
                                equipment.setWeaponTwo(item);
                            }
                            InventoryMenuManager.close();
                        }
                        return false;
                    }
                });
                Tooltip weaponTT = new Tooltip.Builder(new Label(item.getDescription(), labelStyle)).target(weaponContainer).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
                Util.formatTooltip(weaponTT);
                inventory.add(weaponContainer).size(6, 6).pad(0.5f);
            }
        }
        table.row();
        table.add(inventory);

    }

    public PlayerEquipment equipment() {
        return equipment;
    }

    public int slotToChange() {
        return slotToChange;
    }

    public void setSlotToChange(int slotToChange) {
        this.slotToChange = slotToChange;
    }

}
