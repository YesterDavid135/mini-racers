package backend;

import backend.tyre.Tyre;
import backend.weather.Weather;

public class Safetycar extends Car {
    private int lapsDeployedLeft;
    private double safetycarLaptimeMultiplier;

    public Safetycar(Driver driver, int startPosition, double laptimeReference, double racetimeTotal, Tyre[] tyres, double safetycarLaptimeMultiplier) {
        super(driver, startPosition, laptimeReference, racetimeTotal, tyres, false);
        this.lapsDeployedLeft = 0;
        this.safetycarLaptimeMultiplier = safetycarLaptimeMultiplier;
    }

    public void updateLaptime(Weather weather, boolean isSafetycarDeployed, double safetycarLaptimeMultiplier) {
        double randomValue = Math.random() / 150;
        setLaptime(getLaptimeReference() * (1 + randomValue) * safetycarLaptimeMultiplier);
    }

    public void deploySafetycar() {
        lapsDeployedLeft = (int) (Math.random() * (6 - 3)) + 3;
    }

    public void subtractLapsDeployedLeft() {
        lapsDeployedLeft--;
    }

    public int getLapsDeployedLeft() {
        return lapsDeployedLeft;
    }

    public double getSafetycarLaptimeMultiplier() {
        return safetycarLaptimeMultiplier;
    }
}
