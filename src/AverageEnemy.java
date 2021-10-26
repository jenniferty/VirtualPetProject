/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Child class of abstract Enemy class. Object is initialised using the
 * averageenemy database.
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
}
