package me.partlysunny.shapewars.item.equipment;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.VisUI;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.components.WeaponComponent;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.item.types.UtilityItem;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.screens.InGameScreenGuiManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.GameWorld;

import java.util.ArrayList;
import java.util.List;

public class PlayerEquipment {

    private final List<Item> unlockedItems = new ArrayList<>();
    private ArmorItem armorOne = null;
    private ArmorItem armorTwo = null;
    private WeaponItem weaponOne = null;
    private WeaponItem weaponTwo = null;
    private UtilityItem util = null;
    private int activeWeaponSlot = 0;
    private final TextureRegionDrawable regular;
    private final TextureRegionDrawable selected;

    public PlayerEquipment() {
        regular = new TextureRegionDrawable(TextureManager.getTexture("slotBackground"));
        selected = new TextureRegionDrawable(TextureManager.getTexture("slotBackgroundSelected"));
        initGui();
    }

    private void initGui() {
        if (!VisUI.isLoaded()) {
            VisUI.load(new Skin(Gdx.files.internal("flatEarth/flat-earth-ui.json")));
        }
        //Weapons
        Container<Image> weapon1 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Container<Image> weapon2 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Label weapons = new Label("Weapons", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK));
        Container<Label> weaponsLabel = new Container<>(weapons);
        weapon1.setTransform(true);
        weapon2.setTransform(true);
        weaponsLabel.setTransform(true);
        weapon1.setSize(8, 8);
        weapon2.setSize(8, 8);
        weapon1.setPosition(2, 2);
        weapon2.setPosition(4 + weapon1.getWidth(), 2);
        weaponsLabel.setPosition(weapon1.getWidth() + 2, 12);
        weapon1.setBackground(regular, true);
        weapon2.setBackground(regular, true);

        InGameScreenGuiManager.registerGui("weapon1", weapon1, e -> {
            Container<Image> i = (Container<Image>) e;
            i.getActor().setDrawable(new TextureRegionDrawable(TextureManager.getTexture(weaponOne == null ? "noWeapon" : weaponOne.texture())));
            i.setBackground(activeWeaponSlot == 0 ? selected : regular);
        });

        InGameScreenGuiManager.registerGui("weapon2", weapon2, e -> {
            Container<Image> i = (Container<Image>) e;
            i.getActor().setDrawable(new TextureRegionDrawable(TextureManager.getTexture(weaponTwo == null ? "noWeapon" : weaponTwo.texture())));
            i.setBackground(activeWeaponSlot == 1 ? selected : regular);
        });

        InGameScreenGuiManager.registerGui("weaponsLabel", weaponsLabel, e -> {});

        //Armor
        Container<Image> armor2 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Container<Image> armor1 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Label armors = new Label("Armor", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK));
        Container<Label> armorsLabel = new Container<>(armors);
        armor2.setTransform(true);
        armor1.setTransform(true);
        armorsLabel.setTransform(true);
        armor2.setSize(8, 8);
        armor1.setSize(8, 8);
        armor2.setPosition(2, 15);
        armor1.setPosition(2, 16 + armor2.getHeight());
        armorsLabel.setPosition(armor2.getWidth() - 2, 18 + armor2.getHeight() + armor1.getHeight());
        armor2.setBackground(regular, true);
        armor1.setBackground(regular, true);

        InGameScreenGuiManager.registerGui("armor1", armor1, e -> {
            Container<Image> i = (Container<Image>) e;
            i.getActor().setDrawable(new TextureRegionDrawable(TextureManager.getTexture(armorOne == null ? "noWeapon" : armorOne.texture())));
        });

        InGameScreenGuiManager.registerGui("armor2", armor2, e -> {
            Container<Image> i = (Container<Image>) e;
            i.getActor().setDrawable(new TextureRegionDrawable(TextureManager.getTexture(armorTwo == null ? "noWeapon" : armorTwo.texture())));
        });

        InGameScreenGuiManager.registerGui("armorsLabel", armorsLabel, e -> {});
    }

    public List<Item> unlockedItems() {
        return unlockedItems;
    }

    public ArmorItem armorOne() {
        return armorOne;
    }

    public void setArmorOne(ArmorItem armorOne) {
        this.armorOne = armorOne;
    }

    public ArmorItem armorTwo() {
        return armorTwo;
    }

    public void setArmorTwo(ArmorItem armorTwo) {
        this.armorTwo = armorTwo;
    }

    public WeaponItem weaponOne() {
        return weaponOne;
    }

    public void setWeaponOne(WeaponItem weaponOne) {
        this.weaponOne = weaponOne;
        killWeaponEntities();
        respawnWeaponEntities();
    }

    public WeaponItem weaponTwo() {
        return weaponTwo;
    }

    public void setWeaponTwo(WeaponItem weaponTwo) {
        this.weaponTwo = weaponTwo;
        killWeaponEntities();
        respawnWeaponEntities();
    }

    public UtilityItem util() {
        return util;
    }

    public void setUtil(UtilityItem util) {
        this.util = util;
    }

    public int activeWeaponSlot() {
        return activeWeaponSlot;
    }

    public void setActiveWeaponSlot(int activeWeaponSlot) {
        if (activeWeaponSlot > 1 || activeWeaponSlot < 0) {
            throw new IllegalArgumentException("Must be 0 or 1");
        }
        this.activeWeaponSlot = activeWeaponSlot;
        killWeaponEntities();
        respawnWeaponEntities();
    }

    private void killWeaponEntities() {
        GameWorld world = InGameScreen.world;
        world.gameWorld().getEntitiesFor(Family.all(WeaponComponent.class).get()).forEach(entity -> world.gameWorld().removeEntity(entity));
    }

    private void respawnWeaponEntities() {
        GameWorld world = InGameScreen.world;
        WeaponItem item = activeWeaponSlot == 1 ? weaponTwo() : weaponOne();
        if (item == null) {
            return;
        }
        world.gameWorld().addEntity(item.buildEntity(world.gameWorld()));
    }

    public void tryAttack() {
        WeaponItem item = activeWeaponSlot == 1 ? weaponTwo() : weaponOne();
        if (item == null) {
            return;
        }
        item.attack(InGameScreen.playerInfo.playerEntity());
    }
}
