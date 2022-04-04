package backend;

import backend.tyre.*;
import backend.weather.Weather;
import backend.weather.WeatherType;

import java.util.ArrayList;

public class Car {
    private final Driver driver;
    private final boolean isPlayer;
    private final int startPosition;
    private int position;
    private double laptime; //in seconds: 100.0 = 1:40.0
    private double laptimeReference;
    private double racetimeTotal;
    private double fuel; //fuel in L, max = 50.0L
    private double damage; //no damage = 0.0, destroyed = 1.0
    private Tyre[] tyres;
    private double crashChance; //0% = 0, 100% = 100
    private double pitStopTime;
    private ArrayList<LogEntry> log = new ArrayList<>();
    private int currentLap;

    /**
     * Constructor of Car
     *
     * @param driver
     * @param startPosition
     * @param laptimeReference
     * @param racetimeTotal
     * @param tyres
     * @param isPlayer
     */
    public Car(Driver driver, int startPosition, double laptimeReference, double racetimeTotal, Tyre[] tyres, boolean isPlayer) {
        this.driver = driver;
        this.startPosition = startPosition;
        this.position = startPosition;
        this.laptime = 0;
        this.laptimeReference = laptimeReference;
        this.racetimeTotal = racetimeTotal;
        this.fuel = 50;
        this.damage = 0;
        this.tyres = tyres;
        this.crashChance = 0;
        this.isPlayer = isPlayer;
    }

    /**
     * update position of car
     *
     * @param position
     */
    public void updatePosition(int position) {
        this.position = position;
    }

    /**
     * calculate laptime
     *
     * @param weather                    weatherstate
     * @param isSafetycarDeployed        is safetycar deployed
     * @param safetycarLaptimeMultiplier multiplier, which dictates how slow the safetycar is
     * @param lastcar                    last car
     */
    public void updateLaptime(Weather weather, boolean isSafetycarDeployed, double safetycarLaptimeMultiplier, Car lastcar) {
        double staminaInfluence = (1 - driver.getStamina()) / 100;
        double skillInfluence = (1 - driver.getSkill()) / 100;
        double damageInluence = damage / 50;
        double tyreCompoundInfluence = tyres[0].getCompoundInfluence();
        double tyreConditionInfluence = (1 - getCombinedTyreCondition()) / 50;
        double randomValue = Math.random() / 150;
        double wrongTyreInfluence = getWrongTyreLaptimeInfluence(weather);

        if (isSafetycarDeployed) {
            laptime = laptimeReference * (1 + randomValue) * safetycarLaptimeMultiplier;
            if (lastcar != null && lastcar.position != 0) {
                double minLapTime = lastcar.racetimeTotal - racetimeTotal;
                double maxLapTime = lastcar.racetimeTotal + 0.2 - racetimeTotal;
                if (laptime < minLapTime)
                    laptime = minLapTime + randomValue;
                else if (laptime > maxLapTime)
                    laptime = maxLapTime - randomValue;

            }
        } else {
            laptime = laptimeReference * (1 + staminaInfluence + skillInfluence + damageInluence + tyreConditionInfluence + randomValue) * tyreCompoundInfluence * wrongTyreInfluence;
        }
        laptime += pitStopTime;
        pitStopTime = 0;
    }

    /**
     * update total racetime
     */
    public void updateRacetimeTotal() {
        racetimeTotal += laptime;
    }

    /**
     * calculate fuel
     */
    public void updateFuel() {
        fuel = fuel - 1 - ((1 - driver.getSkill()) / 5);
        if (fuel <= 0) fuel = 0;
        else if (fuel < 5 && !isPlayer) {
            double refuelChance = Math.random();
            if (refuelChance > 0.25) {
                log.add(new LogEntry("Refuel " , currentLap));
                refuel((Math.random() * 25) + 5);
            }
        }
    }

    /**
     * update tyres
     *
     * @param driverSkill skillvalue of driver
     * @param weather     weatherstate
     */
    public void updateTyres(double driverSkill, WeatherType weather) {
        for (Tyre tyre : tyres) {
            tyre.updateTyreCondition(driverSkill);
        }
        if (getCombinedTyreCondition() < 0.2 && !isPlayer) {
            double pitChance = Math.random();
            if (pitChance > 0.6) {
                double weatherDumbTyreChance = Math.random();
                log.add(new LogEntry("Tyrechange ", currentLap));

                if (weather == WeatherType.WET && weatherDumbTyreChance < 0.95) {
                    changeTyres(TyreType.WET);
                } else {
                    changeTyres(Math.random() >= 0.5 ? TyreType.HARD : TyreType.SOFT);
                }
            }
        }
    }

