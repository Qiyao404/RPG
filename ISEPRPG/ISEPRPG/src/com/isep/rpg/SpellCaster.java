package com.isep.rpg;

public abstract class SpellCaster extends Hero{
    private int manaPoints;

    public SpellCaster() {
    }

    public SpellCaster(String name,int lifePoints, int armor, int weaponDamage) {
        super(name,lifePoints, armor, weaponDamage);
    }

    public void useConsumable(Consumable consumable) {
        if (consumable instanceof Food) {
            setLifePoints(((Food)consumable).getEffect()+getLifePoints());
        }else if (consumable instanceof Potion) {
            setManaPoints(((Potion)consumable).getEffect()+getManaPoints());
        }
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }
}
