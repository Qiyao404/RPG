package com.isep.rpg;


public class Hunter extends Hero {
    private int arrows;

    public Hunter() {
        super("猎人",60, 30, 60);
        this.arrows = 15;
        getPotions().add(new Potion());
        getLembas().add(new Food());
    }

    @Override
    public int attack(Enemy enemy) {
        if (arrows > 0) {
            arrows--;
            enemy.setLifePoints(enemy.getLifePoints() - getWeaponDamage() + enemy.getArmor());
            return getWeaponDamage() - enemy.getArmor();
        } else {
            return 0;
        }
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }
    @Override
    public String toString() {
        return getName() + ":HP " + getLifePoints() + ",盔甲 " + getArmor()
                + ",箭攻击力 " + getWeaponDamage()+" 弓箭数量 "+ arrows
                + ",食物数量 " + getLembas().size();
    }
}