    /**
     * calculate crashchance
     *
     * @param weather weatherstate
     */
    public void updateCrashChance(Weather weather) {
        double staminaInfluence = (1 - driver.getStamina()) / 4; //up to 0.25% per lap
        double skillInfluence = (1 - driver.getSkill()) / 8; //up to 0.125% per lap
        double damageInluence = damage / 8; //up to 0.125% per lap
        double tyreConditionInfluence = getCombinedTyreCondition() == 0 ? 10 : (1 - getCombinedTyreCondition()) / 2; //up to 0.5% per lap or 10% if all tyres down
        double weatherRiskInfluence = weather.getRiskMultiplier();
        double wrongTyreInfluence = getWrongTyreRiskInfluence(weather);
        crashChance = (staminaInfluence + skillInfluence + damageInluence + tyreConditionInfluence) * weatherRiskInfluence * wrongTyreInfluence;
    }

    /**
     * combines all four tyrevalues into one
     *
     * @return combined tyreCondition
     */
    public double getCombinedTyreCondition() {
        double combinedTyreCondition = 0;
        for (Tyre tyre : tyres) {
            combinedTyreCondition += tyre.getTyreCondition();
        }
        return combinedTyreCondition / 4;
    }

    /**
     * get laptime influence of wrong wather-tyre combination
     *
     * @param weather weatherstate
     * @return wrongTyreInfluence
     */
    public double getWrongTyreLaptimeInfluence(Weather weather) {
        double randomValue = Math.random() / 4;
        double wrongTyreInfluence = 1;
        if (weather.getWeatherType() == WeatherType.WET) {
            if (tyres[0].getTyreType() != TyreType.WET) {
                wrongTyreInfluence = 1.3 + randomValue;
            }
        } else {
            if (tyres[0].getTyreType() == TyreType.WET) {
                wrongTyreInfluence = 1.5;
            }
        }
        return wrongTyreInfluence;
    }

    /**
     * get risk influence of wrong wather-tyre combination
     *
     * @param weather weatherstate
     * @return wrongTyreInfluence
     */
    public double getWrongTyreRiskInfluence(Weather weather) {
        double wrongTyreInfluence = 1;
        if (weather.getWeatherType() == WeatherType.WET) {
            if (tyres[0].getTyreType() != TyreType.WET) {
                wrongTyreInfluence = 3;
            }
        } else {
            if (tyres[0].getTyreType() == TyreType.WET) {
                wrongTyreInfluence = 1.5;
            }
        }
        return wrongTyreInfluence;
    }

    /**
     * Adds fuel to the car
     * max fuel is 50.0 L
     *
     * @param liter how much fuel to add
     */
    public void refuel(double liter) {
        if (liter <= 0) return;
        fuel += liter;
        if (fuel > 50) fuel = 50;
        calculateRefuelTime(liter);
    }

    /**
     * change tyres of car
     *
     * @param type tyre compound
     */
    public void changeTyres(TyreType type) {
        for (int i = 0; i < tyres.length; i++) {
            switch (type) {
                case WET -> tyres[i] = new WetCompound();
                case HARD -> tyres[i] = new HardCompound();
                case SOFT -> tyres[i] = new SoftCompound();
            }
        }
        calculateChangeTyresTime();
    }

    /**
     * calculate time needed to change tyres
     */
    private void calculateChangeTyresTime() {
        if (this.pitStopTime == 0) {
            this.pitStopTime = 20;
        }
        this.pitStopTime += 2 + (Math.random() * 2);
    }

    /**
     * calculate time needed to refuel
     *
     * @param liter amount of liters to refuel
     */
    private void calculateRefuelTime(double liter) {
        if (this.pitStopTime == 0) {
            this.pitStopTime = 20;
        }
        this.pitStopTime += liter / 2;
    }

    /**
     * Getter of driver
     *
     * @return driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Getter of startPosition
     *
     * @return start position
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Getter of position
     *
     * @return current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Getter of laptime
     *
     * @return current laptime
     */
    public double getLaptime() {
        return laptime;
    }

    /**
     * Setter of laptime
     */
    public void setLaptime(double laptime) {
        this.laptime = laptime;
    }

    /**
     * Getter of laptimeReference
     *
     * @return laptime reference
     */
    public double getLaptimeReference() {
        return laptimeReference;
    }

    /**
     * Getter of racetimeTotal
     *
     * @return total racetime in seconds
     */
    public double getRacetimeTotal() {
        return racetimeTotal;
    }

    /**
     * Getter of fuel
     *
     * @return amount of fuel in liters
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Getter of damage
     *
     * @return damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Getter of crashChance
     *
     * @return chance of crashing in current lap
     */
    public double getCrashChance() {
        return crashChance;
    }

    /**
     * Getter of tyres
     *
     * @return list of four tyres
     */
    public Tyre[] getTyres() {
        return tyres;
    }

    public void updateCurrentLap(int lap) {
        this.currentLap = lap;
    }

    public ArrayList<LogEntry> getLog() {
        return log;
    }
}
