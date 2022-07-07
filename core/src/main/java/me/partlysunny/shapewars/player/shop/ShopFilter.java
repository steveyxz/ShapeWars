package me.partlysunny.shapewars.player.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.Tooltip;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.player.InventoryMenuManager;
import me.partlysunny.shapewars.player.equipment.PlayerEquipment;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.player.item.types.ArmorItem;
import me.partlysunny.shapewars.player.item.types.UtilityItem;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

import java.util.function.BiFunction;

public enum ShopFilter {

    WEAPONS((s, e) -> {
        WeaponItem item = (WeaponItem) ItemManager.getItem(s);
        Container<Image> weaponContainer = new Container<>(new Image(TextureManager.getTexture(item.texture())));
        weaponContainer.setBackground(e.regular(), true);
        weaponContainer.getActor().setScaling(Scaling.fit);
        weaponContainer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (InGameScreen.playerInfo.canAfford(item.price())) {
                    InGameScreen.playerInfo.pay(item.price());
                    e.unlockWeapon(s);
                    SoundEffectManager.play("shopBuy", 1);
                    setMessage("Successfully bought " + item.name(), Color.GREEN);
                } else {
                    SoundEffectManager.play("shopCannotAfford", 1);
                    setMessage("Cannot afford!", Color.RED);
                }
                update();
                return true;
            }
        });
        Tooltip weaponTT = new Tooltip.Builder(new Label(item.getDescription() + "\nPrice: " + item.price(), new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK))).target(weaponContainer).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
        Util.formatTooltip(weaponTT);
        return weaponContainer;
    }),
    ARMOR((s, e) -> {
        ArmorItem item = (ArmorItem) ItemManager.getItem(s);
        Container<Image> armorContainer = new Container<>(new Image(TextureManager.getTexture(item.texture())));
        armorContainer.setBackground(e.regular(), true);
        armorContainer.getActor().setScaling(Scaling.fit);
        armorContainer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (InGameScreen.playerInfo.canAfford(item.price())) {
                    InGameScreen.playerInfo.pay(item.price());
                    e.unlockArmor(s);
                    SoundEffectManager.play("shopBuy", 1);
                    setMessage("Successfully bought " + item.name(), Color.GREEN);
                } else {
                    SoundEffectManager.play("shopCannotAfford", 1);
                    setMessage("Cannot afford!", Color.RED);
                }
                update();
                return true;
            }
        });
        Tooltip armorTT = new Tooltip.Builder(new Label(item.getDescription() + "\nPrice: " + item.price(), new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK))).target(armorContainer).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
        Util.formatTooltip(armorTT);
        return armorContainer;
    }),
    UTILITY((s, e) -> {
        UtilityItem item = (UtilityItem) ItemManager.getItem(s);
        Container<Image> utilContainer = new Container<>(new Image(TextureManager.getTexture(item.texture())));
        utilContainer.setBackground(e.regular(), true);
        utilContainer.getActor().setScaling(Scaling.fit);
        utilContainer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (InGameScreen.playerInfo.canAfford(item.price())) {
                    InGameScreen.playerInfo.pay(item.price());
                    e.addUtilItems(s, 1);
                    SoundEffectManager.play("shopBuy", 1);
                    setMessage("Successfully bought " + item.name(), Color.GREEN);
                } else {
                    SoundEffectManager.play("shopCannotAfford", 1);
                    setMessage("Cannot afford!", Color.RED);
                }
                update();
                return true;
            }
        });
        Tooltip utilTT = new Tooltip.Builder(new Label(item.getDescription() + "\nPrice: " + item.price(), new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK))).target(utilContainer).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
        Util.formatTooltip(utilTT);
        return utilContainer;
    }),
    MISC((s, e) -> new Container<>());

    private final BiFunction<String, PlayerEquipment, Container<Image>> buildCell;

    ShopFilter(BiFunction<String, PlayerEquipment, Container<Image>> buildCell) {
        this.buildCell = buildCell;
    }

    private static void update() {
        ((ShopUi) InventoryMenuManager.getMenu("shop")).updateTable((Table) InGameScreen.guiManager.getGui("shopUI"));
    }

    private static void setMessage(String message, Color color) {
        ((ShopUi) InventoryMenuManager.getMenu("shop")).setMessage(message);
        ((ShopUi) InventoryMenuManager.getMenu("shop")).setMessageColor(color);
    }

    @Override
    public String toString() {
        return super.toString().charAt(0) + super.toString().toLowerCase().substring(1, super.toString().length());
    }

    public Container<Image> getItemFrom(String s, PlayerEquipment e) {
        return buildCell.apply(s, e);
    }
}
