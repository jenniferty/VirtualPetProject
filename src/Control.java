
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

    public View view;
    public Model model;
    public Pet pet;
    public Enemy enemy;

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
                this.view.card.show(this.view.pagePanel, "new");
                break;
            case "Continue":
                pet = new Pet();
                this.view.card.show(this.view.pagePanel, "game menu");
                break;
            case "Records":
                String record = model.viewRecord();
                view.outputRecord.setText(record);
                this.view.card.show(this.view.pagePanel, "record");
                break;
            case "Quit":
                System.exit(0);
                break;
            case "OK":
                String name = view.nameInput.getText();
                if (name == null) {
                    view.outputNew.setText("Your pet's name can't be blank!");
                } else if (name.length() > 8) {
                    view.outputNew.setText("Your pet's name can't be greater than 8 characters!");
                } else {
                    pet = new Pet(name);
                    this.view.card.show(view.pagePanel, "game menu");
                }
                break;
            case "Battle":
                String str;
                if (pet.getSatiety() == 0) {
                    str = pet.getName() + " is too hungry to fight.";
                    this.view.outputBattle.setText(str);
                    this.view.card.show(view.pagePanel, "battle");
                    break;
                }
                str = this.model.battleMenu();
                this.view.outputBattle.setText(str);
                this.view.card.show(this.view.pagePanel, "battle");
                break;
            case "Food":
                this.view.outputFood.setText("");
                this.view.card.show(this.view.pagePanel, "food");
                break;
            case "Stats":
                view.outputStat.setText(pet.toString());
                this.view.card.show(this.view.pagePanel, "stats");
                break;
            case "Main Menu":
                this.view.card.show(this.view.pagePanel, "main");
                break;
            case "Fight":
                String fight = this.model.enemy.battle(pet);
                this.view.outputBattle2.setText(fight);
                if (pet.getLevel() == pet.getLevelMax()) {
                    if (pet.getBattleCount() > (pet.getFoodCount() * 3)) {
                        pet.setMessage("A courageous champion has reached the peak of brilliance. With the defeat of " + this.model.enemy.getName() + ", you've helped " + pet.getName() + " reached their goal.");
                        String end = pet.endGame(pet.getMessage());
                        this.view.outputEnd.setText(end);
                        this.view.card.show(this.view.pagePanel, "end");
                    } else {
                        pet.setMessage("A good warrior knows balance. Congratuations " + pet.getName() + " on reaching the end of your journey!");
                        String end = pet.endGame(pet.getMessage());
                        this.view.outputEnd.setText(end);
                        this.view.card.show(this.view.pagePanel, "end");
                    }
                } else if (pet.getHp() == 0) {
                    pet.setMessage("A brave young warrior has died too soon. Farewell " + pet.getName() + " and congratulations " + this.model.enemy.getName() + ".");

                    String end = pet.endGame(pet.getMessage());
                    this.view.outputEnd.setText(end);
                    this.view.card.show(this.view.pagePanel, "end");
                } else if (pet.getSatiety() == 0) {
                    str = pet.getName() + " is too hungry to fight.";
                    this.view.outputBattle.setText(str);
                    this.view.card.show(view.pagePanel, "battle");
                    break;
                } else {
                    this.view.card.show(this.view.pagePanel, "battle2");
                }
                break;
            case "Run":
                this.view.card.show(this.view.pagePanel, "game menu");
                break;
            case "Eat":
                String food = view.foodBox.getSelectedItem().toString();
                Food eat = model.getFood(food);
                this.view.outputFood.setText(pet.eat(eat));
                if (pet.getCordycepsCount() >= 3) {
                    String end = pet.endGame(pet.getMessage());
                    this.view.outputEnd.setText(end);
                    this.view.card.show(this.view.pagePanel, "end");
                }
                if (pet.getHp() <= 0) {
                    String end = pet.endGame(pet.getMessage());
                    this.view.outputEnd.setText(end);
                    this.view.card.show(this.view.pagePanel, "end");
                }
                break;
            case "Back":
                this.view.card.show(this.view.pagePanel, "game menu");
                break;
            case "Try Again":
                this.view.card.show(this.view.pagePanel, "main");
                break;
            case "Next":
                String next = this.model.battleMenu();
                this.view.outputBattle.setText(next);
                this.view.card.show(this.view.pagePanel, "battle");
                break;
        }
    }

}
