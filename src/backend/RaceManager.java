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

    public RaceManager(String playerName, int playerNumber, Difficulty playerDifficulty) {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord(), track.getWeather().getWeatherType(), playerNumber);
        playerCar = dataManager.generatePlayerCar(track.getLaptimeRecord(), track.getWeather().getWeatherType(), playerName, playerNumber, playerDifficulty, cars);
        cars.add(playerCar);
        Safetycar safetycar = dataManager.generateSafetycar(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        this.race = new Race(track, cars, safetycar);
    }

    public Race getRace() {
        return race;
    }

    public ArrayList<Car> getCrashedCarsThisRound() {
        return race.getCrashedCarsThisLap();
    }

    public int getLapsLeft() {
        return race.getLapsLeft();
    }

    //Methods for ControlView

    public double getFuelLeft() {
        return playerCar.getFuel();
    }

    public void refuelCar(double liter) {
        playerCar.refuel(liter);
    }

    public void changeTyre(TyreType tyre) {
        playerCar.changeTyres(tyre);
    }

    public Tyre[] getTyres() {
        return playerCar.getTyres();
    }

}
