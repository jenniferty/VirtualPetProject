
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Class that contains methods used to run the game.
 *
 * @author Jennifer Ty 15903786
 */
public class Game {

    private Pet pet;
    public Enemy enemy;
    private Boolean gameState = true; //true by default
    private String message;

    /**
     * Constructor for Game object. Takes in a Pet object.
     *
     * @param pet to access and modify
     */
    public Game(Pet pet) {
        this.pet = pet;
    }

    /**
     * Method that calls other methods depending on user input.
     */
    public void gameMenu() {
        System.out.println("[B]Battle   [F]Food   [S]Stats   [M]Main Menu");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        while (!input.equalsIgnoreCase("b") && !input.equalsIgnoreCase("f") && !input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("m")) //validates input
        {
            try {
                if (input.length() < 1 || input.length() > 1) {
                    throw new Exception("Input a single character only");
                }
                if (!input.equalsIgnoreCase("b") && !input.equalsIgnoreCase("f") && !input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("m")) {
                    throw new Exception("Please input a valid character (b, f, s or m)");
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("[B]Battle   [F]Food   [S]Stats   [M]Main Menu");
                input = scan.nextLine();
            }
        }
        switch (input) {
            case "b":
            case "B":
                battleMenu();
                break;
            case "f":
            case "F":
                foodMenu();
                break;
            case "s":
            case "S":
                System.out.println(this.getPet().toString());
                break;
            case "m":
            case "M":
                this.setGameState(false);
                break;
        }
    }

    /**
     * Method that loops until user inputs the run command. Creates an Enemy
     * object and modifies the Pet object as long as the user inputs the fight
     * command.
     */
    public void battleMenu() {
        Boolean fight = true;
        if (getPet().getSatiety() == 0) {
            System.out.println(getPet().getName() + " is too hungry to fight.");
            fight = false;
        }
        while (fight) { //start loop
            switch (getPet().getLevel()) { //creates new Enemy object depending on level member of Pet object
                case 1:
                case 2:
                    enemy = new WeakEnemy(pet); //declaration with parent object
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    enemy = new AverageEnemy(pet); //declaration with parent object
                    break;
                case 7:
                case 8:
                case 9:
                    enemy = new StrongEnemy(pet); //declaration with parent object
                    break;
            }
            System.out.println("\n" + enemy.getName() + " has appeared!");
            System.out.println(this.getPet().getName() + " Lv: " + this.getPet().getLevel() + " HP: " + this.getPet().getHp() + "/" + this.getPet().getHpMax());
            System.out.println("[F]Fight   [R]Run");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            while (!input.equalsIgnoreCase("f") && !input.equalsIgnoreCase("r")) //validates input
            {
                try {
                    if (input.length() < 1 || input.length() > 1) {
                        throw new Exception("Input a single character only");
                    }
                    if (!input.equalsIgnoreCase("f") && !input.equalsIgnoreCase("r")) {
                        throw new Exception("Please input a valid character (f or r)");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("[F]Fight   [R]Run");
                    input = scan.nextLine();
                }
            }
            if (input.equalsIgnoreCase("f")) {
                enemy.battle(this.getPet());
                if (getPet().getLevel() == getPet().getLevelMax()) {
                    if (getPet().getBattleCount() > (getPet().getFoodCount() * 3)) {
                        setMessage("A courageous champion has reached the peak of brilliance. With the defeat of " + enemy.getName() + ", you've helped " + getPet().getName() + " reached their goal.");
                    } else {
                        setMessage("A good warrior knows balance. Congratuations " + getPet().getName() + " on reaching the end of your journey!");
                    }
                    endGame(getMessage());
                    fight = false;
                }
                if (getPet().getHp() == 0) {
                    setMessage("A brave young warrior has died too soon. Farewell " + getPet().getName() + " and congratulations " + enemy.getName() + ".");
                    endGame(getMessage());
                    fight = false;
                }
            } else {
                System.out.println("You ran away...");
                fight = false; //ends loop and exits method
            }
        }
    }

    /**
     * Method that loops until the user inputs the exit menu command. Modifies
     * Pet object based on Food enum.
     */
    public void foodMenu() {
        Boolean eat = true;
        while (eat) { //start loop
            System.out.println("[1]Apple   [2]Steak   [3]Burger   [4]Cordyceps   [5]Mushroom   [6]Bitter Powder   [7]Exit Menu");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            int parse;
            while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5") && !input.equals("6") && !input.equals("7")) {
                try {
                    parse = Integer.parseInt(input);
                    if (parse < 0 || parse > 7) {
                        throw new Exception("Input a number from 1 to 7.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input a number from 1 to 7.");
                    System.out.println("[1]Apple   [2]Steak   [3]Burger   [4]Cordyceps   [5]Mushroom   [6]Bitter Powder   [7]Exit Menu");
                    input = scan.nextLine();
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("[1]Apple   [2]Steak   [3]Burger   [4]Cordyceps   [5]Mushroom   [6]Bitter Powder   [7]Exit Menu");
                    input = scan.nextLine();
                }
            }
            parse = Integer.parseInt(input);
            switch (parse) {
                case 1:
                    getPet().eat(Food.APPLE);
                    break;
                case 2:
                    getPet().eat(Food.STEAK);
                    break;
                case 3:
                    getPet().eat(Food.BURGER);
                    break;
                case 4:
                    getPet().eat(Food.CORDYCEPS);
                    if (getPet().getCordycepsCount() >= 3) {
                        setMessage("\"... must... get... higher...\" " + getPet().getName() + " has died by eating cordyceps. What a way to go.");
                        endGame(getMessage());
                        eat = false;
                    }
                    break;
                case 5:
                    getPet().eat(Food.MUSHROOM);
                    if (getPet().getHp() == 0) {
                        setMessage("The Beginner's Guide to Identifying Mushrooms doesn't help those who can't see. " + getPet().getName() + " has died by eating a false morel. Unlucky.");
                        endGame(getMessage());
                        eat = false;
                    }
                    break;
                case 6:
                    getPet().eat(Food.BITTERPOWDER); //ends loop and exits method
                    break;
                case 7:
                    eat = false; //eends loop and exits method
                    break;
            }
        }
    }

    /**
     * Ends the game and saves up to ten records of the pet to the
     * petrecords.txt file. Removes the earliest record if file exceeds nine
     * records. Prints the current record to the console then deletes the
     * current pet.
     *
     * @param message
     */
    public void endGame(String message) {
        /*List<String> records = new LinkedList<>();
        try ( Scanner s = new Scanner(new FileReader("petrecords.txt"))) {
            while (s.hasNext()) { //Adds every nine lines of the petrecords.txt file to a node of a LinkedList
                records.add(s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine() + "\n" + s.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("File can't be read.");
        }
        while (records.size() > 9) { //Removes the earliest element of the List so that it never exceeds ten records. A record consists of six lines.
            records.remove(0);
        }
        records.add(getPet().toString() + "\n" + message); //adds message to record
        PrintWriter pw;
        try { //Updates the petrecords.txt file.
            pw = new PrintWriter(new FileOutputStream("petrecords.txt"));
            for (int i = 0; i < records.size(); i++) {
                pw.println(records.get(i));
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }*/

        PetData data = new PetData();

        try {
            data.statement.executeUpdate("INSERT INTO PETRECORD VALUES (" + "'" + pet.getName()
                    + "' ," + pet.getLevel() + "," + pet.getHpMax() + "," + pet.getHp() + ","
                    + pet.getHappy() + "," + pet.getExp() + "," + pet.getSatiety() + ","
                    + pet.getFoodCount() + "," + pet.getBattleCount() + "," + pet.getLegendCount() + ",'"
                    + message + "')");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(message);
        try { //A delay to allow users to read the console.
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Can't sleep");
        }
        System.out.println("Final stats:");
        System.out.println(getPet().toString());
        pet.setGameOver(true);
        pet.update();
        //getPet().deletePet();
        try { //A delay to allow users to read the console.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Can't sleep");
        }
        System.out.println("Try again?");
        try { //A delay to allow users to read the console.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Can't sleep");
        }
        setGameState(false);
    }

    /**
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * @return the gameState
     */
    public Boolean getGameState() {
        return gameState;
    }

    /**
     * Set to false to return to main method.
     *
     * @param gameState the gameState to set
     */
    public void setGameState(Boolean gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the message
     */
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
