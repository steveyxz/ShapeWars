package me.partlysunny.shapewars.player.item.items.weapons.melee;

public class WoodenStick extends GenericMeleeWeapon {

    @Override
    public String name() {
        return "Wooden Stick";
    }

    @Override
    public String description() {
        return "Just an ordinary stick.";
    }

    @Override
    public String texture() {
        return "woodenStick";
    }

    @Override
    public float renderSizeX() {
        return 8;
    }

    @Override
    public int price() {
        return 15;
    }

    @Override
    public float renderSizeY() {
        return 16;
    }

    @Override
    public int damage() {
        return 6;
    }

    @Override
    protected int coverAngle() {
        return 120;
    }

    @Override
    protected float life() {
        return 0.3f;
    }

    @Override
    public float attackDelay() {
        return 0.5f;
    }
}
