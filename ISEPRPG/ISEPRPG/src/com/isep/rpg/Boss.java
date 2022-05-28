package com.isep.rpg;

public class Boss extends Enemy {
    public Boss() {
        super("Boss", 150, 35, 100);
    }

    @Override
    public String toString() {
        return getName() + ":HP " + getLifePoints() + ",盔甲 " + getArmor() + ", 攻击力 " + getWeaponDamage();
    }
}
