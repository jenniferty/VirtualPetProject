
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jenni
 */
public class Model extends Observable{
    public Data data;
    
    public Model(Data data){
        this.data = data;
    }
    
    static void viewRecord() {
        Data data = new Data();
        ResultSet rs = null;
        try {
            data.statement.setMaxRows(10);
            rs = data.statement.executeQuery("SELECT * FROM PETRECORD");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 0) {
                System.out.println("No records exists. Befriend your pet and complete your own journey today!");
            }
            rs = data.statement.executeQuery("SELECT * FROM PETRECORD");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("NAME") + "\n"
                        + "Level: " + rs.getInt("LEVEL") + "\n"
                        + "HP: " + rs.getInt("HP") + "/" + rs.getInt("HP_MAX") + "\n"
                        + "Happiness: " + rs.getInt("HAPPY") + "/100" + "\n"
                        + "Satiety: " + rs.getInt("SATIETY") + "/100" + "\n"
                        + "Food Eaten: " + rs.getInt("FOOD") + "\n"
                        + "Battles Won: " + rs.getInt("BATTLE") + "\n"
                        + "Legendaries Defeated: " + rs.getInt("LEGEND") + "\n"
                        + rs.getString("MESSAGE") + "\n");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        data.closeConnection();
    }
}
