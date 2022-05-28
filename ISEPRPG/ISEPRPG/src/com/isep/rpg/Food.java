package com.isep.rpg;

public class Food implements Consumable{
    private String name;
    private int effect;

    public Food() {
        this.name = "食物";
        this.effect = 50;
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
