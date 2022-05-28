package com.isep.rpg;


public class Healer extends SpellCaster {
    public Healer() {
        super("治疗师", 80, 25, 15);
        setManaPoints(30);
        getPotions().add(new Potion());
        getLembas().add(new Food());
    }


    public boolean heal(Hero hero) {
        int manaPoints = getManaPoints();
        if (manaPoints > 5) {
            setManaPoints(manaPoints - 5);
            hero.setLifePoints(hero.getLifePoints() + 30);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getName() + ":HP " + getLifePoints() + ",盔甲 " + getArmor()+ ",魔法量 " + getManaPoints()
                + ",攻击力 " + getWeaponDamage()
                + ",食物数量 " + getLembas().size()
                + ",药水数量 " + getPotions().size();
    }
}
