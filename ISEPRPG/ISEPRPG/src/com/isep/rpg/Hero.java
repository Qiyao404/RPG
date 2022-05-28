package com.isep.rpg;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero {

    private String name;
    private int lifePoints;
    private int armor;
    private int weaponDamage;
    private List<Potion> potions;

    private int defend;

    private List<Food> lembas;

    public Hero() {
        potions = new ArrayList<>();
        lembas = new ArrayList<>();
    }

    public Hero(String name, int lifePoints, int armor, int weaponDamage) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.armor = armor;
        this.weaponDamage = weaponDamage;
        potions = new ArrayList<>();
        lembas = new ArrayList<>();
    }

    public int attack(Enemy enemy) {
        int armor = enemy.getArmor();
        if (enemy instanceof Boss) armor = 35;
        int result = weaponDamage - armor;
        result = Math.max(result, 0);
        enemy.setLifePoints(enemy.getLifePoints() - result);
        return result;
    }


    public void defend() {
        defend = 25;
    }


    public void useConsumable(Consumable consumable) {
        if (consumable instanceof Food) {
            setLifePoints(((Food) consumable).getEffect() + getLifePoints());
        }
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

    public List<Potion> getPotions() {
        return potions;
    }

    public void setPotions(List<Potion> potions) {
        this.potions = potions;
    }

    public List<Food> getLembas() {
        return lembas;
    }

    public void setLembas(List<Food> lembas) {
        this.lembas = lembas;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }
}
