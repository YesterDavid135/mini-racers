package backend;

import data.Difficulty;

public class Driver {
    private final String name;
    private final int number;
    private double skill; //pro = 1.0, beginner = 0.0
    private double stamina; //fit = 1.0, tired = 0.0

    public Driver(String name, int number, Difficulty difficulty) {
        this.name = name;
        this.number = number;
        this.stamina = 1;
        generateSkill(difficulty);
    }

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

    public void updateStamina() {
        double updatedStamina = stamina - 0.01 - ((1 - skill) / 50);
        if (updatedStamina > 0) {
            stamina = updatedStamina;
        } else {
            stamina = 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getSkill() {
        return skill;
    }

    public double getStamina() {
        return stamina;
    }
}
