package me.partlysunny.shapewars.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.kotcrab.vis.ui.VisUI;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.player.equipment.PlayerEquipment;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.screens.ScreenGuiManager;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.PlayerKeyMap;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

public class PlayerInfo {

    private final Entity playerEntity;
    private final ShapeWars game;
    private float health = GameInfo.PLAYER_MAX_HEALTH;
    private float maxHealth = GameInfo.PLAYER_MAX_HEALTH;
    private PlayerEquipment equipment;
    private PlayerKeyMap keyMap = new PlayerKeyMap();
    private boolean hasInitGui = false;
    private final AmmoManager ammoManager;

    public PlayerInfo(Entity playerEntity, ShapeWars game) {
        this.playerEntity = playerEntity;
        this.game = game;
        this.equipment = new PlayerEquipment();
        this.ammoManager = new AmmoManager();
    }

    public void initGui() {
        if (hasInitGui) {
            return;
        }
        Util.loadVisUI();
        equipment.initGui();
        ProgressBar bar = new ProgressBar(0, maxHealth, 1, false, VisUI.getSkin());
        bar.updateVisualValue();
        Container<ProgressBar> container = new Container<>(bar);
        container.setTransform(true);
        container.setPosition(TextureRenderingSystem.FRUSTUM_WIDTH * 8 / 12f, TextureRenderingSystem.FRUSTUM_HEIGHT - 10);
        container.setScale(0.3f, 0.3f);
        container.validate();
        InGameScreen.guiManager.registerGui("healthBar", container, actor -> {
            ProgressBar theBar = ((Container<ProgressBar>) actor).getActor();
            theBar.setRange(0, maxHealth);
            theBar.setValue(health);
            theBar.updateVisualValue();
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
}
