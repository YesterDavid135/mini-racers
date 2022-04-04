package backend;

import backend.tyre.Tyre;
import backend.weather.Weather;

public class Safetycar extends Car {
    private int lapsDeployedLeft;
    private double safetycarLaptimeMultiplier;

    /**
     * Constructor of Safetycar
     *
     * @param driver                     driver, which drives the safetycar
     * @param startPosition              startposition of the car
     * @param laptimeReference           laptime, which is used as the "base-laptime" for calculations
     * @param racetimeTotal              total racetime in seconds
     * @param tyres                      tyres of the car
     * @param safetycarLaptimeMultiplier multiplier, which dictates how slow the safetycar is
     */
    public Safetycar(Driver driver, int startPosition, double laptimeReference, double racetimeTotal, Tyre[] tyres, double safetycarLaptimeMultiplier) {
        super(driver, startPosition, laptimeReference, racetimeTotal, tyres, false);
        this.lapsDeployedLeft = 0;
        this.safetycarLaptimeMultiplier = safetycarLaptimeMultiplier;
    }

    /**
     * calculate laptime of car
     *
     * @param weather                    weatherstate of the track
     * @param isSafetycarDeployed        is safetycar deployed in the current lap
     * @param safetycarLaptimeMultiplier multiplier, which dictates how slow the safetycar is
     */
    public void updateLaptime(Weather weather, boolean isSafetycarDeployed, double safetycarLaptimeMultiplier) {
        double randomValue = Math.random() / 150;
        setLaptime(getLaptimeReference() * (1 + randomValue) * safetycarLaptimeMultiplier);
    }

    /**
     * deploys safetycar to track
     */
    public void deploySafetycar() {
        lapsDeployedLeft = (int) (Math.random() * (6 - 3)) + 3;
    }

    /**
     * subtracts laps left for safetycar
     */
    public void subtractLapsDeployedLeft() {
        lapsDeployedLeft--;
    }

    /**
     * Getter for lapsDeployedLeft
     *
     * @return laps deployed left
     */
    public int getLapsDeployedLeft() {
        return lapsDeployedLeft;
    }

    /**
     * Getter for safetycarLaptimeMultiplier
     *
     * @return laptime multiplier for safetycar
     */
    public double getSafetycarLaptimeMultiplier() {
        return safetycarLaptimeMultiplier;
    }
}
