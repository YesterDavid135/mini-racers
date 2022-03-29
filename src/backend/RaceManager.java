package backend;

import backend.tyre.SoftCompound;
import backend.tyre.Tyre;
import backend.tyre.TyreType;
import data.DataManager;
import data.Difficulty;

import java.util.ArrayList;

public class RaceManager {
    private final DataManager dataManager = new DataManager();
    private final Race race;

    public RaceManager(String playerName, int playerNumber, Difficulty playerDifficulty) {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        Car playerCar = dataManager.generatePlayerCar(track.getLaptimeRecord(), track.getWeather().getWeatherType(), playerName, playerNumber, playerDifficulty, cars);
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
        return Math.random() * 50; //todo
    }

    public void refuelCar(double liter) {
        System.out.println("Refueled Car to " + liter + "Liter"); //todo
    }

    public void changeTyre(TyreType tyre) {
        System.out.println("Changed Tyres to " + tyre); //todo
    }

    public Tyre[] getTyres() {
        Tyre[] tyres = new Tyre[4]; //todo

        for (int i = 0; i < tyres.length; i++) {
            tyres[i] = new SoftCompound();
            tyres[i].setTyreCondition(Math.random());
        }

        return tyres;
    }

}
