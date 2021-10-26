
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private String message;

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
        Data data = new Data();
        data.updateCurrentPet(this);
    }

    /**
     * Reads and populates members of the Pet object from the petstats.txt file.
     */
    public final void initialise() {
        ResultSet rs = null;
        Data data = new Data();

        try {
            rs = data.getStatement().executeQuery("SELECT * FROM CURRENTPET");
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

        Data data = new Data();
        ResultSet rs = null;
        Boolean canContinue = null;
        Boolean valid = null;
        try {
            rs = data.getStatement().executeQuery("SELECT * FROM CURRENTPET");
            if (rs.next()) {
                rs = rs;
            }
            valid = validateCurrentPet(rs);
            if (!valid) {
                return false;
            }
            canContinue = !rs.getBoolean("GAMEOVER");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return canContinue; //true if gameover false if can continue with pet
    }

    public Boolean validateCurrentPet(ResultSet rs) {
        try {
            if (rs.getString("NAME").length() > 8 || rs.getString("NAME").length() < 1) {
                return false;
            }
            if (rs.getInt("LEVEL") < 1 || rs.getInt("LEVEL") > this.getLevelMax() - 1) {
                return false;
            }
            if (rs.getInt("HP") < 1 || rs.getInt("HP") > (100 + ((rs.getInt("LEVEL") - 1) * 10))) {
                return false;
            }
            if (rs.getInt("HAPPY") < 0 || rs.getInt("HAPPY") > this.getHappyMax()) {
                return false;
            }
            if (rs.getInt("EXP") < 0 || rs.getInt("EXP") >= this.getExpMax()) {
                return false;
            }
            if (rs.getInt("SATIETY") < 0 || rs.getInt("SATIETY") > this.getSatietyMax()) {
                return false;
            }
            if (rs.getInt("CORDYCEPS") < 0 || rs.getInt("CORDYCEPS") > 2) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Takes in a Food enum and modifies members of the Pet object based on the
     * values of each constant.
     *
     * @param food
     */
    public String eat(Food food) {

        /**
         * Code so that the pet can't eat if it is full unless the food does not
         * increase satiety.
         */
        StringBuilder build = new StringBuilder();
        if (this.getSatiety() < this.getSatietyMax() || (this.getSatiety() == this.getSatietyMax() && food.getSatietyChange() == 0)) {
            build.append(this.getName() + " ate " + food + ".\n");
            build.append(updateHp(this.getHp() + food.getHpChange()));
            if (this.getHp() > 0) { //game will end if hp is 0. This prevents further changes
                build.append(updateExp(this.getExp() + food.getExpChange()));
                build.append(updateLevel((this.getExp() / 100) + 1));
                build.append(updateSatiety(this.getSatiety() + food.getSatietyChange()));
                build.append(updateHappy(this.getHappy() + food.getHappyChange()));
                setFoodCount(this.foodCount + 1);
                if (food.equals(Food.CORDYCEPS)) {
                    setCordycepsCount(this.getCordycepsCount() + 1);
                }
                if (this.getCordycepsCount() >= 3) {
                    setMessage("\"... must... get... higher...\" " + this.getName() + " has died by eating cordyceps. What a way to go.");
                    //endGame(getMessage());
                    build.append(getMessage() + "\n");
                }
                if (this.getHp() == 0) {
                    setMessage("The Beginner's Guide to Identifying Mushrooms doesn't help those who can't see. " + this.getName() + " has died by eating a false morel. Unlucky.");
                    //endGame(getMessage());
                    build.append(getMessage() + "\n");
                }
            }
            update();
        } else { //prints messsage when satiety is full and food chosen increases satiety.
            build.append(this.getName() + " is full!");
        }
        return build.toString();
    }

    public String endGame(String message) {
        Data data = new Data();
        StringBuilder build = new StringBuilder();
        int id = 0;
        try {
            ResultSet rs = data.getStatement().executeQuery("SELECT * FROM PETRECORD");
            while (rs.next()) {
                id++;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        id++;
        try {
            data.getStatement().executeUpdate("INSERT INTO PETRECORD VALUES (" + id + ",'" + this.getName()
                    + "' ," + this.getLevel() + "," + this.getHpMax() + "," + this.getHp() + ","
                    + this.getHappy() + "," + this.getExp() + "," + this.getSatiety() + ","
                    + this.getFoodCount() + "," + this.getBattleCount() + "," + this.getLegendCount() + ",'"
                    + message + "')");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        build.append(message + "\n");

        build.append("Final stats:\n");
        build.append(this.toString() + "\n");
        this.setGameOver(true);
        this.update();
        build.append("Try again?");
        return build.toString();
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
    public String isLevelChange(int level) {
        if (level > this.level) {//prints message when level changes
            return this.getName() + " is now level " + level + "!\n";
        }
        return "";
    }

    /**
     * Bundles the set method with the method used to check if there is a change
     * to the assigned value.
     *
     * @param level
     */
    public String updateLevel(int level) {
        String str = isLevelChange(level);
        setLevel(level);
        return str;
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
    public String isHpChange(int hp) {
        if ((hp - this.getHp()) > 0) { //Compare current and new values, prints a message if true
            return this.getName() + " has recovered " + (hp - this.hp) + " HP.\n";
        } else if ((hp - this.hp) < 0) {
            return this.getName() + " has lost " + (this.hp - hp) + " HP.\n";
        }
        return "";
    }

    /**
     * Method that checks if parameter is out of bounds, then corrects it.
     * Bundles the set method and the method that checks if parameter is
     * different from the value assigned to the hp member.
     *
     * @param hp new value
     */
    public String updateHp(int hp) {
        if (hp > this.getHpMax()) { //prevents values from going out of bounds.
            hp = this.getHpMax();
        } else if (hp < 0) {
            hp = 0;
        }
        String str = isHpChange(hp);
        setHp(hp);
        return str;
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
    public String isHappyChange(int happy) {

        if ((happy - this.getHappy()) > 0) { //Compares current and new values, then prints a message
            return this.getName() + " has regained " + (happy - this.happy) + " happiness.\n";
            //isHappy();
        } else if ((happy - this.happy) < 0) {
            return this.getName() + " has lost " + (this.happy - happy) + " happiness.\n";
            //isHappy();
        } else if ((happy - this.happy) == 0) {
            //isHappy();
        }
        return "";
    }

    /**
     * Method that checks if parameter is out of bounds, then corrects it.
     * Bundles the set method and the method that checks if parameter is
     * different from the value assigned to the happy member.
     *
     * @param happy new value
     */
    public String updateHappy(int happy) {
        if (happy > this.getHappyMax()) { //Prevents parameters from going out of bounds.
            happy = this.getHappyMax();
        } else if (happy < 0) {
            happy = 0;
        }
        String str = isHappyChange(happy);
        setHappy(happy);
        str = str + isHappy();
        return str;
    }

    /**
     * Method to implicitly update the user on the pet's current happiness after
     * a change. Prints out key phrases.
     */
    public String isHappy() {
        if (this.getHappy() == 0) {
            return this.getName() + " has sunk into the depths of despair.";
        } else if (this.getHappy() < 25) {
            return this.getName() + " is in a bad mood.";
        } else if (this.getHappy() >= 25 && this.getHappy() < 50) {
            return this.getName() + " is feeling down.";
        } else if (this.getHappy() > 80 && this.getHappy() < 95) {
            return this.getName() + " is super happy.";
        } else if (this.getHappy() >= 95) {
            return this.getName() + " is over the moon.";
        }
        return "";
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
    public String updateExp(int exp) {
        if (exp > this.getExpMax()) { //prevents value from going over the maximum
            exp = this.getExpMax();
        }
        int expChange = exp - this.getExp();
        if (expChange > 0) { //prints message only if there is a change
            setExp(exp);
            return this.getName() + " has gained " + expChange + " EXP points!\n";
        }
        return "";
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
    public String isSatietyChange(int satiety) {
        if ((satiety - this.getSatiety()) > 0) { //Compares current and new values, then prints a message
            return this.getName() + " has gained " + (satiety - this.satiety) + " points in satiety.\n";
            //isHungry();
        } else if ((satiety - this.satiety) < 0) {
            return this.getName() + " has lost " + (this.satiety - satiety) + " points in satiety.\n";
            //isHungry();
        }
        return "";
    }

    /**
     * Method that checks if parameter is out of bounds, then corrects it.
     * Bundles the set method and the method that checks if parameter is
     * different from the value assigned to the satiety member.
     *
     * @param satiety new value
     */
    public String updateSatiety(int satiety) {
        if (satiety > this.getSatietyMax()) { //Prevents the parameters from going out of bounds.
            satiety = this.getSatietyMax();
        } else if (satiety < 0) {
            satiety = 0;
        }
        String str = isSatietyChange(satiety);
        setSatiety(satiety);
        str = str + isHungry() + "\n";
        return str;
    }

    /**
     * Method to implicitly update the user on the pet's current satiety after a
     * change. Prints out key phrases.
     */
    public String isHungry() {
        if (this.getSatiety() == 0) {
            return this.getName() + " is begging for food. \"Please feed me.\"";
        } else if (this.getSatiety() < 25) {
            return this.getName() + " hasn't eaten in a while. You should feed them.";
        } else if (this.getSatiety() >= 25 && this.getSatiety() < 50) {
            return this.getName() + " is hungry.";
        } else if (this.getSatiety() >= 50 && this.getSatiety() < 75) {
            return this.getName() + " is a bit peckish.";
        } else if (this.getSatiety() >= 75 && this.getSatiety() < 100) {
            return this.getName() + " wants a snack.";
        } else if (this.getSatiety() == this.getSatietyMax()) {
            return this.getName() + " has a round belly.";
        }
        return "";
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

    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
