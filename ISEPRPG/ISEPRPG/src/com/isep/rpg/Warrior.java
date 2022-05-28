package com.isep.rpg;


public class Warrior extends Hero {
    public Warrior() {
        super("战士", 100, 35, 40);
        getPotions().add(new Potion());
        getLembas().add(new Food());
    }


    @Override
    public String toString() {
        return getName() + ":HP " + getLifePoints() + ",盔甲 " + getArmor()
                + ",斧头攻击力 " + getWeaponDamage()
                + ",食物数量 " + getLembas().size();
    }
}
