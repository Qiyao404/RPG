package com.isep.rpg;

public class BasicEnemy extends Enemy {
    public BasicEnemy(String name) {
        super(name, 60, 35, 60);
    }


    @Override
    public String toString() {
        return getName() + ":HP " + getLifePoints() + ",盔甲 " + getArmor() + ", 攻击力 " + getWeaponDamage();
    }
}
