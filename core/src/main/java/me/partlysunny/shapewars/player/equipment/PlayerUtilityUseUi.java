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
import me.partlysunny.shapewars.player.InventoryMenu;
import me.partlysunny.shapewars.player.InventoryMenuManager;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.player.item.types.UtilityItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

import java.util.Iterator;
import java.util.Map;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class PlayerUtilityUseUi extends InventoryMenu {

    private static final Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK);
    private static final Label.LabelStyle remainingStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.RED);

    public PlayerUtilityUseUi(PlayerEquipment equipment, Stage stage) {
        super(equipment, stage);
    }

    @Override
    protected void buildUi() {
        Table table = new Table();
        table.setPosition(FRUSTUM_WIDTH * 3 / 4f, FRUSTUM_HEIGHT / 4f);
        table.setSize(FRUSTUM_WIDTH / 4, FRUSTUM_HEIGHT / 2f);
        table.setBackground(TextureRegionDrawableCache.get("utilBackground"));

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (shown) {
                    InventoryMenuManager.close();
                }
                return false;
            }
        });

        updateTable(table);

        InGameScreen.guiManager.registerGui("utilUI", table, e -> {
            if (equipment.hasUtilChanged()) {
                updateTable((Table) e);
                equipment.setHasUtilChanged(false);
            }
            ((Label) table.findActor("title")).setText("Utility Items");
            table.setVisible(shown);
        });
    }

    private void updateTable(Table table) {
        table.setVisible(shown);

        table.clear();

        table.row().width(40).padBottom(4);
        Label actor = new Label("Utility Items", labelStyle);
        actor.setAlignment(Alignment.CENTER.getAlignment());
        actor.setName("title");
        table.add(actor);

        Table inventory = new Table();
        inventory.setSize(table.getWidth(), table.getHeight() - 4);

        Map<String, Integer> utilItems = equipment.utilities();

        int rowMax = 2;
        int rows = (int) Math.ceil(utilItems.size() / (float) rowMax);
        Iterator<Map.Entry<String, Integer>> it = utilItems.entrySet().iterator();

        for (int i = 0; i < rows; i++) {
            inventory.row().pad(0.5f);
            for (int j = 0; j < rowMax; j++) {
                int index = i * rowMax + j;
                if (index >= utilItems.size()) {
                    break;
                }
                Map.Entry<String, Integer> utilItem = it.next();
                UtilityItem item = (UtilityItem) ItemManager.getItem(utilItem.getKey());
                Container<Image> weaponContainer = new Container<>(new Image(TextureManager.getTexture(item.texture())));
                weaponContainer.setBackground(equipment.regular(), true);
                weaponContainer.getActor().setScaling(Scaling.fit);
                weaponContainer.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (shown) {
                            InGameScreen.playerInfo.equipment().useItem(utilItem.getKey());
                            updateTable(table);
                        }
                        return false;
                    }
                });
                Label description = new Label(item.getDescription(), labelStyle);
                Label usesRemaining = new Label("You have " + utilItem.getValue() + " remaining", remainingStyle);
                Table mainTable = new Table();
                mainTable.setName("mainTable");
                mainTable.row().padTop(10);
                mainTable.add(description).width(26);
                mainTable.row().padTop(10);
                mainTable.add(usesRemaining).width(26);
                Tooltip weaponTT = new Tooltip.Builder(mainTable).target(weaponContainer).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
                Util.formatTooltip(weaponTT);
                inventory.add(weaponContainer).size(6, 6).pad(0.5f);
            }
        }
        table.row();
        table.add(inventory);

    }
}
