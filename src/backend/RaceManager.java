package backend;

import backend.tyre.SoftCompound;
import backend.tyre.Tyre;
import backend.tyre.TyreType;
import data.DataManager;
import data.DiscordWebhook;

import java.util.ArrayList;

public class RaceManager {
    private final DataManager dataManager = new DataManager();
    private final Race race;

    public RaceManager() {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        Safetycar safetycar = dataManager.generateSafetycar(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        this.race = new Race(track, cars, safetycar);
        new DiscordWebhook();
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
