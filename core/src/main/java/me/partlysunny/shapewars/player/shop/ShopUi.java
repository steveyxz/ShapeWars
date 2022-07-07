package me.partlysunny.shapewars.player.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.player.InventoryMenu;
import me.partlysunny.shapewars.player.InventoryMenuManager;
import me.partlysunny.shapewars.player.equipment.PlayerEquipment;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;

import java.util.ArrayList;
import java.util.List;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class ShopUi extends InventoryMenu {

    private final PlayerEquipment equipment;
    private final Stage stage;
    private final Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK);
    private final Label.LabelStyle messageStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.06f), Color.BLACK);
    private ShopFilter filter = ShopFilter.MISC;
    private ShopFilter rememberedFilter = ShopFilter.WEAPONS;
    private String message = "";
    private Color messageColor = Color.BLACK;


    public ShopUi(PlayerEquipment equipment, Stage stage) {
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

        InGameScreen.guiManager.registerGui("shopUI", table, e -> {
            if (rememberedFilter != filter) {
                updateTable(table);
                rememberedFilter = filter;
            }
            ((Label) table.findActor("title")).setText(filter.toString() + " Shop");
            ((Label) table.findActor("message")).setText(message);
            ((Label) table.findActor("message")).getStyle().fontColor = messageColor;
            table.setVisible(shown);
        });
    }

    public void updateTable(Table table) {
        table.clear();

        table.row().width(40).padBottom(4);
        Label title = new Label(filter.toString() + " Shop", labelStyle);
        title.setAlignment(Alignment.CENTER.getAlignment());
        title.setName("title");
        table.add(title);

        Table filterMenu = new Table();
        filterMenu.setSize(table.getWidth(), 30);
        filterMenu.add(
                createFilterButton(table, ShopFilter.ARMOR),
                createFilterButton(table, ShopFilter.WEAPONS),
                createFilterButton(table, ShopFilter.UTILITY),
                createFilterButton(table, ShopFilter.MISC)
        );
        table.row();
        table.add(filterMenu);

        Table shop = new Table();
        shop.setSize(table.getWidth(), table.getHeight() - 4);

        List<String> items;
        switch (filter) {
            case ARMOR: {
                items = ItemManager.getAllArmors(true);
                break;
            }
            case WEAPONS: {
                items = ItemManager.getAllWeapons(true);
                break;
            }
            case UTILITY: {
                items = ItemManager.getAllUtilities();
                break;
            }
            default:
                items = new ArrayList<>();
        }

        int rowMax = 5;
        int rows = (int) Math.ceil(items.size() / (float) rowMax);

        for (int i = 0; i < rows; i++) {
            shop.row().pad(0.5f);
            for (int j = 0; j < rowMax; j++) {
                int index = i * rowMax + j;
                if (index >= items.size()) {
                    break;
                }
                String s = items.get(index);
                shop.add(filter.getItemFrom(s, equipment)).size(6, 6).pad(0.5f);
            }
        }
        table.row();
        table.add(shop);

        table.row().width(40).padTop(4);

        Label message = new Label(this.message, messageStyle);
        message.setAlignment(Alignment.CENTER.getAlignment());
        message.getStyle().fontColor = messageColor;
        message.setName("message");

        table.add(message);
    }

    private TextButton createFilterButton(Table t, ShopFilter link) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = TextureRegionDrawableCache.get("filterSelectUp");
        style.down = TextureRegionDrawableCache.get("filterSelectOver");
        style.over = TextureRegionDrawableCache.get("filterSelectOver");
        style.font = FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.04f);
        process(style.up);
        process(style.down);
        process(style.over);
        TextButton filterButton = new TextButton(link.toString(), style);
        filterButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                filter = link;
                updateTable(t);
                return true;
            }
        });
        return filterButton;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Color messageColor() {
        return messageColor;
    }

    public void setMessageColor(Color messageColor) {
        this.messageColor = messageColor;
    }

    private void process(Drawable d) {
        d.setMinWidth(6);
        d.setMinHeight(3);
    }

}
