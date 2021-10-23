
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * A Pet class that represents a pet.
 *
 * @author Jennifer Ty 15903786
 */
public class Pet {

    private String name;
    private int level;
    private final int levelMax = 10;
    private int hpMax;
    private int hp;
    private final int happyMax = 100;
    private int happy;
    private final int expMax = 900;
    private int exp;
    private final int satietyMax = 100;
    private int satiety;
    private int cordycepsCount;
    private int foodCount;
    private int battleCount;
    private int legendCount;
    private Boolean gameOver;

    /**
     * Constructor used to create a new default Pet object.
     *
     * @param name
     */
    public Pet(String name) {

        this.name = name;
        this.level = 1;
        this.hpMax = 100;
        this.hp = 100;
        this.happy = 50;
        this.exp = 0;
        this.satiety = 50;
        this.cordycepsCount = 0;
        this.foodCount = 0;
        this.battleCount = 0;
        this.gameOver = false;
        update(); //overwrites any existing content of the petstats.txt file 
    }

    /**
     * Constructor used to create a new Pet object using the values of a
     * previously used Pet. Values are obtained through the petstats.txt file.
     * Calls the initialise() method to populate members of the Pet class.
     */
    public Pet() {
        initialise();
    }

    /**
     * Saves the pet's values to the petstat.txt file so that the user can
     * continue the game with the same pet.
     */
    public final void update() {
        /*PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream("petstats.txt"));
            pw.println(this.getName());
            pw.println(this.getLevel());
            pw.println(this.getHpMax());
            pw.println(this.getHp());
            pw.println(this.getHappy());
            pw.println(this.getExp());
            pw.println(this.getSatiety());
            pw.println(this.getCordycepsCount());
            pw.println(this.getFoodCount());
            pw.println(this.getBattleCount());
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("File can't be read.");
        }*/

        PetData current = new PetData();
        current.createCurrentPetTable(this);
    }

