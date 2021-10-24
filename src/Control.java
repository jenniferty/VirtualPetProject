
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
                break;
            case "Pet Records":
                this.view.card.show(this.view.pagePanel, "record");
                break;
            case "Quit":
                break;
            case "OK":
                break;
            case "Battle":
                this.view.card.show(this.view.pagePanel, "battle");
                break;
            case "Food":
                this.view.card.show(this.view.pagePanel, "food");
                break;
            case "Stats":
                this.view.card.show(this.view.pagePanel, "stats");
                break;
            case "Main Menu":
                this.view.card.show(this.view.pagePanel, "main");
                break;
            case "Fight":
                break;
            case "Run":
                break;
            case "Eat":
                
                break;
            case "Back":
                this.view.card.show(this.view.pagePanel, "game");
                break;
        }
    }

}
