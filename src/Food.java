/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  enum class to create different typesof foods as constants.
 *
 * @author Jennifer Ty 15903786
 */
public enum Food {

    APPLE(3, 5, 5, 5) {
        @Override
        public String toString() {
            return "an apple";
        }
    },
    STEAK(10, 10, 10, 10) {
        @Override
        public String toString() {
            return "a steak";
        }
    },
    BURGER(5, 20, 2, 2) {
        @Override
        public String toString() {
            return "a burger";
        }
    },
    CORDYCEPS(0, 20, 20, 50) {
        @Override
        public String toString() {
            return "some cordyceps";
        }
    },
    MUSHROOM(5, -20, -10, 0) {
        @Override
        public String toString() {
            return "some poisonous mushrooms";
        }
    },
    BITTERPOWDER(0, -20, 30, 0) {
        @Override
        public String toString(){
            return "bitter tasting powder";
        }
    };

    private final int satietyChange;
    private final int happyChange;
    private final int hpChange;
    private final int expChange;

    /**
     * Constructor for Food enum. Gives the constants certain values that can be
     * used to modify Pet objects.
     *
     * @param satietyChange used to change the satiety value of Pet object
     * @param happyChange used to change the happy value of Pet object
     * @param hpChange used to change the hp value of Pet object
     * @param expChange used to change the exp value of Pet object
     */
    private Food(int satietyChange, int happyChange, int hpChange, int expChange) {
        this.satietyChange = satietyChange;
        this.happyChange = happyChange;
        this.hpChange = hpChange;
        this.expChange = expChange;
    }

    /**
     * @return the satietyChange
     */
    public int getSatietyChange() {
        return satietyChange;
    }

    /**
     * @return the happyChange
     */
    public int getHappyChange() {
        return happyChange;
    }

    /**
     * @return the hpChange
     */
    public int getHpChange() {
        return hpChange;
    }

    /**
     * @return the expChange
     */
    public int getExpChange() {
        return expChange;
    }
}
