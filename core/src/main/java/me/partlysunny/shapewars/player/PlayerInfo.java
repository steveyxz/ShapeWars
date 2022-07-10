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
import me.partlysunny.shapewars.world.components.enemy.loot.LootPickupHandle;
import me.partlysunny.shapewars.world.components.player.PlayerKeyMap;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

public class PlayerInfo {

    private final Entity playerEntity;
    private final AmmoManager ammoManager;
    private final GlyphLayout layout = new GlyphLayout();
    private float health = GameInfo.PLAYER_MAX_HEALTH;
    private float maxHealth = GameInfo.PLAYER_MAX_HEALTH;
    private PlayerEquipment equipment;
    private PlayerKeyMap keyMap = new PlayerKeyMap();
    private boolean hasInitGui = false;
    private int money = 0;

    public PlayerInfo(Entity playerEntity) {
        this.playerEntity = playerEntity;
        this.equipment = new PlayerEquipment();
        this.ammoManager = new AmmoManager();
        ContactDispatcher.registerListener(new PlayerMeleeHandle());
        ContactDispatcher.registerListener(new LootPickupHandle());
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

        Button.ButtonStyle shopStyle = new Button.ButtonStyle(TextureRegionDrawableCache.get("shop"), TextureRegionDrawableCache.get("shopDown"), TextureRegionDrawableCache.get("shop"));
        shopStyle.over = TextureRegionDrawableCache.get("shopDown");
        shopStyle.up.setMinHeight(8);
        shopStyle.up.setMinWidth(8);
        shopStyle.over.setMinHeight(8);
        shopStyle.over.setMinWidth(8);
        shopStyle.down.setMinHeight(8);
        shopStyle.down.setMinWidth(8);

        Container<Button> shopButton = new Container<>(new Button(shopStyle));
        shopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InventoryMenuManager.set("shop");
                return false;
            }
        });

        shopButton.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 11 / 12f, 2);
        shopButton.setSize(8, 8);

        InGameScreen.guiManager.registerGui("shopButton", shopButton, e -> {
        });

        Button.ButtonStyle utilStyle = new Button.ButtonStyle(TextureRegionDrawableCache.get("util"), TextureRegionDrawableCache.get("utilDown"), TextureRegionDrawableCache.get("util"));
        utilStyle.over = TextureRegionDrawableCache.get("utilDown");
        utilStyle.up.setMinHeight(8);
        utilStyle.up.setMinWidth(8);
        utilStyle.over.setMinHeight(8);
        utilStyle.over.setMinWidth(8);
        utilStyle.down.setMinHeight(8);
        utilStyle.down.setMinWidth(8);

        Container<Button> utilButton = new Container<>(new Button(utilStyle));
        utilButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InventoryMenuManager.set("util");
                return false;
            }
        });

        utilButton.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 11 / 12f, 4 + shopButton.getHeight());
        utilButton.setSize(8, 8);

        InGameScreen.guiManager.registerGui("utilButton", utilButton, e -> {
        });

        Container<Image> moneySymbol = new Container<>(new Image(TextureManager.getTexture("coin")));

        moneySymbol.getActor().getDrawable().setMinHeight(6);
        moneySymbol.getActor().getDrawable().setMinWidth(6);

        moneySymbol.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * (10 / 12f + 1 / 96f), 2.5f);
        moneySymbol.setSize(6, 6);

        InGameScreen.guiManager.registerGui("moneySymbol", moneySymbol, e -> {
        });

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
        if (health > 0) {
            ParticleEffectManager.startEffect("enemyMeleeAttack", (int) TextureRenderingSystem.metersToPixels(playerBody.position.x), (int) TextureRenderingSystem.metersToPixels(playerBody.position.y), 100);
            VisualEffectManager.getEffect("damage").playEffect(playerEntity);
        }
        if (health < 0) {
            ParticleEffectManager.startEffect("heart", (int) TextureRenderingSystem.metersToPixels(playerBody.position.x), (int) TextureRenderingSystem.metersToPixels(playerBody.position.y), 100);
        }
        if (this.health <= 0) {
            PlayerKiller.kill();
            money = 0;
        }
    }

    public void pay(int price) {
        money -= price;
    }

    public int money() {
        return money;
    }
}
