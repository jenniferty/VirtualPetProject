
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jennifer Ty 15903786
 */
public class Control implements ActionListener {

    private View view;
    private Model model;
    private Pet pet;
    private Enemy enemy;

    /**
     * Constructor for control.
     *
     * @param view
     * @param model
     */
    public Control(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
    }

    /**
     * ActionListener for buttons in View.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //gets the button labels
        switch (command) {
            case "New Game": //switches to "new" card
                this.getView().getNameInput().setText(""); //resets the textfield
                this.getView().getCard().show(this.getView().getPagePanel(), "new");
                break;
            case "Continue": //switches to "game menu" card
                setPet(new Pet()); //initialise pet object with values from currentpet database
                this.getView().getCard().show(this.getView().getPagePanel(), "game menu");
                break;
            case "Records": //switches to "record" card
                String record = getModel().viewRecord(); //gets text from petrecord database
                getView().getOutputRecord().setText(record); //sets the jtextpane with record
                this.getView().getCard().show(this.getView().getPagePanel(), "record");
                break;
            case "Quit": //exits main method
                System.exit(0);
                break;
            case "OK"://validates jtextfield 1-8 characters
                String name = getView().getNameInput().getText();
                getView().getOutputNew().setText("");//resets jtextpane
                if (name.trim().isEmpty()) {//check if jtextfield is empty or is only whitespaces
                    getView().getOutputNew().setText("Your pet's name can't be blank!");
                } else if (name.length() > 8) {
                    getView().getOutputNew().setText("Your pet's name can't be greater than 8 characters!");
                } else { //initialise pet object with name and changes view if valid
                    setPet(new Pet(name));
                    this.getView().getCard().show(getView().getPagePanel(), "game menu");
                }
                break;
            case "Battle": //switch to "battle" card
                String str;
                if (getPet().getSatiety() == 0) {//disables fight button if true
                    str = getPet().getName() + " is too hungry to fight.";
                    this.getView().getFightButton().setEnabled(false);
                    this.getView().getOutputBattle().setText(str);
                    this.getView().getCard().show(getView().getPagePanel(), "battle");
                    break;
                }
                this.getView().getFightButton().setEnabled(true);//reenables fight button if satiety>0
                str = this.getModel().battleMenu(); //generates a random enemy
                this.getView().getOutputBattle().setText(str);
                this.getView().getCard().show(this.getView().getPagePanel(), "battle");
                break;
            case "Food"://switches to "food" card
                this.getView().getOutputFood().setText("");//resets jtextpane
                this.getView().getCard().show(this.getView().getPagePanel(), "food");
                break;
            case "Stats"://switches to "records" card
                getView().getOutputStat().setText(getPet().toString());//prints last 10 entrys in petrecord database
                this.getView().getCard().show(this.getView().getPagePanel(), "stats");
                break;
            case "Main Menu"://switches to "main" card
                if(getPet().canContinue()){//validates if continue button can be pressed
                    this.getView().getContinueButton().setEnabled(true);
                }
                this.getView().getCard().show(this.getView().getPagePanel(), "main");
                break;
            case "Fight": //switches to "battle2" card
                String fight = this.getModel().getEnemy().battle(getPet()); //calculates stat changes
                this.getView().getOutputBattle2().setText(fight);
                this.getView().getCard().show(this.getView().getPagePanel(), "battle2");
                break;
            case "Run"://returns to "game menu" card
                this.getView().getCard().show(this.getView().getPagePanel(), "game menu");
                break;
            case "Eat"://button that calculates stat changes after eating
                String food = getView().getFoodBox().getSelectedItem().toString();
                Food eat = getModel().getFood(food);
                this.getView().getOutputFood().setText(getPet().eat(eat));
                if (getPet().getCordycepsCount() >= 3) {//ends game if true
                    String end = getPet().endGame(getPet().getMessage());
                    this.getView().getOutputEnd().setText(end);
                    this.getView().getCard().show(this.getView().getPagePanel(), "end");
                }
                if (getPet().getHp() <= 0) {//ends game if true
                    String end = getPet().endGame(getPet().getMessage());
                    this.getView().getOutputEnd().setText(end);
                    this.getView().getCard().show(this.getView().getPagePanel(), "end");
                }
                break;
            case "Back"://return to "game menu" card
                this.getView().getCard().show(this.getView().getPagePanel(), "game menu");
                break;
            case "Try Again"://returns to "main" card and disables continue button
                this.view.getContinueButton().setEnabled(false);
                this.getView().getCard().show(this.getView().getPagePanel(), "main");
                break;
            case "Next"://returns to "battle" card
                String next = this.getModel().battleMenu();
                if (getPet().getHp() == 0) {///ends game if true
                    getPet().setMessage("A brave young warrior has died too soon. Farewell " + getPet().getName() + " and congratulations " + this.getModel().getEnemy().getName() + ".");
                    String end = getPet().endGame(getPet().getMessage());
                    this.getView().getOutputEnd().setText(end);
                    this.getView().getCard().show(this.getView().getPagePanel(), "end");
                    break;
                } else if (getPet().getLevel() == getPet().getLevelMax()) {
                    if (getPet().getBattleCount() > (getPet().getFoodCount() * 3)) {
                        getPet().setMessage("A courageous champion has reached the peak of brilliance. With the defeat of " + this.getModel().getEnemy().getName() + ", you've helped " + getPet().getName() + " reached their goal.");
                        String end = getPet().endGame(getPet().getMessage());
                        this.getView().getOutputEnd().setText(end);
                        this.getView().getCard().show(this.getView().getPagePanel(), "end");
                        break;
                    } else {
                        getPet().setMessage("A good warrior knows balance. Congratuations " + getPet().getName() + " on reaching the end of your journey!");
                        String end = getPet().endGame(getPet().getMessage());
                        this.getView().getOutputEnd().setText(end);
                        this.getView().getCard().show(this.getView().getPagePanel(), "end");
                        break;
                    }
                } else if (getPet().getSatiety() == 0) {//returns to "battle" card while disabling fight button
                    str = getPet().getName() + " is too hungry to fight.";
                    this.getView().getFightButton().setEnabled(false);
                    this.getView().getOutputBattle().setText(str);
                    this.getView().getCard().show(getView().getPagePanel(), "battle");
                    break;
                }
                this.getView().getOutputBattle().setText(next);
                this.getView().getCard().show(this.getView().getPagePanel(), "battle");
                break;
        }
    }

    /**
     * @return the view
     */
    public View getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * @return the model
     */
    public Model getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * @param pet the pet to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * @param enemy the enemy to set
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
