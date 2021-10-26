
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Class that contains the view
 * @author Jennifer Ty 15903786
 */
public class View extends JFrame implements Observer {

    private final CardLayout card = new CardLayout();
    private final JPanel pagePanel, mainMenuPanel, gameMenuPanel, newGamePanel, recordsMenuPanel, battleMenuPanel, battleMenuPanel2, foodMenuPanel, statsMenuPanel, endMenuPanel;
    private JTextPane outputMain, outputGame, outputNew, outputRecord, outputBattle, outputBattle2, outputFood, outputStat, outputEnd;
    private final JLabel petName = new JLabel("Enter your pet's name (8 char):");
    private JTextField nameInput = new JTextField(8);
    private final JButton nameButton = new JButton("OK");
    private final JButton newGameButton = new JButton("New Game");
    private final JButton continueButton = new JButton("Continue");
    private final JButton viewRecordsButton = new JButton("Records");
    private final JButton quitButton = new JButton("Quit");
    private final JButton quitButton2 = new JButton("Quit");
    private final JButton battleButton = new JButton("Battle");
    private final JButton fightButton = new JButton("Fight");
    private final JButton runButton = new JButton("Run");
    private final JButton foodButton = new JButton("Food");
    private final JButton eatButton = new JButton("Eat");
    private final JButton backButton = new JButton("Back");
    private final JButton backButton2 = new JButton("Back");
    private final JButton viewStatsButton = new JButton("Stats");
    private final JButton mainMenuButton = new JButton("Main Menu");
    private final JButton mainMenuButton2 = new JButton("Main Menu");
    private final JButton mainMenuButton3 = new JButton("Main Menu");
    private final JButton tryAgainButton = new JButton("Try Again");
    private final JButton nextButton = new JButton("Next");
    private final JLabel foodLabel = new JLabel("Food:");
    private String[] food = new String[6];
    private JComboBox foodBox = new JComboBox(food);
    private ImageIcon image;

    /**
     * Constructor for View class. Sets up cards.
     */
    public View() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(630, 500);
        this.pagePanel = new JPanel(getCard());
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

    /**
     * main card
     * @return a JPanel card
     */
    public final JPanel mainMenu() {
        JPanel main = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputMain(new JTextPane());
        this.setImage(new ImageIcon(new ImageIcon("title.png").getImage().getScaledInstance(600, 350, Image.SCALE_DEFAULT)));
        getOutputMain().insertIcon(getImage());
        getOutputMain().setEditable(false);
        outputPanel.add(getOutputMain());
        outputPanel.setSize(600, 350);
        main.add(outputPanel, BorderLayout.NORTH);
        main.add(getNewGameButton());
        main.add(getContinueButton());
        Boolean valid = !validateSave();
        getContinueButton().setEnabled(valid);
        main.add(getViewRecordsButton());
        main.add(getQuitButton());
        return main;
    }

