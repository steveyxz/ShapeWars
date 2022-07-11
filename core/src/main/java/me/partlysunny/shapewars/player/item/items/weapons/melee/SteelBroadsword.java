package me.partlysunny.shapewars.player.item.items.weapons.melee;

public class SteelBroadsword extends GenericMeleeWeapon {
    @Override
    public String name() {
        return "Steel Broadsword";
    }

    @Override
    public String description() {
        return "Powerful blade forged centuries ago...";
    }

    @Override
    public String texture() {
        return "steelBroadsword";
    }

    @Override
    public float renderSizeX() {
        return 10;
    }

    @Override
    public int price() {
        return 400;
    }

    @Override
    public float renderSizeY() {
        return 20;
    }

    @Override
    protected int coverAngle() {
        return 160;
    }

    @Override
    protected float life() {
        return 0.7f;
    }

    @Override
    public int damage() {
        return 60;
    }

    @Override
    public float attackDelay() {
        return 1;
    }
}
