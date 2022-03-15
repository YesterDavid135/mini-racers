package backend;

public class Car {
    private final Driver driver;
    private final int startPosition;
    private int position;
    private double laptime; //in seconds: 100.0 = 1:40.0
    private double laptimeReference;
    private double racetimeTotal;
    private double fuel; //fuel in L, max = 50.0L
    private double damage; //no damage = 0.0, destroyed = 1.0
    private double tyreCondition; //100% = 1.0, 0% = 0.0
    private double crashChance; //0% = 0, 100% = 100

    public Car(Driver driver, int startPosition, double laptimeReference, double racetimeTotal) {
        this.driver = driver;
        this.startPosition = startPosition;
        this.position = startPosition;
        this.laptime = 0;
        this.laptimeReference = laptimeReference;
        this.racetimeTotal = racetimeTotal;
        this.fuel = 50;
        this.damage = 0;
        this.tyreCondition = 1;
        this.crashChance = 0;
    }

    public void updatePosition(int position) {
        this.position = position;
    }

    public void updateLaptime(double weatherInfluence) {
        double staminaInfluence = (1 - driver.getStamina()) / 100;
        double skillInfluence = (1 - driver.getSkill()) / 100;
        double damageInluence = damage / 50;
        double tyreConditionInfluence = (1 - tyreCondition) / 50;
        double randomValue = Math.random() / 150;
        laptime = laptimeReference * (1 + staminaInfluence + skillInfluence + damageInluence + tyreConditionInfluence + randomValue) * weatherInfluence;
    }

    public void updateRacetimeTotal() {
        racetimeTotal += laptime;
    }

    public void updateFuel() {
        double updatedFuel = fuel - 1  - ((1 - driver.getSkill()) / 5);
        if (updatedFuel > 0) {
            fuel = updatedFuel;
        } else {
            fuel = 0;
        }
    }

    public void updateTyreCondition() {
        double updatedTyreCondition = tyreCondition - 0.03  - ((1 - driver.getSkill()) / 100);
        if (updatedTyreCondition > 0) {
            tyreCondition = updatedTyreCondition;
        } else {
            tyreCondition = 0;
        }
    }

    public void updateCrashChance(double riskMultiplier) {
        double staminaInfluence = (1 - driver.getStamina()) / 4; //up to 0.25% per lap
        double skillInfluence = (1 - driver.getSkill()) / 8; //up to 0.125% per lap
        double damageInluence = damage / 8; //up to 0.125% per lap
        double tyreConditionInfluence = (1 - tyreCondition) / 2; //up to 0.5% per lap
        crashChance = (staminaInfluence + skillInfluence + damageInluence + tyreConditionInfluence) * riskMultiplier;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getPosition() {
        return position;
    }

    public double getLaptime() {
        return laptime;
    }

    public double getRacetimeTotal() {
        return racetimeTotal;
    }

    public double getFuel() {
        return fuel;
    }

    public double getDamage() {
        return damage;
    }

    public double getTyreCondition() {
        return tyreCondition;
    }

    public double getCrashChance() {
        return crashChance;
    }
}
