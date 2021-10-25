
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 ** Child class of abstract Enemy class. Object is initialised using the
 * strongenemy.txt file.
 *
 * @author Jennifer Ty 15903786
 */
public class StrongEnemy extends Enemy {

    private Boolean isLegend; //new member unique to StrongEnemy child class

    /**
     * Constructor for StrongEnemy class. Calls the initialise() method to
     * populate the members of the class.
     */
    public StrongEnemy(Pet pet) {
        this.pet=pet;
        this.happyWin = 8;
        this.satietyWin = -4;
        initialise();
    }

    /**
     * Overrides the initialise() method to set the isLegend member not present
     * in other Enemy child classes.
     */
    @Override
    protected void initialise() {       
        ResultSet initialiseEnemy = this.getEnemyData(pet.getLevel());
        try {
            this.setName(initialiseEnemy.getString("NAME"));
            this.setLower_hp(initialiseEnemy.getInt("MIN_DAMAGE"));
            this.setUpper_hp(initialiseEnemy.getInt("MAX_DAMAGE"));
            this.setExp_value(initialiseEnemy.getInt("EXP"));
            this.setMessage(initialiseEnemy.getString("MESSAGE"));
            this.setIsLegend(initialiseEnemy.getBoolean("LEGEND"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Overrides the battle() method to use the isLegend member to modify Pet
     * object.
     *
     * @param pet Pet object to modify.
     */
    //@Override
    protected String battle(Pet pet) {
        StringBuilder build = new StringBuilder();
        int hpLoss = (int) ((Math.random() * (getUpper_hp() - getLower_hp()) + getLower_hp()));
        build.append(pet.updateHp(pet.getHp() - hpLoss)+"\n");
        if (pet.getHp() == 0) {
            build.append(pet.getName() + " was defeated by " + getName() + ".\n");
        } else {
            build.append(pet.getName() + " has defeated " + getName() + ".\n");
            build.append(this.getMessage()+"\n");
            if (this.getIsLegend()) { //new code added
                pet.setLegendCount(pet.getLegendCount() + 1);
                build.append("It was a legendary battle.\n");
            }
            build.append(pet.updateExp(this.getExp_value()) + "\n");
            build.append(pet.updateHappy(pet.getHappy() + this.getHappyWin()) + "\n");
            build.append(pet.updateSatiety(pet.getSatiety() + this.getSatietyWin()) + "\n");
            pet.setBattleCount(pet.getBattleCount() + 1);
        }
        pet.update();
        return build.toString();
    }

    /**
     * @return the isLegend
     */
    public Boolean getIsLegend() {
        return isLegend;
    }

    /**
     * @param isLegend the isLegend to set
     */
    public void setIsLegend(Boolean isLegend) {
        this.isLegend = isLegend;
    }
}
