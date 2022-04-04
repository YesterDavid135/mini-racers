package backend;

import backend.tyre.Tyre;
import backend.tyre.TyreType;
import data.DataManager;
import data.Difficulty;

import java.util.ArrayList;

public class RaceManager {
    private final DataManager dataManager = new DataManager();
    private final Race race;
    private final Car playerCar;

    /**
     * Constructor of RaceManager
     *
     * @param playerName       Name of Player
     * @param playerNumber     Number, which the Player chose
     * @param playerDifficulty Difficulty, which the Player chose
     */
    public RaceManager(String playerName, int playerNumber, Difficulty playerDifficulty) {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord(), track.getWeather().getWeatherType(), playerNumber);
        playerCar = dataManager.generatePlayerCar(track.getLaptimeRecord(), track.getWeather().getWeatherType(), playerName, playerNumber, playerDifficulty, cars);
        cars.add(playerCar);
        Safetycar safetycar = dataManager.generateSafetycar(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        this.race = new Race(track, cars, safetycar);
    }

    /**
     * Getter of race
     *
     * @return race object
     */
    public Race getRace() {
        return race;
    }

    /**
     * Getter of cars
     *
     * @return List of cars, which are in the race
     */
    public ArrayList<Car> getCrashedCarsThisRound() {
        return race.getCrashedCarsThisLap();
    }

    /**
     * Getter of lapsLeft
     *
     * @return laps left to drive in the race
     */
    public int getLapsLeft() {
        return race.getLapsLeft();
    }

    //Methods for ControlView

    /**
     * Refuels car
     *
     * @param liter amount of liters, which have to be refueled
     */
    public void refuelCar(double liter) {
        playerCar.refuel(liter);
    }

    /**
     * change tyres of the car
     *
     * @param tyre which tyre compound needs to get on the car
     */
    public void changeTyre(TyreType tyre) {
        playerCar.changeTyres(tyre);
    }

    /**
     * Getter of fuelLeft
     *
     * @return amount of liters left in the car
     */
    public double getFuelLeft() {
        return playerCar.getFuel();
    }

    /**
     * Getter of tyres
     *
     * @return list of all four tyres of the car
     */
    public Tyre[] getTyres() {
        return playerCar.getTyres();
    }

}
