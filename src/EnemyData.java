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

public class EnemyData {

    DBManager dbManager;
    Connection conn;
    Statement statement;

    public EnemyData() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EnemyData weak = new EnemyData();
        EnemyData average = new EnemyData();
        EnemyData strong = new EnemyData();

        try {
            weak.checkExistedTable("WEAKENEMY");
            weak.statement.addBatch("CREATE  TABLE WEAKENEMY  (ENEMYID INT, NAME VARCHAR(20), MIN_DAMAGE INT, MAX_DAMAGE INT, EXP INT, MESSAGE VARCHAR(200))");
            weak.statement.addBatch("INSERT INTO WEAKENEMY VALUES (1, 'Worm', 0, 1, 0, 'Squashed. Like a bug.'),\n"
                    + "(2, 'Goblin', 3, 8, 8, 'Goblins are people too...'),\n"
                    + "(3, 'Wolf', 4, 10, 15, 'Don''t worry. You wouldn''t kill a good doggie.'),\n"
                    + "(4, 'Bandit', 3, 6, 13, 'Good thing you''re broke.'),\n"
                    + "(5, 'Giant Viper', 10, 15, 10, 'I hate snakes.')");
            weak.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getNextException());
        }
        weak.closeConnection();

        try {
            average.checkExistedTable("AVERAGEENEMY");
            average.statement.addBatch("CREATE  TABLE AVERAGEENEMY  (ENEMYID  INT,   NAME   VARCHAR(20),   MIN_DAMAGE   INT,   MAX_DAMAGE   INT,   EXP   INT,   MESSAGE   VARCHAR(200))");
            average.statement.addBatch("INSERT INTO AVERAGEENEMY VALUES (1, 'Bear', 10, 18, 15, 'You''ve fought a bear and won. Impressive.'),\n"
                    + "(2, 'Ogre', 14, 21, 17, 'You can now rescue the princess.'),\n"
                    + "(3, 'Garuda', 13, 20, 20, 'Jumping took a lot of effort.'),\n"
                    + "(4, 'Goblin King', 15, 23, 30, '\"I''ve failed... my goblin brethren...\"'),\n"
                    + "(5, 'Treant', 8, 22, 7, 'Hurry and leave the forest! More are coming!'),\n"
                    + "(6, 'Super Panda', 20, 45, 40, 'It was kung fu fighting.'),\n"
                    + "(7, 'Zombie', 5, 30, 14, 'brainsbrainsbrainsbrainsbrains')");
            average.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getNextException());
        }
        average.closeConnection();

        try {
            strong.checkExistedTable("STRONGENEMY");
            strong.statement.addBatch("CREATE  TABLE STRONGENEMY  (ENEMYID  INT,   NAME   VARCHAR(20),   MIN_DAMAGE   INT,   MAX_DAMAGE   INT,   EXP   INT,   MESSAGE   VARCHAR(200),   LEGEND   BOOLEAN)");
            strong.statement.addBatch("INSERT INTO STRONGENEMY VALUES (1, 'Bahamut', 35, 100, 75, 'Bad dragon.', true),\n"
                    + "(2, 'Final Boss', 150, 190, 300, 'The impossible...', true),\n"
                    + "(3, 'Basilisk', 15, 50, 30, 'I really hate snakes.', false),\n"
                    + "(4, 'King Hopsy', 0, 80, 25, 'There it goes...', false),\n"
                    + "(5, 'Giant Rat', 25, 40, 20, 'Eww...', false),\n"
                    + "(6, 'Lich', 40, 50, 30, 'What''s up with the undead being cute girls!?', true),\n"
                    + "(7, 'Chaos', 0, 100, 65, '???', true),\n"
                    + "(8, 'Haunted Doll', 10, 60, 22, '\"...(sob)...\"', false),\n"
                    + "(9, 'Fediel', 50, 110, 95, 'The giant skeletal, three-headed dragon reveals her true form. A cute, one-headed girl.', true)");
            strong.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getNextException());
        }
        strong.closeConnection();
    }

    public ResultSet getEnemyData(int level) {
        ResultSet rs = null;

        switch (level) { //creates new Enemy object depending on level member of Pet object
            case 1:
            case 2:
                    try {
                rs = this.statement.executeQuery("SELECT * FROM WEAKENEMY ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            break;
            case 3:
            case 4:
            case 5:
            case 6:
                    try {
                rs = this.statement.executeQuery("SELECT * FROM AVERAGEENEMY ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            break;
            case 7:
            case 8:
            case 9:
                    try {
                rs = this.statement.executeQuery("SELECT * FROM STRONGENEMY ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            break;
        }
        return rs;

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
                System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) {
                    statement.executeUpdate("Drop table " + name);
                    System.out.println("Table " + name + " has been deleted.");
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
