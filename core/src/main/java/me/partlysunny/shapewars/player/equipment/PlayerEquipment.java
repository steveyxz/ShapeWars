package me.partlysunny.shapewars.player.equipment;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.Tooltip;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.components.WeaponComponent;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.item.types.UtilityItem;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.screens.InGameScreenGuiManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.GameWorld;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.List;

public class PlayerEquipment {

    private final List<Item> unlockedItems = new ArrayList<>();
    private final TextureRegionDrawable regular;
    private final TextureRegionDrawable selected;
    private ArmorItem armorOne = null;
    private ArmorItem armorTwo = null;
    private WeaponItem weaponOne = null;
    private WeaponItem weaponTwo = null;
    private List<String> unlockedWeapons = new ArrayList<>();
    private List<String> unlockedArmor = new ArrayList<>();
    private int activeWeaponSlot = 0;

    public PlayerEquipment() {
        regular = new TextureRegionDrawable(TextureManager.getTexture("slotBackground"));
        selected = new TextureRegionDrawable(TextureManager.getTexture("slotBackgroundSelected"));
        initGui();
    }

    public void initGui() {
        Util.loadVisUI();
        //Weapons
        Container<Image> weapon1 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Container<Image> weapon2 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK);
        Label weapons = new Label("Weapons", labelStyle);
        Container<Label> weaponsLabel = new Container<>(weapons);
        weapon1.setTransform(true);
        weapon1.setSize(8, 8);
        weapon1.setPosition(2, 2);
        weapon1.setBackground(regular, true);

        weapon2.setTransform(true);
        weapon2.setSize(8, 8);
        weapon2.setPosition(4 + weapon1.getWidth(), 2);
        weapon2.setBackground(regular, true);

        weaponsLabel.setTransform(true);
        weaponsLabel.setPosition(weapon1.getWidth() + 2, 12);

        Tooltip weapon1TT = new Tooltip.Builder(new Label("Weapon 1", labelStyle)).target(weapon1).style(new Tooltip.TooltipStyle(new TextureRegionDrawable(TextureManager.getTexture("tooltipBackground")))).build();
        Tooltip weapon2TT = new Tooltip.Builder(new Label("Weapon 2", labelStyle)).target(weapon2).style(new Tooltip.TooltipStyle(new TextureRegionDrawable(TextureManager.getTexture("tooltipBackground")))).build();

        weapon1TT.getContentCell().align(Alignment.CENTER.getAlignment());
        ((Label) weapon1TT.getContent()).setAlignment(Alignment.CENTER.getAlignment());
        weapon1TT.setSize(30, 30);

        weapon2TT.getContentCell().align(Alignment.CENTER.getAlignment());
        ((Label) weapon2TT.getContent()).setAlignment(Alignment.CENTER.getAlignment());
        weapon2TT.setSize(30, 30);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                weapon1TT.setPosition(weapon1.getX(), weapon1.getY() + weapon1.getHeight() + 1);
                weapon2TT.setPosition(weapon2.getX(), weapon2.getY() + weapon2.getHeight() + 1);
                if (weaponOne != null) {
                    ((Label) weapon1TT.getContent()).setText(weaponOne.getDescription());
                } else {
                    ((Label) weapon1TT.getContent()).setText("No weapon!");
                }
                if (weaponTwo != null) {
                    ((Label) weapon2TT.getContent()).setText(weaponTwo.getDescription());
                } else {
                    ((Label) weapon2TT.getContent()).setText("No weapon!");
                }
            }
        }, 0, 0.1f);

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

        InGameScreenGuiManager.registerGui("weaponsLabel", weaponsLabel, e -> {
        });

        //Armor
        Container<Image> armor2 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Container<Image> armor1 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Label armors = new Label("Armor", labelStyle);
        Container<Label> armorsLabel = new Container<>(armors);

        armor2.setTransform(true);
        armor2.setSize(8, 8);
        armor2.setPosition(2, 15);
        armor2.setBackground(regular, true);

        armor1.setTransform(true);
        armor1.setSize(8, 8);
        armor1.setPosition(2, 16 + armor2.getHeight());
        armor1.setBackground(regular, true);

        armorsLabel.setTransform(true);
        armorsLabel.setPosition(armor2.getWidth() - 2, 18 + armor2.getHeight() + armor1.getHeight());

        Tooltip armor1TT = new Tooltip.Builder(new Label("Armor 1", labelStyle)).target(armor1).style(new Tooltip.TooltipStyle(new TextureRegionDrawable(TextureManager.getTexture("tooltipBackground")))).build();
        Tooltip armor2TT = new Tooltip.Builder(new Label("Armor 2", labelStyle)).target(armor2).style(new Tooltip.TooltipStyle(new TextureRegionDrawable(TextureManager.getTexture("tooltipBackground")))).build();

        armor1TT.getContentCell().align(Alignment.CENTER.getAlignment());
        ((Label) armor1TT.getContent()).setAlignment(Alignment.CENTER.getAlignment());
        armor1TT.setSize(30, 30);

        armor2TT.getContentCell().align(Alignment.CENTER.getAlignment());
        ((Label) armor2TT.getContent()).setAlignment(Alignment.CENTER.getAlignment());
        armor2TT.setSize(30, 30);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                armor1TT.setPosition(armor1.getX() + armor1.getWidth() + 1, armor1.getY());
                armor2TT.setPosition(armor2.getX() + armor2.getWidth() + 1, armor2.getY());
                if (armorOne != null) {
                    ((Label) armor1TT.getContent()).setText(armorOne.getDescription());
                } else {
                    ((Label) armor1TT.getContent()).setText("No armor!");
                }
                if (armorTwo != null) {
                    ((Label) armor2TT.getContent()).setText(armorTwo.getDescription());
                } else {
                    ((Label) armor2TT.getContent()).setText("No armor!");
                }
            }
        }, 0, 0.1f);

        InGameScreenGuiManager.registerGui("armor1", armor1, e -> {
            Container<Image> i = (Container<Image>) e;
            i.getActor().setDrawable(new TextureRegionDrawable(TextureManager.getTexture(armorOne == null ? "noWeapon" : armorOne.texture())));
        });

        InGameScreenGuiManager.registerGui("armor2", armor2, e -> {
            Container<Image> i = (Container<Image>) e;
            i.getActor().setDrawable(new TextureRegionDrawable(TextureManager.getTexture(armorTwo == null ? "noWeapon" : armorTwo.texture())));
        });

        InGameScreenGuiManager.registerGui("armorsLabel", armorsLabel, e -> {
        });
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

    public void unlockWeapon(String weapon) {
        unlockedWeapons.add(weapon);
    }

    public void unlockArmor(String armor) {
        unlockedArmor.add(armor);
    }


    public void setWeaponOne(WeaponItem weaponOne) {
        this.weaponOne = weaponOne;
        if (weaponTwo == weaponOne) {
            this.weaponTwo = null;
        }
        killWeaponEntities();
        respawnWeaponEntities();
    }

    public WeaponItem weaponTwo() {
        return weaponTwo;
    }

    public void setWeaponTwo(WeaponItem weaponTwo) {
        this.weaponTwo = weaponTwo;
        if (weaponOne == weaponTwo) {
            this.weaponOne = null;
        }
        killWeaponEntities();
        respawnWeaponEntities();
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
        world.gameWorld().getEntitiesFor(Family.all(WeaponComponent.class).get()).forEach(LateRemover::tagToRemove);
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

    public List<String> unlockedWeapons() {
        return unlockedWeapons;
    }

    public List<String> unlockedArmor() {
        return unlockedArmor;
    }
}
