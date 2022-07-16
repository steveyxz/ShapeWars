package me.partlysunny.shapewars.player.item.items.weapons.ranged;

public class TinyGun extends MiniGun {

    @Override
    public float attackDelay() {
        return 0.07f;
    }

    @Override
    public float usesRegenRate() {
        return 0.2f;
    }

    @Override
    public int maxUses() {
        return 200;
    }

    @Override
    public String name() {
        return "Tiny Gun";
    }

    @Override
    public String description() {
        return "First prototypes of the minigun...";
    }

    @Override
    public String texture() {
        return "tinyGun";
    }

    @Override
    public int price() {
        return 250;
    }

    @Override
    public int damage() {
        return 3;
    }
}
