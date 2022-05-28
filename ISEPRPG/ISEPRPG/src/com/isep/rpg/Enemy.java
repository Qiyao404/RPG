package com.isep.rpg;


public abstract class Enemy {
    private String name;
    private int lifePoints;
    private int armor;
    private int weaponDamage;
    public Enemy() {
    }

    public Enemy(String name, int lifePoints, int armor, int weaponDamage) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.armor = armor;
        this.weaponDamage = weaponDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifePoints() {
        return Math.max(lifePoints, 0);
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(int weaponDamage) {
        this.weaponDamage = weaponDamage;
    }
}
