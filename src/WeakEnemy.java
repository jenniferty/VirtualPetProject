
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Child class of abstract Enemy class. Object is initialised using the
 * weakenemy.txt file.
 *
 * @author Jennifer Ty 15903786
 */
public class WeakEnemy extends Enemy {

    /**
     * Constructor for WeakEnemy class. Calls the initialise() method to
     * populate the members of the class.
     */
    public WeakEnemy(Pet pet) {
        this.pet=pet;
        this.happyWin = 2;
        this.satietyWin = -2;
        initialise();
    }

    /**
     * Overrides the abstract method. Opens the weakenemy.txt file and adds each
     * line as an element of an ArrayList.
     *
     * @return an ArrayList that is used to initialise an WeakEnemy object.
     */
   // @Override
    /*protected ArrayList<String> getList() {
        List<String> enemyList = new ArrayList<>();
        try ( Scanner s = new Scanner(new FileReader("weakenemy.txt"))) {
            while (s.hasNext()) {
                enemyList.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("File can't be read.");
        }
        return (ArrayList) enemyList;
    }*/
}
