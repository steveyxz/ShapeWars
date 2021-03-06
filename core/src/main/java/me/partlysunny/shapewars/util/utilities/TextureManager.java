package me.partlysunny.shapewars.util.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    private static final Map<String, Texture> textures = new HashMap<>();

    public static void registerTexture(String id, Texture t) {
        textures.put(id, t);
    }

    public static void unregisterTexture(String id) {
        textures.remove(id);
    }

    public static Texture getTexture(String id) {
        return textures.get(id);
    }

    public static void initTextures() {
        //Logos
        doPngTexture("logo");
        doPngTexture("mainScreenLogo");
        //Entities
        doPngTexture("player");
        doPngTexture("wall");
        doPngTexture("rock");
        doPngTexture("tnt");
        doPngTexture("crate");
        doPngTexture("coin");
        //UI
        doPngTexture("slotBackground");
        doPngTexture("slotBackgroundSelected");
        doPngTexture("filterSelectOver");
        doPngTexture("filterSelectUp");
        doPngTexture("mainMenuButton");
        doPngTexture("mainMenuButtonDown");
        doPngTexture("mainMenuButtonChecked");
        doPngTexture("tooltipBackground");
        doPngTexture("equipmentSwapBackground");
        doPngTexture("utilBackground");
        doPngTexture("settings");
        doPngTexture("settingsDown");
        doPngTexture("progressBar");
        doPngTexture("progressBarKnob");
        doPngTexture("moneySign");
        doPngTexture("shop");
        doPngTexture("shopDown");
        doPngTexture("util");
        doPngTexture("utilDown");
        //Weapons
        doPngTexture("noWeapon");
        doPngTexture("circleBlaster");
        doPngTexture("circlePummeler");
        doPngTexture("bombLauncher");
        doPngTexture("bombLobber");
        doPngTexture("bombSprayer");
        doPngTexture("machineBomber");
        doPngTexture("triangleShotgun");
        doPngTexture("squareSniper");
        doPngTexture("squareRifle");
        doPngTexture("tinyGun");
        doPngTexture("miniGun");
        doPngTexture("woodenStick");
        doPngTexture("katana");
        doPngTexture("steelBroadsword");
        doPngTexture("dualSquaresaber");
        //Armor
        doPngTexture("oldTunic");
        doPngTexture("paddedGreaves");
        doPngTexture("spikedHelm");
        doPngTexture("infusedBoots");
        doPngTexture("empoweredScarf");
        //Utilities
        doPngTexture("basicHpPot");
        doPngTexture("mediumHpPot");
        doPngTexture("basicAmmoPack");
        //Bullets
        doPngTexture("basicBullet");
        doPngTexture("basicBomb");
        doPngTexture("shotgunBullet");
        doPngTexture("sniperBullet");
        doPngTexture("enemyBomb");
        doPngTexture("enemyBlasterBullet");
        //Enemies
        doPngTexture("basicEnemy");
        doPngTexture("tankEnemy");
        doPngTexture("blasterEnemy");
        doPngTexture("spawnIndicator");
        doPngTexture("commanderEnemy");
        doPngTexture("megaTankEnemy");
        doPngTexture("bomberEnemy");
        doPngTexture("sniperEnemy");
        doPngTexture("generalEnemy");
        doPngTexture("swarmEnemy");
        doPngTexture("swarmEnemyCore");
        doPngTexture("hexDefenderEnemy");
        doPngTexture("royalGuardEnemy");
        doPngTexture("shapeKing");
    }

    private static void doPngTexture(String value) {
        registerTexture(value, new Texture(Gdx.files.internal("assets/textures/" + value + ".png")));
    }

}
