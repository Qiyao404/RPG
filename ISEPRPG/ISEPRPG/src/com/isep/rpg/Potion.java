package com.isep.rpg;


public class Potion implements Consumable {
    private String name;
    private int effect;

    public Potion() {
        this.name = "药水";
        this.effect = 20;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
