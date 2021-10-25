
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jenni
 */
public class Model extends Observable {

    public Data data;
    public Enemy enemy;

    public Model(Data data) {
        this.data = data;
    }

    public String viewRecord() {
        Data data = new Data();
        ResultSet rs = null;
        //String record = null;
        StringBuilder build = new StringBuilder();
        try {
            data.statement.setMaxRows(10);
            rs = data.statement.executeQuery("SELECT * FROM PETRECORD");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 0) {
                return "No records exists. Befriend your pet and complete your own journey today!";
                //System.out.println("No records exists. Befriend your pet and complete your own journey today!");
            }
            rs = data.statement.executeQuery("SELECT * FROM PETRECORD ORDER BY ID DESC");
            while (rs.next()) {
                build.append("Name: " + rs.getString("NAME") + "\n"
                        + "Level: " + rs.getInt("LEVEL") + "\n"
                        + "HP: " + rs.getInt("HP") + "/" + rs.getInt("HP_MAX") + "\n"
                        + "Happiness: " + rs.getInt("HAPPY") + "/100" + "\n"
                        + "Satiety: " + rs.getInt("SATIETY") + "/100" + "\n"
                        + "Food Eaten: " + rs.getInt("FOOD") + "\n"
                        + "Battles Won: " + rs.getInt("BATTLE") + "\n"
                        + "Legendaries Defeated: " + rs.getInt("LEGEND") + "\n"
                        + rs.getString("MESSAGE") + "\n\n");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        data.closeConnection();
        return build.toString();
    }

    public String battleMenu() {
        StringBuilder build = new StringBuilder();
        Pet pet=new Pet();
        if (pet.getSatiety() == 0) {
            build.append(pet.getName() + " is too hungry to fight.\n");
        }
        switch (pet.getLevel()) { //creates new Enemy object depending on level member of Pet object
            case 1:
            case 2:
                enemy = new WeakEnemy(pet); //declaration with parent object
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                enemy = new AverageEnemy(pet); //declaration with parent object
                break;
            case 7:
            case 8:
            case 9:
                enemy = new StrongEnemy(pet); //declaration with parent object
                break;
        }
        build.append(enemy.getName() + " has appeared!\n");
        build.append(pet.getName() + " Lv: " + pet.getLevel() + " HP: " + pet.getHp() + "/" + pet.getHpMax());

        return build.toString();
    }

    public Food getFood(String food) {
        switch (food) {
            case "Apple":
                return Food.APPLE;
            case "Steak":
                return Food.STEAK;
            case "Burger":
                return Food.BURGER;
            case "Cordyceps":
                return Food.CORDYCEPS;
            case "Mushroom":
                return Food.MUSHROOM;
            case "Bitter Powder":
                return Food.BITTERPOWDER;
        }
        return null;
    }
}
