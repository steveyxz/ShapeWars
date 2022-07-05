package me.partlysunny.shapewars.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import me.partlysunny.shapewars.player.equipment.PlayerChangeArmorUi;
import me.partlysunny.shapewars.player.equipment.PlayerChangeWeaponUi;
import me.partlysunny.shapewars.player.shop.ShopUi;
import me.partlysunny.shapewars.screens.InGameScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventoryMenuManager {

    private static final Map<String, InventoryMenu> menus = new HashMap<>();
    private static String current = "";

    public static void registerMenu(String id, InventoryMenu menu) {
        menus.put(id, menu);
    }

    public static InventoryMenu getMenu(String id) {
        return menus.get(id);
    }

    public static void unregisterMenu(String id) {
        menus.remove(id);
    }

    public static void set(String id) {
        close();
        current = id;
        getMenu(id).shown = true;
    }

    public static void close() {
        if (!current.equals("")) {
            getMenu(current).shown = false;
        }
        current = "";
    }

    public static void init(Stage stage) {
        registerMenu("armor", new PlayerChangeArmorUi(InGameScreen.playerInfo.equipment(), stage));
        registerMenu("weapon", new PlayerChangeWeaponUi(InGameScreen.playerInfo.equipment(), stage));
        registerMenu("shop", new ShopUi(InGameScreen.playerInfo.equipment(), stage));
    }

    public static boolean isOpen() {
        return !Objects.equals(current, "");
    }
}
