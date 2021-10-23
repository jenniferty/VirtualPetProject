
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
     * Overrides the abstract method. Opens the strongenemy.txt file and adds
     * each line as an element of an ArrayList.
     *
     * @return an ArrayList that is used to initialise an StrongEnemy object.
     */
    @Override
   /* protected ArrayList<String> getList() {
        ArrayList<String> enemyList = new ArrayList<>();
        try ( Scanner s = new Scanner(new FileReader("strongenemy.txt"))) {
            while (s.hasNext()) {
                enemyList.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("File can't be read.");
        }
        return enemyList;
    }*/

    /**
     * Overrides the battle() method to use the isLegend member to modify Pet
     * object.
     *
     * @param pet Pet object to modify.
     */
    //@Override
    protected void battle(Pet pet) {
        int hpLoss = (int) ((Math.random() * (getUpper_hp() - getLower_hp()) + getLower_hp()));
        pet.updateHp(pet.getHp() - hpLoss);
        if (pet.getHp() == 0) {
            System.out.println(pet.getName() + " was defeated by " + getName() + ".");
        } else {
            System.out.println(pet.getName() + " has defeated " + getName() + ".");
            System.out.println(this.getMessage());
            if (this.getIsLegend()) { //new code added
                pet.setLegendCount(pet.getLegendCount() + 1);
                System.out.println("It was a legendary battle.");
            }
            try { //A delay to allow users to read the console.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Can't sleep");
            }
            pet.updateExp(this.getExp_value());
            pet.updateHappy(pet.getHappy() + this.getHappyWin());
            pet.updateSatiety(pet.getSatiety() + this.getSatietyWin());
            pet.setBattleCount(pet.getBattleCount() + 1);
            try { //A delay to allow users to read the console.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Can't sleep");
            }
        }
        pet.update();
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
