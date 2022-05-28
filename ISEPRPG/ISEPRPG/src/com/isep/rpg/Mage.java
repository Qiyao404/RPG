package com.isep.rpg;


public class Mage extends SpellCaster {
    public Mage() {
        super("法师", 80, 25, 25);
        setManaPoints(30);
        getPotions().add(new Potion());
        getLembas().add(new Food());
    }

    @Override
    public int attack(Enemy enemy) {
        //魔法无视防御
        enemy.setLifePoints(enemy.getLifePoints() - getWeaponDamage());
        return getWeaponDamage();
    }

    @Override
    public String toString() {
        return getName() + ":HP " + getLifePoints() + ",盔甲 " + getArmor() + ",魔法量 " + getManaPoints()
                + ",魔法攻击力 " + getWeaponDamage()
                + ",食物数量 " + getLembas().size()
                + ",药水数量 " + getPotions().size();
    }
}
