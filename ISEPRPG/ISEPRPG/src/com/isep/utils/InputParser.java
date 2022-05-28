package com.isep.utils;

import com.isep.rpg.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InputParser {
    private Game game;
    private int enemyActionCount = 0;

    public InputParser(Game game) {
        this.game = game;
    }

    public Hero addHero(String type) {
        Hero hero = null;
        if ("法师".equals(type)) {
            hero = new Mage();
            game.getHeroes().add(hero);
        } else if ("治疗师".equals(type)) {
            hero = new Healer();
            game.getHeroes().add(hero);
        } else if ("猎人".equals(type)) {
            hero = new Hunter();
            game.getHeroes().add(hero);
        } else if ("战士".equals(type)) {
            hero = new Warrior();
            game.getHeroes().add(hero);
        }
        return hero;
    }


    public String[] parser(Hero hero, String selectedItem, Enemy enemy) {
        String[] result = new String[]{"", ""};
        if (hero.getLifePoints() <= 0) {
            result[0] = "fail";
            result[1] = hero.getName() + "已阵亡，请选择其他英雄";
            return result;
        }
        if ("药水".equals(selectedItem)) {
            if (hero instanceof Hunter || hero instanceof Warrior) {
                result[0] = "fail";
                result[1] = "你是" + hero.getName() + ",你不能喝药水，请重新选择";
            } else {
                List<Potion> potions = hero.getPotions();
                if (potions.size() > 0) {
                    result[0] = "success";
                    Potion remove = potions.remove(0);
                    hero.useConsumable(remove);
                    result[1] = hero.getName() + "使用药水，增加" + remove.getEffect() + " 点魔法量";
                } else {
                    result[0] = "fail";
                    result[1] = hero.getName() + "没有药水了，请重新选择";
                }
            }
        } else if ("食物".equals(selectedItem)) {
            List<Food> lembas = hero.getLembas();
            if (lembas.size() > 0) {
                result[0] = "success";
                Food remove = lembas.remove(0);
                hero.useConsumable(remove);
                result[1] = hero.getName() + "使用食物，增加" + remove.getEffect() + " 点生命值";
            } else {
                result[0] = "fail";
                result[1] = hero.getName() + "没有食物了，请重新选择";
            }
        } else if ("攻击".equals(selectedItem)) {
            if (enemy == null) {
                result[0] = "fail";
                result[1] = "请选择需要攻击的敌人";
            } else {
                result[0] = "success";
                int attack = hero.attack(enemy);
                result[1] = hero.getName() + "攻击了敌人" + enemy.getName() + " ,造成其 HP 减少" + attack;
                if (hero instanceof Hunter) {
                    int arrows = ((Hunter) hero).getArrows();
                    if (arrows == 0) {
                        result[1] += "，弓箭已用完";
                    }
                }
                if (enemy.getLifePoints() <= 0) {
                    game.getDeadEnemyTurn().add(enemy);
                }
            }
        } else if ("治疗".equals(selectedItem)) {
            if (hero instanceof Healer) {
                Healer healer = (Healer) hero;
                List<Hero> heroes = game.getHeroes();
                Hero hero2 = heroes.stream().min(Comparator.comparingInt(Hero::getLifePoints)).get();
                boolean heal = healer.heal(hero2);
                if (heal) {
                    result[0] = "success";
                    result[1] = hero.getName() + "消耗5蓝量治疗了血量最低的队友" + hero2.getName() + " ,增加其 HP 30";
                } else {
                    result[0] = "fail";
                    result[1] = "蓝量不足，无法治疗，请重新选择";
                }
            } else {
                result[0] = "fail";
                result[1] = "只有治疗师才能进行治疗";
            }
        } else if ("防御".equals(selectedItem)) {
            hero.defend();
            result[0] = "success";
            result[1] = hero.getName() + "选择了防御，防御值增加5";
        }
        return result;
    }


    public String enemyAction() {
        List<Enemy> enemies = game.getEnemies().stream().filter(e -> e.getLifePoints() > 0).collect(Collectors.toList());
        Enemy enemy = enemies.get(enemyActionCount % enemies.size());
        enemyActionCount++;
        if (enemy.getName().equals("治疗师")) {
            Enemy enemy1 = enemies.stream().findAny().get();
            enemy1.setLifePoints(enemy1.getLifePoints() + 25);
            return "敌人" + enemy.getName() + "治疗敌方" + enemy1.getName() + ",增加其HP" + 25;
        } else {
            Hero hero = game.getHeroes().stream().filter(h -> h.getLifePoints() > 0).findAny().get();
            int armor = hero.getDefend() + hero.getArmor();//英雄盔甲防御
            int hurt = Math.max(enemy.getWeaponDamage() - armor, 0);//造成的伤害
            hero.setLifePoints(hero.getLifePoints() - hurt);
            if (hero.getLifePoints() <= 0) {
                game.getDeadHeroTurn().add(hero);
            }
            return "敌人" + enemy.getName() + "攻击了我方" + hero.getName() + ",造成了" + hero.getName() + " HP 减少 " + hurt;
        }
    }

    public String getInfo() {
        String heroStr = game.getHeroes().stream().map(Object::toString).collect(Collectors.joining("\n"));
        String enemyStr = game.getEnemies().stream().map(Object::toString).collect(Collectors.joining("\n"));
        return "英雄：\n" + heroStr + "\n敌人：\n" + enemyStr;
    }


    public String awardHero(String selectedItem) {

        game.getHeroes().stream().filter(h -> h.getLifePoints() > 0)
                .forEach(h -> {
                    if (selectedItem.equals("增加防御")) {
                        h.setArmor(h.getArmor() + 5);
                    } else if (selectedItem.equals("增加伤害")) {
                        h.setWeaponDamage(h.getWeaponDamage() + 3);
                    } else {
                        h.getLembas().add(new Food());
                        h.getPotions().add(new Potion());
                    }
                });
        return "回合内打败敌人，全体英雄" + selectedItem;
    }

    public String awardEnemy() {
        game.getEnemies().stream().filter(e -> e.getLifePoints() > 0).forEach(e -> {
            e.setLifePoints((int) (e.getLifePoints() + e.getLifePoints() * 0.05));
            e.setArmor((int) (e.getArmor() + e.getArmor() * 0.05));
            e.setWeaponDamage((int) (e.getWeaponDamage() + e.getWeaponDamage() * 0.05));
        });
        return "回合内打败英雄，全体成员属性增加 5%";
    }
}
