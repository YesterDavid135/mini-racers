package backend;

public class Driver {
    private final String name;
    private final int number;
    private double skill; //pro = 1.0, beginner = 0.0
    private double stamina; //fit = 1.0, tired = 0.0

    public Driver(String name, int number) {
        this.name = name;
        this.number = number;
        this.stamina = 1;
        generateSkill();
    }

    public void generateSkill() {
        skill = Math.random();
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
