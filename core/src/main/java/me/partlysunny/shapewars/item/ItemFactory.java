package me.partlysunny.shapewars.item;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.partlysunny.shapewars.TextureManager;
import me.partlysunny.shapewars.item.components.ItemComponent;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.item.components.WeaponComponent;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.item.types.UtilityItem;
import me.partlysunny.shapewars.item.types.WeaponItem;

public final class ItemFactory {

    private static final ItemFactory INSTANCE = new ItemFactory();

    public static ItemFactory getInstance() {
        return INSTANCE;
    }

    public void addItemBasics(PooledEngine engine, Entity entity, Item itemType) {
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        ItemComponent itemComponent = engine.createComponent(ItemComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

        itemComponent.init(InGameScreen.playerInfo.playerEntity(), itemType);
        transformComponent.init(itemType.renderSizeX(), itemType.renderSizeY());
        textureComponent.init(new TextureRegion(TextureManager.getTexture(itemType.texture())));

        entity.add(itemComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);

        if (itemType instanceof WeaponItem) {
            addWeaponBasics(engine, entity, (WeaponItem) itemType);
        } else if (itemType instanceof ArmorItem) {
            addArmorBasics(engine, entity, (ArmorItem) itemType);
        } else if (itemType instanceof UtilityItem) {
            addUtilityBasics(engine, entity, (UtilityItem) itemType);
        }
    }

    private void addUtilityBasics(PooledEngine engine, Entity entity, UtilityItem itemType) {

    }

    private void addWeaponBasics(PooledEngine engine, Entity entity, WeaponItem itemType) {
        WeaponComponent weaponComponent = engine.createComponent(WeaponComponent.class);
        weaponComponent.init(itemType);
        entity.add(weaponComponent);
    }

    private void addArmorBasics(PooledEngine engine, Entity entity, ArmorItem itemType) {

    }

}
