
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Child class of abstract Enemy class. Object is initialised using the
 * weakenemy table.
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
}
