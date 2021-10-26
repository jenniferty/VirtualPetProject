
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

    private DBManager dbManager;
    private Connection conn;
    private Statement statement;

    public PetData() {
        this.dbManager = new DBManager();
        this.conn = dbManager.getConnection();
        try {
            this.statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createPetDatabases() {
        try {
            this.checkExistedTable("PETRECORD");
            this.getStatement().executeUpdate("CREATE  TABLE PETRECORD  (NAME   VARCHAR(8),"
                    + "   LEVEL   INT,   HP_MAX   INT,   HP   INT,   HAPPY INT,   EXP INT,"
                    + " SATIETY INT, FOOD INT, BATTLE INT, LEGEND INT, MESSAGE VARCHAR(200))");
            this.checkExistedTable("CURRENTPET");
            this.getStatement().executeUpdate("CREATE  TABLE CURRENTPET  (NAME   VARCHAR(8),"
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
            current.getStatement().executeUpdate("CREATE  TABLE CURRENTPET  (NAME   VARCHAR(8),"
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
            this.getStatement().executeUpdate("INSERT INTO CURRENTPET VALUES (\" + \"'\" + pet.getName()\n"
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
            record.getStatement().executeUpdate("CREATE  TABLE PETRECORD  (NAME   VARCHAR(8),"
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
            DatabaseMetaData dbmd = this.getConn().getMetaData();
            String[] types = {"TABLE"};
            setStatement(this.getConn().createStatement());
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                //System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) {
                    getStatement().executeUpdate("Drop table " + name);
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
        this.getDbManager().closeConnections();
    }

    /**
     * @return the dbManager
     */
    public DBManager getDbManager() {
        return dbManager;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
