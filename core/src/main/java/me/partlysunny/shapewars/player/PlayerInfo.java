package me.partlysunny.shapewars.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.kotcrab.vis.ui.VisUI;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.player.equipment.PlayerEquipment;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.ContactDispatcher;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.PlayerKeyMap;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

public class PlayerInfo {

    private final Entity playerEntity;
    private final ShapeWars game;
    private final AmmoManager ammoManager;
    private float health = GameInfo.PLAYER_MAX_HEALTH;
    private float maxHealth = GameInfo.PLAYER_MAX_HEALTH;
    private PlayerEquipment equipment;
    private PlayerKeyMap keyMap = new PlayerKeyMap();
    private boolean hasInitGui = false;
    private int money = 0;
    private final GlyphLayout layout = new GlyphLayout();

    public PlayerInfo(Entity playerEntity, ShapeWars game) {
        this.playerEntity = playerEntity;
        this.game = game;
        this.equipment = new PlayerEquipment();
        this.ammoManager = new AmmoManager();
        ContactDispatcher.registerListener(new PlayerMeleeHandle());
    }

    public void initGui() {
        if (hasInitGui) {
            return;
        }
        Util.loadVisUI();
        equipment.initGui();
        ProgressBar bar = new ProgressBar(0, maxHealth, 1, false, VisUI.getSkin());
        bar.updateVisualValue();
        bar.setAnimateDuration(0.5f);
        bar.setAnimateInterpolation(Interpolation.linear);
        Container<ProgressBar> container = new Container<>(bar);
        container.setTransform(true);
        container.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 8 / 12f, TextureRenderingSystem.FRUSTUM_HEIGHT - 10);
        container.setScale(0.3f, 0.3f);
        container.validate();
        InGameScreen.guiManager.registerGui("healthBar", container, actor -> {
            ProgressBar theBar = ((Container<ProgressBar>) actor).getActor();
            theBar.setRange(0, maxHealth);
            theBar.setValue(health);
        });

        Button.ButtonStyle style = new Button.ButtonStyle(TextureRegionDrawableCache.get("shop"), TextureRegionDrawableCache.get("shopDown"), TextureRegionDrawableCache.get("shop"));
        style.over = TextureRegionDrawableCache.get("shopDown");
        style.up.setMinHeight(8);
        style.up.setMinWidth(8);
        style.over.setMinHeight(8);
        style.over.setMinWidth(8);
        style.down.setMinHeight(8);
        style.down.setMinWidth(8);
        Container<Button> shopButton = new Container<>(new Button(style));
        shopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InventoryMenuManager.set("shop");
                return false;
            }
        });

        shopButton.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 11 / 12f, 2);
        shopButton.setSize(8, 8);

        InGameScreen.guiManager.registerGui("shopButton", shopButton, e -> {});

        Container<Image> moneySymbol = new Container<>(new Image(TextureManager.getTexture("moneySign")));

        moneySymbol.getActor().getDrawable().setMinHeight(7);
        moneySymbol.getActor().getDrawable().setMinWidth(7);

        moneySymbol.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 10 / 12f, 2);
        moneySymbol.setSize(7, 7);

        InGameScreen.guiManager.registerGui("moneySymbol", moneySymbol, e -> {});

        Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.2f), Color.BLACK);
        Container<Label> money = new Container<>(new Label(String.valueOf(this.money), labelStyle));
        layout.setText(labelStyle.font, String.valueOf(this.money));
        money.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 10 / 12f - 1 - layout.width / 2f, 1 - layout.height);

        InGameScreen.guiManager.registerGui("moneyAmount", money, e -> {
            if (money.getActor().getText().toString().equals(String.valueOf(this.money))) {
                return;
            }
            money.getActor().setText(String.valueOf(this.money));
            layout.setText(labelStyle.font, String.valueOf(this.money));
            money.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 10 / 12f - 1 - layout.width / 2f, 1 - layout.height);
        });

        hasInitGui = true;
    }

    public float health() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float maxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public PlayerEquipment equipment() {
        return equipment;
    }

    public void setEquipment(PlayerEquipment equipment) {
        this.equipment = equipment;
    }

    public Entity playerEntity() {
        return playerEntity;
    }

    public PlayerKeyMap keyMap() {
        return keyMap;
    }

    public void setKeyMap(PlayerKeyMap keyMap) {
        this.keyMap = keyMap;
    }

    public AmmoManager ammoManager() {
        return ammoManager;
    }

    public TransformComponent getTransformComponent() {
        if (!Mappers.transformMapper.has(playerEntity)) {
            throw new IllegalArgumentException("Player entity does not have transform component!");
        }
        return Mappers.transformMapper.get(playerEntity);
    }

    public void giveMoney(int amount) {
        money += amount;
    }

    public boolean canAfford(int price) {
        return price <= money;
    }

    public void damage(int health) {
        float totalDamage = health;
        float damageReduction = 1;
        if (equipment.armorOne() != null) damageReduction -= equipment.armorOne().getProtection();
        if (equipment.armorTwo() != null) damageReduction -= equipment.armorTwo().getProtection();
        totalDamage *= damageReduction;
        setHealth(this.health - totalDamage);
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
        TransformComponent playerBody = Mappers.transformMapper.get(playerEntity);
        ParticleEffectManager.startEffect("enemyMeleeAttack", (int) TextureRenderingSystem.metersToPixels(playerBody.position.x), (int) TextureRenderingSystem.metersToPixels(playerBody.position.y), 100);
        VisualEffectManager.getEffect("damage").playEffect(playerEntity);
        if (this.health <= 0) {
            PlayerKiller.kill();
        }
    }

    public void pay(int price) {
        money -= price;
    }
}
