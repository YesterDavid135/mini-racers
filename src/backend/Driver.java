package backend;

import data.Difficulty;

public class Driver {
    private final String name;
    private int number;
    private double skill; //pro = 1.0, beginner = 0.0
    private double stamina; //fit = 1.0, tired = 0.0

    /**
     * Constructor of Driver
     *
     * @param name       Name of Player
     * @param number     Number, which the Player chose
     * @param difficulty Difficulty, which the Player chose
     */
    public Driver(String name, int number, Difficulty difficulty) {
        this.name = name;
        this.number = number;
        this.stamina = 1;
        generateSkill(difficulty);
    }

    /**
     * generates random skilllevel for driver
     *
     * @param difficulty
     */
    public void generateSkill(Difficulty difficulty) {
        if (difficulty.equals(Difficulty.EASY)) {
            skill = (Math.random() / 3) + 0.66;
        } else if (difficulty.equals(Difficulty.INTERMEDIATE)) {
            skill = (Math.random() / 3) + 0.33;
        } else if (difficulty.equals(Difficulty.HARD)) {
            skill = Math.random() / 3;
        } else {
            skill = 0.01;
        }
    }

    /**
     * calculate new stamina value
     */
    public void updateStamina() {
        double updatedStamina = stamina - 0.01 - ((1 - skill) / 50);
        if (updatedStamina > 0) {
            stamina = updatedStamina;
        } else {
            stamina = 0;
        }
    }

    /**
     * Getter of name
     *
     * @return name of driver
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of number
     *
     * @return number of driver
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter of number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Getter of skill
     *
     * @return skill of driver
     */
    public double getSkill() {
        return skill;
    }

    /**
     * Getter of stamina
     *
     * @return stamina of driver
     */
    public double getStamina() {
        return stamina;
    }
}
