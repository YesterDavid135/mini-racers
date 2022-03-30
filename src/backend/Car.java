package backend;

import backend.tyre.*;
import backend.weather.Weather;
import backend.weather.WeatherType;

public class Car {
    private final Driver driver;
    private final int startPosition;
    private int position;
    private double laptime; //in seconds: 100.0 = 1:40.0
    private double laptimeReference;
    private double racetimeTotal;
    private double fuel; //fuel in L, max = 50.0L
    private double damage; //no damage = 0.0, destroyed = 1.0
    private Tyre[] tyres;
    private double crashChance; //0% = 0, 100% = 100

    public Car(Driver driver, int startPosition, double laptimeReference, double racetimeTotal, Tyre[] tyres) {
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
    }

    public void updatePosition(int position) {
        this.position = position;
    }

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

    }

    public void updateRacetimeTotal() {
        racetimeTotal += laptime;
    }

    public void updateFuel() {
        double updatedFuel = fuel - 1 - ((1 - driver.getSkill()) / 5);
        if (updatedFuel > 0) {
            fuel = updatedFuel;
        } else {
            fuel = 0;
        }
    }

    public void updateTyres(double driverSkill) {
        for (Tyre tyre : tyres) {
            tyre.updateTyreCondition(driverSkill);
        }
    }

    public void updateCrashChance(Weather weather) {
        double staminaInfluence = (1 - driver.getStamina()) / 4; //up to 0.25% per lap
        double skillInfluence = (1 - driver.getSkill()) / 8; //up to 0.125% per lap
        double damageInluence = damage / 8; //up to 0.125% per lap
        double tyreConditionInfluence = (1 - getCombinedTyreCondition()) / 2; //up to 0.5% per lap
        double weatherRiskInfluence = weather.getRiskMultiplier();
        double wrongTyreInfluence = getWrongTyreRiskInfluence(weather);
        crashChance = (staminaInfluence + skillInfluence + damageInluence + tyreConditionInfluence) * weatherRiskInfluence * wrongTyreInfluence;
    }

    public double getCombinedTyreCondition() {
        double combinedTyreCondition = 0;
        for (Tyre tyre : tyres) {
            combinedTyreCondition += tyre.getTyreCondition();
        }
        return combinedTyreCondition / 4;
    }

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

    public void setLaptime(double laptime) {
        this.laptime = laptime;
    }

    public double getLaptimeReference() {
        return laptimeReference;
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

    public double getCrashChance() {
        return crashChance;
    }

    /**
     * Adds fuel to the car
     * max fuel is 50.0 L
     *
     * @param liter how much fuel to add
     */
    public void addFuel(double liter) {
        if (liter <= 0) return;
        fuel += liter;
        if (fuel > 50) fuel = 50;
    }

    public Tyre[] getTyres() {
        return tyres;
    }

    public void changeTyre(TyreType type) {
        for (int i = 0; i < tyres.length; i++) {
            switch (type) {
                case WET -> tyres[i] = new WetCompound();
                case HARD -> tyres[i] = new HardCompound();
                case SOFT -> tyres[i] = new SoftCompound();
            }
        }
    }
}
