
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Child class of abstract Enemy class. Object is initialised using the
 * averageenemy.txt file.
 *
 * @author Jennifer Ty 15903786
 */
public class AverageEnemy extends Enemy {

    /**
     * Constructor for AverageEnemy class. Calls the initialise() method to
     * populate the members of the class.
     */
    public AverageEnemy(Pet pet) {
        this.pet = pet;
        this.happyWin = 5;
        this.satietyWin = -4;
        initialise();
    }

    /**
     * Overrides the abstract method. Opens the averageenemy.txt file and adds
     * each line as an element of an ArrayList.
     *
     * @return an ArrayList that is used to initialise an AverageEnemy object.
     */
    /*@Override
    protected ArrayList<String> getList() {
        ArrayList<String> enemyList = new ArrayList<>();
        try ( Scanner s = new Scanner(new FileReader("averageenemy.txt"))) {
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
}
