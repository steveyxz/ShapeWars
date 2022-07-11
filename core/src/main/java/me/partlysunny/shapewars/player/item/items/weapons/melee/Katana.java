package me.partlysunny.shapewars.player.item.items.weapons.melee;

public class Katana extends GenericMeleeWeapon {
    @Override
    public String name() {
        return "Katana";
    }

    @Override
    public String description() {
        return "Slice and dice!";
    }

    @Override
    public String texture() {
        return "katana";
    }

    @Override
    public float renderSizeX() {
        return 8;
    }

    @Override
    public int price() {
        return 100;
    }

    @Override
    public float renderSizeY() {
        return 16;
    }

    @Override
    protected int coverAngle() {
        return 150;
    }

    @Override
    protected float life() {
        return 0.4f;
    }

    @Override
    public int damage() {
        return 20;
    }

    @Override
    public float attackDelay() {
        return 0.5f;
    }
}