    /**
     * new game card
     * @return a JPanel card
     */
    public final JPanel newGame() {
        JPanel newgame = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputNew(new JTextPane());
        outputPanel.setSize(600, 350);
        getOutputNew().setText("");
        getOutputNew().setEditable(false);
        getOutputNew().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputNew());
        newgame.add(outputPanel, BorderLayout.NORTH);
        newgame.add(getPetName());
        newgame.add(getNameInput());
        newgame.add(getNameButton());
        newgame.add(getMainMenuButton());
        return newgame;
    }

    /**
     * game menu card
     * @return a JPanel card
     */
    public final JPanel gameMenu() {
        JPanel start = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputGame(new JTextPane());
        outputPanel.setSize(600, 350);
        getOutputGame().setText("");
        getOutputGame().setEditable(false);
        getOutputGame().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputGame());
        start.add(outputPanel, BorderLayout.NORTH);
        start.add(getBattleButton());
        start.add(getFoodButton());
        start.add(getViewStatsButton());
        start.add(getMainMenuButton2());
        return start;
    }

    /**
     * records card
     * @return a JPanel card
     */
    public final JPanel recordsMenu() {
        JPanel records = new JPanel();
        setOutputRecord(new JTextPane());
        getOutputRecord().setEditable(false);
        JScrollPane outputPanel = new JScrollPane(getOutputRecord());
        outputPanel.setPreferredSize(new Dimension(600, 350));
        records.add(outputPanel, BorderLayout.NORTH);
        records.add(getMainMenuButton3());
        return records;
    }

    /**
     * battle menu card
     * @return a JPanel card
     */
    public final JPanel battleMenu() {
        JPanel battle = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputBattle(new JTextPane());
        outputPanel.setSize(600, 350);
        getOutputBattle().setText("");
        getOutputBattle().setEditable(false);
        getOutputBattle().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputBattle());
        battle.add(outputPanel, BorderLayout.NORTH);
        battle.add(getFightButton());
        battle.add(getRunButton());
        return battle;
    }
    
    /**
     * second battle menu card
     * @return a JPanel card
     */
    public final JPanel battleMenu2() {
        JPanel battle = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputBattle2(new JTextPane());
        outputPanel.setSize(600, 350);
        getOutputBattle2().setText("");
        getOutputBattle2().setEditable(false);
        getOutputBattle2().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputBattle2());
        battle.add(outputPanel, BorderLayout.NORTH);
        battle.add(getNextButton());
        return battle;
    }

    /**
     * food menu card
     * @return a JPanel card
     */
    public final JPanel foodMenu() {
        JPanel foodPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputFood(new JTextPane());
        outputPanel.setSize(600, 350);
        getOutputFood().setText("");
        getOutputFood().setEditable(false);
        getOutputFood().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputFood());
        outputPanel.setVisible(true);
        foodPanel.add(outputPanel, BorderLayout.NORTH);
        foodPanel.add(getFoodLabel());
        getFood()[0] = "Apple";
        getFood()[1] = "Steak";
        getFood()[2] = "Burger";
        getFood()[3] = "Cordyceps";
        getFood()[4] = "Mushroom";
        getFood()[5] = "Bitter Powder";
        setFoodBox(new JComboBox(getFood()));
        foodPanel.add(getFoodBox());
        foodPanel.add(getEatButton());
        foodPanel.add(getBackButton2());
        return foodPanel;
    }

    /**
     * current pet stats menu card
     * @return a JPanel card
     */
    public final JPanel statsMenu() {
        JPanel stats = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputStat(new JTextPane());
        outputPanel.setPreferredSize(new Dimension(600, 350));
        getOutputStat().setText("");
        getOutputStat().setEditable(false);
        getOutputStat().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputStat());
        outputPanel.setVisible(true);
        stats.add(outputPanel, BorderLayout.NORTH);
        stats.add(getBackButton());
        return stats;
    }

    /**
     * end menu card
     * @return a JPanel card
     */
    public final JPanel endMenu() {
        JPanel end = new JPanel();
        JPanel outputPanel = new JPanel();
        setOutputEnd(new JTextPane());
        outputPanel.setPreferredSize(new Dimension(600, 350));
        getOutputEnd().setText("");
        getOutputEnd().setEditable(false);
        getOutputEnd().setPreferredSize(new Dimension(600, 350));
        outputPanel.add(getOutputEnd());
        outputPanel.setVisible(true);
        end.add(outputPanel, BorderLayout.NORTH);
        end.add(getTryAgainButton());
        end.add(getQuitButton2());
        return end;
    }

    /**
     * Method that adds actionlisteners to buttons
     * @param listener 
     */
    public void addActionListener(ActionListener listener) {
        this.getNewGameButton().addActionListener(listener);
        this.getContinueButton().addActionListener(listener);
        this.getViewRecordsButton().addActionListener(listener);
        this.getQuitButton().addActionListener(listener);
        this.getQuitButton2().addActionListener(listener);
        this.getNameButton().addActionListener(listener);
        this.getBattleButton().addActionListener(listener);
        this.getFoodButton().addActionListener(listener);
        this.getViewStatsButton().addActionListener(listener);
        this.getMainMenuButton().addActionListener(listener);
        this.getMainMenuButton2().addActionListener(listener);
        this.getMainMenuButton3().addActionListener(listener);
        this.getFightButton().addActionListener(listener);
        this.getRunButton().addActionListener(listener);
        this.getEatButton().addActionListener(listener);
        this.getBackButton().addActionListener(listener);
        this.getBackButton2().addActionListener(listener);
        this.getTryAgainButton().addActionListener(listener);
        this.getNextButton().addActionListener(listener);
    }

    /**
     * method that validates currentpet table
     * @return 
     */
    public Boolean validateSave() {
        Data data = new Data();
        ResultSet rs = null;
        Boolean canContinue = null;
        Boolean valid = null;
        try {
            rs = data.getStatement().executeQuery("SELECT * FROM CURRENTPET");
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

    /**
     * method to validate resultset
     * @param rs
     * @return 
     */
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

    /**
     * Overrides update method
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        
    }

        /**
     * @return the card
     */
    public final CardLayout getCard() {
        return card;
    }

    /**
     * @return the pagePanel
     */
    public JPanel getPagePanel() {
        return pagePanel;
    }

    /**
     * @return the mainMenuPanel
     */
    public JPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    /**
     * @return the gameMenuPanel
     */
    public JPanel getGameMenuPanel() {
        return gameMenuPanel;
    }

    /**
     * @return the newGamePanel
     */
    public JPanel getNewGamePanel() {
        return newGamePanel;
    }

    /**
     * @return the recordsMenuPanel
     */
    public JPanel getRecordsMenuPanel() {
        return recordsMenuPanel;
    }

    /**
     * @return the battleMenuPanel
     */
    public JPanel getBattleMenuPanel() {
        return battleMenuPanel;
    }

    /**
     * @return the battleMenuPanel2
     */
    public JPanel getBattleMenuPanel2() {
        return battleMenuPanel2;
    }

    /**
     * @return the foodMenuPanel
     */
    public JPanel getFoodMenuPanel() {
        return foodMenuPanel;
    }

    /**
     * @return the statsMenuPanel
     */
    public JPanel getStatsMenuPanel() {
        return statsMenuPanel;
    }

    /**
     * @return the endMenuPanel
     */
    public JPanel getEndMenuPanel() {
        return endMenuPanel;
    }

    /**
     * @return the outputMain
     */
    public JTextPane getOutputMain() {
        return outputMain;
    }

    /**
     * @param outputMain the outputMain to set
     */
    public void setOutputMain(JTextPane outputMain) {
        this.outputMain = outputMain;
    }

    /**
     * @return the outputGame
     */
    public JTextPane getOutputGame() {
        return outputGame;
    }

    /**
     * @param outputGame the outputGame to set
     */
    public void setOutputGame(JTextPane outputGame) {
        this.outputGame = outputGame;
    }

    /**
     * @return the outputNew
     */
    public JTextPane getOutputNew() {
        return outputNew;
    }

    /**
     * @param outputNew the outputNew to set
     */
    public void setOutputNew(JTextPane outputNew) {
        this.outputNew = outputNew;
    }

    /**
     * @return the outputRecord
     */
    public JTextPane getOutputRecord() {
        return outputRecord;
    }

    /**
     * @param outputRecord the outputRecord to set
     */
    public void setOutputRecord(JTextPane outputRecord) {
        this.outputRecord = outputRecord;
    }

    /**
     * @return the outputBattle
     */
    public JTextPane getOutputBattle() {
        return outputBattle;
    }

    /**
     * @param outputBattle the outputBattle to set
     */
    public void setOutputBattle(JTextPane outputBattle) {
        this.outputBattle = outputBattle;
    }

    /**
     * @return the outputBattle2
     */
    public JTextPane getOutputBattle2() {
        return outputBattle2;
    }

    /**
     * @param outputBattle2 the outputBattle2 to set
     */
    public void setOutputBattle2(JTextPane outputBattle2) {
        this.outputBattle2 = outputBattle2;
    }

    /**
     * @return the outputFood
     */
    public JTextPane getOutputFood() {
        return outputFood;
    }

    /**
     * @param outputFood the outputFood to set
     */
    public void setOutputFood(JTextPane outputFood) {
        this.outputFood = outputFood;
    }

    /**
     * @return the outputStat
     */
    public JTextPane getOutputStat() {
        return outputStat;
    }

    /**
     * @param outputStat the outputStat to set
     */
    public void setOutputStat(JTextPane outputStat) {
        this.outputStat = outputStat;
    }

    /**
     * @return the outputEnd
     */
    public JTextPane getOutputEnd() {
        return outputEnd;
    }

    /**
     * @param outputEnd the outputEnd to set
     */
    public void setOutputEnd(JTextPane outputEnd) {
        this.outputEnd = outputEnd;
    }

    /**
     * @return the petName
     */
    public JLabel getPetName() {
        return petName;
    }

    /**
     * @return the nameInput
     */
    public JTextField getNameInput() {
        return nameInput;
    }

    /**
     * @param nameInput the nameInput to set
     */
    public void setNameInput(JTextField nameInput) {
        this.nameInput = nameInput;
    }

    /**
     * @return the nameButton
     */
    public JButton getNameButton() {
        return nameButton;
    }

    /**
     * @return the newGameButton
     */
    public JButton getNewGameButton() {
        return newGameButton;
    }

    /**
     * @return the continueButton
     */
    public JButton getContinueButton() {
        return continueButton;
    }

    /**
     * @return the viewRecordsButton
     */
    public JButton getViewRecordsButton() {
        return viewRecordsButton;
    }

    /**
     * @return the quitButton
     */
    public JButton getQuitButton() {
        return quitButton;
    }

    /**
     * @return the quitButton2
     */
    public JButton getQuitButton2() {
        return quitButton2;
    }

    /**
     * @return the battleButton
     */
    public JButton getBattleButton() {
        return battleButton;
    }

    /**
     * @return the fightButton
     */
    public JButton getFightButton() {
        return fightButton;
    }

    /**
     * @return the runButton
     */
    public JButton getRunButton() {
        return runButton;
    }

    /**
     * @return the foodButton
     */
    public JButton getFoodButton() {
        return foodButton;
    }

    /**
     * @return the eatButton
     */
    public JButton getEatButton() {
        return eatButton;
    }

    /**
     * @return the backButton
     */
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * @return the backButton2
     */
    public JButton getBackButton2() {
        return backButton2;
    }

    /**
     * @return the viewStatsButton
     */
    public JButton getViewStatsButton() {
        return viewStatsButton;
    }

    /**
     * @return the mainMenuButton
     */
    public JButton getMainMenuButton() {
        return mainMenuButton;
    }

    /**
     * @return the mainMenuButton2
     */
    public JButton getMainMenuButton2() {
        return mainMenuButton2;
    }

    /**
     * @return the mainMenuButton3
     */
    public JButton getMainMenuButton3() {
        return mainMenuButton3;
    }

    /**
     * @return the tryAgainButton
     */
    public JButton getTryAgainButton() {
        return tryAgainButton;
    }

    /**
     * @return the nextButton
     */
    public JButton getNextButton() {
        return nextButton;
    }

    /**
     * @return the foodLabel
     */
    public JLabel getFoodLabel() {
        return foodLabel;
    }

    /**
     * @return the food
     */
    public String[] getFood() {
        return food;
    }

    /**
     * @param food the food to set
     */
    public void setFood(String[] food) {
        this.food = food;
    }

    /**
     * @return the foodBox
     */
    public JComboBox getFoodBox() {
        return foodBox;
    }

    /**
     * @param foodBox the foodBox to set
     */
    public void setFoodBox(JComboBox foodBox) {
        this.foodBox = foodBox;
    }

    /**
     * @return the image
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
