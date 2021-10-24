
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jenni
 */
public class View extends JFrame implements Observer {

    public CardLayout card =new CardLayout();
    public JPanel pagePanel;
    public JPanel mainMenuPanel, gameMenuPanel, newGamePanel, recordsMenuPanel, battleMenuPanel, foodMenuPanel, statsMenuPanel;
    private JLabel petName = new JLabel("Enter your pet's name (8 char):");
    private JTextField nameInput = new JTextField(8);
    private JButton nameButton = new JButton("OK");
    private JButton newGameButton = new JButton("New Game");
    private JButton continueButton = new JButton("Continue");
    private JButton viewRecordsButton = new JButton("Records");
    private JButton quitButton = new JButton("Quit");
    private JButton battleButton = new JButton("Battle");
    private JButton fightButton = new JButton("Fight");
    private JButton runButton = new JButton("Run");
    private JButton foodButton = new JButton("Food");
    private JButton eatButton = new JButton("Eat");
    private JButton backButton = new JButton("Back");
    private JButton viewStatsButton = new JButton("Stats");
    private JButton mainMenuButton = new JButton("Main Menu");
    private JLabel foodLabel = new JLabel("Food:");
    private String[] food = new String[6];
    private JComboBox foodBox = new JComboBox(food);
    private ImageIcon image;

    public View() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(630, 500);
        //this.card = new CardLayout();
        this.pagePanel = new JPanel(card);
        this.mainMenuPanel = mainMenu();
        this.newGamePanel = newGame();
        this.recordsMenuPanel = recordsMenu();
        this.gameMenuPanel = gameMenu();
        this.battleMenuPanel = battleMenu();
        this.foodMenuPanel = foodMenu();
        this.statsMenuPanel = statsMenu();
        this.pagePanel.add(mainMenuPanel, "main");
        this.pagePanel.add(newGamePanel, "new");
        this.pagePanel.add(recordsMenuPanel, "record");
        this.pagePanel.add(gameMenuPanel, "game menu");
        this.pagePanel.add(battleMenuPanel, "battle");
        this.pagePanel.add(foodMenuPanel, "food");
        this.pagePanel.add(statsMenuPanel, "stats");
        this.add(pagePanel);
        this.add(newGamePanel);
        this.add(recordsMenuPanel);
        this.add(gameMenuPanel);
        this.add(battleMenuPanel);
        this.add(foodMenuPanel);
        this.add(statsMenuPanel);
        this.add(mainMenuPanel);
        this.setVisible(true);
    }
    
    public JPanel mainMenu(){
        JPanel main = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        JPanel button = new JPanel();
        String message = this.titleScreen();
        this.image = new ImageIcon(new ImageIcon("title.png").getImage().getScaledInstance(600, 350, Image.SCALE_DEFAULT));
        output.insertIcon(image);
        output.setEditable(false);
        outputPanel.add(output);
        outputPanel.setSize(600, 350);
        main.add(outputPanel, BorderLayout.NORTH);
        button.add(newGameButton);
        button.add(continueButton);
        button.add(viewRecordsButton);
        button.add(quitButton);
        main.add(button, BorderLayout.SOUTH);
        outputPanel.setVisible(true);

        return main;
    }

    public JPanel newGame() {
        JPanel newgame = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        newgame.add(petName);
        newgame.add(nameInput);
        newgame.add(nameButton);
        newgame.add(mainMenuButton);
        outputPanel.setSize(600, 350);
        //this.getContentPane().removeAll();
        newgame.setVisible(true);
        outputPanel.setVisible(true);
        //newgame.add(newGamePanel, BorderLayout.SOUTH);
        newgame.add(outputPanel, BorderLayout.NORTH);
        newgame.setVisible(true);
        
        return newgame;
    }

    public JPanel gameMenu() {
        
        JPanel start = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        start.add(battleButton);
        start.add(foodButton);
        start.add(viewStatsButton);
        start.add(mainMenuButton);
        outputPanel.setSize(600, 350);
        //this.getContentPane().removeAll();
        start.setVisible(true);
        outputPanel.setVisible(true);
        //start.add(gameMenuPanel, BorderLayout.SOUTH);
        start.add(outputPanel, BorderLayout.NORTH);
        start.setVisible(true);
        
        return start;
    }
    
    public JPanel recordsMenu() {
        JPanel records = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        records.add(mainMenuButton);
        outputPanel.setSize(600, 350);
        //this.getContentPane().removeAll();
        outputPanel.setVisible(true);
        //records.add(statsMenuPanel, BorderLayout.SOUTH);
        records.add(outputPanel, BorderLayout.NORTH);
        records.setVisible(true);
        
        return records;
    }

    public JPanel battleMenu() {
        
        JPanel battle = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        battle.add(fightButton);
        battle.add(runButton);
        outputPanel.setSize(600, 350);
        //this.getContentPane().removeAll();
        battle.setVisible(true);
        outputPanel.setVisible(true);
        //battle.add(gameMenuPanel, BorderLayout.SOUTH);
        battle.add(outputPanel, BorderLayout.NORTH);
        battle.setVisible(true);
        
        return battle;
    }

    public JPanel foodMenu() {
        
        JPanel foodPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        outputPanel.setSize(600, 350);
        output.setText("");
        output.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(output);
        outputPanel.setVisible(true);
        foodPanel.add(outputPanel, BorderLayout.NORTH);
        foodPanel.add(foodLabel);
        food[0] = "Apple";
        food[1] = "Steak";
        food[2] = "Burger";
        food[3] = "Cordyceps";
        food[4] = "Mushroom";
        food[5] = "Bitter Powder";
        foodBox = new JComboBox(food);
        foodPanel.add(foodBox);
        foodPanel.add(eatButton);
        foodPanel.setVisible(true);       
        return foodPanel;
    }

    public JPanel statsMenu() {
        JPanel stats = new JPanel();
        JPanel outputPanel = new JPanel();
        JTextPane output = new JTextPane();
        outputPanel.setPreferredSize(new Dimension(600, 350));
        output.setText("");
        output.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(output);
        outputPanel.setVisible(true);
        stats.add(outputPanel, BorderLayout.NORTH);
        stats.add(backButton);
        //stats.setVisible(true);        
        return stats;
    }
    
    public void addActionListener(ActionListener listener){
        this.newGameButton.addActionListener(listener);
        this.continueButton.addActionListener(listener);
        this.viewRecordsButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
        this.nameButton.addActionListener(listener);
        this.battleButton.addActionListener(listener);
        this.foodButton.addActionListener(listener);
        this.viewStatsButton.addActionListener(listener);
        this.mainMenuButton.addActionListener(listener);
        this.fightButton.addActionListener(listener);
        this.runButton.addActionListener(listener);
        this.eatButton.addActionListener(listener);
        this.backButton.addActionListener(listener);
    }

    public String titleScreen() {
        StringBuilder build = new StringBuilder();
        String line;
        try { //title screen
            FileReader fr = new FileReader("title.txt");
            BufferedReader inputStream = new BufferedReader(fr);
            while ((line = inputStream.readLine()) != null) {
                build.append(line);
                build.append("\n");
            };
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading from file ");
        }
        return build.toString();
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
