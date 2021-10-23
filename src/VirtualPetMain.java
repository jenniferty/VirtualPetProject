
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Main method.
 *
 * @author Jennifer Ty 15903786
 */
public class VirtualPetMain {

    public static Scanner scan = new Scanner(System.in);
    public static Game game;
    public static Pet pet;

    public static void main(String[] args) {
        while (true) { //loops code until user uses the quit command
            try { //title screen
                FileReader fr = new FileReader("title.txt");
                BufferedReader inputStream = new BufferedReader(fr);
                String line = null;
                while ((line = inputStream.readLine()) != null) {
                    System.out.println(line);
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println("Error reading from file ");
            }
            try {
                Thread.sleep(1000); //pauses thread for visual effect
            } catch (InterruptedException e) {
                System.out.println("Can't sleep");
            }
            System.out.println("Welcome to Knock-offmon Iivii Edition\n");
            System.out.println("[N]New Game   [C]Continue   [Q]Quit   [R]Records of previous adventures");
            String input;
            input = scan.nextLine();
            while (!input.equalsIgnoreCase("n")) //validates input
            {
                try {
                    if (input.length() < 1 || input.length() > 1) {
                        throw new Exception("Input a single character only");
                    }
                    if (!input.equalsIgnoreCase("c") && !input.equalsIgnoreCase("q") && !input.equalsIgnoreCase("r")) {
                        throw new Exception("Please input a valid character (n, c, q or r)");
                    }
                    if (input.equalsIgnoreCase("c")) { //statement to prevent further play with an invalid Pet object
                        pet = new Pet();
                        Boolean isValid = pet.canContinue();
                        if (isValid) { //cannot exit try statement if Pet object is not valid
                            throw new Exception("Please create a new pet.");
                        } else {
                            break; //exits the try statement if valid
                        }
                    }
                    if (input.equalsIgnoreCase("q")) //to quit program
                    {
                        System.out.println("See you next time.");
                        System.exit(0);
                    }
                    if (input.equalsIgnoreCase("r")) //view records of previous completed playthroughs
                    {
                        viewRecord();
                        System.out.println("[N]New Game   [C]Continue   [Q]Quit   [R]Records of previous adventures");
                        input = scan.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("[N]New Game   [C]Continue   [Q]Quit   [R]Records of previous adventures");
                    input = scan.nextLine();
                }
            }
            if (input.equalsIgnoreCase("n")) {
                String name;
                System.out.println("Name your Iivii!(8 characters)");
                name = scan.nextLine();
                while (true) {
                    try {
                        if (name.length() < 1 || name.length() > 8) {
                            throw new Exception("Input a name that is 1-8 characters long.");
                        }
                    } catch (Exception e) {
                        System.out.println("Please input a valid response. " + e);
                        name = scan.nextLine();
                    }
                    break;
                }
                pet = new Pet(name); //uses constructor for new pet
            }
            game = new Game(pet); //creates new game object with valid Pet object
            while (game.getGameState()) { //loops method until set false
                game.gameMenu();
            }
            game.setGameState(true); //allows the above statement to run if user inputs continue command
        }
    }

    /**
     * A method that prints the contents of the petrecords.txt file to the
     * console. Prints a message if file is empty.
     */
    static void viewRecord() {
        /*try { //Checks if file is empty. If empty, prints a message.
            FileReader fr = new FileReader("petrecords.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            if (line == null) {
                System.out.println("No records exists. Befriend your pet and complete your own journey today!");
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file.");
        }
        try { //Prints contents of the file to the console if not empty.
            FileReader fr = new FileReader("petrecords.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file.");
        }*/

        ResultSet rs = null;
        PetData record = new PetData();
        try {
            record.statement.setMaxRows(10);
            rs = record.statement.executeQuery("SELECT * FROM PETRECORD");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 0) {
                System.out.println("No records exists. Befriend your pet and complete your own journey today!");
            }
            rs = record.statement.executeQuery("SELECT * FROM PETRECORD");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("NAME") + "\n"
                        + "Level: " + rs.getInt("LEVEL") + "\n"
                        + "HP: " + rs.getInt("HP") + "/" + rs.getInt("HP_MAX") + "\n"
                        + "Happiness: " + rs.getInt("HAPPY") + "/100" + "\n"
                        + "Satiety: " + rs.getInt("SATIETY") + "/100" + "\n"
                        + "Food Eaten: " + rs.getInt("FOOD") + "\n"
                        + "Battles Won: " + rs.getInt("BATTLE") + "\n"
                        + "Legendaries Defeated: " + rs.getInt("LEGEND") + "\n"
                        + rs.getString("MESSAGE") + "\n");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        record.closeConnection();
    }
}
