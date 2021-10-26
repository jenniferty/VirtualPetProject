
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jenni
 */
public class Control implements ActionListener {

    private View view;
    private Model model;
    private Pet pet;
    private Enemy enemy;

    public Control(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New Game":
                this.getView().getNameInput().setText("");
                this.getView().getCard().show(this.getView().getPagePanel(), "new");
                break;
            case "Continue":
                setPet(new Pet());
                this.getView().getCard().show(this.getView().getPagePanel(), "game menu");
                break;
            case "Records":
                String record = getModel().viewRecord();
                getView().getOutputRecord().setText(record);
                this.getView().getCard().show(this.getView().getPagePanel(), "record");
                break;
            case "Quit":
                System.exit(0);
                break;
            case "OK":
                String name = getView().getNameInput().getText();
                if (name.length()==0) {
                    getView().getOutputNew().setText("Your pet's name can't be blank!");
                } else if (name.length() > 8) {
                    getView().getOutputNew().setText("Your pet's name can't be greater than 8 characters!");
                } else {
                    setPet(new Pet(name));
                    this.getView().getCard().show(getView().getPagePanel(), "game menu");
                }
                break;
            case "Battle":
                String str;
                if (getPet().getSatiety() == 0) {
                    str = getPet().getName() + " is too hungry to fight.";
                    this.getView().getOutputBattle().setText(str);
                    this.getView().getCard().show(getView().getPagePanel(), "battle");
                    break;
                }
                str = this.getModel().battleMenu();
                this.getView().getOutputBattle().setText(str);
                this.getView().getCard().show(this.getView().getPagePanel(), "battle");
                break;
            case "Food":
                this.getView().getOutputFood().setText("");
                this.getView().getCard().show(this.getView().getPagePanel(), "food");
                break;
            case "Stats":
                getView().getOutputStat().setText(getPet().toString());
                this.getView().getCard().show(this.getView().getPagePanel(), "stats");
                break;
            case "Main Menu":
                this.getView().getCard().show(this.getView().getPagePanel(), "main");
                break;
            case "Fight":
                String fight = this.getModel().getEnemy().battle(getPet());
                this.getView().getOutputBattle2().setText(fight);
                if (getPet().getLevel() == getPet().getLevelMax()) {
                    if (getPet().getBattleCount() > (getPet().getFoodCount() * 3)) {
                        getPet().setMessage("A courageous champion has reached the peak of brilliance. With the defeat of " + this.getModel().getEnemy().getName() + ", you've helped " + getPet().getName() + " reached their goal.");
                        String end = getPet().endGame(getPet().getMessage());
                        this.getView().getOutputEnd().setText(end);
                        this.getView().getCard().show(this.getView().getPagePanel(), "end");
                    } else {
                        getPet().setMessage("A good warrior knows balance. Congratuations " + getPet().getName() + " on reaching the end of your journey!");
                        String end = getPet().endGame(getPet().getMessage());
                        this.getView().getOutputEnd().setText(end);
                        this.getView().getCard().show(this.getView().getPagePanel(), "end");
                    }
                } else if (getPet().getHp() == 0) {
                    getPet().setMessage("A brave young warrior has died too soon. Farewell " + getPet().getName() + " and congratulations " + this.getModel().getEnemy().getName() + ".");

                    String end = getPet().endGame(getPet().getMessage());
                    this.getView().getOutputEnd().setText(end);
                    this.getView().getCard().show(this.getView().getPagePanel(), "end");
                } else if (getPet().getSatiety() == 0) {
                    str = getPet().getName() + " is too hungry to fight.";
                    this.getView().getOutputBattle().setText(str);
                    this.getView().getCard().show(getView().getPagePanel(), "battle");
                    break;
                } else {
                    this.getView().getCard().show(this.getView().getPagePanel(), "battle2");
                }
                break;
            case "Run":
                this.getView().getCard().show(this.getView().getPagePanel(), "game menu");
                break;
            case "Eat":
                String food = getView().getFoodBox().getSelectedItem().toString();
                Food eat = getModel().getFood(food);
                this.getView().getOutputFood().setText(getPet().eat(eat));
                if (getPet().getCordycepsCount() >= 3) {
                    String end = getPet().endGame(getPet().getMessage());
                    this.getView().getOutputEnd().setText(end);
                    this.getView().getCard().show(this.getView().getPagePanel(), "end");
                }
                if (getPet().getHp() <= 0) {
                    String end = getPet().endGame(getPet().getMessage());
                    this.getView().getOutputEnd().setText(end);
                    this.getView().getCard().show(this.getView().getPagePanel(), "end");
                }
                break;
            case "Back":
                this.getView().getCard().show(this.getView().getPagePanel(), "game menu");
                break;
            case "Try Again":
                this.getView().getCard().show(this.getView().getPagePanel(), "main");
                break;
            case "Next":
                String next = this.getModel().battleMenu();
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
