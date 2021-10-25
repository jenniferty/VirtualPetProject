/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jenni
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class Data {

    DBManager dbManager;
    Connection conn;
    Statement statement;

    public Data() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        createDatabase();
    }

    public static void createDatabase() {    
        try {
            Data data = new Data();
            data.checkExistedTable("WEAKENEMY");
            data.statement.addBatch("CREATE  TABLE WEAKENEMY  (ENEMYID INT, NAME VARCHAR(20), MIN_DAMAGE INT, MAX_DAMAGE INT, EXP INT, MESSAGE VARCHAR(200))");
            data.statement.addBatch("INSERT INTO WEAKENEMY VALUES (1, 'Worm', 0, 1, 0, 'Squashed. Like a bug.'),\n"
                    + "(2, 'Goblin', 3, 8, 8, 'Goblins are people too...'),\n"
                    + "(3, 'Wolf', 4, 10, 15, 'Don''t worry. You wouldn''t kill a good doggie.'),\n"
                    + "(4, 'Bandit', 3, 6, 13, 'Good thing you''re broke.'),\n"
                    + "(5, 'Giant Viper', 10, 15, 10, 'I hate snakes.')");
            data.statement.executeBatch();
            
            data.checkExistedTable("AVERAGEENEMY");
            data.statement.addBatch("CREATE  TABLE AVERAGEENEMY  (ENEMYID  INT,   NAME   VARCHAR(20),   MIN_DAMAGE   INT,   MAX_DAMAGE   INT,   EXP   INT,   MESSAGE   VARCHAR(200))");
            data.statement.addBatch("INSERT INTO AVERAGEENEMY VALUES (1, 'Bear', 10, 18, 15, 'You''ve fought a bear and won. Impressive.'),\n"
                    + "(2, 'Ogre', 14, 21, 17, 'You can now rescue the princess.'),\n"
                    + "(3, 'Garuda', 13, 20, 20, 'Jumping took a lot of effort.'),\n"
                    + "(4, 'Goblin King', 15, 23, 30, '\"I''ve failed... my goblin brethren...\"'),\n"
                    + "(5, 'Treant', 8, 22, 7, 'Hurry and leave the forest! More are coming!'),\n"
                    + "(6, 'Super Panda', 20, 45, 40, 'It was kung fu fighting.'),\n"
                    + "(7, 'Zombie', 5, 30, 14, 'brainsbrainsbrainsbrainsbrains')");
            data.statement.executeBatch();
            
            data.checkExistedTable("STRONGENEMY");
            data.statement.addBatch("CREATE  TABLE STRONGENEMY  (ENEMYID  INT,   NAME   VARCHAR(20),   MIN_DAMAGE   INT,   MAX_DAMAGE   INT,   EXP   INT,   MESSAGE   VARCHAR(200),   LEGEND   BOOLEAN)");
            data.statement.addBatch("INSERT INTO STRONGENEMY VALUES (1, 'Bahamut', 35, 100, 75, 'Bad dragon.', true),\n"
                    + "(2, 'Final Boss', 150, 190, 300, 'The impossible...', true),\n"
                    + "(3, 'Basilisk', 15, 50, 30, 'I really hate snakes.', false),\n"
                    + "(4, 'King Hopsy', 0, 80, 25, 'There it goes...', false),\n"
                    + "(5, 'Giant Rat', 25, 40, 20, 'Eww...', false),\n"
                    + "(6, 'Lich', 40, 50, 30, 'What''s up with the undead being cute girls!?', true),\n"
                    + "(7, 'Chaos', 0, 100, 65, '???', true),\n"
                    + "(8, 'Haunted Doll', 10, 60, 22, '\"...(sob)...\"', false),\n"
                    + "(9, 'Fediel', 50, 110, 95, 'The giant skeletal, three-headed dragon reveals her true form. A cute, one-headed girl.', true)");
            data.statement.executeBatch();
            
            data.checkExistedTable("PETRECORD");
            data.statement.executeUpdate("CREATE  TABLE PETRECORD  (NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, FOOD INT, BATTLE INT, LEGEND INT, MESSAGE VARCHAR(200))");
            
            data.checkExistedTable("CURRENTPET");
            data.statement.executeUpdate("CREATE  TABLE CURRENTPET  (ID INT, NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, CORDYCEPS INT, FOOD INT, BATTLE INT, GAMEOVER BOOLEAN)");
            data.closeConnection();
        } catch (SQLException ex) {
            System.out.println("check"+ex.getMessage());
        }
    }
    
    public void updateCurrentPet(Pet pet) {
        try {
            this.statement.executeUpdate("DELETE FROM CURRENTPET WHERE ID=1");
            this.statement.executeUpdate("INSERT INTO CURRENTPET VALUES (1," + "'" + pet.getName()
                    + "' ," + pet.getLevel() + "," + pet.getHpMax() + "," + pet.getHp() + ","
                    + pet.getHappy() + "," + pet.getExp() + "," + pet.getSatiety() + ","
                    + pet.getCordycepsCount() + "," + pet.getFoodCount() + ","
                    + pet.getBattleCount() + "," + pet.getGameOver() + ")");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
