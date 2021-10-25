
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

    public CardLayout card = new CardLayout();
    public JPanel pagePanel;
    public JPanel mainMenuPanel, gameMenuPanel, newGamePanel, recordsMenuPanel, battleMenuPanel, battleMenuPanel2, foodMenuPanel, statsMenuPanel, endMenuPanel;
    public JTextPane outputMain, outputGame, outputNew, outputRecord, outputBattle, outputBattle2, outputFood, outputStat, outputEnd;
    private JLabel petName = new JLabel("Enter your pet's name (8 char):");
    public JTextField nameInput = new JTextField(8);
    private JButton nameButton = new JButton("OK");
    private JButton newGameButton = new JButton("New Game");
    public JButton continueButton = new JButton("Continue");
    private JButton viewRecordsButton = new JButton("Records");
    private JButton quitButton = new JButton("Quit");
    private JButton quitButton2 = new JButton("Quit");
    private JButton battleButton = new JButton("Battle");
    private JButton fightButton = new JButton("Fight");
    private JButton runButton = new JButton("Run");
    private JButton foodButton = new JButton("Food");
    private JButton eatButton = new JButton("Eat");
    private JButton backButton = new JButton("Back");
    private JButton backButton2 = new JButton("Back");
    private JButton viewStatsButton = new JButton("Stats");
    private JButton mainMenuButton = new JButton("Main Menu");
    private JButton mainMenuButton2 = new JButton("Main Menu");
    private JButton mainMenuButton3 = new JButton("Main Menu");
    private JButton tryAgainButton = new JButton("Try Again");
    private JButton nextButton = new JButton("Next");
    private JLabel foodLabel = new JLabel("Food:");
    private String[] food = new String[6];
    public JComboBox foodBox = new JComboBox(food);
    private ImageIcon image;

    Pet pet;

    public View() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(630, 500);
        this.pagePanel = new JPanel(card);
        this.mainMenuPanel = mainMenu();
        this.newGamePanel = newGame();
        this.recordsMenuPanel = recordsMenu();
        this.gameMenuPanel = gameMenu();
        this.battleMenuPanel = battleMenu();
        this.battleMenuPanel2 = battleMenu2();
        this.foodMenuPanel = foodMenu();
        this.statsMenuPanel = statsMenu();
        this.endMenuPanel = endMenu();
        this.pagePanel.add(mainMenuPanel, "main");
        this.pagePanel.add(newGamePanel, "new");
        this.pagePanel.add(recordsMenuPanel, "record");
        this.pagePanel.add(gameMenuPanel, "game menu");
        this.pagePanel.add(battleMenuPanel, "battle");
        this.pagePanel.add(battleMenuPanel2, "battle2");
        this.pagePanel.add(foodMenuPanel, "food");
        this.pagePanel.add(statsMenuPanel, "stats");
        this.pagePanel.add(endMenuPanel, "end");
        this.add(pagePanel);
        this.setVisible(true);
        this.card.show(pagePanel, "main");
    }

    public JPanel mainMenu() {
        JPanel main = new JPanel();
        JPanel outputPanel = new JPanel();
        outputMain = new JTextPane();
        String message = this.titleScreen();
        this.image = new ImageIcon(new ImageIcon("title.png").getImage().getScaledInstance(600, 350, Image.SCALE_DEFAULT));
        outputMain.insertIcon(image);
        outputMain.setEditable(false);
        outputPanel.add(outputMain);
        outputPanel.setSize(600, 350);
        main.add(outputPanel, BorderLayout.NORTH);
        main.add(newGameButton);
        main.add(continueButton);
        Boolean valid = !validateSave();
        continueButton.setEnabled(valid);
        main.add(viewRecordsButton);
        main.add(quitButton);
        return main;
    }

    public JPanel newGame() {
        JPanel newgame = new JPanel();
        JPanel outputPanel = new JPanel();
        outputNew = new JTextPane();
        outputPanel.setSize(600, 350);
        outputNew.setText("");
        outputNew.setEditable(false);
        outputNew.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputNew);
        newgame.add(outputPanel, BorderLayout.NORTH);
        newgame.add(petName);
        newgame.add(nameInput);
        newgame.add(nameButton);
        newgame.add(mainMenuButton);
        return newgame;
    }

    public JPanel gameMenu() {
        JPanel start = new JPanel();
        JPanel outputPanel = new JPanel();
        outputGame = new JTextPane();
        outputPanel.setSize(600, 350);
        outputGame.setText("");
        outputGame.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputGame);
        start.add(outputPanel, BorderLayout.NORTH);
        start.add(battleButton);
        start.add(foodButton);
        start.add(viewStatsButton);
        start.add(mainMenuButton2);
        return start;
    }

    public JPanel recordsMenu() {
        JPanel records = new JPanel();
        outputRecord = new JTextPane();
        JScrollPane outputPanel = new JScrollPane(outputRecord);
        outputPanel.setPreferredSize(new Dimension(600, 350));
        //outputRecord.setPreferredSize(new Dimension(600, 350));
        records.add(outputPanel, BorderLayout.NORTH);
        records.add(mainMenuButton3);
        return records;
    }

    public JPanel battleMenu() {
        JPanel battle = new JPanel();
        JPanel outputPanel = new JPanel();
        outputBattle = new JTextPane();
        outputPanel.setSize(600, 350);
        outputBattle.setText("");
        outputBattle.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputBattle);
        battle.add(outputPanel, BorderLayout.NORTH);
        battle.add(fightButton);
        battle.add(runButton);
        return battle;
    }
    
    public JPanel battleMenu2() {
        JPanel battle = new JPanel();
        JPanel outputPanel = new JPanel();
        outputBattle2 = new JTextPane();
        outputPanel.setSize(600, 350);
        outputBattle2.setText("");
        outputBattle2.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputBattle2);
        battle.add(outputPanel, BorderLayout.NORTH);
        battle.add(nextButton);
        return battle;
    }

    public JPanel foodMenu() {
        JPanel foodPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        outputFood = new JTextPane();
        outputPanel.setSize(600, 350);
        outputFood.setText("");
        outputFood.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputFood);
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
        foodPanel.add(backButton2);
        return foodPanel;
    }

    public JPanel statsMenu() {
        JPanel stats = new JPanel();
        JPanel outputPanel = new JPanel();
        outputStat = new JTextPane();
        outputPanel.setPreferredSize(new Dimension(600, 350));
        outputStat.setText("");
        outputStat.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputStat);
        outputPanel.setVisible(true);
        stats.add(outputPanel, BorderLayout.NORTH);
        stats.add(backButton);
        return stats;
    }

    public JPanel endMenu() {
        JPanel end = new JPanel();
        JPanel outputPanel = new JPanel();
        outputEnd = new JTextPane();
        outputPanel.setPreferredSize(new Dimension(600, 350));
        outputEnd.setText("");
        outputEnd.setPreferredSize(new Dimension(600, 350));
        outputPanel.add(outputEnd);
        outputPanel.setVisible(true);
        end.add(outputPanel, BorderLayout.NORTH);
        end.add(tryAgainButton);
        end.add(quitButton2);
        return end;
    }

    public void addActionListener(ActionListener listener) {
        this.newGameButton.addActionListener(listener);
        this.continueButton.addActionListener(listener);
        this.viewRecordsButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
        this.quitButton2.addActionListener(listener);
        this.nameButton.addActionListener(listener);
        this.battleButton.addActionListener(listener);
        this.foodButton.addActionListener(listener);
        this.viewStatsButton.addActionListener(listener);
        this.mainMenuButton.addActionListener(listener);
        this.mainMenuButton2.addActionListener(listener);
        this.mainMenuButton3.addActionListener(listener);
        this.fightButton.addActionListener(listener);
        this.runButton.addActionListener(listener);
        this.eatButton.addActionListener(listener);
        this.backButton.addActionListener(listener);
        this.backButton2.addActionListener(listener);
        this.tryAgainButton.addActionListener(listener);
        this.nextButton.addActionListener(listener);
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
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading from file ");
        }
        return build.toString();
    }

    public Boolean validateSave() {
        Data data = new Data();
        ResultSet rs = null;
        Boolean canContinue = null;
        Boolean valid = null;
        try {
            rs = data.statement.executeQuery("SELECT * FROM CURRENTPET");
            if (rs.next() == false) {
                return true;
            }
            valid = validateCurrentPet(rs);
            if (!valid) {
                return true;
            }
            canContinue = rs.getBoolean("GAMEOVER");//true if game=over
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return canContinue;
    }

    public Boolean validateCurrentPet(ResultSet rs) {
        try {
            if (rs.getString("NAME").length() > 8 || rs.getString("NAME").length() < 1) {
                return false;
            }
            if (rs.getInt("LEVEL") < 1 || rs.getInt("LEVEL") > 9) {
                return false;
            }
            if (rs.getInt("HP") < 1 || rs.getInt("HP") > (100 + ((rs.getInt("LEVEL") - 1) * 10))) {
                return false;
            }
            if (rs.getInt("HAPPY") < 0 || rs.getInt("HAPPY") > 100) {
                return false;
            }
            if (rs.getInt("EXP") < 0 || rs.getInt("EXP") >= 900) {
                return false;
            }
            if (rs.getInt("SATIETY") < 0 || rs.getInt("SATIETY") > 100) {
                return false;
            }
            if (rs.getInt("CORDYCEPS") < 0 || rs.getInt("CORDYCEPS") > 2) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
