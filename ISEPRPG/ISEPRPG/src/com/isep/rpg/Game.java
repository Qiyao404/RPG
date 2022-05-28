package com.isep.rpg;

import com.isep.utils.InputParser;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Hero> heroes;
    private int playerTurn = 1;
    private InputParser inputParser;

    private List<Enemy> enemies;

    private List<Enemy> deadEnemyTurn;
    private List<Hero> deadHeroTurn;


    public Game() {
        heroes = new ArrayList<>();
        enemies = new ArrayList<>();
        inputParser = new InputParser(this);
        deadEnemyTurn = new ArrayList<>();
        deadHeroTurn = new ArrayList<>();
    }


    public void playGame(){
        playerTurn = 1;
        deadEnemyTurn.clear();
        deadHeroTurn.clear();
    }


    public void generateCombat(){
        enemies.clear();
        for (Hero hero : heroes) {
            enemies.add(new BasicEnemy(hero.getName()));
        }
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public InputParser getInputParser() {
        return inputParser;
    }

    public void setInputParser(InputParser inputParser) {
        this.inputParser = inputParser;
    }


    public List<Enemy> getDeadEnemyTurn() {
        return deadEnemyTurn;
    }

    public List<Hero> getDeadHeroTurn() {
        return deadHeroTurn;
    }


    public int getWinner() {
        if (heroes.stream().filter(h->h.getLifePoints()<=0).count() ==heroes.size()) {
            return 2;
        }
        if (enemies.stream().filter(e->e.getLifePoints()<=0).count()==enemies.size()) {
            return 1;
        }
        return 0;
    }
}
