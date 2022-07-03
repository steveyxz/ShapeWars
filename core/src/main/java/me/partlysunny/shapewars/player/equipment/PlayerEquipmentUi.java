package me.partlysunny.shapewars.player.equipment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.Tooltip;
import me.partlysunny.shapewars.screens.InGameScreenGuiManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class PlayerEquipmentUi {

    private final PlayerEquipment equipment;
    private final Stage stage;
    private boolean scheduled = false;


    public PlayerEquipmentUi(PlayerEquipment equipment, Stage stage) {
        this.equipment = equipment;
        this.stage = stage;
        buildUi();
    }

    private void buildUi() {
        Table table = new Table();
        table.setPosition(FRUSTUM_WIDTH / 4f, FRUSTUM_HEIGHT / 4f);
        table.setSize(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f);
        table.setDebug(true);
        updateTable(table);
        InGameScreenGuiManager.registerGui("equipmentUi", table, e -> updateTable((Table) e));
    }

    private void updateTable(Table table) {
        table.setPosition(FRUSTUM_WIDTH / 4f, FRUSTUM_HEIGHT / 4f);
        table.setSize(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f);
        table.setDebug(true);
        table.clear();

        Container<Image> weapon1 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Container<Image> weapon2 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.07f), Color.BLACK);
        weapon1.setTransform(true);
        weapon1.setSize(8, 8);
        weapon1.setBackground(equipment.regular(), true);

        weapon2.setTransform(true);
        weapon2.setSize(8, 8);
        weapon2.setBackground(equipment.regular(), true);

        Tooltip weapon1TT = new Tooltip.Builder(new Label("Weapon 1", labelStyle)).target(weapon1).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
        Tooltip weapon2TT = new Tooltip.Builder(new Label("Weapon 2", labelStyle)).target(weapon2).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();

        weapon1TT.getContentCell().align(Alignment.CENTER.getAlignment());
        ((Label) weapon1TT.getContent()).setAlignment(Alignment.CENTER.getAlignment());
        weapon1TT.setSize(30, 30);

        weapon2TT.getContentCell().align(Alignment.CENTER.getAlignment());
        ((Label) weapon2TT.getContent()).setAlignment(Alignment.CENTER.getAlignment());
        weapon2TT.setSize(30, 30);

        if (!scheduled) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    weapon1TT.setPosition(weapon1.getX(), weapon1.getY() + weapon1.getHeight() + 1);
                    weapon2TT.setPosition(weapon2.getX(), weapon2.getY() + weapon2.getHeight() + 1);
                    if (equipment().hasChangedWeaponOne()) {
                        if (equipment.weaponOne() != null) {
                            ((Label) weapon1TT.getContent()).setText(equipment.weaponOne().getDescription());
                        } else {
                            ((Label) weapon1TT.getContent()).setText("No weapon!");
                        }
                        equipment.setHasChangedWeaponOne(false);
                    }
                    if (equipment.hasChangedWeaponTwo()) {
                        if (equipment.weaponTwo() != null) {
                            ((Label) weapon2TT.getContent()).setText(equipment.weaponTwo().getDescription());
                        } else {
                            ((Label) weapon2TT.getContent()).setText("No weapon!");
                        }
                        equipment.setHasChangedWeaponTwo(false);
                    }
                    weapon1.getActor().setDrawable(TextureRegionDrawableCache.get(equipment.weaponOne() == null ? "noWeapon" : equipment.weaponOne().texture()));
                    weapon1.setBackground(equipment.activeWeaponSlot() == 0 ? equipment.selected() : equipment.regular());
                    weapon2.getActor().setDrawable(TextureRegionDrawableCache.get(equipment.weaponTwo() == null ? "noWeapon" : equipment.weaponTwo().texture()));
                    weapon2.setBackground(equipment.activeWeaponSlot() == 1 ? equipment.selected() : equipment.regular());
                }
            }, 0, 0.1f);
            scheduled = true;
        }

        Container<Image> armor2 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));
        Container<Image> armor1 = new Container<>(new Image(TextureManager.getTexture("noWeapon")));

        armor2.setTransform(true);
        armor2.setSize(8, 8);
        armor2.setBackground(equipment.regular(), true);

        armor1.setTransform(true);
        armor1.setSize(8, 8);
        armor1.setBackground(equipment.regular(), true);

        Tooltip armor1TT = new Tooltip.Builder(new Label("Armor 1", labelStyle)).target(armor1).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();
        Tooltip armor2TT = new Tooltip.Builder(new Label("Armor 2", labelStyle)).target(armor2).style(new Tooltip.TooltipStyle(TextureRegionDrawableCache.get("tooltipBackground"))).build();

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
                if (equipment.armorOne() != null) {
                    ((Label) armor1TT.getContent()).setText(equipment.armorOne().getDescription());
                } else {
                    ((Label) armor1TT.getContent()).setText("No armor!");
                }
                if (equipment.armorTwo() != null) {
                    ((Label) armor2TT.getContent()).setText(equipment.armorTwo().getDescription());
                } else {
                    ((Label) armor2TT.getContent()).setText("No armor!");
                }
                armor1.getActor().setDrawable(TextureRegionDrawableCache.get(equipment.armorOne() == null ? "noWeapon" : equipment.armorOne().texture()));
                armor2.getActor().setDrawable(TextureRegionDrawableCache.get(equipment.armorTwo() == null ? "noWeapon" : equipment.armorTwo().texture()));
            }
        }, 0, 0.1f);

        table.add(weapon1).size(8, 8);
        table.add(weapon2).size(8, 8).padRight(2);
        table.add(armor1).size(8, 8);
        table.add(armor2).size(8, 8);
        table.row().pad(1, 0, 1, 0);
    }

    public PlayerEquipment equipment() {
        return equipment;
    }


}
