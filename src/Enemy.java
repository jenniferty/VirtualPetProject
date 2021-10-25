
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * The parent class of Enemy type objects.
 *
 * @author Jennifer Ty 15903786
 */
public abstract class Enemy {

    protected String name;
    protected int lower_hp;
    protected int upper_hp;
    protected int exp_value;
    protected int happyWin;
    protected int satietyWin;
    protected String message;
    Connection conn;
    protected Statement statement;
    protected Pet pet;
    ResultSet set;

    /**
     * Empty constructor. Cannot create an Enemy object without a child class
     * constructor.
     */
    protected Enemy() {

    }

    /**
     * Method to be inherited by the child classes. Used to create an ArrayList
     * from the contents of a file. Child classes will need to override this
     * method because they use different files to obtain an ArrayList.
     *
     * @return an ArrayList of Strings.
     */
    // protected abstract ArrayList getList();
    /**
     * Method to be inherited by the child classes. Will be called in the
     * constructor to populate the members of the class.
     */
    protected void initialise() {
        try {
            ResultSet initialiseEnemy = this.getEnemyData(pet.getLevel());
            this.setName(initialiseEnemy.getString("NAME"));
            this.setLower_hp(initialiseEnemy.getInt("MIN_DAMAGE"));
            this.setUpper_hp(initialiseEnemy.getInt("MAX_DAMAGE"));
            this.setExp_value(initialiseEnemy.getInt("EXP"));
            this.setMessage(initialiseEnemy.getString("MESSAGE"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Takes in a Pet object and modifies it based on the Enemy object.
     *
     * @param pet the parameter that gets modified
     */
    protected String battle(Pet pet) {
        StringBuilder build = new StringBuilder();
        int hpLoss = (int) ((Math.random() * (getUpper_hp() - getLower_hp()) + getLower_hp())); //randomizes the modification of the hp variable in the Pet class
        build.append(pet.updateHp(pet.getHp() - hpLoss) + "\n");
        if (pet.getHp() == 0) {
            build.append(pet.getName() + " was defeated by " + getName() + ".\n");
        } else {
            build.append(pet.getName() + " has defeated " + getName() + ".\n");
            build.append(this.getMessage());
            build.append(pet.updateExp(this.getExp_value()) + "\n");
            build.append(pet.updateHappy(pet.getHappy() + this.getHappyWin()) + "\n");
            build.append(pet.updateSatiety(pet.getSatiety() + this.getSatietyWin()) + "\n");
            pet.setBattleCount(pet.getBattleCount() + 1);
        }
        pet.update(); //updates the petstats.txt file with the new modifications
        return build.toString();
    }

    protected ResultSet getEnemyData(int level) {
        Data data = new Data();
        ResultSet rs = null;
        switch (level) {
            case 1:
            case 2:
                    try {
                rs = data.statement.executeQuery("SELECT * FROM WEAKENEMY ORDER BY RANDOM() OFFSET 0 ROW FETCH NEXT 1 ROWS ONLY");
                if (rs.next()) {
                    rs = rs;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            break;
            case 3:
            case 4:
            case 5:
            case 6:
                    try {
                rs = data.statement.executeQuery("SELECT * FROM AVERAGEENEMY ORDER BY RANDOM() OFFSET 0 ROW FETCH NEXT 1 ROWS ONLY");
                if (rs.next()) {
                    rs = rs;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            break;
            case 7:
            case 8:
            case 9:
                    try {
                rs = data.statement.executeQuery("SELECT * FROM STRONGENEMY ORDER BY RANDOM() OFFSET 0 ROW FETCH NEXT 1 ROWS ONLY");
                if (rs.next()) {
                    rs = rs;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            break;
        }
        return rs;
    }

    /**
     * @return the name
     */
    protected String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lower_hp
     */
    protected int getLower_hp() {
        return lower_hp;
    }

    /**
     * @param lower_hp the lower_hp to set
     */
    protected void setLower_hp(int lower_hp) {
        this.lower_hp = lower_hp;
    }

    /**
     * @return the upper_hp
     */
    protected int getUpper_hp() {
        return upper_hp;
    }

    /**
     * @param upper_hp the upper_hp to set
     */
    protected void setUpper_hp(int upper_hp) {
        this.upper_hp = upper_hp;
    }

    /**
     * @return the exp_value
     */
    protected int getExp_value() {
        return exp_value;
    }

    /**
     * @param exp_value the exp_value to set
     */
    protected void setExp_value(int exp_value) {
        this.exp_value = exp_value;
    }

    /**
     * @return the message
     */
    protected String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    protected void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the happyWin
     */
    protected int getHappyWin() {
        return happyWin;
    }

    /**
     * @return the satietyWin
     */
    protected int getSatietyWin() {
        return satietyWin;
    }

}
