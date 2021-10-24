
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jenni
 */
public class PetData {

    DBManager dbManager;
    Connection conn;
    Statement statement;

    public PetData() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createPetDatabases() {
        try {
            this.checkExistedTable("PETRECORD");
            this.statement.executeUpdate("CREATE  TABLE PETRECORD  (NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, FOOD INT, BATTLE INT, LEGEND INT, MESSAGE VARCHAR(200))");
            this.checkExistedTable("CURRENTPET");
            this.statement.executeUpdate("CREATE  TABLE CURRENTPET  (NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, CORDYCEPS INT, FOOD INT, BATTLE INT, GAMEOVER BOOLEAN)");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        this.closeConnection();
    }

    public void createCurrentPetTable(Pet pet) {
        PetData current = new PetData();

        try {
            current.checkExistedTable("CURRENTPET");
            current.statement.executeUpdate("CREATE  TABLE CURRENTPET  (NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, CORDYCEPS INT, FOOD INT, BATTLE INT, GAMEOVER BOOLEAN)");
            /*current.statement.executeUpdate("INSERT INTO CURRENTPET VALUES (" + "'" + pet.getName()
                    + "' ," + pet.getLevel() + "," + pet.getHpMax() + "," + pet.getHp() + ","
                    + pet.getHappy() + "," + pet.getExp() + "," + pet.getSatiety() + ","
                    + pet.getCordycepsCount() + "," + pet.getFoodCount() + ","
                    + pet.getBattleCount() + "," + pet.getGameOver() + ")");*/
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        current.closeConnection();
    }

    public void updateCurrentPet(Pet pet) {
        try {
            this.statement.executeUpdate("INSERT INTO CURRENTPET VALUES (\" + \"'\" + pet.getName()\n"
                    + "+ \"' ,\" + pet.getLevel() + \",\" + pet.getHpMax() + \",\" + pet.getHp() + \",\"\n"
                    + "+ pet.getHappy() + \",\" + pet.getExp() + \",\" + pet.getSatiety() + \",\"\n"
                    + "+ pet.getCordycepsCount() + \",\" + pet.getFoodCount() + \",\"\n"
                    + "+ pet.getBattleCount() + \",\" + pet.getGameOver() + \") WHERE ROW_NUMBER = 1");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createPetRecord() {
        PetData record = new PetData();

        try {
            record.checkExistedTable("PETRECORD");
            record.statement.executeUpdate("CREATE  TABLE PETRECORD  (NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, FOOD INT, BATTLE INT, LEGEND INT, MESSAGE VARCHAR(200))");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        record.closeConnection();
    }

    /**
     * From tutorial 8 solutions.
     *
     * @param name
     */
    public void checkExistedTable(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                //System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) {
                    statement.executeUpdate("Drop table " + name);
                    //System.out.println("Table " + name + " has been deleted.");
                    break;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}
