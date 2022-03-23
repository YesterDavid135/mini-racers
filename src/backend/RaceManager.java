package backend;

import data.DataManager;

import java.util.ArrayList;

public class RaceManager {
    private final DataManager dataManager = new DataManager();
    private final Race race;

    public RaceManager() {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        Safetycar safetycar = dataManager.generateSafetycar(track.getLaptimeRecord(), track.getWeather().getWeatherType());
        this.race = new Race(track, cars, safetycar);
    }

    public Race getRace() {
        return race;
    }

    public ArrayList<Car> getCrashedCarsThisRound(){
        return race.getCrashedCarsThisLap();
    }

    public int getLapsLeft(){
        return race.getLapsLeft();
    }
}
