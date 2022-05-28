package com.isep;

import com.isep.rpg.Boss;
import com.isep.rpg.Enemy;
import com.isep.rpg.Game;
import com.isep.rpg.Hero;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GameController implements Initializable {
    @FXML
    Label playTurn;
    @FXML
    ComboBox<Integer> c_num;
    @FXML
    ComboBox<String> c_target;
    @FXML
    VBox vb_heros, vb_enemy;
    @FXML
    ListView<String> infos;

    @FXML
    Button start, ok;

    Game game = new Game();


    ToggleGroup thero = new ToggleGroup();
    ToggleGroup tenemy = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        c_num.getItems().addAll(1, 2, 3, 4, 5, 6);
        c_num.getSelectionModel().select(0);
        c_target.getItems().addAll("攻击", "防御", "治疗", "食物", "药水");
        c_target.getSelectionModel().select(0);
        c_num.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                game.getHeroes().clear();
                vb_heros.getChildren().clear();
                vb_enemy.getChildren().clear();
                start.setDisable(false);
                thero.getToggles().clear();
                tenemy.getToggles().clear();
            }
        });
    }

    @FXML
    public void addHero(Event event) {
        if (vb_heros.getChildren().size() >= c_num.getSelectionModel().getSelectedItem()) {
            return;
        }
        String type = ((Button) event.getSource()).getText();
        RadioButton radioButton = new RadioButton(type);
        radioButton.setToggleGroup(thero);
        Hero hero = game.getInputParser().addHero(type);
        radioButton.setUserData(hero);
        vb_heros.getChildren().add(radioButton);
        infos.getItems().add("添加英雄：" + type);
    }

    @FXML
    public void start(Event event) {
        if (thero.getToggles().size() != c_num.getSelectionModel().getSelectedItem()) {
            return;
        }
        game.generateCombat();
        List<Enemy> enemies = game.getEnemies();
        for (Enemy enemy : enemies) {
            RadioButton radioButton = new RadioButton(enemy.getName());
            radioButton.setToggleGroup(tenemy);
            radioButton.setUserData(enemy);
            vb_enemy.getChildren().add(radioButton);
            infos.getItems().add("添加敌人：" + enemy.getName());
        }
        infos.getItems().add("战斗开始");
        start.setDisable(true);
        infos.scrollTo(Integer.MAX_VALUE);
        thero.selectToggle((Toggle) vb_heros.getChildren().get(0));
        ok.setDisable(false);
        game.playGame();

    }

    @FXML
    public void ok(Event event) {
        String selectedItem = c_target.getSelectionModel().getSelectedItem();
        Hero hero;
        if (!start.isDisable()) {
            infos.getItems().add("请先开始游戏");
            infos.scrollTo(Integer.MAX_VALUE);
            return;
        }
        infos.getItems().add("第 " + game.getPlayerTurn() + " 回合");
        infos.getItems().add(game.getInputParser().getInfo());

        if (thero.getSelectedToggle() == null) return;
        hero = (Hero) thero.getSelectedToggle().getUserData();
        Enemy enemy = null;
        if (tenemy.getSelectedToggle() != null) {
            enemy = (Enemy) tenemy.getSelectedToggle().getUserData();
        }
        String[] parser = game.getInputParser().parser(hero, selectedItem, enemy);
        if (parser[0].equals("fail")) {
            infos.getItems().add(parser[1]);
            return;
        } else if (parser[0].equals("success")) {
            infos.getItems().add(parser[1]);
        }
        check();
        int winner = game.getWinner();
        if (winner != 0) {
            infos.getItems().add(winner == 1 ? "（英雄获胜）" : "（敌人获胜）");
            infos.scrollTo(Integer.MAX_VALUE);
            ok.setDisable(true);
            return;
        }
        infos.getItems().add("（敌人行动）");
        infos.getItems().add(game.getInputParser().enemyAction());
        game.setPlayerTurn(game.getPlayerTurn() + 1);
        playTurn.setText("第 " + (game.getPlayerTurn() + 1) + " 回合");
        check();
        winner = game.getWinner();
        if (winner != 0) {
            infos.getItems().add(winner == 1 ? "（英雄获胜）" : "（敌人获胜）");
            infos.scrollTo(Integer.MAX_VALUE);
            ok.setDisable(true);
            return;
        }

        if(game.getPlayerTurn() % 10 == 0){
            Boss boss = new Boss();
            game.getEnemies().add(boss);
            RadioButton radioButton = new RadioButton(boss.getName());
            radioButton.setToggleGroup(tenemy);
            radioButton.setUserData(boss);
            vb_enemy.getChildren().add(radioButton);
            infos.getItems().add("添加(BOSS)：" + boss.getName() + " " + boss.toString());
        }

        if (game.getDeadEnemyTurn().size() > 0) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("增加防御", "增加防御", "增加伤害", "增加药水和食物");
            dialog.setTitle("选择奖励");
            dialog.setHeaderText("杀死一名敌人，请选择需要的奖励");
            dialog.showAndWait();
            game.getDeadEnemyTurn().clear();
            infos.getItems().add(game.getInputParser().awardHero(dialog.getSelectedItem()));
        }
        if (game.getDeadHeroTurn().size() > 0) {
            game.getDeadHeroTurn().clear();
            infos.getItems().add(game.getInputParser().awardEnemy());
        }
        infos.scrollTo(Integer.MAX_VALUE);
    }


    private void check() {
        List<Hero> heroes = game.getHeroes().stream().filter(h -> h.getLifePoints() <= 0).collect(Collectors.toList());
        if (heroes.size() > 0) {
            for (Hero hero : heroes) {
                Node node = vb_heros.getChildren().stream().filter(n -> n.getUserData() == hero).findFirst().get();
                node.setDisable(true);
                ((ToggleButton) node).setSelected(false);
            }
        }
        List<Enemy> enemies = game.getEnemies().stream().filter(e -> e.getLifePoints() <= 0).collect(Collectors.toList());
        if (enemies.size() > 0) {
            for (Enemy enemy : enemies) {
                Node node = vb_enemy.getChildren().stream().filter(e -> e.getUserData() == enemy).findFirst().get();
                node.setDisable(true);
                ((ToggleButton) node).setSelected(false);
            }
        }
    }
}