    /**
     * Reads and populates members of the Pet object from the petstats.txt file.
     */
    public final void initialise() {
        /*List<String> petValues = new ArrayList<>();

        try ( Scanner s = new Scanner(new FileReader("petstats.txt"))) {
            while (s.hasNext()) {
                petValues.add(s.nextLine()); //each element of the ArrayList contains a value used to initialise Pet object
            }
            setName(petValues.get(0));
            setLevel(Integer.parseInt(petValues.get(1)));
            setHp(Integer.parseInt(petValues.get(3)));
            setHappy(Integer.parseInt(petValues.get(4)));
            setExp(Integer.parseInt(petValues.get(5)));
            setSatiety(Integer.parseInt(petValues.get(6)));
            setCordycepsCount(Integer.parseInt(petValues.get(7)));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No save file exists."); //prints this message if file is empty or is not the correct length
        } catch (NumberFormatException e) {
            System.out.println("Corrupted number field.");
        }*/

        PetData current = new PetData();
        ResultSet rs = null;

        try {
            rs = current.statement.executeQuery("SELECT * FROM CURRENTPET");
            if (rs.next()) {
                rs = rs;
            }
            
            setName(rs.getString("NAME"));
            setLevel(rs.getInt("LEVEL"));
            setHp(rs.getInt("HP"));
            setHappy(rs.getInt("HAPPY"));
            setExp(rs.getInt("EXP"));
            setSatiety(rs.getInt("SATIETY"));
            setCordycepsCount(rs.getInt("CORDYCEPS"));
            setBattleCount(rs.getInt("BATTLE"));
            setFoodCount(rs.getInt("FOOD"));
            setGameOver(false);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Checks if values in petstats.txt are valid or empty.
     *
     * @return a Boolean |true if file is valid|false if file is invalid
     */
    public Boolean canContinue() {
        /*try { //try statement to check if file is empty
            FileReader file = new FileReader("petstats.txt");
            BufferedReader br = new BufferedReader(file);
            String e = br.readLine();
            br.close();
            if (e == null) {
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file.");
        }

        List<String> petValues = new ArrayList<>();

        try ( Scanner s = new Scanner(new FileReader("petstats.txt"))) { //try statement to validate values in file if not empty
            while (s.hasNext()) {
                petValues.add(s.nextLine()); //each element of the ArrayList contains a value used to initialise Pet object
            }
            String cName = petValues.get(0);
            int cLevel = Integer.parseInt(petValues.get(1));
            int cHp = Integer.parseInt(petValues.get(3));
            int cHappy = Integer.parseInt(petValues.get(4));
            int cExp = Integer.parseInt(petValues.get(5));
            int cSatiety = Integer.parseInt(petValues.get(6));
            int cordyceps = Integer.parseInt(petValues.get(7));
            //series of if statements to check if values are out of bounds
            if (cName.length() > 8 || cName.length() < 1) {
                return false;
            }
            if (cLevel < 1 || cLevel > this.getLevelMax() - 1) {
                return false;
            }
            if (cHp < 1 || cHp > (100 + ((cLevel - 1) * 10))) {
                return false;
            }
            if (cHappy < 0 || cHappy > this.getHappyMax()) {
                return false;
            }
            if (cExp < 0 || cExp >= this.getExpMax()) {
                return false;
            }
            if (cSatiety < 0 || cSatiety > this.getSatietyMax()) {
                return false;
            }
            if (cordyceps < 0 || cordyceps > 2) {
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file.");
        } catch (NumberFormatException e) {
            return false; //returns false if integers cannot be parsed
        } catch (Exception e) {
            System.out.println(e); //catches this exception if values are out of bounds
        }*/
        PetData data = new PetData();
        ResultSet rs = null;
        Boolean canContinue = null;
        try {
            rs = data.statement.executeQuery("SELECT * FROM CURRENTPET");
            if (rs.next()) {
                rs = rs;
            }
            canContinue = rs.getBoolean("GAMEOVER");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return canContinue;
    }

    /**
     * Method used to empty petstats.txt when game ends(not exited). Prevents
     * users from using Pets that have reached an ending eg. death.
     */
    public final void deletePet() {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream("petstats.txt"));
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("File cannot be read.");
        }
    }

    /**
     * Takes in a Food enum and modifies members of the Pet object based on the
     * values of each constant.
     *
     * @param food
     */
    public void eat(Food food) {

        /**
         * Code so that the pet can't eat if it is full unless the food does not
         * increase satiety.
         */
        if (this.getSatiety() < this.getSatietyMax() || (this.getSatiety() == this.getSatietyMax() && food.getSatietyChange() == 0)) {
            System.out.println(this.getName() + " ate " + food + ".");
            updateHp(this.getHp() + food.getHpChange());
            if (this.getHp() > 0) { //game will end if hp is 0. This prevents further changes
                updateExp(this.getExp() + food.getExpChange());
                updateLevel((this.getExp() / 100) + 1);
                updateSatiety(this.getSatiety() + food.getSatietyChange());
                updateHappy(this.getHappy() + food.getHappyChange());
                setFoodCount(this.foodCount + 1);
                if (food.equals(Food.CORDYCEPS)) {
                    setCordycepsCount(this.getCordycepsCount() + 1);
                }
            }
            update();
        } else { //prints messsage when satiety is full and food chosen increases satiety.
            System.out.println(this.getName() + " is full!");
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Method to set the level and will set the hpMax accordingly.
     *
     * @param level the level to set
     */
    public void setLevel(int level) {
        switch (level) {//increases hpMax when level changes
            case 1:
                setHpMax(100);
                break;
            case 2:
                setHpMax(110);
                break;
            case 3:
                setHpMax(120);
                break;
            case 4:
                setHpMax(130);
                break;
            case 5:
                setHpMax(140);
                break;
            case 6:
                setHpMax(150);
                break;
            case 7:
                setHpMax(160);
                break;
            case 8:
                setHpMax(170);
                break;
            case 9:
                setHpMax(180);
                break;
            case 10:
                setHpMax(190);
                break;
        }
        this.level = level;
    }

    /**
     * Checks if the parameter is different from the value assigned to the level
     * member.
     *
     * @param level value to check
     */
    public void isLevelChange(int level) {
        if (level > this.level) {//prints message when level changes
            System.out.println(this.getName() + " is now level " + level + "!");
        }
    }

    /**
     * Bundles the set method with the method used to check if there is a change
     * to the assigned value.
     *
     * @param level
     */
    public void updateLevel(int level) {
        isLevelChange(level);
        setLevel(level);
    }

    /**
     * @return the hpMax
     */
    public int getHpMax() {
        return hpMax;
    }

    /**
     * @param hpMax the hpMax to set
     */
    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Checks if the parameter is different from the value assigned to the hp
     * member.
     *
     * @param hp value to campare
     */
    public void isHpChange(int hp) {
        if ((hp - this.getHp()) > 0) { //Compare current and new values, prints a message if true
            System.out.println(this.getName() + " has recovered " + (hp - this.hp) + " HP.");
        } else if ((hp - this.hp) < 0) {
            System.out.println(this.getName() + " has lost " + (this.hp - hp) + " HP.");
        }
    }

    /**
     * Method that checks if parameter is out of bounds, then corrects it.
     * Bundles the set method and the method that checks if parameter is
     * different from the value assigned to the hp member.
     *
     * @param hp new value
     */
    public void updateHp(int hp) {
        if (hp > this.getHpMax()) { //prevents values from going out of bounds.
            hp = this.getHpMax();
        } else if (hp < 0) {
            hp = 0;
        }
        isHpChange(hp);
        setHp(hp);
    }

    /**
     * @return the happyMax
     */
    public int getHappyMax() {
        return happyMax;
    }

    /**
     * @return the happy
     */
    public int getHappy() {
        return happy;
    }

    /**
     * @param happy the happy to set
     */
    public void setHappy(int happy) {
        this.happy = happy;
    }

    /**
     * Checks if the parameter is different from the value assigned to the happy
     * member.
     *
     * @param happy value to check
     */
    public void isHappyChange(int happy) {

        if ((happy - this.getHappy()) > 0) { //Compares current and new values, then prints a message
            System.out.println(this.getName() + " has regained " + (happy - this.happy) + " happiness.");
            isHappy();
        } else if ((happy - this.happy) < 0) {
            System.out.println(this.getName() + " has lost " + (this.happy - happy) + " happiness.");
            isHappy();
        } else if ((happy - this.happy) == 0) {
            isHappy();
        }
    }

    /**
     * Method that checks if parameter is out of bounds, then corrects it.
     * Bundles the set method and the method that checks if parameter is
     * different from the value assigned to the happy member.
     *
     * @param happy new value
     */
    public void updateHappy(int happy) {
        if (happy > this.getHappyMax()) { //Prevents parameters from going out of bounds.
            happy = this.getHappyMax();
        } else if (happy < 0) {
            happy = 0;
        }
        isHappyChange(happy);
        setHappy(happy);
    }

    /**
     * Method to implicitly update the user on the pet's current happiness after
     * a change. Prints out key phrases.
     */
    public void isHappy() {
        if (this.getHappy() == 0) {
            System.out.println(this.getName() + " has sunk into the depths of despair.");
        } else if (this.getHappy() < 25) {
            System.out.println(this.getName() + " is in a bad mood.");
        } else if (this.getHappy() >= 25 && this.getHappy() < 50) {
            System.out.println(this.getName() + " is feeling down.");
        } else if (this.getHappy() > 80 && this.getHappy() < 95) {
            System.out.println(this.getName() + " is super happy.");
        } else if (this.getHappy() >= 95) {
            System.out.println(this.getName() + " is over the moon.");
        }
    }

    /**
     * @return the expMax
     */
    public int getExpMax() {
        return expMax;
    }

    /**
     * @param exp the expMax to set
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    /**
     * @return the exp
     */
    public int getExp() {
        return exp;
    }

    /**
     * Method that checks if parameter is out of bounds, corrects, then sets the
     * exp member with the new value
     *
     * @param exp new value
     */
    public void updateExp(int exp) {
        if (exp > this.getExpMax()) { //prevents value from going over the maximum
            exp = this.getExpMax();
        }
        int expChange = exp - this.getExp();
        if (expChange > 0) { //prints message only if there is a change
            System.out.println(this.getName() + " has gained " + expChange + " EXP points!");
        }
        setExp(exp);
    }

    /**
     * @return the satietyMax
     */
    public int getSatietyMax() {
        return satietyMax;
    }

    /**
     * @return the satiety
     */
    public int getSatiety() {
        return satiety;
    }

    /**
     * @param satiety the satiety to set
     */
    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    /**
     * Checks if the parameter is different from the value assigned to the
     * satiety member.
     *
     * @param satiety
     */
    public void isSatietyChange(int satiety) {
        if ((satiety - this.getSatiety()) > 0) { //Compares current and new values, then prints a message
            System.out.println(this.getName() + " has gained " + (satiety - this.satiety) + " points in satiety.");
            isHungry();
        } else if ((satiety - this.satiety) < 0) {
            System.out.println(this.getName() + " has lost " + (this.satiety - satiety) + " points in satiety.");
            isHungry();
        }
    }

    /**
     * Method that checks if parameter is out of bounds, then corrects it.
     * Bundles the set method and the method that checks if parameter is
     * different from the value assigned to the satiety member.
     *
     * @param satiety new value
     */
    public void updateSatiety(int satiety) {
        if (satiety > this.getSatietyMax()) { //Prevents the parameters from going out of bounds.
            satiety = this.getSatietyMax();
        } else if (satiety < 0) {
            satiety = 0;
        }
        isSatietyChange(satiety);
        setSatiety(satiety);
    }

    /**
     * Method to implicitly update the user on the pet's current satiety after a
     * change. Prints out key phrases.
     */
    public void isHungry() {
        if (this.getSatiety() == 0) {
            System.out.println(this.getName() + " is begging for food. \"Please feed me.\"");
        } else if (this.getSatiety() < 25) {
            System.out.println(this.getName() + " hasn't eaten in a while. You should feed them.");
        } else if (this.getSatiety() >= 25 && this.getSatiety() < 50) {
            System.out.println(this.getName() + " is hungry.");
        } else if (this.getSatiety() >= 50 && this.getSatiety() < 75) {
            System.out.println(this.getName() + " is a bit peckish.");
        } else if (this.getSatiety() >= 75 && this.getSatiety() < 100) {
            System.out.println(this.getName() + " wants a snack.");
        } else if (this.getSatiety() == this.getSatietyMax()) {
            System.out.println(this.getName() + " has a round belly.");
        }
    }

    /**
     * @return the levelMax
     */
    public int getLevelMax() {
        return levelMax;
    }

    /**
     *
     * @return a String representation of the Pet object.
     */
    @Override
    public String toString() {
        return "Name: " + this.getName() + "\n"
                + "Level: " + this.getLevel() + "\n"
                + "HP: " + this.getHp() + "/" + this.getHpMax() + "\n"
                + "Happiness: " + this.getHappy() + "/" + this.getHappyMax() + "\n"
                + "Satiety: " + this.getSatiety() + "/" + this.getSatietyMax() + "\n"
                + "Food Eaten: " + this.getFoodCount() + "\n"
                + "Battles Won: " + this.getBattleCount() + "\n"
                + "Legendaries Defeated: " + this.getLegendCount();
    }

    /**
     * @return the cordycepsCount
     */
    public int getCordycepsCount() {
        return cordycepsCount;
    }

    /**
     * @param cordycepsCount the cordycepsCount to set
     */
    public void setCordycepsCount(int cordycepsCount) {
        this.cordycepsCount = cordycepsCount;
    }

    /**
     * @return the foodCount
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * @param foodCount the foodCount to set
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * @return the battleCount
     */
    public int getBattleCount() {
        return battleCount;
    }

    /**
     * @param battleCount the battleCount to set
     */
    public void setBattleCount(int battleCount) {
        this.battleCount = battleCount;
    }

    /**
     * @return the legendCount
     */
    public int getLegendCount() {
        return legendCount;
    }

    /**
     * @param legendCount the legendCount to set
     */
    public void setLegendCount(int legendCount) {
        this.legendCount = legendCount;
    }

    /**
     * @return the gameOver
     */
    public Boolean getGameOver() {
        return gameOver;
    }

    /**
     * @param gameOver the gameOver to set
     */
    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }
}
