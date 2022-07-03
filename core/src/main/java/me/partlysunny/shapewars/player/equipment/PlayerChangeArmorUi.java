package me.partlysunny.shapewars.player.equipment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.Tooltip;
import me.partlysunny.shapewars.item.items.ItemManager;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.screens.InGameScreenGuiManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

import java.util.List;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class PlayerChangeArmorUi extends InventoryMenu {

    private final PlayerEquipment equipment;
    private final Stage stage;
    private int slotToChange = 0;
    private final Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK);


    public PlayerChangeArmorUi(PlayerEquipment equipment, Stage stage) {
        this.equipment = equipment;
        this.stage = stage;
        buildUi();
    }

    private void buildUi() {
        Table table = new Table();
        table.setPosition(FRUSTUM_WIDTH / 4f, FRUSTUM_HEIGHT / 4f);
        table.setSize(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (shown) {
                    InventoryMenuManager.close();
                }
                return false;
            }
        });

        InGameScreenGuiManager.registerGui("armorUI", table, e -> updateTable((Table) e));
    }

    private void updateTable(Table table) {
        table.setVisible(shown);
        table.clear();

        List<String> unlockedArmor = equipment.unlockedArmors();

        int rows = (int) Math.ceil(unlockedArmor.size() / 9f);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= unlockedArmor.size()) {
                    break;
                }
                String armor = unlockedArmor.get(index);
                ArmorItem item = (ArmorItem) ItemManager.getItem(armor);
                Container<Image> armorContainer = new Container<>(new Image(TextureManager.getTexture(item.texture())));
                armorContainer.setBackground(equipment.regular(), true);
                armorContainer.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (shown) {
                            if (slotToChange == 0) {
                                equipment.setArmorOne(item);
                            } else {
                                equipment.setArmorTwo(item);
                            }
                            InventoryMenuManager.close();
                        }
                        return false;
                    }
                });
                Tooltip armorTT = new Tooltip.Builder(new Label(item.getDescription(), labelStyle)).target(armorContainer).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
                Util.formatTooltip(armorTT);
                table.add(armorContainer).size(6, 6).pad(0.5f);
            }
            table.row().pad(0.5f);
        }
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